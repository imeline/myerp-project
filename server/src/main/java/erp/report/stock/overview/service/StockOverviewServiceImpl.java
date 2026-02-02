package erp.report.stock.overview.service;

import erp.report.stock.overview.dto.internal.StockOverviewItemRow;
import erp.report.stock.overview.dto.request.StockOverviewFindRequest;
import erp.report.stock.overview.dto.response.StockOverviewResponse;
import erp.report.stock.overview.dto.response.StockOverviewResponse.ItemDetailRow;
import erp.report.stock.overview.dto.response.StockOverviewResponse.Range;
import erp.report.stock.overview.dto.response.StockOverviewResponse.Summary;
import erp.report.stock.overview.mapper.StockOverviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockOverviewServiceImpl implements StockOverviewService {

    private final StockOverviewMapper stockOverviewMapper;

    @Override
    @Transactional(readOnly = true)
    public StockOverviewResponse findStockOverview(StockOverviewFindRequest request, long tenantId) {

        int year = request.year();
        int month = request.month();

        YearMonth ym = YearMonth.of(year, month);
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atEndOfMonth();

        // 전년 동월
        int prevYear = year - 1;
        YearMonth prevYm = YearMonth.of(prevYear, month);
        LocalDate prevStartDate = prevYm.atDay(1);
        LocalDate prevEndDate = prevYm.atEndOfMonth();

        // 1) 품목별 집계(입고/출고/현재재고)
        List<StockOverviewItemRow> rows =
                stockOverviewMapper.findAllItemRowByMonth(tenantId, startDate, endDate);

        int totalInboundQuantity = 0;
        int totalOutboundQuantity = 0;
        int totalOnHandQuantity = 0;

        List<ItemDetailRow> details = new ArrayList<>(rows.size());
        for (StockOverviewItemRow row : rows) {
            int inbound = row.inboundQuantity();
            int outbound = row.outboundQuantity();
            int onHand = row.onHandQuantity();

            totalInboundQuantity = Math.addExact(totalInboundQuantity, inbound);
            totalOutboundQuantity = Math.addExact(totalOutboundQuantity, outbound);
            totalOnHandQuantity = Math.addExact(totalOnHandQuantity, onHand);

            details.add(new ItemDetailRow(
                    row.itemId(),
                    row.name(),
                    inbound,
                    outbound,
                    onHand,
                    row.unit()
            ));
        }

        // 3) 전년 동월 합계(입/출고)
        Integer prevInboundTotal = defaultZero(
                stockOverviewMapper.sumInboundQuantityByMonth(tenantId, prevStartDate, prevEndDate));
        Integer prevOutboundTotal = defaultZero(
                stockOverviewMapper.sumOutboundQuantityByMonth(tenantId, prevStartDate, prevEndDate));

        // 4) YoY 계산
        Double yoyInboundChangeRate = computeRatePercent(totalInboundQuantity - prevInboundTotal, prevInboundTotal);
        Double yoyOutboundChangeRate = computeRatePercent(totalOutboundQuantity - prevOutboundTotal, prevOutboundTotal);

        // 5) 요약 + 범위 + 응답 조립
        Summary summary = new Summary(
                totalInboundQuantity,
                totalOutboundQuantity,
                totalOnHandQuantity,
                yoyInboundChangeRate,
                yoyOutboundChangeRate,
                details.size()
        );

        Range range = new Range(year, month);

        return new StockOverviewResponse(
                summary,
                details,
                range
        );
    }

    private static Integer defaultZero(Integer value) {
        return value == null ? 0 : value;
    }

    /**
     * (분자/분모)*100, 분모가 0이거나 null이면 null 반환.
     * 소수점 첫째 자리에서 반올림.
     */
    private static Double computeRatePercent(Integer numerator, Integer denominator) {
        if (numerator == null || denominator == null) return null;
        if (denominator == 0) return null;
        double value = ((double) numerator / (double) denominator) * 100.0;
        return Math.round(value * 10.0) / 10.0;
    }
}