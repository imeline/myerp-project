package erp.stock.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockSummaryRow(
        @NotNull
        @PositiveOrZero
        Integer totalItemCount,

        @NotNull
        @PositiveOrZero
        Integer confirmedPurchaseCount,

        @NotNull
        @PositiveOrZero
        Integer zeroAvailableItemCount,

        @NotNull
        @PositiveOrZero
        Integer totalInventoryValue
) {
}
