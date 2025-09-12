package erp.order.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemStockFindRow(
        @NotBlank
        String code,

        @NotBlank
        String name,

        @NotNull
        @Positive
        Integer quantity,

        @NotNull
        @Positive
        Integer unitPrice,

        @NotNull
        @Positive
        Integer subtotal,

        @NotNull
        @PositiveOrZero
        Integer availableQuantity,

        @NotNull
        @PositiveOrZero
        Integer onHandQuantity,

        @NotNull
        @PositiveOrZero
        Integer allocatedQuantity,

        // 가용재고보다 많이 주문하면 음수도 될 수 있음
        @NotNull
        Integer availableStockAfterOutbound
) {
}
