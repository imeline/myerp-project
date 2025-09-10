package erp.report.stock.overview.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockOverviewRow(
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
