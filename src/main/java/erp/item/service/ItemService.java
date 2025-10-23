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
    Long saveItemAndStock(ItemSaveRequest request);

    ItemInfoResponse findItem(long itemId);

    PageResponse<ItemFindResponse> findAllItems(ItemFindAllRequest request);

    List<ItemOptionResponse> findAllItemOption();

    List<ItemPriceRow> findAllItemPriceByIds(List<Long> itemIds);

    void updateItem(long itemId, ItemUpdateRequest request);

    void softDeleteItem(long itemId);
}
