package erp.user.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErpAccount extends TimeStamped {
    private Long accountId;
    private String loginEmail;
    private String password;
    private String uuid;
    private String role;
    private Long employeeId;
}
