package erp.purchase.dto.request;

import erp.global.util.time.DatePeriod;
import erp.purchase.enums.PurchaseStatus;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PurchaseFindAllRequest(
        @PositiveOrZero
        Integer page,
        @Positive   // null이면 PageParam 기본값 사용
        Integer size,
        DatePeriod period,
        PurchaseStatus status,
        String code
) {
}
