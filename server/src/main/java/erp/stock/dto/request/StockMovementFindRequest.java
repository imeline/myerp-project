package erp.stock.dto.request;

import erp.global.util.time.DatePeriod;
import erp.stock.enums.MovementStatusFilter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record StockMovementFindRequest(
        DatePeriod period,          // 기간(ALL 허용)

        String code,                // 입출고 번호 부분 검색

        MovementStatusFilter status, // ACTIVE, CANCELED, ALL

        @NotNull
        @PositiveOrZero
        Integer page,

        @NotNull
        @Positive
        Integer size
) {
}
