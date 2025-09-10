package erp.stock.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockFindAllRow(
        @NotNull
        Long itemId,
        @NotNull
        String itemName,
        @NotNull
        String itemCode,
        @NotNull
        @PositiveOrZero
        Integer availableQuantity,
        @NotNull
        @PositiveOrZero
        Integer onHandQuantity,
        @NotNull
        @PositiveOrZero
        Integer allocatedQuantity,
        @NotNull
        String unit,
        @NotNull
        String warehouse,
        @NotNull
        @PositiveOrZero
        Integer unitPrice,
        @NotNull
        @PositiveOrZero
        Integer inventoryValue
) {
}
