package erp.report.trade.overview.dto.response;


import lombok.Builder;

import java.util.List;

/**
 * 매출·매입 리포트(Trade Overview) 단일 종합 응답
 * - summary: 요약 카드
 * - series: 차트용 월별 시계열
 * - monthlyDetails: 하단 표(월별 상세)
 * - range: 실제 집계 범위
 */
@Builder
public record TradeOverviewReportResponse(
        Summary summary,                     // 총 주문금액/총 매입금액/손익/전년 대비
        List<SeriesPoint> series,            // 월별 시계열 (주문/매입)
        List<MonthlyRow> monthlyDetails,     // 월별 상세 표
        Range range                          // 실제 집계 범위
) {
    @Builder
    public record Summary(
            Integer totalOrderAmount,        // 총 주문 금액(= 매출 인식 기준: 출고일 집계)
            Integer totalPurchaseAmount,     // 총 매입 금액(= 입고일 집계)
            Integer grossProfitAmount,       // 손익(주문-매입)
            Double grossProfitRate,         // 손익률(%), 주문 0이면 null
            Double yoyOrderChangeRate,      // 전년 대비 주문 증감률(%), 분모 0이면 null
            Double yoyPurchaseChangeRate    // 전년 대비 매입 증감률(%), 분모 0이면 null
    ) {
    }

    @Builder
    public record SeriesPoint(
            Integer month,                   // 월(1~12)
            Integer orderAmount,             // 해당 월 주문 금액(= 매출, 출고일 기준)
            Integer purchaseAmount           // 해당 월 매입 금액(입고일 기준)
    ) {
    }

    @Builder
    public record MonthlyRow(
            Integer month,                   // 월(1~12)
            Integer orderAmount,             // 주문 금액
            Integer purchaseAmount,          // 매입 금액
            Double profitRate,              // 이익률(%), 주문 0이면 null
            Double orderMoMChangeRate       // 전월 대비 주문 증감률(%), 첫 달/분모 0이면 null
    ) {
    }

    @Builder
    public record Range(
            Integer year,                    // 집계 연도
            Integer fromMonth,               // 시작 월
            Integer toMonth,                  // 종료 월
            Integer effectiveToMonth
    ) {
    }
}