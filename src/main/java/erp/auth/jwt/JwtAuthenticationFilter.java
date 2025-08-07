package erp.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
// OncePerRequestFilter → 모든 요청마다 단 한 번만 실행되는 필터로 만든다는 의미
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userUuid;

        // 아래 조건을 만족하지 않으면, 인증 로직 패스함 (다음 필터로 넘김)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userUuid = jwtTokenProvider.extractUuid(jwt);

        // 현재 사용자 인증 정보가 없을 경우에만 인증 시도
        // (현재 사용 기기에서 이미 로그인 중, 중복 인증 방지)
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // DB 등에서 해당 userUuid로 사용자 정보 로드
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userUuid);
            // 시큐리티에서 사용할 인증 객체 생성
            //두 번째 파라미터는 credentials인데, JWT 기반 인증에서는 필요 없으므로 null
            if (jwtTokenProvider.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // authenticationToken 에 인증 정보 설정
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 현재 요청의 SecurityContext에 인증 객체 등록
                // 이걸로 시큐리티가 이 스레드 안에선 "이 요청은 인증된 사용자야"라고 인식
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // 다음 필터로 요청을 넘김
        filterChain.doFilter(request, response);

    }
}
