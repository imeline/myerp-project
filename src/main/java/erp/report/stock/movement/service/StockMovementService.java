package erp.report.stock.movement.service;

import erp.report.stock.movement.dto.request.StockMovementFindRequest;
import erp.report.stock.movement.dto.response.StockMovementFindAllResponse;

public interface StockMovementService {
    StockMovementFindAllResponse findAllMovement(
            long itemId,
            StockMovementFindRequest request,
            long tenantId
    );
}
