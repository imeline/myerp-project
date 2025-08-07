package erp.auth.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErpAccount extends TimeStamped {
    private Long erpAccountId;
    private String loginEmail;
    private String password;
    private String uuid;
    private String role;
    private Long employeeId;
}
