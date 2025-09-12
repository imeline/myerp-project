package erp.stock.service;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.response.PageResponse;
import erp.global.util.PageParam;
import erp.global.util.Strings;
import erp.item.enums.ItemCategory;
import erp.item.validation.ItemValidator;
import erp.stock.domain.Stock;
import erp.stock.dto.internal.StockFindAllRow;
import erp.stock.dto.internal.StockFindFilterRow;
import erp.stock.dto.internal.StockPriceFindRow;
import erp.stock.dto.request.StockFindAllRequest;
import erp.stock.dto.response.StockFindAllResponse;
import erp.stock.dto.response.StockPriceFindResponse;
import erp.stock.enums.StockSortBy;
import erp.stock.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static erp.global.util.RowCountGuards.requireOneRowAffected;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockMapper stockMapper;

    private final ItemValidator itemValidator;


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

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StockFindAllResponse> findAllStock(
            StockFindAllRequest request,
            long tenantId
    ) {
        String normalizedItemName = Strings.normalizeOrNull(request.name());
        String normalizedWarehouseName = Strings.normalizeOrNull(request.warehouse());
        ItemCategory itemCategory = request.category();

        PageParam pageParameter = PageParam.of(request.page(), request.size(), 20);

        StockSortBy sortBy = request.sortBy();
        String sortDirection = resolveDefaultSortDirection(sortBy);

        StockFindFilterRow query = StockFindFilterRow.of(
                normalizedItemName,
                itemCategory,
                normalizedWarehouseName,
                request.availableQuantityFrom(),
                request.availableQuantityTo(),
                sortBy,
                sortDirection,
                pageParameter.offset(),
                pageParameter.size()
        );

        List<StockFindAllRow> rows = stockMapper.findAllStockFindAllRow(
                tenantId,
                query
        );
        if (rows.isEmpty())
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_STOCK);

        long totalCount = stockMapper.countByStock(
                tenantId,
                query
        );
        List<StockFindAllResponse> responseList = rows.stream()
                .map(StockFindAllResponse::from)
                .toList();

        return PageResponse.of(
                responseList,
                pageParameter.page(),
                totalCount,
                pageParameter.size()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public StockPriceFindResponse findStockPrice(long itemId, long tenantId) {
        StockPriceFindRow row =
                stockMapper.findStockPriceFindRowByItemId(tenantId, itemId)
                        .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_ITEM));

        return StockPriceFindResponse.from(row);
    }

    @Override
    @Transactional
    public void increaseOnHand(long itemId, int delta, long tenantId) {
        if (delta <= 0) {
            throw new GlobalException(ErrorStatus.INVALID_STOCK_QUANTITY);
        }
        itemValidator.validItemIdsIfPresent(List.of(itemId), tenantId);
        int affectedRowCount = stockMapper.increaseOnHand(
                tenantId, itemId, delta);
        requireOneRowAffected(affectedRowCount, ErrorStatus.NOT_FOUND_STOCK);
    }

    @Override
    @Transactional
    public void decreaseOnHand(long itemId, int delta, long tenantId) {
        if (delta <= 0) {
            throw new GlobalException(ErrorStatus.INVALID_STOCK_QUANTITY);
        }
        itemValidator.validItemIdsIfPresent(List.of(itemId), tenantId);
        int affected = stockMapper.decreaseOnHandIfEnough(
                tenantId, itemId, delta);
        requireOneRowAffected(affected, ErrorStatus.NOT_FOUND_STOCK);
    }

    @Override
    @Transactional
    public void increaseAllocatedIfEnoughOnHand(long tenantId, long itemId, int quantity) {
        int affectedRowCount = stockMapper.increaseAllocatedIfEnoughOnHand(
                tenantId, itemId, quantity
        );
        requireOneRowAffected(affectedRowCount, ErrorStatus.INCREASE_STOCK_ALLOCATED_FAIL);
    }

    @Override
    @Transactional
    public void decreaseAllocated(long tenantId, long itemId, int quantity) {
        int affectedRowCount = stockMapper.decreaseAllocatedIfEnough(
                tenantId, itemId, quantity
        );
        requireOneRowAffected(affectedRowCount, ErrorStatus.DECREASE_STOCK_ALLOCATED_FAIL);
    }


    /**
     * 기본 정렬 방향 정책
     * - 텍스트(ITEM_NAME, ITEM_CODE): ASC
     * - 수치/일자(그 외): DESC
     */
    private String resolveDefaultSortDirection(
            StockSortBy sortBy
    ) {
        if (sortBy == null) {
            return "DESC";
        }
        return switch (sortBy) {
            case ITEM_NAME, ITEM_CODE -> "ASC";
            default -> "DESC";
        };
    }
}
