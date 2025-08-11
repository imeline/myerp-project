package erp.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

    // 공통
    INTERNAL_SERVER_ERROR("SERVER500", "Internal server error"),
    BAD_REQUEST("REQUEST400", "Bad request"),

    // AUTH
    EXIST_LOGIN_EMAIL("AUTH101", "기존 사용중인 이메일입니다."),
    INVALID_LOGIN_CREDENTIALS("AUTH102", "아이디 또는 비밀번호가 일치하지 않습니다."),
    ;

    private final String status;
    private final String message;
}
