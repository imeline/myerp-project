package erp.log.dto.request;

import erp.log.enums.LogType;

public record LogSaveRequest(
        LogType type,                // ACCESS | WORK | LOGIN | ERROR
        boolean success,            // true=Y, false=N
        String message,             // 목록 한 줄 요약(<=500)
        Object payload         // JSON 문자열(CLOB)        // 상세 JSON(CLOB)
) {
}
