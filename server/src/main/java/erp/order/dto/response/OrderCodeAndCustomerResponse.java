package erp.order.dto.response;

import erp.order.dto.internal.OrderCodeAndCustomerRow;
import lombok.Builder;

@Builder
public record OrderCodeAndCustomerResponse(
        Long orderId,
        String code,
        String customer
) {
    public static OrderCodeAndCustomerResponse from(OrderCodeAndCustomerRow row) {
        return OrderCodeAndCustomerResponse.builder()
                .orderId(row.orderId())
                .code(row.code())
                .customer(row.customer())
                .build();
    }
}