package erp.order.dto.internal;

import erp.order.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record OrderSearchFilter(
        LocalDate startDate,
        LocalDate endDate,
        String code,
        OrderStatus status,
        Integer offset,
        Integer size
) {
    public static OrderSearchFilter of(
            LocalDate startDate,
            LocalDate endDate,
            String code,
            OrderStatus status,
            Integer offset,
            Integer size
    ) {
        return OrderSearchFilter.builder()
                .startDate(startDate)
                .endDate(endDate)
                .code(code)
                .status(status)
                .offset(offset)
                .size(size)
                .build();
    }

}
