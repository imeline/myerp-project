package erp.purchase.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PurchaseItemSaveRequest(
        @NotNull
        Long itemId,
        @NotNull
        @PositiveOrZero
        Integer quantity
) {
}
