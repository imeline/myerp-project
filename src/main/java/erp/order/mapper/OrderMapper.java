package erp.order.mapper;

import erp.order.domain.Order;
import erp.order.dto.internal.OrderCodeAndCustomerRow;
import erp.order.dto.internal.OrderDetailRow;
import erp.order.dto.internal.OrderFindRow;
import erp.order.enums.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderMapper {
    long nextId();

    int save(@Param("order") Order order);

    List<OrderFindRow> findAllOrderFindRow(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code,
            @Param("status") OrderStatus status,
            @Param("offset") int offset,
            @Param("size") int size
    );

    List<OrderCodeAndCustomerRow> findAllCodeAndCustomer(

    );

    Optional<OrderDetailRow> findOrderDetailRow(
            @Param("orderId") long orderId
    );

    int updateStatusToIfConfirmed(
            @Param("orderId") long orderId,
            @Param("toStatus") OrderStatus toStatus
    );

    int updateStatusToConfirmedIfShipped(
            @Param("orderId") long orderId
    );

    Optional<OrderStatus> findStatusById(
            @Param("orderId") long orderId
    );

    long countByPeriodAndCodeAndStatus(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code,
            @Param("status") OrderStatus status
    );

    boolean existsById(
            @Param("orderId") long orderId
    );

    boolean existsConfirmByItemId(
            @Param("itemId") long itemId
    );
}
