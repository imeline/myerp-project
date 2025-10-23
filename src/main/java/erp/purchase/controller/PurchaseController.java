package erp.purchase.controller;

import erp.global.response.ApiResponse;
import erp.global.response.PageResponse;
import erp.purchase.dto.request.PurchaseFindAllRequest;
import erp.purchase.dto.request.PurchaseSaveRequest;
import erp.purchase.dto.response.PurchaseCodeAndSupplierResponse;
import erp.purchase.dto.response.PurchaseDetailResponse;
import erp.purchase.dto.response.PurchaseFindAllResponse;
import erp.purchase.dto.response.PurchaseItemsSummaryResponse;
import erp.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ApiResponse<Long> savePurchaseAndItems(@Valid @RequestBody PurchaseSaveRequest request) {
        return ApiResponse.onSuccess(
                purchaseService.savePurchaseAndPurchaseItems(request));
    }

    @GetMapping
    public ApiResponse<PageResponse<PurchaseFindAllResponse>> findAllPurchases(
            @Valid @RequestBody PurchaseFindAllRequest request
    ) {
        return ApiResponse.onSuccess(
                purchaseService.findAllPurchase(request));
    }

    @GetMapping("/{purchaseId}/items/summary")
    public ApiResponse<PurchaseItemsSummaryResponse> findPurchaseItemsSummary(
            @PathVariable long purchaseId) {
        return ApiResponse.onSuccess(
                purchaseService.findPurchaseItemsSummary(purchaseId));
    }

    @GetMapping("/code-suppliers")
    public ApiResponse<List<PurchaseCodeAndSupplierResponse>> findAllPurchaseCodeAndSupplier() {
        return ApiResponse.onSuccess(
                purchaseService.findAllPurchaseCodeAndSupplier());
    }

    @GetMapping("/{purchaseId}")
    public ApiResponse<PurchaseDetailResponse> findPurchaseDetail(@PathVariable Long purchaseId) {
        return ApiResponse.onSuccess(
                purchaseService.findPurchaseDetail(purchaseId));
    }

    @DeleteMapping("/{purchaseId}")
    public ApiResponse<Void> cancelPurchase(@PathVariable Long purchaseId) {
        purchaseService.cancelPurchase(purchaseId);
        return ApiResponse.onSuccess(null);
    }
}
