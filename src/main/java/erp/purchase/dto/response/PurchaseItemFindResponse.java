package erp.purchase.dto.response;

import erp.purchase.dto.internal.PurchaseItemFindRow;
import lombok.Builder;

@Builder
public record PurchaseItemFindResponse(
        String code,
        String name,
        Integer quantity,
        Integer unitPrice,
        Integer subtotal
) {
    public static PurchaseItemFindResponse from(PurchaseItemFindRow row) {
        return PurchaseItemFindResponse.builder()
                .code(row.code())
                .name(row.name())
                .quantity(row.quantity())
                .unitPrice(row.unitPrice())
                .subtotal(row.subtotal())
                .build();
    }
}
