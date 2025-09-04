package erp.purchase.controller;

import erp.global.response.ApiResponse;
import erp.global.response.PageResponse;
import erp.global.tenant.TenantContext;
import erp.purchase.dto.request.PurchaseFindAllRequest;
import erp.purchase.dto.request.PurchaseSaveRequest;
import erp.purchase.dto.response.PurchaseCodeAndSupplierResponse;
import erp.purchase.dto.response.PurchaseFindResponse;
import erp.purchase.dto.response.PurchaseItemFindResponse;
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

    /**
     * 발주 등록 (발주 + 발주아이템 저장)
     */
    @PostMapping
    public ApiResponse<Long> savePurchaseAndItems(@Valid @RequestBody PurchaseSaveRequest request) {
        long tenantId = TenantContext.get();
        long newPurchaseId = purchaseService.savePurchaseAndPurchaseItems(request, tenantId);
        return ApiResponse.onSuccess(newPurchaseId);
    }

    /**
     * 발주 목록 조회 (기간/상태/코드 검색 + 페이징, 삭제 제외)
     */
    @GetMapping
    public ApiResponse<PageResponse<PurchaseFindResponse>> findAllPurchases(
            @Valid @RequestBody PurchaseFindAllRequest request
    ) {
        long tenantId = TenantContext.get();
        PageResponse<PurchaseFindResponse> page = purchaseService.findAllPurchase(request, tenantId);
        return ApiResponse.onSuccess(page);
    }

    /**
     * 특정 발주의 발주아이템 목록 조회
     */
    @GetMapping("/{purchaseId}/items")
    public ApiResponse<List<PurchaseItemFindResponse>> findAllPurchaseItem(
            @PathVariable long purchaseId
    ) {
        long tenantId = TenantContext.get();
        List<PurchaseItemFindResponse> responses = purchaseService.findAllPurchaseItems(purchaseId, tenantId);
        return ApiResponse.onSuccess(responses);
    }

    /**
     * 발주번호·공급사 목록 (CONFIRMED만)
     */
    @GetMapping("/code-suppliers")
    public ApiResponse<List<PurchaseCodeAndSupplierResponse>> findAllPurchaseCodeAndSupplier() {
        long tenantId = TenantContext.get();
        List<PurchaseCodeAndSupplierResponse> responses = purchaseService.findAllPurchaseCodeAndSupplier(tenantId);
        return ApiResponse.onSuccess(responses);
    }

    /**
     * 발주 취소(삭제) — status를 CANCELLED로 변경. SHIPPED이면 불가
     */
    @DeleteMapping("/{purchaseId}")
    public ApiResponse<Void> cancelPurchase(@PathVariable long purchaseId) {
        long tenantId = TenantContext.get();
        purchaseService.cancelPurchase(purchaseId, tenantId);
        return ApiResponse.onSuccess(null);
    }
}
