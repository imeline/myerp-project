package erp.report.stock.movement.dto.internal;

import erp.report.stock.movement.enums.MovementStatus;
import erp.report.stock.movement.enums.StockMovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record StockMovementFindRow(
        @NotNull
        LocalDate movementDate,

        @NotNull
        StockMovementType movementType,

        @NotNull
        @PositiveOrZero
        Integer quantity,

        @NotNull
        String employeeName,

        @NotNull
        String code,

        @NotNull
        MovementStatus status,

        @NotNull
        String warehouse
) {
}
