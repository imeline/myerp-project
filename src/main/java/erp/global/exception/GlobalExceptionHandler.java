package erp.global.exception;

import erp.global.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    protected BaseResponse<?> baseError(GlobalException ex, HttpServletRequest request) {
        log.error("errorStatus: {}, url: {}, message: {}", ex.getStatus(), request.getRequestURI(),
                ex.getMessage());
        return BaseResponse.onFailure(ex.getStatus(), ex.getMessage());
    }

    // RuntimeException의 경우 일반적인 에러 메시지를 사용하여 보안 유지해야 함
    @ExceptionHandler(RuntimeException.class)
    protected BaseResponse<?> runtimeError(RuntimeException ex, HttpServletRequest request) {
        log.error("url: {}, message: {}", request.getRequestURI(), ex.getMessage());
        return BaseResponse.onFailure(ErrorStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> processValidationError(MethodArgumentNotValidException ex,
                                                  HttpServletRequest request) {
        log.error("url: {}, message: {}", request.getRequestURI(), ex.getMessage());

        StringBuilder builder = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            builder.append(fieldError.getDefaultMessage());
            builder.append(" / ");
        });

        return BaseResponse.onFailure(ErrorStatus.BAD_REQUEST, builder.toString());
    }
}
