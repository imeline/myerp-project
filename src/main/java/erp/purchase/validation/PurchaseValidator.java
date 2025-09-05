package erp.purchase.validation;

public interface PurchaseValidator {
    void validPurchaseId(Long purchaseId, long tenantId);
}
