package erp.report.stock.overview.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record StockOverviewResponse(
        Summary summary,               // 요약 카드
        List<ItemDetailRow> details,   // 표 + 차트 데이터의 단일 소스
        Range range                    // 응답 범위(연/월)
) {
    @Builder
    public record Summary(
            Integer totalInboundQuantity,   // 해당 연월 총 입고
            Integer totalOutboundQuantity,  // 해당 연월 총 출고
            Integer totalOnHandQuantity,    // 현재 재고 총합
            Double yoyInboundChangeRate,   // 전년 동월 대비 입고 증감률(%)
            Double yoyOutboundChangeRate,  // 전년 동월 대비 출고 증감률(%)
            Integer itemCount               // 품목 수
    ) {
    }

    @Builder
    public record ItemDetailRow(
            Long itemId,
            String name,
            Integer inboundQuantity,
            Integer outboundQuantity,
            Integer onHandQuantity,
            String unit
    ) {
    }

    @Builder
    public record Range(
            Integer year,
            Integer month
    ) {
    }
}
