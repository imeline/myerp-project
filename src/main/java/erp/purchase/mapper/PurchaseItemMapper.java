package erp.purchase.mapper;

import erp.purchase.domain.PurchaseItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseItemMapper {
    long nextId();

    int saveAll(List<PurchaseItem> items);
}
