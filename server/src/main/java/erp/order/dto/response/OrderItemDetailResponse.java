package erp.order.dto.response;

import erp.order.dto.internal.OrderItemDetailRow;
import lombok.Builder;

@Builder
public record OrderItemDetailResponse(
        String name,
        String code,
        String unit,
        Integer quantity,
        Integer unitPrice,
        Integer subtotal
) {
    public static OrderItemDetailResponse from(OrderItemDetailRow row) {
        return OrderItemDetailResponse.builder()
                .name(row.name())
                .code(row.code())
                .unit(row.unit())
                .quantity(row.quantity())
                .unitPrice(row.unitPrice())
                .subtotal(row.subtotal())
                .build();
    }
}
