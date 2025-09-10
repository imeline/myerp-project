package erp.stock.dto.response;

import erp.stock.dto.internal.StockFindAllRow;
import lombok.Builder;

@Builder
public record StockFindAllResponse(Long itemId,
                                   String itemName,
                                   String itemCode,
                                   Integer availableQuantity,
                                   Integer onHandQuantity,
                                   Integer allocatedQuantity,
                                   String unit,
                                   String warehouse,
                                   Integer unitPrice,
                                   Integer inventoryValue
) {
    public static StockFindAllResponse from(StockFindAllRow row) {
        return StockFindAllResponse.builder()
                .itemId(row.itemId())
                .itemName(row.itemName())
                .itemCode(row.itemCode())
                .availableQuantity(row.availableQuantity())
                .onHandQuantity(row.onHandQuantity())
                .allocatedQuantity(row.allocatedQuantity())
                .unit(row.unit())
                .warehouse(row.warehouse())
                .unitPrice(row.unitPrice())
                .inventoryValue(row.inventoryValue())
                .build();
    }
}
