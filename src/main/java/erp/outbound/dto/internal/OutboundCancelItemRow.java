package erp.outbound.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OutboundCancelItemRow(
        @NotNull
        Long itemId,

        @NotNull
        @Positive
        Integer quantity,

        @NotNull
        Long orderId
) {
}
