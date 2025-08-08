package erp.global.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final ErrorStatus status;

    public GlobalException(ErrorStatus status, String message) {
        super(message);
        this.status = status;
    }
}
