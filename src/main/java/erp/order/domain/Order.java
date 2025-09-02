package erp.order.domain;


import erp.global.base.TimeStamped;
import erp.order.enums.OrderStatus;
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
    private OrderStatus status;
    private long employeeId;
    private long companyId;
}
