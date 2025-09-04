package erp.purchase.mapper;

import erp.purchase.domain.PurchaseItem;
import erp.purchase.dto.internal.PurchaseItemFindRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseItemMapper {
    long nextId();

    int saveAll(@Param("tenantId") long tenantId,
                @Param("items") List<PurchaseItem> items);

    List<PurchaseItemFindRow> findAllPurchaseItemFindRow(
            @Param("tenantId") long tenantId,
            @Param("purchaseId") long purchaseId
    );
}
