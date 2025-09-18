package erp.report.stock.overview.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockOverviewFindRequest(
        @NotNull
        @Min(1900)
        Integer year,   // 조회 연도

        @NotNull
        @Min(1)
        @Max(12)
        Integer month   // 조회 월
) {
}
