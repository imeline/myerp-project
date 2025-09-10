package erp.report.stock.overview.controller;

import erp.global.response.ApiResponse;
import erp.global.tenant.TenantContext;
import erp.report.stock.overview.dto.response.StockOverviewFindResponse;
import erp.report.stock.overview.service.StockOverviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/report/stock/overview")
public class StockOverviewController {

    private final StockOverviewServiceImpl stockOverviewService;

    @GetMapping
    public ApiResponse<StockOverviewFindResponse> findOverview() {
        long tenantId = TenantContext.get();
        return ApiResponse.onSuccess(stockOverviewService.findOverview(tenantId));
    }
}
