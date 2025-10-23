package erp.stock.service;

import erp.global.response.PageResponse;
import erp.stock.dto.request.StockFindAllRequest;
import erp.stock.dto.request.StockMovementFindRequest;
import erp.stock.dto.response.StockFindAllResponse;
import erp.stock.dto.response.StockMovementFindAllResponse;
import erp.stock.dto.response.StockPriceFindResponse;
import erp.stock.dto.response.StockSummaryFindResponse;

public interface StockService {

    long saveStock(long itemId, int initialQuantity, String warehouse);

    PageResponse<StockFindAllResponse> findAllStock(StockFindAllRequest request);

    StockPriceFindResponse findStockPrice(long itemId);

    void updateStockWarehouse(long itemId, String warehouse);

    void increaseOnHand(long itemId, int delta);

    void decreaseOnHand(long itemId, int delta);

    void increaseAllocatedIfEnoughOnHand(long itemId, int quantity);

    void decreaseAllocated(long itemId, int quantity);

    StockMovementFindAllResponse findAllMovement(
            long itemId,
            StockMovementFindRequest request
    );

    StockSummaryFindResponse findSummary();
}
