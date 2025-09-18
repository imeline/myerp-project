package erp.item.service;

import erp.global.response.PageResponse;
import erp.item.dto.internal.ItemPriceRow;
import erp.item.dto.request.ItemFindAllRequest;
import erp.item.dto.request.ItemSaveRequest;
import erp.item.dto.request.ItemUpdateRequest;
import erp.item.dto.response.ItemFindResponse;
import erp.item.dto.response.ItemInfoResponse;
import erp.item.dto.response.ItemOptionResponse;

import java.util.List;

public interface ItemService {
    Long saveItemAndStock(ItemSaveRequest request, long tenantId);

    ItemInfoResponse findItem(long itemId, long tenantId);

    PageResponse<ItemFindResponse> findAllItems(ItemFindAllRequest request, long tenantId);

    List<ItemOptionResponse> findAllItemOption(long tenantId);

    List<ItemPriceRow> findAllItemPriceByIds(
            List<Long> itemIds, long tenantId);

    void updateItem(long itemId, ItemUpdateRequest request, long tenantId);

    void softDeleteItem(long itemId, long tenantId);
}
