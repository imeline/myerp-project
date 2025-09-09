package erp.stock.dto.request;

import erp.item.enums.ItemCategory;
import erp.stock.enums.StockSortBy;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record StockFindAllRequest(
        String name,                       // 품목명(부분 일치)
        ItemCategory category,             // 품목 카테고리
        String warehouse,                  // 창고명

        @PositiveOrZero
        Integer availableQuantityFrom,     // 가용재고 하한(포함)

        @PositiveOrZero
        Integer availableQuantityTo,       // 가용재고 상한(포함)

        @NotNull
        StockSortBy sortBy,                // 정렬 기준

        @NotNull
        @PositiveOrZero
        Integer page,                      // 0-base

        @NotNull
        @Positive
        Integer size                       // 페이지 크기(>=1)
) {

    /**
     * from ≤ to 검증 (둘 다 null이 아니면 검사, 하나라도 null이면 통과)
     */
    @AssertTrue(message = "가용재고 하한(From)은 상한(To)보다 작거나 같아야 합니다.")
    public boolean isValidQuantityRange() {
        if (availableQuantityFrom == null || availableQuantityTo == null) {
            return true;
        }
        return availableQuantityFrom <= availableQuantityTo;
    }
}
