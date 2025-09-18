package erp.order.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemSaveRequest(

        @NotNull
        long itemId,

        @Positive
        @NotNull
        int quantity
) {
}