package erp.stock.service;

import erp.global.response.PageResponse;
import erp.stock.dto.request.StockFindAllRequest;
import erp.stock.dto.response.StockFindAllResponse;
import erp.stock.dto.response.StockPriceFindResponse;

public interface StockService {

    long saveStock(long itemId, int initialQuantity, String warehouse,
                   long tenantId);

    PageResponse<StockFindAllResponse> findAllStock(
            StockFindAllRequest request,
            long tenantId
    );

    StockPriceFindResponse findStockPrice(long itemId, long tenantId);

    void increaseOnHandQuantity(long itemId, int delta, long tenantId);
}
