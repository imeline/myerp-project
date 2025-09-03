package erp.item.service;

import erp.global.shared.dto.PageResponse;
import erp.item.dto.request.ItemFindAllRequest;
import erp.item.dto.request.ItemSaveRequest;
import erp.item.dto.request.ItemUpdateRequest;
import erp.item.dto.response.ItemFindResponse;
import erp.item.dto.response.ItemInfoResponse;

public interface ItemService {
    Long saveItem(ItemSaveRequest request, long tenantId);

    ItemInfoResponse findItem(long itemId, long tenantId);

    PageResponse<ItemFindResponse> findAllItems(ItemFindAllRequest request, long tenantId);

    void updateItem(long itemId, ItemUpdateRequest request, long tenantId);

    void softDeleteItem(long itemId, long tenantId);
}
