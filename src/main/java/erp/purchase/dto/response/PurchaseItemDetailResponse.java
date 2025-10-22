package erp.purchase.dto.response;

import erp.purchase.dto.internal.PurchaseItemDetailRow;
import lombok.Builder;

@Builder
public record PurchaseItemDetailResponse(
        String name,
        String code,
        Integer quantity,
        String unit,
        Integer unitPrice,
        Integer subtotal
) {
    public static PurchaseItemDetailResponse from(PurchaseItemDetailRow row) {
        return PurchaseItemDetailResponse.builder()
                .name(row.name())
                .code(row.code())
                .quantity(row.quantity())
                .unit(row.unit())
                .unitPrice(row.unitPrice())
                .subtotal(row.subtotal())
                .build();
    }
}
