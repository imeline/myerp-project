package erp.order.dto.response;

import erp.order.dto.internal.OrderFindRow;
import erp.order.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record OrderFindAllResponse(
        Long orderId,
        LocalDate orderDate,
        String customer,
        Integer itemCount,
        Integer totalAmount,
        OrderStatus status,
        String employeeName
) {
    public static OrderFindAllResponse from(OrderFindRow row) {
        return OrderFindAllResponse.builder()
                .orderId(row.orderId())
                .orderDate(row.orderDate())
                .customer(row.customer())
                .itemCount(row.itemCount())
                .totalAmount(row.totalAmount())
                .status(row.status())
                .employeeName(row.employeeName())
                .build();
    }
}
