package erp.report.stock.movement.dto.internal;

import erp.global.util.PageParam;
import erp.report.stock.movement.enums.MovementStatusFilter;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StockMovementSearchFilter(
        Long itemId,
        LocalDate startDate,
        LocalDate endDate,
        String code,
        MovementStatusFilter status,
        int page,
        int size,
        int offset
) {
    public static StockMovementSearchFilter of(
            long itemId,
            LocalDate startDate,
            LocalDate endDate,
            String code,
            MovementStatusFilter status,
            PageParam pageParameter
    ) {
        return StockMovementSearchFilter.builder()
                .itemId(itemId)
                .startDate(startDate)
                .endDate(endDate)
                .code(code)
                .status(status)
                .page(pageParameter.page())
                .size(pageParameter.size())
                .offset(pageParameter.offset())
                .build();
    }
}
