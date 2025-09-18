package erp.auth.security.filter;

import erp.global.context.AuditContext;
import erp.global.context.RequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static erp.auth.security.filter.AccessLogFilter.clientIp;

/**
 * 모든 요청에 상관관계 ID(X-Request-Id)를 부여/전파한다.
 * 응답 헤더에도 동일 값을 실어 프론트·게이트웨이·서버 로그 간 추적을 가능하게 한다.
 */
@Component
public class RequestIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String requestId = Optional.ofNullable(request.getHeader("X-Request-Id"))
                .filter(s -> !s.isBlank())
                .orElse(UUID.randomUUID().toString());

        MDC.put("requestId", requestId);
        response.setHeader("X-Request-Id", requestId);

        String ip = clientIp(request);
        try {
            RequestContext.set(
                    requestId, ip, request.getMethod(), request.getRequestURI(), request.getHeader("User-Agent")
            );
            chain.doFilter(request, response);
        } finally {
            AuditContext.clear();
            RequestContext.clear();
            MDC.remove("requestId");
        }
    }
}