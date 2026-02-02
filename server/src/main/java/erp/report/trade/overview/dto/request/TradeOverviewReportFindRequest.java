package erp.report.trade.overview.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TradeOverviewReportFindRequest(
        @NotNull
        @Min(1900)
        Integer year,      // 조회 연도

        @NotNull
        @Min(1)
        @Max(12)
        Integer fromMonth, // 시작 월

        @NotNull
        @Min(1)
        @Max(12)
        Integer toMonth    // 종료 월
) {
    @AssertTrue(message = "fromMonth는 toMonth보다 클 수 없습니다.")
    public boolean isValidMonthRange() {
        if (fromMonth == null || toMonth == null)
            return true;
        return fromMonth <= toMonth;
    }
}
