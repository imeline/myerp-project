package erp.purchase.dto.internal;

import jakarta.validation.constraints.NotNull;

public record PurchaseItemDetailRow(
        @NotNull
        String name,
        @NotNull
        String code,
        @NotNull
        Integer quantity,
        @NotNull
        String unit,
        @NotNull
        Integer unitPrice,
        @NotNull
        Integer subtotal
) {
}
