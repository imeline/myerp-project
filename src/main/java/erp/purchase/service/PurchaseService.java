package erp.purchase.service;

import erp.purchase.dto.request.PurchaseSaveRequest;

public interface PurchaseService {
    long savePurchaseAndPurchaseItems(PurchaseSaveRequest request, long tenantId);
}
