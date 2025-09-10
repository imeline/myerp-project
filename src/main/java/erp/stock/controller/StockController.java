package erp.stock.controller;

import erp.global.response.ApiResponse;
import erp.global.response.PageResponse;
import erp.global.tenant.TenantContext;
import erp.stock.dto.request.StockFindAllRequest;
import erp.stock.dto.response.StockFindAllResponse;
import erp.stock.dto.response.StockPriceFindResponse;
import erp.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/stock")
public class StockController {

    private final StockService stockService;

    /**
     * 재고 목록 조회
     */
    @GetMapping
    public ApiResponse<PageResponse<StockFindAllResponse>> findAllStocks(
            @Valid @RequestBody StockFindAllRequest request
    ) {
        long tenantId = TenantContext.get();
        PageResponse<StockFindAllResponse> response =
                stockService.findAllStock(request, tenantId);
        return ApiResponse.onSuccess(response);
    }

    /**
     * 단일 품목 재고/단가 조회
     */
    @GetMapping("/{itemId}")
    public ApiResponse<StockPriceFindResponse> findStockPrice(
            @PathVariable long itemId
    ) {
        long tenantId = TenantContext.get();
        StockPriceFindResponse response = stockService.findStockPrice(itemId, tenantId);
        return ApiResponse.onSuccess(response);
    }

}
