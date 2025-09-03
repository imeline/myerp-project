package erp.purchase.dto.internal;

import jakarta.validation.constraints.NotNull;

public record Totals(
        @NotNull
        Integer totalQuantity,
        @NotNull
        Integer totalAmount
) {
}
