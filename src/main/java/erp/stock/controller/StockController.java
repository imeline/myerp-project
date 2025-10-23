package erp.stock.controller;

import erp.global.response.ApiResponse;
import erp.global.response.PageResponse;
import erp.stock.dto.request.StockFindAllRequest;
import erp.stock.dto.request.StockMovementFindRequest;
import erp.stock.dto.response.StockFindAllResponse;
import erp.stock.dto.response.StockMovementFindAllResponse;
import erp.stock.dto.response.StockPriceFindResponse;
import erp.stock.dto.response.StockSummaryFindResponse;
import erp.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/stock")
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ApiResponse<PageResponse<StockFindAllResponse>> findAllStocks(
            @Valid @RequestBody StockFindAllRequest request) {
        return ApiResponse.onSuccess(stockService.findAllStock(request));
    }

    @GetMapping("/{itemId}")
    public ApiResponse<StockPriceFindResponse> findStockPrice(
            @PathVariable long itemId) {

        return ApiResponse.onSuccess(stockService.findStockPrice(itemId));
    }

    @GetMapping("{itemId}/movement")
    public ApiResponse<StockMovementFindAllResponse> findMovements(
            @PathVariable long itemId,
            @Valid @RequestBody StockMovementFindRequest request) {
        return ApiResponse.onSuccess(stockService.findAllMovement(itemId, request));
    }

    @GetMapping("/summary")
    public ApiResponse<StockSummaryFindResponse> findSummary() {
        return ApiResponse.onSuccess(stockService.findSummary());
    }

}
