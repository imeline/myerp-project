package erp.purchase.mapper;

import erp.purchase.domain.Purchase;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseMapper {
    long nextId();

    int save(long tenantId, Purchase purchase);
}
