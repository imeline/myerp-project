package erp.stock.service;

import erp.global.response.PageResponse;
import erp.stock.dto.request.StockFindAllRequest;
import erp.stock.dto.request.StockMovementFindRequest;
import erp.stock.dto.response.StockFindAllResponse;
import erp.stock.dto.response.StockMovementFindAllResponse;
import erp.stock.dto.response.StockPriceFindResponse;
import erp.stock.dto.response.StockSummaryFindResponse;

public interface StockService {

    long saveStock(long itemId, int initialQuantity, String warehouse,
                   long tenantId);

    PageResponse<StockFindAllResponse> findAllStock(
            StockFindAllRequest request,
            long tenantId
    );

    StockPriceFindResponse findStockPrice(long itemId, long tenantId);

    void updateStockWarehouse(long itemId, String warehouse, long tenantId);

    void increaseOnHand(long itemId, int delta, long tenantId);

    void decreaseOnHand(long itemId, int delta, long tenantId);

    void increaseAllocatedIfEnoughOnHand(long tenantId, long itemId, int quantity);

    void decreaseAllocated(long tenantId, long itemId, int quantity);

    StockMovementFindAllResponse findAllMovement(
            long itemId,
            StockMovementFindRequest request,
            long tenantId
    );

    StockSummaryFindResponse findSummary(long tenantId);
}
