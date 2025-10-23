package erp.order.controller;

import erp.global.response.ApiResponse;
import erp.global.response.PageResponse;
import erp.order.dto.request.OrderFindAllRequest;
import erp.order.dto.request.OrderSaveRequest;
import erp.order.dto.response.OrderCodeAndCustomerResponse;
import erp.order.dto.response.OrderDetailResponse;
import erp.order.dto.response.OrderFindAllResponse;
import erp.order.dto.response.OrderItemsSummaryResponse;
import erp.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<Long> saveOrderAndItems(@Valid @RequestBody OrderSaveRequest request) {
        return ApiResponse.onSuccess(
                orderService.saveOrderAndOrderItems(request));
    }

    @GetMapping
    public ApiResponse<PageResponse<OrderFindAllResponse>> findAllOrders(
            @Valid @RequestBody OrderFindAllRequest request
    ) {
        return ApiResponse.onSuccess(
                orderService.findAllOrder(request));
    }

    @GetMapping("/{orderId}/items/summary")
    public ApiResponse<OrderItemsSummaryResponse> findOrderItemsSummary(
            @PathVariable long orderId
    ) {
        return ApiResponse.onSuccess(
                orderService.findOrderItemsSummary(orderId));
    }

    @GetMapping("/code-customers")
    public ApiResponse<List<OrderCodeAndCustomerResponse>> findAllOrderCodeAndCustomer() {
        return ApiResponse.onSuccess(
                orderService.findAllOrderCodeAndCustomer());
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetailResponse> findOrderDetail(@PathVariable long orderId) {
        return ApiResponse.onSuccess(
                orderService.findOrderDetail(orderId));
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<Void> cancelOrder(@PathVariable long orderId) {
        orderService.cancelOrder(orderId);
        return ApiResponse.onSuccess(null);
    }
}
