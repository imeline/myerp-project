package erp.item.controller;

import erp.global.response.ApiResponse;
import erp.global.response.PageResponse;
import erp.item.dto.request.ItemFindAllRequest;
import erp.item.dto.request.ItemSaveRequest;
import erp.item.dto.request.ItemUpdateRequest;
import erp.item.dto.response.ItemFindResponse;
import erp.item.dto.response.ItemInfoResponse;
import erp.item.dto.response.ItemOptionResponse;
import erp.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ApiResponse<Long> saveItemAndStock(@Valid @RequestBody ItemSaveRequest request) {
        return ApiResponse.onSuccess(itemService.saveItemAndStock(request));
    }

    @GetMapping("/{itemId}")
    public ApiResponse<ItemInfoResponse> findItem(@PathVariable Long itemId) {
        return ApiResponse.onSuccess(itemService.findItem(itemId));
    }

    @GetMapping
    public ApiResponse<PageResponse<ItemFindResponse>> findAllItem(
            @Valid @RequestBody ItemFindAllRequest request) {
        return ApiResponse.onSuccess(itemService.findAllItems(request));
    }

    @GetMapping("/options")
    public ApiResponse<List<ItemOptionResponse>> findAllItemOption() {
        return ApiResponse.onSuccess(itemService.findAllItemOption());
    }

    @PutMapping("/{itemId}")
    public ApiResponse<Void> updateItem(@PathVariable Long itemId,
                                        @Valid @RequestBody ItemUpdateRequest request) {
        itemService.updateItem(itemId, request);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{itemId}")
    public ApiResponse<Void> softDeleteItem(@PathVariable Long itemId) {
        itemService.softDeleteItem(itemId);
        return ApiResponse.onSuccess(null);
    }
}
