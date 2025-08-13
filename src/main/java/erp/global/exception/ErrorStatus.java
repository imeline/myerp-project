package erp.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

    // 공통
    BAD_REQUEST("BAD_REQUEST_400", "잘못된 요청입니다."),
    NOT_READABLE("NOT_READABLE_400", "요청 본문을 해석할 수 없습니다."),
    UNAUTHORIZED("UNAUTHORIZED_401", "인증이 필요합니다."),
    FORBIDDEN("FORBIDDEN_403", "접근 권한이 없습니다."),
    NOT_FOUND("NOT_FOUND_404", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR_500",
            "서버 내부 오류가 발생했습니다."),

    // AUTH
    EXIST_LOGIN_EMAIL("AUTH101", "기존 사용중인 이메일입니다."),
    INVALID_LOGIN_CREDENTIALS("AUTH102", "아이디 또는 비밀번호가 일치하지 않습니다.");

    private final String status;
    private final String message;
}
