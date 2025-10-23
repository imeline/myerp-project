package erp.stock.validation;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.stock.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockValidatorImpl implements StockValidator {

    private final StockMapper stockMapper;

    @Override
    public void validZeroStockByItemId(long itemId) {
        if (stockMapper.existsPositiveByItemId(itemId)) {
            throw new GlobalException(ErrorStatus.CANNOT_DELETE_ITEM_STOCK_NOT_ZERO);
        }
    }
}
