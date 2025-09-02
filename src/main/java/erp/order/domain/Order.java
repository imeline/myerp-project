package erp.order.domain;


import erp.global.base.TimeStamped;
import erp.global.shared.enums.FulfillmentStatus;
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
    private Integer totalQuantity;
    private Integer totalAmount;
    private FulfillmentStatus status;
    private long employeeId;
    private long companyId;
}
