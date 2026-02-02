package erp.global.util;

import jakarta.validation.constraints.NotNull;

public record Totals(
        @NotNull
        Integer totalQuantity,
        @NotNull
        Integer totalAmount
) {
}
