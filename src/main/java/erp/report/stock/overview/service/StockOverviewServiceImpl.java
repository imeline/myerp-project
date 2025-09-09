package erp.report.stock.overview.service;

import erp.report.stock.overview.dto.internal.StockOverviewRow;
import erp.report.stock.overview.dto.response.StockOverviewFindResponse;
import erp.report.stock.overview.mapper.StockOverviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockOverviewServiceImpl implements StockOverviewService {

    private final StockOverviewMapper stockOverviewMapper;

    public StockOverviewFindResponse findOverview(long tenantId) {
        StockOverviewRow row = stockOverviewMapper.findStockOverviewRow(tenantId);
        return StockOverviewFindResponse.from(row);
    }

}
