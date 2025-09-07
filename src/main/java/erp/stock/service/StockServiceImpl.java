package erp.stock.service;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.stock.domain.Stock;
import erp.stock.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static erp.global.util.RowCountGuards.requireOneRowAffected;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockMapper stockMapper;


    @Override
    @Transactional
    public long saveStock(long itemId, int initialQuantity, String warehouse,
                          long tenantId) {
        if (initialQuantity < 0)
            throw new GlobalException(ErrorStatus.INVALID_STOCK_QUANTITY);

        long newStockId = stockMapper.nextId();

        Stock stock = Stock.resister(
                newStockId,
                warehouse,
                initialQuantity,
                itemId,
                tenantId
        );

        int affectedRowCount = stockMapper.save(tenantId, stock);
        requireOneRowAffected(affectedRowCount, ErrorStatus.CREATE_STOCK_FAIL);

        return newStockId;
    }
}
