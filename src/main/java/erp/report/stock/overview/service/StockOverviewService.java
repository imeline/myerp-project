package erp.report.stock.overview.service;

import erp.report.stock.overview.dto.request.StockOverviewFindRequest;
import erp.report.stock.overview.dto.response.StockOverviewResponse;

public interface StockOverviewService {
    StockOverviewResponse findStockOverview(StockOverviewFindRequest request);
}
