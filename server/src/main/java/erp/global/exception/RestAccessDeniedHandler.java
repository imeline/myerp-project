package erp.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import erp.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
// 스프링 시큐리티에서 인증된 사용자가 권한이 없는 리소스에 접근할 때 호출되는 핸들러 (403 에러)
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex)
            throws IOException {
        log.warn("403 Forbidden - uri: {}, reason: {}", request.getRequestURI(), ex.getMessage());

        ApiResponse<?> body = ApiResponse.onFailure(ErrorStatus.FORBIDDEN, ex.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
