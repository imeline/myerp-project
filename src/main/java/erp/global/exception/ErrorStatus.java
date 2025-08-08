package erp.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

    INTERNAL_SERVER_ERROR("SERVER500", "Internal server error"),
    BAD_REQUEST("REQUEST400", "Bad request");

    private final String status;
    private final String message;
}
