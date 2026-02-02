package erp.purchase.dto.response;

import erp.purchase.dto.internal.PurchaseItemStockFindRow;
import lombok.Builder;

@Builder
public record PurchaseItemStockFindResponse(
    String code,
    String name,
    Integer quantity,
    Integer unitPrice,
    Integer subtotal,
    Integer availableStock,
    Integer onHandStock,
    Integer allocatedStock,
    Integer availableStockAfterInbound
) {

    public static PurchaseItemStockFindResponse from(PurchaseItemStockFindRow row) {
        return PurchaseItemStockFindResponse.builder()
            .code(row.code())
            .name(row.name())
            .quantity(row.quantity())
            .unitPrice(row.unitPrice())
            .subtotal(row.subtotal())
            .availableStock(row.availableStock())
            .onHandStock(row.onHandStock())
            .allocatedStock(row.allocatedStock())
            .availableStockAfterInbound(row.availableStockAfterInbound())
            .build();
    }
}
