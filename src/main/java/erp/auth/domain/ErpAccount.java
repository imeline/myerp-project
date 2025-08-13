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
    private Long employeeId;
    private String loginEmail;
    private String password;
    private String uuid;
    private ErpAccountRole role;

    public static ErpAccount register(Long erpAccountId, String loginEmail,
                                      String hashPassword,
                                      ErpAccountRole role, Long employeeId) {

        String newUuid = UUID.randomUUID().toString()
                .replace("-", "");

        return ErpAccount.builder()
                .erpAccountId(erpAccountId)
                .loginEmail(loginEmail)
                .password(hashPassword)
                .uuid(newUuid)
                .role(role)
                .employeeId(employeeId)
                .build();
    }
}
