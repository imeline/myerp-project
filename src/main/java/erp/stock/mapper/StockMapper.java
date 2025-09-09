package erp.stock.mapper;

import erp.stock.domain.Stock;
import erp.stock.dto.internal.StockFindAllRow;
import erp.stock.dto.internal.StockFindFilterRow;
import erp.stock.dto.internal.StockPriceFindRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StockMapper {

    long nextId();

    int save(@Param("tenantId") long tenantId, @Param("stock") Stock stock);


    List<StockFindAllRow> findAllStockFindAllRow(@Param("tenantId") long tenantId,
                                                 @Param("query") StockFindFilterRow query);

    Optional<StockPriceFindRow> findStockPriceFindRowByItemId(
            @Param("tenantId") long tenantId,
            @Param("itemId") long itemId
    );

    long countByStock(@Param("tenantId") long tenantId,
                      @Param("query") StockFindFilterRow query);
}
