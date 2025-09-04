package erp.item.service;

import erp.global.response.PageResponse;
import erp.item.dto.request.ItemFindAllRequest;
import erp.item.dto.request.ItemSaveRequest;
import erp.item.dto.request.ItemUpdateRequest;
import erp.item.dto.response.ItemFindResponse;
import erp.item.dto.response.ItemIdAndNameResponse;
import erp.item.dto.response.ItemInfoResponse;

import java.util.List;

public interface ItemService {
    Long saveItem(ItemSaveRequest request, long tenantId);

    ItemInfoResponse findItem(long itemId, long tenantId);

    PageResponse<ItemFindResponse> findAllItems(ItemFindAllRequest request, long tenantId);

    List<ItemIdAndNameResponse> findAllItemIdAndName(long tenantId);

    void updateItem(long itemId, ItemUpdateRequest request, long tenantId);

    void softDeleteItem(long itemId, long tenantId);
}
