package erp.outbound.service;

import erp.employee.validation.EmployeeValidator;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.util.Codes;
import erp.order.dto.internal.OrderItemQuantityRow;
import erp.order.service.OrderService;
import erp.order.validation.OrderValidator;
import erp.outbound.domain.Outbound;
import erp.outbound.dto.request.OutboundSaveRequest;
import erp.outbound.enums.OutboundStatus;
import erp.outbound.mapper.OutboundMapper;
import erp.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static erp.global.util.Codes.DEFAULT_MAX_TRY;
import static erp.global.util.RowCountGuards.requireOneRowAffected;

@Service
@RequiredArgsConstructor
public class OutboundServiceImpl implements OutboundService {

    private final OutboundMapper outboundMapper;
    private final OrderService orderService;
    private final StockService stockService;

    private final EmployeeValidator employeeValidator;
    private final OrderValidator orderValidator;

    @Override
    @Transactional
    public long saveOutbound(OutboundSaveRequest request, long tenantId) {
        LocalDate outboundDate = request.outboundDate();
        long orderId = request.orderId();
        long employeeId = request.employeeId();

        orderValidator.validOrderIdIfPresent(orderId, tenantId);
        employeeValidator.validEmployeeIdIfPresent(employeeId, tenantId);

        // 1) 주문 품목 수량 조회
        List<OrderItemQuantityRow> itemRows =
                orderService.findAllOrderItemQuantityRow(orderId, tenantId);

        // 2) 재고 체크 및 차감
        for (OrderItemQuantityRow row : itemRows) {
            long itemId = row.itemId();
            int quantity = row.quantity();

            // 현재 재고(on_hand) 감소 — 부족하면 예외
            stockService.decreaseOnHand(itemId, quantity, tenantId);

            // 예약 재고(allocated) 감소 — 부족하면 예외
            stockService.decreaseAllocated(tenantId, itemId, quantity);
        }

        // 3) 출고 저장 (코드 유니크 충돌 시 재시도)
        int year = outboundDate.getYear();
        for (int attempt = 0; attempt < DEFAULT_MAX_TRY; attempt++) {
            String outboundCode = "OB-" + year + "-" + Codes.randomToken(8);
            try {
                long newOutboundId = outboundMapper.nextId();

                Outbound outbound = Outbound.builder()
                        .outboundId(newOutboundId)
                        .code(outboundCode)
                        .outboundDate(outboundDate)
                        .status(OutboundStatus.ACTIVE)
                        .employeeId(employeeId)
                        .orderId(orderId)
                        .companyId(tenantId)
                        .build();

                int affectedRowCount = outboundMapper.save(tenantId, outbound);
                requireOneRowAffected(affectedRowCount, ErrorStatus.CREATE_OUTBOUND_FAIL);

                // 5) 주문 상태 SHIPPED로 변경 (CONFIRMED일 때만)
                orderService.updateStatusToShippedIfConfirmed(orderId, tenantId);

                return newOutboundId;

            } catch (DuplicateKeyException e) {
                if (attempt == DEFAULT_MAX_TRY - 1) {
                    throw new GlobalException(ErrorStatus.CREATE_OUTBOUND_FAIL);
                }
                // 재시도
            }
        }

        throw new GlobalException(ErrorStatus.CREATE_OUTBOUND_FAIL);
    }
}
