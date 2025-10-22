package erp.report.trade.overview.service;

import erp.report.trade.overview.dto.request.TradeOverviewReportFindRequest;
import erp.report.trade.overview.dto.response.TradeOverviewReportResponse;

public interface TradeOverviewService {

    TradeOverviewReportResponse findTradeOverview(
            TradeOverviewReportFindRequest request, long tenantId);
}
