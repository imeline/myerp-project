package erp.purchase.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseItemFindRow(
        @NotNull
        String code,

        @NotNull
        String name,

        @NotNull
        @Positive
        Integer quantity,

        @NotNull
        @Positive
        Integer unitPrice,

        @NotNull
        @Positive
        Integer subtotal   // quantity * unitPrice : 소계
) {
}
