package erp.order.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemQuantityRow(

        @NotNull
        Long itemId,

        @NotNull
        @Positive
        Integer quantity
) {
}
