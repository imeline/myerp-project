package erp.auth.security.filter;

import erp.auth.security.jwt.JwtTokenProvider;
import erp.auth.security.model.UserPrincipal;
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
            final String name = jwtTokenProvider.extractName(jwt);
            final Long tenantId = jwtTokenProvider.extractTenantId(jwt); // 힌트(검증은 다음 필터)

            UserPrincipal principal = UserPrincipal.of(
                    uuid, role, name, null, tenantId
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
        }

        // 다음 필터로 요청을 넘김
        filterChain.doFilter(request, response);
    }
}
