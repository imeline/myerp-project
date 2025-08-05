package erp.company.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Company extends TimeStamped {
    private Long id;
    private String name;
    private String bizNo;
    private String address;
    private String phone;
}
