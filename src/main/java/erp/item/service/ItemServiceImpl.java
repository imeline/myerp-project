package erp.item.service;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.shared.dto.PageResponse;
import erp.item.domain.Item;
import erp.item.dto.internal.ItemFindRow;
import erp.item.dto.request.ItemFindAllRequest;
import erp.item.dto.request.ItemSaveRequest;
import erp.item.dto.request.ItemUpdateRequest;
import erp.item.dto.response.ItemInfoResponse;
import erp.item.enums.ItemCategory;
import erp.item.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        int affected = itemMapper.save(tenantId, item);
        assertAffected(affected, ErrorStatus.CREATE_ITEM_FAIL);

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
    public PageResponse<ItemFindRow> findAllItems(ItemFindAllRequest request, long tenantId) {
        int size = (request.size() == null || request.size() < 1) ? 20 : request.size();
        int page = (request.page() == null || request.page() < 0) ? 0 : request.page();
        int offset = page * size;

        String name = normalizeOrNull(request.name());
        ItemCategory category = request.category(); // null 허용 - 필터링 안 함

        List<ItemFindRow> rows = itemMapper.findAllItemFindRow(
                tenantId, name, category, offset, size);
        if (rows.isEmpty())
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_ITEM);

        long total = itemMapper.countByNameAndCategory(tenantId, name, category);
        return PageResponse.of(rows, page, total, size);
    }

    @Override
    @Transactional
    public void updateItem(long itemId, ItemUpdateRequest request, long tenantId) {
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

        int affected = itemMapper.updateById(tenantId, item);
        assertAffected(affected, ErrorStatus.UPDATE_ITEM_FAIL);
    }

    @Override
    @Transactional
    public void softDeleteItem(long itemId, long tenantId) {
        // todo: 입고, 출고, 주문, 판매, 재고 등 연관 데이터(삭제된건 제외) 존재 여부 체크 추가 필요

        int affected = itemMapper.softDeleteById(tenantId, itemId);
        assertAffected(affected, ErrorStatus.DELETE_ITEM_FAIL);
    }

    private void validateNameUnique(String name, Long excludeId, long tenantId) {
        if (itemMapper.existsByName(tenantId, name, excludeId))
            throw new GlobalException(ErrorStatus.DUPLICATE_ITEM_NAME);
    }

    private void validateCodeUnique(String code, Long excludeId, long tenantId) {
        if (itemMapper.existsByCode(tenantId, code, excludeId))
            throw new GlobalException(ErrorStatus.DUPLICATE_ITEM_CODE);
    }

    private void assertAffected(int affected, ErrorStatus status) {
        if (affected != 1) throw new GlobalException(status);
    }

    private String normalizeOrNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
