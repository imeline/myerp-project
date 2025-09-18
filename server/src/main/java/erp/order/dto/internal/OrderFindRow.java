package erp.order.dto.internal;

import erp.order.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrderFindRow(
        @NotNull
        Long orderId,
        @NotNull
        LocalDate orderDate,
        @NotBlank
        String customer,
        @NotNull
        Integer itemCount,
        @NotNull
        Integer totalAmount,
        @NotNull
        OrderStatus status,
        @NotBlank
        String employeeName
) {
}
