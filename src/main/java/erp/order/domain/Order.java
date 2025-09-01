package erp.order.domain;


import erp.global.base.TimeStamped;
import erp.global.enums.FulfillmentStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends TimeStamped {
    private Long orderId;
    private String code;
    private String customer;
    private LocalDate orderDate;
    private FulfillmentStatus status;
    private long employeeId;
    private long companyId;
}
