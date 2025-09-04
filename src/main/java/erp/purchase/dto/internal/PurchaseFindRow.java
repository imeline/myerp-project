package erp.purchase.dto.internal;

import erp.purchase.enums.PurchaseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record PurchaseFindRow(
        @NotNull
        Long purchaseId,

        @NotNull
        String code,

        @NotNull
        String supplier,

        @NotNull
        @Positive
        Integer totalQuantity,

        @NotNull
        @Positive
        Integer totalAmount,

        @NotNull
        LocalDate purchaseDate,

        @NotNull
        PurchaseStatus status,

        @NotBlank
        String employeeName
) {
}
