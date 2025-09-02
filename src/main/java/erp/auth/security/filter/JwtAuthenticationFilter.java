package erp.auth.security.filter;

import erp.auth.security.jwt.JwtTokenProvider;
import erp.auth.security.model.UserPrincipal;
import erp.global.tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
/** Authorization 헤더의 JWT를 검증하고, UserPrincipal를 생성해 SecurityContext에 저장 */
// OncePerRequestFilter → 모든 요청마다 단 한 번만 실행되는 필터로 만든다는 의미
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        Long tenantIdToUse; // 이번 요청에서 사용할 tenantId

        try {
            final String authHeader = request.getHeader("Authorization");
            // 아래 조건을 만족하지 않으면, 인증 로직 패스함 (다음 필터로 넘김)
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                // 다음 필터로 넘기거나(체인 계속) 마지막이면 서블릿/컨트롤러로 진입시키는 호출
                // JwtAuthenticationFilter 는 패스, soft-pass 전략(다음 어떤 필터가 401 응답)
                filterChain.doFilter(request, response);
                return;
            }
            final String header = authHeader.trim();
            final String BEARER = "Bearer ";
            if (!header.regionMatches(true, 0, BEARER, 0, BEARER.length())) {
                filterChain.doFilter(request, response);
                return;
            }
            final String jwt = header.substring(BEARER.length()).trim();

            // jwt 서명/만료 검증
            if (!jwtTokenProvider.validateToken(jwt)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 현재 사용자 인증 정보가 없을 경우에만 인증 시도
            // (현재 사용 기기에서 이미 로그인 중, 중복 인증 방지)
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // 최초 인증 생성 - 토큰 클레임으로 Principal 구성
                final String uuid = jwtTokenProvider.extractUuid(jwt);
                final String role = jwtTokenProvider.extractRole(jwt);
                final Long tenantId = jwtTokenProvider.extractTenantId(jwt);

                UserPrincipal principal = UserPrincipal.of(
                        uuid, role, null, tenantId
                );

                // 시큐리티에서 사용할 인증 객체 생성
                // 두 번째 파라미터는 credentials인데, JWT 기반 인증에서는 필요 없으므로 null
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                principal, null, principal.getAuthorities()
                        );

                // authenticationToken 에 인증 정보 설정
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 현재 요청의 SecurityContext에 인증 객체 등록
                // 이걸로 시큐리티가 이 스레드 안에선 "이 요청은 인증된 사용자야"라고 인식
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // 여기서 테넌트 컨텍스트 세팅 (최초 인증일 때)
                tenantIdToUse = tenantId;
            } else {
                // 이미 인증된 요청(필터 체인 재진입 등) → Principal에서 바로 세팅
                var auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth.getPrincipal() instanceof UserPrincipal up) {
                    tenantIdToUse = up.getTenantId();
                } else {
                    // fallback: principal이 UserPrincipal이 아닐 경우 토큰에서 추출
                    // tenantId 이 지역 변수라 사용 못하고 다시 추출
                    tenantIdToUse = jwtTokenProvider.extractTenantId(jwt);
                }
            }

            // tenant_id를 JWT 필터 성공 시점에서 세팅
            if (tenantIdToUse != null) {
                TenantContext.set(tenantIdToUse);
            }

            // 다음 필터로 요청을 넘김
            // doFilter 안에서 컨트롤러 → 서비스 → 매퍼 → SQL 실행이 모두 일어남
            filterChain.doFilter(request, response);

        } finally {
            // ThreadLocal은 요청 단위 컨텍스트 저장소로 쓰지만, 스레드가 재사용되므로 반드시 해제 필요
            // 해제를 안 하면 다른 사용자의 요청에 값이 섞여 보안 이슈 생김
            // 클리어 - ThreadLocal 누수 방지
            TenantContext.clear();
        }
    }
}