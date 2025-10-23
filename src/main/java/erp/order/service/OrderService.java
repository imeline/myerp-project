package erp.order.service;

import erp.global.response.PageResponse;
import erp.order.dto.internal.OrderItemQuantityRow;
import erp.order.dto.request.OrderFindAllRequest;
import erp.order.dto.request.OrderSaveRequest;
import erp.order.dto.response.OrderCodeAndCustomerResponse;
import erp.order.dto.response.OrderDetailResponse;
import erp.order.dto.response.OrderFindAllResponse;
import erp.order.dto.response.OrderItemsSummaryResponse;
import erp.order.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    long saveOrderAndOrderItems(OrderSaveRequest request);

    PageResponse<OrderFindAllResponse> findAllOrder(
            OrderFindAllRequest request);

    OrderItemsSummaryResponse findOrderItemsSummary(
            long orderId);

    List<OrderCodeAndCustomerResponse> findAllOrderCodeAndCustomer();

    OrderDetailResponse findOrderDetail(long orderId);

    void cancelOrder(long orderId);

    OrderStatus findStatusById(long orderId);

    List<OrderItemQuantityRow> findAllOrderItemQuantityRow(long orderId);

    void updateStatusToShippedIfConfirmed(long orderId);

    void updateStatusToConfirmedIfShipped(long orderId);
}
