package erp.purchase.validation;

public interface PurchaseValidator {
    void validPurchaseIdIfPresent(Long purchaseId, long tenantId);
}
