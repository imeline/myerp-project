package erp.report.stock.movement.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record StockMovementSummaryRow(

        @NotNull
        String itemName,

        @NotNull
        String itemCode,

        @NotNull
        @PositiveOrZero
        Integer onHandQuantity,

        @NotNull
        String warehouse,

        @NotNull
        @PositiveOrZero
        Integer totalInboundCount,

        @NotNull
        @PositiveOrZero
        Integer totalOutboundCount
) {
    public static StockMovementSummaryRow from(StockMovementSummaryRow row) {
        return StockMovementSummaryRow.builder()
                .itemName(row.itemName())
                .itemCode(row.itemCode())
                .onHandQuantity(row.onHandQuantity())
                .warehouse(row.warehouse())
                .totalInboundCount(row.totalInboundCount())
                .totalOutboundCount(row.totalOutboundCount())
                .build();
    }
}
