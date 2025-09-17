package erp.global.exception;

import erp.global.response.ApiResponse;
import erp.log.dto.request.LogSaveRequest;
import erp.log.enums.LogType;
import erp.log.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static erp.global.context.AuditContext.isBusinessFailureAudited;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
// mvc controller error 처리 + ERROR 감사 로그 기록
public class GlobalExceptionHandler {

    private final LogService logService;

    // 왜 default로 prod 프로필을 사용하지 않는지?
    // 로컬에서 prod로 연결되어 버리면 실제 운영 DB에 연결될 수 있기 때문
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    /**
     * 비즈니스 예외 처리
     */
    protected ApiResponse<?> handleGlobalException(GlobalException ex, HttpServletRequest request) {
        log.warn("biz error: {}, url: {}, msg: {}", ex.getStatus(), request.getRequestURI(), ex.getMessage());

        if (shouldAuditAsError(ex.getStatus())) {
            writeErrorLog(
                    "business-exception",
                    request,
                    Map.of("code", ex.getStatus().name(), "message", ex.getMessage()),
                    ex
            );
        }
        return ApiResponse.onFailure(ex.getStatus(), ex.getMessage());
    }

    /**
     * @Valid @RequestBody 검증 실패
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("400 BadRequest(MethodArgumentNotValid) url: {}, detail: {}", request.getRequestURI(), message);

        return ApiResponse.onFailure(ErrorStatus.BAD_REQUEST, message);
    }

    /**
     * @Valid @ModelAttribute, @RequestParam 검증 실패
     */
    @ExceptionHandler(BindException.class)
    public ApiResponse<?> handleBindException(BindException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("400 BadRequest(BindException) url: {}, detail: {}", request.getRequestURI(), message);
        return ApiResponse.onFailure(ErrorStatus.BAD_REQUEST, message);
    }

    /**
     * JSON 파싱 실패 / 요청 본문 읽기 실패
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<?> handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("400 BadRequest(NotReadable) url: {}, detail: {}", request.getRequestURI(), ex.getMessage());
        return ApiResponse.onFailure(ErrorStatus.NOT_READABLE);
    }

    /**
     * 예상치 못한 모든 런타임 예외 처리 (내부 메시지 숨김)
     */
    @ExceptionHandler(RuntimeException.class)
    protected ApiResponse<?> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        if (ex instanceof GlobalException ge) {
            return handleGlobalException(ge, request);
        }
        log.error("500 InternalServerError url: {}, detail: ", request.getRequestURI(), ex);

        // ERROR 감사 로그 (성공=false)
        writeErrorLog(
                "unhandled-exception",
                request,
                Map.of(
                        "error", ex.getClass().getSimpleName(),
                        "message", ex.getMessage()
                ),
                ex
        );

        boolean isProd = "prod".equalsIgnoreCase(activeProfile);
        return isProd
                ? ApiResponse.onFailure(ErrorStatus.INTERNAL_SERVER_ERROR)
                : ApiResponse.onFailure(ErrorStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private void writeErrorLog(String message, HttpServletRequest request, Map<String, Object> extra, Throwable ex) {
        try {
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("path", request.getRequestURI());
            payload.put("method", request.getMethod());
            if (request.getQueryString() != null) {
                payload.put("query", request.getQueryString());
            }
            if (extra != null && !extra.isEmpty()) {
                payload.putAll(extra);
            }
            payload.put("exception", ex.getClass().getName());

            // LogServiceImpl이 RequestContext로 ip/requestId를 채워주므로
            // 여기선 단순 메시지/페이로드만 전달
            logService.save(new LogSaveRequest(
                    LogType.ERROR,
                    false,
                    message,
                    payload
            ));
        } catch (Exception loggingEx) {
            // 감사로그 실패로 사용자 응답이 영향을 받지 않도록 방어
            log.warn("failed to write error audit log: {}", loggingEx.toString());
        }
    }

    private boolean shouldAuditAsError(ErrorStatus errorStatus) {
        if (isBusinessFailureAudited()) return false;
        if (errorStatus == null) return true;
        return switch (errorStatus) {
            // 서버/환경 문제 → DB 감사로그 남김
            case INTERNAL_SERVER_ERROR, NULL_TENANT_ID -> true;

            // 클라이언트/권한 문제 → DB 감사로그 남기지 않음 (애플리케이션 로그만)
            case BAD_REQUEST, NOT_READABLE, UNAUTHORIZED, FORBIDDEN,
                 NOT_FOUND -> false;
            default -> false;
        };
    }
}
