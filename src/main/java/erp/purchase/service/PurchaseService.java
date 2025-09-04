package erp.purchase.service;

import erp.global.response.PageResponse;
import erp.purchase.dto.request.PurchaseFindAllRequest;
import erp.purchase.dto.request.PurchaseItemFindAllRequest;
import erp.purchase.dto.request.PurchaseSaveRequest;
import erp.purchase.dto.response.PurchaseFindResponse;
import erp.purchase.dto.response.PurchaseItemFindResponse;

import java.util.List;

public interface PurchaseService {
    long savePurchaseAndPurchaseItems(
            PurchaseSaveRequest request, long tenantId);

    PageResponse<PurchaseFindResponse> findAllPurchase(
            PurchaseFindAllRequest request, long tenantId);

    List<PurchaseItemFindResponse> findAllPurchaseItems(
            PurchaseItemFindAllRequest request, long tenantId);
}
