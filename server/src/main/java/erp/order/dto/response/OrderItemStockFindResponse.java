package erp.order.dto.response;

import erp.order.dto.internal.OrderItemStockFindRow;
import lombok.Builder;

@Builder
public record OrderItemStockFindResponse(
        String code,
        String name,
        Integer quantity,
        Integer unitPrice,
        Integer subtotal,
        Integer availableQuantity,
        Integer onHandQuantity,
        Integer allocatedQuantity,
        Integer availableStockAfterOutbound
) {
    public static OrderItemStockFindResponse from(OrderItemStockFindRow row) {
        return OrderItemStockFindResponse.builder()
                .code(row.code())
                .name(row.name())
                .quantity(row.quantity())
                .unitPrice(row.unitPrice())
                .subtotal(row.subtotal())
                .availableQuantity(row.availableQuantity())
                .onHandQuantity(row.onHandQuantity())
                .allocatedQuantity(row.allocatedQuantity())
                .availableStockAfterOutbound(row.availableStockAfterOutbound())
                .build();
    }
}
