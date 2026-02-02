package erp.global.util;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

public record CountsAndTotals(
    @NotNull
    @Positive
    Integer totalItemCount,

    @NotNull
    @Positive
    Integer totalQuantity,

    @NotNull
    @Positive
    Integer totalAmount
) {

    /**
     * 리스트에서 수량/금액을 추출해 합계를 계산한다. 서비스에서 빈 목록 여부는 사전에 검증(예: NOT_FOUND_XXX)하고 호출하는 걸 권장.
     */
    public static <T> CountsAndTotals computeFrom(
        List<T> items,
        ToIntFunction<T> quantityExtractor,
        ToIntFunction<T> subtotalExtractor
    ) {
        Objects.requireNonNull(items, "items");
        Objects.requireNonNull(quantityExtractor, "quantityExtractor");
        Objects.requireNonNull(subtotalExtractor, "subtotalExtractor");

        int count = items.size();
        int qty = 0;
        int amt = 0;
        for (T it : items) {
            qty = Math.addExact(qty, quantityExtractor.applyAsInt(it));
            amt = Math.addExact(amt, subtotalExtractor.applyAsInt(it));
        }
        // 빈 리스트는 서비스에서 걸러지므로 @Positive 위반 없음
        return new CountsAndTotals(count, qty, amt);
    }

    /**
     * 정수 합계가 이미 계산된 경우 편의 팩토리.
     */
    public static CountsAndTotals of(int totalItemCount, int totalQuantity, int totalAmount) {
        return new CountsAndTotals(totalItemCount, totalQuantity, totalAmount);
    }
}
