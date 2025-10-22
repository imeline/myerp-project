package erp.order.dto.response;

import erp.global.util.CountsAndTotals;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderItemsSummaryResponse(
        List<OrderItemStockFindResponse> items,
        Integer totalItemCount,
        Integer totalQuantity,
        Integer totalAmount
) {
    public static OrderItemsSummaryResponse of(List<OrderItemStockFindResponse> items) {
        CountsAndTotals totals = CountsAndTotals.computeFrom(
                items,
                OrderItemStockFindResponse::quantity,
                OrderItemStockFindResponse::subtotal
        );
        return OrderItemsSummaryResponse.builder()
                .items(items)
                .totalItemCount(totals.totalItemCount())
                .totalQuantity(totals.totalQuantity())
                .totalAmount(totals.totalAmount())
                .build();
    }
}
