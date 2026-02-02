package erp.inbound.dto.internal;

import erp.inbound.enums.InboundStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InboundCancelItemRow(
        @NotNull
        Long purchaseId,

        @NotNull
        InboundStatus inboundStatus,

        @NotNull
        Long itemId,

        @NotNull
        @Positive
        Integer quantity
) {
}
