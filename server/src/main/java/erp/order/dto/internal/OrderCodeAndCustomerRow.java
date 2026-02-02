package erp.order.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderCodeAndCustomerRow(
        @NotNull
        Long orderId,

        @NotBlank
        String code,

        @NotBlank
        String customer
) {
}
