package erp.stock.dto.internal;

import erp.item.enums.ItemCategory;
import erp.stock.enums.StockSortBy;
import lombok.Builder;

@Builder
public record StockFindFilterRow(
        String itemName,
        ItemCategory itemCategory,
        String warehouseName,
        Integer availableQuantityFrom,
        Integer availableQuantityTo,
        StockSortBy sortBy,
        String sortDirection,
        int offset,
        int size
) {

    public static StockFindFilterRow of(
            String itemName,
            ItemCategory itemCategory,
            String warehouseName,
            Integer availableQuantityFrom,
            Integer availableQuantityTo,
            StockSortBy sortBy,
            String sortDirection,
            int offset,
            int size
    ) {
        return StockFindFilterRow.builder()
                .itemName(itemName)
                .itemCategory(itemCategory)
                .warehouseName(warehouseName)
                .availableQuantityFrom(availableQuantityFrom)
                .availableQuantityTo(availableQuantityTo)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .offset(offset)
                .size(size)
                .build();
    }
}
