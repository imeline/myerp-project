package erp.order.validation;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.order.dto.request.OrderItemSaveRequest;
import erp.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderValidatorImpl implements OrderValidator {

    private final OrderMapper orderMapper;

    @Override
    public void validItemIdsUniqueInRequest(List<OrderItemSaveRequest> requestItems) {
        Set<Long> set = new HashSet<>();
        for (OrderItemSaveRequest requestItem : requestItems) {
            if (!set.add(requestItem.itemId())) {
                throw new GlobalException(ErrorStatus.DUPLICATE_ITEM);
            }
        }
    }

    @Override
    public void validOrderIdIfPresent(long orderId) {
        if (!orderMapper.existsById(orderId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_ORDER);
        }
    }

    @Override
    public void validNoConfirmByItemId(long itemId) {
        if (orderMapper.existsConfirmByItemId(itemId)) {
            throw new GlobalException(ErrorStatus.CANNOT_DELETE_ITEM_BY_CONFIRMED_ORDER);
        }
    }
}
