package erp.report.stock.movement.service;

import erp.global.util.PageParam;
import erp.global.util.Strings;
import erp.global.util.time.DateRange;
import erp.global.util.time.Periods;
import erp.report.stock.movement.dto.internal.StockMovementFindRow;
import erp.report.stock.movement.dto.internal.StockMovementSearchFilter;
import erp.report.stock.movement.dto.internal.StockMovementSummaryRow;
import erp.report.stock.movement.dto.request.StockMovementFindRequest;
import erp.report.stock.movement.dto.response.StockMovementFindAllResponse;
import erp.report.stock.movement.mapper.StockMovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementMapper stockMovementMapper;

    /**
     * 재고 변동 내역 조회 (팝업)
     * <p>
     * - 기간/상태/코드 필터 적용
     * - 요약(onHand)은 현재 재고 기준, 집계(입고/출고 건수)는 필터 기준
     */
    public StockMovementFindAllResponse findAllMovement(
            long itemId,
            StockMovementFindRequest request,
            long tenantId
    ) {
        DateRange dateRange = Periods.resolve(request.period(), LocalDate.now());
        String normalizedCode = Strings.normalizeOrNull(request.code());
        PageParam pageParameter = PageParam.of(request.page(), request.size(), 20);

        StockMovementSearchFilter filter = StockMovementSearchFilter.of(
                itemId,
                dateRange.startDate(),
                dateRange.endDate(),
                normalizedCode,
                request.status(),
                pageParameter
        );

        StockMovementSummaryRow summaryFromDatabase =
                stockMovementMapper.findMovementSummary(tenantId, filter);
        StockMovementSummaryRow summary = StockMovementSummaryRow.from(summaryFromDatabase);


        List<StockMovementFindRow> rows = stockMovementMapper.findMovementRows(tenantId, filter);
        long totalCount = stockMovementMapper.countMovementRows(tenantId, filter);

        return StockMovementFindAllResponse.of(
                summary,
                rows,
                filter.page(),
                totalCount,
                filter.size()
        );
    }

}
