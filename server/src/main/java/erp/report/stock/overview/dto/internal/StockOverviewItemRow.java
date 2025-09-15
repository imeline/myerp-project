package erp.report.stock.overview.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockOverviewItemRow(
        @NotNull
        Long itemId,                 // 품목 ID

        @NotNull
        String name,                 // 품목명

        @NotNull
        String unit,                 // 단위

        @NotNull
        @PositiveOrZero
        Integer inboundQuantity,   // 연월 입고 수량

        @NotNull
        @PositiveOrZero
        Integer outboundQuantity,  // 연월 출고 수량

        @NotNull
        @PositiveOrZero
        Integer onHandQuantity     // 현재 재
) {
}
