package erp.purchase.service;

import erp.purchase.dto.request.PurchaseSaveRequest;

public interface PurchaseService {
    long save(PurchaseSaveRequest request, long tenantId);
}
