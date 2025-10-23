package erp.report.trade.overview.service;

import erp.report.trade.overview.dto.internal.TradeOverviewMonthlyAmountRow;
import erp.report.trade.overview.dto.request.TradeOverviewReportFindRequest;
import erp.report.trade.overview.dto.response.TradeOverviewReportResponse;
import erp.report.trade.overview.dto.response.TradeOverviewReportResponse.MonthlyRow;
import erp.report.trade.overview.dto.response.TradeOverviewReportResponse.Range;
import erp.report.trade.overview.dto.response.TradeOverviewReportResponse.SeriesPoint;
import erp.report.trade.overview.dto.response.TradeOverviewReportResponse.Summary;
import erp.report.trade.overview.mapper.TradeOverviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TradeOverviewServiceImpl implements TradeOverviewService {

    private final TradeOverviewMapper tradeOverviewMapper;

    @Override
    @Transactional(readOnly = true)
    public TradeOverviewReportResponse findTradeOverview(
            TradeOverviewReportFindRequest request
    ) {
        int year = request.year();
        int fromMonth = request.fromMonth();
        int toMonth = request.toMonth();

        YearMonth startYm = YearMonth.of(year, fromMonth);
        YearMonth endYm = YearMonth.of(year, toMonth);
        LocalDate startDate = startYm.atDay(1);
        LocalDate endDate = endYm.atEndOfMonth();

        // 올해라면 오늘까지만, 과거/다른 해는 요청 그대로
        LocalDate today = LocalDate.now();
        int curYear = today.getYear();
        LocalDate effectiveEndDate = (year == curYear && endDate.isAfter(today)) ? today : endDate;

        // 관측된 마지막 달
        int observedToMonth = (year == curYear)
                ? Math.min(toMonth, today.getMonthValue())
                : toMonth;

        // 1) 월별 합계 조회(미래 달 제외된 effectiveEndDate 사용)
        List<TradeOverviewMonthlyAmountRow> monthlyRows =
                tradeOverviewMapper.findAllMonthlyOrderPurchaseAmountRow(
                        startDate, effectiveEndDate);

        Map<Integer, Integer> monthToOrderAmount = new HashMap<>();
        Map<Integer, Integer> monthToPurchaseAmount = new HashMap<>();
        for (TradeOverviewMonthlyAmountRow row : monthlyRows) {
            monthToOrderAmount.put(row.month(), defaultZero(row.orderAmount()));
            monthToPurchaseAmount.put(row.month(), defaultZero(row.purchaseAmount()));
        }

        // 2) 전년도 동기간: '관측된 개월 수'까지만 비교
        int prevYear = year - 1;
        YearMonth prevStartYm = YearMonth.of(prevYear, fromMonth);
        YearMonth prevEndYm = YearMonth.of(prevYear, observedToMonth);
        LocalDate prevStartDate = prevStartYm.atDay(1);
        LocalDate prevEndDate = prevEndYm.atEndOfMonth();

        Integer prevOrderTotal = defaultZero(
                tradeOverviewMapper.sumOrderAmountByPeriod(prevStartDate, prevEndDate));
        Integer prevPurchaseTotal = defaultZero(
                tradeOverviewMapper.sumPurchaseAmountByPeriod(prevStartDate, prevEndDate));

        // 3) 응답 조립(미래 달은 값 null로)
        List<SeriesPoint> series = new ArrayList<>();
        List<MonthlyRow> monthlyDetails = new ArrayList<>();
        int orderTotal = 0, purchaseTotal = 0;
        Integer previousMonthOrderAmount = null;

        for (int m = fromMonth; m <= toMonth; m++) {
            boolean observed = (m <= observedToMonth);

            int rawOrder = monthToOrderAmount.getOrDefault(m, 0);
            int rawPurchase = monthToPurchaseAmount.getOrDefault(m, 0);

            Integer orderAmount = observed ? rawOrder : null;
            Integer purchaseAmount = observed ? rawPurchase : null;

            Double profitRate = null;
            Double orderMoMChangeRate = null;

            if (observed) {
                orderTotal = Math.addExact(orderTotal, rawOrder);
                purchaseTotal = Math.addExact(purchaseTotal, rawPurchase);

                profitRate = computeRatePercent(rawOrder - rawPurchase, rawOrder);
                orderMoMChangeRate = computeRatePercent(
                        (previousMonthOrderAmount == null) ? null : rawOrder - previousMonthOrderAmount,
                        previousMonthOrderAmount
                );
                previousMonthOrderAmount = rawOrder;
            }

            series.add(new SeriesPoint(m, orderAmount, purchaseAmount));
            monthlyDetails.add(new MonthlyRow(m, orderAmount, purchaseAmount, profitRate, orderMoMChangeRate));
        }

        int grossProfitAmount = Math.subtractExact(orderTotal, purchaseTotal);
        Double grossProfitRate = computeRatePercent(grossProfitAmount, orderTotal);
        Double yoyOrderChangeRate = computeRatePercent(orderTotal - prevOrderTotal, prevOrderTotal);
        Double yoyPurchaseChangeRate = computeRatePercent(purchaseTotal - prevPurchaseTotal, prevPurchaseTotal);

        Summary summary = new Summary(
                orderTotal, purchaseTotal, grossProfitAmount,
                grossProfitRate, yoyOrderChangeRate, yoyPurchaseChangeRate
        );

        Range range = new Range(year, fromMonth, toMonth, observedToMonth);

        return new TradeOverviewReportResponse(summary, series, monthlyDetails, range);
    }

    private static Integer defaultZero(Integer value) {
        return value == null ? 0 : value;
    }

    /**
     * (분자/분모)*100 계산. 분모가 없거나 0이면 null 반환.
     * 결과는 소수 첫째 자리에서 반올림
     */
    private static Double computeRatePercent(Integer numerator, Integer denominator) {
        if (numerator == null || denominator == null) return null;
        if (denominator == 0) return null;
        double value = ((double) numerator / (double) denominator) * 100.0;
        return Math.round(value * 10.0) / 10.0;
    }


}
