package erp.order.dto.request;

import erp.global.util.time.DatePeriod;
import erp.order.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderFindAllRequest(

        @NotNull
        @PositiveOrZero
        Integer page,

        @NotNull
        @Positive
        Integer size,

        DatePeriod period,

        String code,

        OrderStatus status
) {
}