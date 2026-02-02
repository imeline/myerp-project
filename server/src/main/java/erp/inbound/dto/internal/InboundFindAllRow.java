package erp.inbound.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record InboundFindAllRow(
        @NotNull
        Long inboundId,

        @NotNull
        String inboundCode,

        @NotNull
        LocalDate inboundDate,

        @NotBlank
        String employeeName,

        @NotNull
        Long purchaseId,

        @NotBlank
        String purchaseCode,

        @NotNull
        @PositiveOrZero
        Integer totalQuantity,

        @NotNull
        @PositiveOrZero
        Integer totalAmount
) {
}
