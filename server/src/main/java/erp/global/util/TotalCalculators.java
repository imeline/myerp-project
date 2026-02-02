package erp.global.util;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TotalCalculators {
    /**
     * 리스트 + 추출자 + 가격맵을 받아 총수량/총금액을 계산.
     * - 수량/금액은 Math.*Exact로 오버플로 가드
     * - 가격 누락 시 GlobalException(ErrorStatus.NOT_FOUND_ITEM)
     */
    public static <T> Totals computeTotals(
            List<T> items,
            ToIntFunction<T> quantityExtractor,
            ToLongFunction<T> itemIdExtractor,
            Map<Long, Integer> priceMap
    ) {
        Objects.requireNonNull(items, "items");
        Objects.requireNonNull(quantityExtractor, "quantityExtractor");
        Objects.requireNonNull(itemIdExtractor, "itemIdExtractor");
        Objects.requireNonNull(priceMap, "priceMap");

        int totalQuantity = 0;
        int totalAmount = 0;

        for (T it : items) {
            int quantity = quantityExtractor.applyAsInt(it);
            long itemId = itemIdExtractor.applyAsLong(it);

            Integer unitPrice = priceMap.get(itemId);
            if (unitPrice == null) {
                throw new GlobalException(ErrorStatus.NOT_FOUND_ITEM);
            }

            totalQuantity = Math.addExact(totalQuantity, quantity);
            totalAmount = Math.addExact(totalAmount, Math.multiplyExact(quantity, unitPrice));
        }
        return new Totals(totalQuantity, totalAmount);
    }
}
