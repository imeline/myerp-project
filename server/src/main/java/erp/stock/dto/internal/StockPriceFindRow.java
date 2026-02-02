package erp.stock.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockPriceFindRow(
        @NotNull
        @PositiveOrZero
        Integer onHandQuantity,

        @NotNull
        @PositiveOrZero
        Integer allocatedQuantity,

        @NotNull
        @PositiveOrZero
        Integer availableQuantity,

        @NotNull
        @PositiveOrZero
        Integer unitPrice
) {
}
