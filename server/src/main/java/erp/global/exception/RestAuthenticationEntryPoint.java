package erp.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import erp.global.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


// 401 error handler
@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {

        ApiResponse<?> body;
        Throwable cause = authException.getCause();

        if (cause instanceof ExpiredJwtException) {
            body = ApiResponse.onFailure(ErrorStatus.EXPIRED_ACCESS_TOKEN);
        } else if (cause instanceof JwtException || cause instanceof IllegalArgumentException) {
            body = ApiResponse.onFailure(ErrorStatus.INVALID_TOKEN);
        } else {
            body = ApiResponse.onFailure(ErrorStatus.UNAUTHORIZED);
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
