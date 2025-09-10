package erp.report.stock.overview.dto.response;

import erp.report.stock.overview.dto.internal.StockOverviewRow;
import lombok.Builder;

/**
 * 재고 현황 조회 응답 (Find)
 */
@Builder
public record StockOverviewFindResponse(

        Integer totalItemCount,
        Integer confirmedPurchaseCount,
        Integer zeroAvailableItemCount,
        Integer totalInventoryValue
) {
    public static StockOverviewFindResponse from(StockOverviewRow row) {
        return StockOverviewFindResponse.builder()
                .totalItemCount(row.totalItemCount())
                .confirmedPurchaseCount(row.confirmedPurchaseCount())
                .zeroAvailableItemCount(row.zeroAvailableItemCount())
                .totalInventoryValue(row.totalInventoryValue())
                .build();
    }
}
