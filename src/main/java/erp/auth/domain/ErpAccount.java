package erp.auth.domain;

import erp.auth.enums.ErpAccountRole;
import erp.global.base.TimeStamped;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErpAccount extends TimeStamped {
    private Long erpAccountId;
    private long employeeId; // 인덱스 필요 시 추가
    private String loginEmail; // 인덱스 설정
    private String password;
    private String uuid;
    private ErpAccountRole role;
    private long companyId;

    public static ErpAccount register(Long erpAccountId, long employeeId,
                                      String loginEmail, String hashPassword,
                                      ErpAccountRole role, long companyId) {

        String newUuid = UUID.randomUUID().toString()
                .replace("-", "");

        return ErpAccount.builder()
                .erpAccountId(erpAccountId)
                .employeeId(employeeId)
                .loginEmail(loginEmail)
                .password(hashPassword)
                .uuid(newUuid)
                .role(role)
                .companyId(companyId)
                .build();
    }
}
