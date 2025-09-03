package erp.item.service;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.response.PageResponse;
import erp.global.util.PageParam;
import erp.item.domain.Item;
import erp.item.dto.internal.ItemFindRow;
import erp.item.dto.internal.ItemIdAndNameRow;
import erp.item.dto.request.ItemFindAllRequest;
import erp.item.dto.request.ItemSaveRequest;
import erp.item.dto.request.ItemUpdateRequest;
import erp.item.dto.response.ItemFindResponse;
import erp.item.dto.response.ItemIdAndNameResponse;
import erp.item.dto.response.ItemInfoResponse;
import erp.item.enums.ItemCategory;
import erp.item.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static erp.global.util.RowCountGuards.requireOneRowAffected;
import static erp.global.util.Strings.normalizeOrNull;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public Long saveItem(ItemSaveRequest request, long tenantId) {
        String name = normalizeOrNull(request.name());
        String code = normalizeOrNull(request.code());
        ItemCategory category = request.category();

        validateNameUnique(name, null, tenantId);
        validateCodeUnique(code, null, tenantId);

        long newId = itemMapper.nextId();

        Item item = Item.of(
                newId,
                name,
                code,
                request.price(),
                request.unit(),
                category,
                tenantId
        );

        int affectedRowCount = itemMapper.save(tenantId, item);
        requireOneRowAffected(affectedRowCount, ErrorStatus.CREATE_ITEM_FAIL);

        return newId;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemInfoResponse findItem(long itemId, long tenantId) {
        Item item = itemMapper.findById(tenantId, itemId)
                .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_ITEM));
        return ItemInfoResponse.from(item);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ItemFindResponse> findAllItems(ItemFindAllRequest request, long tenantId) {
        PageParam pageParam = PageParam.of(request.page(), request.size(), 20);
        String name = normalizeOrNull(request.name());
        ItemCategory category = request.category(); // 검색 조건 없으면 null

        List<ItemFindRow> rows = itemMapper.findAllItemFindRow(
                tenantId, name, category, pageParam.offset(), pageParam.size());

        if (rows.isEmpty())
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_ITEM);

        List<ItemFindResponse> responses = rows.stream()
                .map(ItemFindResponse::from)
                .toList();

        long total = itemMapper.countByNameAndCategory(tenantId, name, category);
        return PageResponse.of(responses, pageParam.page(), total, pageParam.size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemIdAndNameResponse> findAllItemIdAndName(long tenantId) {
        List<ItemIdAndNameRow> rows = itemMapper.findAllIdAndNameByTenantId(tenantId);
        if (rows.isEmpty())
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_ITEM);
        return rows.stream()
                .map(ItemIdAndNameResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public void updateItem(long itemId, ItemUpdateRequest request, long tenantId) {
        // todo: 입고, 출고, 주문, 판매, 재고 등 연관 데이터(삭제된건 제외) 존재 여부 체크 추가 필요
        String name = normalizeOrNull(request.name());
        String code = normalizeOrNull(request.code());
        ItemCategory category = request.category();

        validateNameUnique(name, itemId, tenantId);
        validateCodeUnique(code, itemId, tenantId);

        Item item = Item.of(
                itemId,
                name,
                code,
                request.price(),
                request.unit(),
                category,
                tenantId
        );

        int affectedRowCount = itemMapper.updateById(tenantId, item);
        requireOneRowAffected(affectedRowCount, ErrorStatus.UPDATE_ITEM_FAIL);
    }

    @Override
    @Transactional
    public void softDeleteItem(long itemId, long tenantId) {
        // todo: 입고, 출고, 주문, 판매, 재고 등 연관 데이터(삭제된건 제외) 존재 여부 체크 추가 필요

        int affectedRowCount = itemMapper.softDeleteById(tenantId, itemId);
        requireOneRowAffected(affectedRowCount, ErrorStatus.DELETE_ITEM_FAIL);
    }

    private void validateNameUnique(String name, Long excludeId, long tenantId) {
        if (itemMapper.existsByName(tenantId, name, excludeId))
            throw new GlobalException(ErrorStatus.DUPLICATE_ITEM_NAME);
    }

    private void validateCodeUnique(String code, Long excludeId, long tenantId) {
        if (itemMapper.existsByCode(tenantId, code, excludeId))
            throw new GlobalException(ErrorStatus.DUPLICATE_ITEM_CODE);
    }
}
