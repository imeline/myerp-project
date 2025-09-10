package erp.report.stock.movement.controller;

import erp.global.response.ApiResponse;
import erp.global.tenant.TenantContext;
import erp.report.stock.movement.dto.request.StockMovementFindRequest;
import erp.report.stock.movement.dto.response.StockMovementFindAllResponse;
import erp.report.stock.movement.service.StockMovementServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/report/stock/{itemId}/movement")
public class StockMovementController {

    private final StockMovementServiceImpl stockMovementService;

    @GetMapping
    public ApiResponse<StockMovementFindAllResponse> findMovements(
            @PathVariable long itemId,
            @Valid @RequestBody StockMovementFindRequest request
    ) {
        long tenantId = TenantContext.get();
        return ApiResponse.onSuccess(stockMovementService.findAllMovement(itemId, request, tenantId));
    }
}
