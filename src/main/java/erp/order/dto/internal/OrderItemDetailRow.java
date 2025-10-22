package erp.order.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemDetailRow(
        @NotBlank
        String name,

        @NotBlank
        String code,

        @NotBlank
        String unit,

        @NotNull
        @Positive
        Integer quantity,

        @NotNull
        @Positive
        Integer unitPrice,

        @NotNull
        @Positive
        Integer subtotal
) {
}
