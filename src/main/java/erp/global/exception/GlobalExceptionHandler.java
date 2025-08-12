package erp.global.exception;

import erp.global.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
// mvc controller error 처리
public class GlobalExceptionHandler {

    // 왜 default로 prod 프로필을 사용하지 않는지?
    // 로컬에서 prod로 연결되어 버리면 실제 운영 DB에 연결될 수 있기 때문
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(GlobalException.class)
    protected BaseResponse<?> handleGlobalException(GlobalException ex, HttpServletRequest request) {
        log.error("errorStatus: {}, url: {}, message: {}", ex.getStatus(), request.getRequestURI(), ex.getMessage());
        return BaseResponse.onFailure(ex.getStatus(), ex.getMessage());
    }

    /**
     * @Valid @RequestBody 검증 실패
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("400 BadRequest(MethodArgumentNotValid) url: {}, detail: {}", request.getRequestURI(), ex.getMessage());
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return BaseResponse.onFailure(ErrorStatus.BAD_REQUEST, message);
    }

    /**
     * @Valid @ModelAttribute, @RequestParam 검증 실패
     */
    @ExceptionHandler(BindException.class)
    public BaseResponse<?> handleBindException(BindException ex, HttpServletRequest request) {
        log.warn("400 BadRequest(BindException) url: {}, detail: {}", request.getRequestURI(), ex.getMessage());
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return BaseResponse.onFailure(ErrorStatus.BAD_REQUEST, message);
    }

    /**
     * JSON 파싱 실패 / 요청 본문 읽기 실패
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<?> handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("400 BadRequest(NotReadable) url: {}, detail: {}", request.getRequestURI(), ex.getMessage());
        return BaseResponse.onFailure(ErrorStatus.NOT_READABLE);
    }

    /**
     * 예상치 못한 모든 런타임 예외 처리 (내부 메시지 숨김)
     */
    @ExceptionHandler(RuntimeException.class)
    protected BaseResponse<?> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("500 InternalServerError url: {}, detail: ", request.getRequestURI(), ex);
        // prod 환경에서는 상세 메시지 숨김
        boolean isProd = "prod".equalsIgnoreCase(activeProfile);
        return isProd
                ? BaseResponse.onFailure(ErrorStatus.INTERNAL_SERVER_ERROR)
                : BaseResponse.onFailure(ErrorStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
