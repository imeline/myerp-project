package erp.order.domain;


import erp.global.base.TimeStamped;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends TimeStamped {
    private Long orderId;
    private Long employeeId;
    private String customer;
    private String code;
    private LocalDate orderDate;
}
