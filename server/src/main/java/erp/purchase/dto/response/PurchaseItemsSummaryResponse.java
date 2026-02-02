package erp.purchase.dto.response;

import erp.global.util.CountsAndTotals;
import java.util.List;
import lombok.Builder;

@Builder
public record PurchaseItemsSummaryResponse(
    List<PurchaseItemStockFindResponse> items,
    Integer totalItemCount,
    Integer totalQuantity,
    Integer totalAmount
) {

    public static PurchaseItemsSummaryResponse of(List<PurchaseItemStockFindResponse> items) {
        CountsAndTotals aggregates = CountsAndTotals.computeFrom(
            items,
            PurchaseItemStockFindResponse::quantity,
            PurchaseItemStockFindResponse::subtotal
        );
        return PurchaseItemsSummaryResponse.builder()
            .items(items)
            .totalItemCount(aggregates.totalItemCount())
            .totalQuantity(aggregates.totalQuantity())
            .totalAmount(aggregates.totalAmount())
            .build();
    }

    public static PurchaseItemsSummaryResponse of(
        List<PurchaseItemStockFindResponse> items,
        CountsAndTotals aggregates
    ) {
        return PurchaseItemsSummaryResponse.builder()
            .items(items)
            .totalItemCount(aggregates.totalItemCount())
            .totalQuantity(aggregates.totalQuantity())
            .totalAmount(aggregates.totalAmount())
            .build();
    }
}
