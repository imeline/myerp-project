package erp.order.validation;

import erp.order.dto.request.OrderItemSaveRequest;

import java.util.List;

public interface OrderValidator {
    void validItemIdsUniqueInRequest(List<OrderItemSaveRequest> requestItems);

    void validOrderIdIfPresent(long orderId, long tenantId);
}
