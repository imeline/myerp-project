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

    int saveAll(@Param("items") List<PurchaseItem> items);

    List<PurchaseItemStockFindRow> findAllPurchaseItemStockFindRow(
            @Param("purchaseId") long purchaseId
    );

    List<PurchaseItemDetailRow> findAllPurchaseItemDetailRow(
            @Param("purchaseId") long purchaseId);

    List<PurchaseItemQuantityRow> findAllPurchaseItemQuantityRow(
            @Param("purchaseId") long purchaseId
    );
}
