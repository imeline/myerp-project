package erp.purchase.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseItemSaveRequest(
        @NotNull
        Long itemId,
        @NotNull
        @Positive
        Integer quantity
) {
}
