package erp.stock.dto.response;

import erp.stock.dto.internal.StockSummaryRow;
import lombok.Builder;

/**
 * 재고 현황 조회 응답 (Find)
 */
@Builder
public record StockSummaryFindResponse(

        Integer totalItemCount,
        Integer confirmedPurchaseCount,
        Integer zeroAvailableItemCount,
        Integer totalInventoryValue
) {
    public static StockSummaryFindResponse from(StockSummaryRow row) {
        return StockSummaryFindResponse.builder()
                .totalItemCount(row.totalItemCount())
                .confirmedPurchaseCount(row.confirmedPurchaseCount())
                .zeroAvailableItemCount(row.zeroAvailableItemCount())
                .totalInventoryValue(row.totalInventoryValue())
                .build();
    }
}
