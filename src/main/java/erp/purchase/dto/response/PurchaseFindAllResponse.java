package erp.purchase.dto.response;

import erp.purchase.dto.internal.PurchaseFindRow;
import erp.purchase.enums.PurchaseStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PurchaseFindAllResponse(
        long purchaseId,
        String code,
        String supplier,
        int itemCount,
        int totalAmount,
        LocalDate purchaseDate,
        PurchaseStatus status,
        String employeeName
) {

    public static PurchaseFindAllResponse from(PurchaseFindRow row) {
        return PurchaseFindAllResponse.builder()
                .purchaseId(row.purchaseId())
                .code(row.code())
                .supplier(row.supplier())
                .itemCount(row.itemCount())
                .totalAmount(row.totalAmount())
                .purchaseDate(row.purchaseDate())
                .status(row.status())
                .employeeName(row.employeeName())
                .build();
    }
}
