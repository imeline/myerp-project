package erp.purchase.dto.request;

import erp.global.util.time.DatePeriod;
import erp.purchase.enums.PurchaseStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PurchaseFindAllRequest(
        @NotNull
        @PositiveOrZero
        Integer page,
        @NotNull
        @Positive
        Integer size,
        DatePeriod period,         // 최근 30일 등
        PurchaseStatus status,     // 상태 필터 (발주/주문에서만 사용, 입출고는 사용 안함)
        String code                // 발주번호(유니크 코드) 검색 (부분/정확 일치)
) {
}
