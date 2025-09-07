package erp.stock.mapper;

import erp.stock.domain.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockMapper {

    long nextId();

    int save(@Param("tenantId") long tenantId, @Param("stock") Stock stock);
}
