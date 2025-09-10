package erp.purchase.dto.response;

import erp.purchase.dto.internal.PurchaseFindRow;
import erp.purchase.enums.PurchaseStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PurchaseFindResponse(
    long purchaseId,
    String code,
    String supplier,
    int totalQuantity,
    int totalAmount,
    LocalDate purchaseDate,
    PurchaseStatus status,
    String employeeName
) {

    public static PurchaseFindResponse from(PurchaseFindRow row) {
        return PurchaseFindResponse.builder()
            .purchaseId(row.purchaseId())
            .code(row.code())
            .supplier(row.supplier())
            .totalQuantity(row.totalQuantity())
            .totalAmount(row.totalAmount())
            .purchaseDate(row.purchaseDate())
            .status(row.status())
            .employeeName(row.employeeName())
            .build(
            );
    }
}
