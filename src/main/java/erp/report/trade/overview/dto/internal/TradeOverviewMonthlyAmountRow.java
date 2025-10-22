package erp.report.trade.overview.dto.internal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TradeOverviewMonthlyAmountRow(
        @NotNull
        @Min(1)
        @Max(12)
        Integer month,              // 집계 월

        @NotNull
        @PositiveOrZero
        Integer orderAmount,        // 주문 금액(출고일 기준)

        @NotNull
        @PositiveOrZero
        Integer purchaseAmount      // 매입 금액(입고일 기준)
) {
}
