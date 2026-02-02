package erp.purchase.dto.internal;

import erp.purchase.enums.PurchaseStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PurchaseDetailRow(
        @NotNull
        String code,
        @NotNull
        String supplier,
        @NotNull
        LocalDate purchaseDate,
        @NotNull
        PurchaseStatus status,
        @NotNull
        String employeeName,
        @NotNull
        Integer totalItemCount,
        @NotNull
        Integer totalQuantity,
        @NotNull
        Integer totalAmount
) {
}
