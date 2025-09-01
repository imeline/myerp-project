package erp.item.controller;

import erp.global.dto.PageResponse;
import erp.global.response.BaseResponse;
import erp.global.tenant.TenantContext;
import erp.item.dto.internal.ItemFindRow;
import erp.item.dto.request.ItemFindAllRequest;
import erp.item.dto.request.ItemSaveRequest;
import erp.item.dto.request.ItemUpdateRequest;
import erp.item.dto.response.ItemInfoResponse;
import erp.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public BaseResponse<Long> saveItem(@Valid @RequestBody ItemSaveRequest request) {
        long tenantId = TenantContext.get();
        return BaseResponse.onSuccess(itemService.saveItem(request, tenantId));
    }

    @GetMapping("/{itemId}")
    public BaseResponse<ItemInfoResponse> findItem(@PathVariable long itemId) {
        long tenantId = TenantContext.get();
        return BaseResponse.onSuccess(itemService.findItem(itemId, tenantId));
    }

    @GetMapping
    public BaseResponse<PageResponse<ItemFindRow>> findAllItem(
            @Valid @RequestBody ItemFindAllRequest request) {
        long tenantId = TenantContext.get();
        return BaseResponse.onSuccess(itemService.findAllItems(request, tenantId));
    }

    @PutMapping("/{itemId}")
    public BaseResponse<Void> updateItem(@PathVariable long itemId,
                                         @Valid @RequestBody ItemUpdateRequest request) {
        long tenantId = TenantContext.get();
        itemService.updateItem(itemId, request, tenantId);
        return BaseResponse.onSuccess(null);
    }

    @DeleteMapping("/{itemId}")
    public BaseResponse<Void> softDeleteItem(@PathVariable long itemId) {
        long tenantId = TenantContext.get();
        itemService.softDeleteItem(itemId, tenantId);
        return BaseResponse.onSuccess(null);
    }
}
