package erp.purchase.service;

import erp.global.response.PageResponse;
import erp.purchase.dto.internal.PurchaseItemQuantityRow;
import erp.purchase.dto.request.PurchaseFindAllRequest;
import erp.purchase.dto.request.PurchaseSaveRequest;
import erp.purchase.dto.response.PurchaseCodeAndSupplierResponse;
import erp.purchase.dto.response.PurchaseDetailResponse;
import erp.purchase.dto.response.PurchaseFindAllResponse;
import erp.purchase.dto.response.PurchaseItemsSummaryResponse;

import java.util.List;

public interface PurchaseService {

    long savePurchaseAndPurchaseItems(
            PurchaseSaveRequest request);

    PageResponse<PurchaseFindAllResponse> findAllPurchase(
            PurchaseFindAllRequest request);

    PurchaseItemsSummaryResponse findPurchaseItemsSummary(long purchaseId);

    List<PurchaseCodeAndSupplierResponse> findAllPurchaseCodeAndSupplier();

    PurchaseDetailResponse findPurchaseDetail(long purchaseId);

    void cancelPurchase(long purchaseId);

    List<PurchaseItemQuantityRow> findAllPurchaseItemQuantityRow(long purchaseId);

    void updateStatusToShippedIfConfirmed(long purchaseId);

    void updateStatusToConfirmedIfShipped(long purchaseId);
}
