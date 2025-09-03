package erp.item.controller;

import erp.global.response.ApiResponse;
import erp.global.response.PageResponse;
import erp.global.tenant.TenantContext;
import erp.item.dto.request.ItemFindAllRequest;
import erp.item.dto.request.ItemSaveRequest;
import erp.item.dto.request.ItemUpdateRequest;
import erp.item.dto.response.ItemFindResponse;
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
    public ApiResponse<Long> saveItem(@Valid @RequestBody ItemSaveRequest request) {
        long tenantId = TenantContext.get();
        return ApiResponse.onSuccess(itemService.saveItem(request, tenantId));
    }

    @GetMapping("/{itemId}")
    public ApiResponse<ItemInfoResponse> findItem(@PathVariable long itemId) {
        long tenantId = TenantContext.get();
        return ApiResponse.onSuccess(itemService.findItem(itemId, tenantId));
    }

    @GetMapping
    public ApiResponse<PageResponse<ItemFindResponse>> findAllItem(
            @Valid @RequestBody ItemFindAllRequest request) {
        long tenantId = TenantContext.get();
        return ApiResponse.onSuccess(itemService.findAllItems(request, tenantId));
    }

    @PutMapping("/{itemId}")
    public ApiResponse<Void> updateItem(@PathVariable long itemId,
                                        @Valid @RequestBody ItemUpdateRequest request) {
        long tenantId = TenantContext.get();
        itemService.updateItem(itemId, request, tenantId);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{itemId}")
    public ApiResponse<Void> softDeleteItem(@PathVariable long itemId) {
        long tenantId = TenantContext.get();
        itemService.softDeleteItem(itemId, tenantId);
        return ApiResponse.onSuccess(null);
    }
}
