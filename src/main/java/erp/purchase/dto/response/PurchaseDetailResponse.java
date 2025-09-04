package erp.purchase.dto.response;

import erp.purchase.dto.internal.PurchaseDetailRow;
import erp.purchase.dto.internal.PurchaseItemDetailRow;
import erp.purchase.enums.PurchaseStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PurchaseDetailResponse(
        String code,
        String supplier,
        LocalDate purchaseDate,
        PurchaseStatus status,
        String employeeName,
        Integer totalItemCount,
        Integer totalQuantity,
        List<PurchaseItemDetailResponse> items,
        Integer totalAmount
) {
    public static PurchaseDetailResponse of(PurchaseDetailRow row,
                                            List<PurchaseItemDetailRow> itemRows) {
        List<PurchaseItemDetailResponse> items = itemRows.stream()
                .map(PurchaseItemDetailResponse::from)
                .toList();

        return PurchaseDetailResponse.builder()
                .code(row.code())
                .supplier(row.supplier())
                .purchaseDate(row.purchaseDate())
                .status(row.status())
                .employeeName(row.employeeName())
                .totalItemCount(row.totalItemCount())
                .totalQuantity(row.totalQuantity())
                .items(items)
                .totalAmount(row.totalAmount())
                .build();
    }
}
