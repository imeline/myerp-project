package erp.purchase.validation;

import erp.purchase.dto.request.PurchaseItemSaveRequest;

import java.util.List;

public interface PurchaseValidator {
    void validPurchaseIdIfPresent(long purchaseId, long tenantId);

    void validItemIdsUniqueInRequest(List<PurchaseItemSaveRequest> requestItems);

    void validNoConfirmByItemId(long itemId, long tenantId);
}
