package erp.auth.security.filter;

import erp.log.dto.request.LogSaveRequest;
import erp.log.enums.LogType;
import erp.log.service.LogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HTTP 접근 정보를 자동 수집(메서드·URI·상태·처리시간·IP·UA).
 * 로그인(/api/v1/auth/**) 경로는 ACCESS 로그를 남기지 않는다(로그인은 LOGIN 로그만).
 */
@Component
@RequiredArgsConstructor
public class AccessLogFilter extends OncePerRequestFilter {

    private final LogService logService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/actuator/health")
                || uri.startsWith("/swagger-ui")
                || uri.startsWith("/v3/api-docs")
                || uri.startsWith("/public/")
                || uri.startsWith("/api/v1/auth"); // 로그인 관련은 ACCESS 로그 스킵
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        long startNs = System.nanoTime();
        try {
            chain.doFilter(request, response);
        } finally {
            long elapsedMs = (System.nanoTime() - startNs) / 1_000_000;

            String requestId = MDC.get("requestId");
            String ip = clientIp(request);
            String ua = request.getHeader("User-Agent");
            String method = request.getMethod();
            String path = request.getRequestURI();
            String query = request.getQueryString();
            int status = response.getStatus();

            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("method", method);
            payload.put("path", path);
            payload.put("query", query);
            payload.put("status", status);
            payload.put("elapsedMs", elapsedMs);
            payload.put("ip", ip);
            payload.put("userAgent", ua);

            // 로깅 실패가 응답에 영향 주지 않도록 보호
            try {
                logService.save(
                        new LogSaveRequest(
                                LogType.ACCESS,
                                status < 400,
                                "access: " + method + " " + path,
                                payload
                        )
                );
            } catch (Exception ex) {
                // 필요 시 로그 레벨 조정 가능
                org.slf4j.LoggerFactory.getLogger(getClass())
                        .warn("Access logging failed: {}", ex.toString());
            }
        }
    }

    protected static String clientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        String xri = request.getHeader("X-Real-IP");
        if (xri != null && !xri.isBlank()) {
            return xri.trim();
        }
        return request.getRemoteAddr();
    }
}
