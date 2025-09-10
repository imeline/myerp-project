package erp.purchase.mapper;

import erp.purchase.domain.PurchaseItem;
import erp.purchase.dto.internal.PurchaseItemDetailRow;
import erp.purchase.dto.internal.PurchaseItemQuantityRow;
import erp.purchase.dto.internal.PurchaseItemStockFindRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseItemMapper {

    long nextId();

    int saveAll(@Param("tenantId") long tenantId,
                @Param("items") List<PurchaseItem> items);

    List<PurchaseItemStockFindRow> findAllPurchaseItemStockFindRow(
            @Param("tenantId") long tenantId,
            @Param("purchaseId") long purchaseId
    );

    List<PurchaseItemDetailRow> findAllPurchaseItemDetailRow(
            @Param("tenantId") long tenantId,
            @Param("purchaseId") long purchaseId);

    List<PurchaseItemQuantityRow> findAllPurchaseItemQuantityRow(
            @Param("tenantId") long tenantId,
            @Param("purchaseId") long purchaseId
    );
}
