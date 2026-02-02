package erp.order.mapper;

import erp.order.domain.OrderItem;
import erp.order.dto.internal.OrderItemDetailRow;
import erp.order.dto.internal.OrderItemQuantityRow;
import erp.order.dto.internal.OrderItemStockFindRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    long nextId();

    int save(@Param("tenantId") long tenantId,
             @Param("orderItem") OrderItem orderItem);

    List<OrderItemStockFindRow> findAllOrderItemStockFindRow(
            @Param("tenantId") long tenantId,
            @Param("orderId") long orderId);

    List<OrderItemDetailRow> findAllOrderItemDetailRow(
            @Param("tenantId") long tenantId,
            @Param("orderId") long orderId);

    List<OrderItemQuantityRow> findAllOrderItemQuantityRow(
            @Param("tenantId") long tenantId,
            @Param("orderId") long orderId);
}
