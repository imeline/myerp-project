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
    private int totalQuantity;
    private int totalAmount;
    private OrderStatus status;
    private long employeeId;
    private long companyId;

    public static Order register(long orderId, String code, String customer,
                                 LocalDate orderDate, int totalQuantity, int totalAmount,
                                 long employeeId, long companyId) {
        return Order.builder()
                .orderId(orderId)
                .code(code)
                .customer(customer)
                .orderDate(orderDate)
                .totalQuantity(totalQuantity)
                .totalAmount(totalAmount)
                .status(OrderStatus.CONFIRMED)
                .employeeId(employeeId)
                .companyId(companyId)
                .build();
    }
}
