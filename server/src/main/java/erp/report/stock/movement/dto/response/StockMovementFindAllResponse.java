package erp.report.stock.movement.dto.response;

import erp.global.response.PageResponse;
import erp.report.stock.movement.dto.internal.StockMovementFindRow;
import erp.report.stock.movement.dto.internal.StockMovementSummaryRow;
import lombok.Builder;

import java.util.List;

@Builder
public record StockMovementFindAllResponse(
        StockMovementSummaryRow summary,
        PageResponse<StockMovementFindRow> rows
) {
    public static StockMovementFindAllResponse of(
            StockMovementSummaryRow summary,
            List<StockMovementFindRow> rowList,
            int page,
            long total,
            int size
    ) {
        return StockMovementFindAllResponse.builder()
                .summary(summary)
                .rows(PageResponse.of(rowList, page, total, size))
                .build();
    }
}