package erp.purchase.dto.internal;

import jakarta.validation.constraints.NotNull;

public record PurchaseItemQuantityRow(
        @NotNull
        Long purchaseItemId,
        @NotNull
        Long itemId,
        @NotNull
        Integer quantity
) {
}
