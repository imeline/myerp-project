package erp.stock.service;

public interface StockService {

    long saveStock(long itemId, int initialQuantity, String warehouse,
                   long tenantId);
}
