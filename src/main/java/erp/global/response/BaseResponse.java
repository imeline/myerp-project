package erp.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import erp.global.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private Boolean isSuccess;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> BaseResponse<T> onSuccess(T data) {
        return new BaseResponse<>(true, String.valueOf(HttpStatus.OK.value()), null,
                data);
    }

    // 커스텀 에러 처리
    public static <T> BaseResponse<T> onFailure(ErrorStatus status) {
        return new BaseResponse<>(false, status.getStatus(), status.getMessage(), null);
    }

    // RestControllerAdvice 에러 처리
    public static <T> BaseResponse<T> onFailure(ErrorStatus status, String message) {
        return new BaseResponse<>(false, status.getStatus(), message, null);
    }
}
