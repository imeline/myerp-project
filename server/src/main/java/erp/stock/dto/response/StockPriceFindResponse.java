package erp.stock.dto.response;

import erp.stock.dto.internal.StockPriceFindRow;
import lombok.Builder;

@Builder
public record StockPriceFindResponse(
        Integer onHandQuantity,
        Integer allocatedQuantity,
        Integer availableQuantity,
        Integer unitPrice
) {

    public static StockPriceFindResponse from(
            StockPriceFindRow row
    ) {
        return StockPriceFindResponse.builder()
                .onHandQuantity(row.onHandQuantity())
                .allocatedQuantity(row.allocatedQuantity())
                .availableQuantity(row.availableQuantity())
                .unitPrice(row.unitPrice())
                .build();
    }
}
