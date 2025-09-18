package erp.log.domain;

import erp.log.enums.LogType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Log {
    private Long logId;
    private Long companyId;
    private LogType type;         // ACCESS | WORK | LOGIN | ERROR
    private boolean success;     // true -> 'Y', false -> 'N'
    private String employeeUuid;
    private String employeeName;
    private String message;      // 목록 한 줄 요약(<=500)
    private String ipAddress;
    private String requestId;
    private String payloadJson;  // CLOB(JSON 문자열)
    private LocalDateTime createdAt;

    public static Log register(
            Long logId,
            LogType type,
            boolean success,
            String employeeUuid, // 로그인 같은 경우는 null 가능
            String employeeName, // 로그인 같은 경우는 null 가능
            String message,
            String ipAddress, // 내부 호출이나 비 http 요청 같은 경우는 null 가능
            String requestId, // 내부 호출이나 비 http 요청 같은 경우는 null 가능
            String payloadJson // CLOB(JSON 문자열, 4000자 넘어갈 수 있음, null 가능)
    ) {
        return Log.builder()
                .logId(logId)
                .type(type)
                .success(success)
                .employeeUuid(employeeUuid)
                .employeeName(employeeName)
                .message(message)
                .ipAddress(ipAddress)
                .requestId(requestId)
                .payloadJson(payloadJson)
                .build();
    }
}
