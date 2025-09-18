package erp.stock.mapper;

import erp.stock.domain.Stock;
import erp.stock.dto.internal.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StockMapper {

    long nextId();

    int save(@Param("tenantId") long tenantId, @Param("stock") Stock stock);


    List<StockFindAllRow> findAllStockFindAllRow(@Param("tenantId") long tenantId,
                                                 @Param("filter") StockFindFilterRow filter);

    Optional<StockPriceFindRow> findStockPriceFindRowByItemId(
            @Param("tenantId") long tenantId,
            @Param("itemId") long itemId
    );

    int updateWarehouse(@Param("tenantId") long tenantId,
                        @Param("itemId") long itemId,
                        @Param("warehouse") String warehouse);

    int increaseOnHand(@Param("tenantId") long tenantId,
                       @Param("itemId") long itemId,
                       @Param("delta") int delta);

    int decreaseOnHandIfEnough(@Param("tenantId") long tenantId,
                               @Param("itemId") long itemId,
                               @Param("delta") int delta);

    int increaseAllocatedIfEnoughOnHand(@Param("tenantId") long tenantId,
                                        @Param("itemId") long itemId,
                                        @Param("quantity") int quantity);

    int decreaseAllocatedIfEnough(@Param("tenantId") long tenantId,
                                  @Param("itemId") long itemId,
                                  @Param("quantity") int quantity);

    long countByStock(@Param("tenantId") long tenantId,
                      @Param("filter") StockFindFilterRow filter);

    StockMovementSummaryRow findMovementSummary(@Param("tenantId") long tenantId,
                                                @Param("filter") StockMovementSearchFilter filter);

    List<StockMovementFindRow> findMovementRows(@Param("tenantId") long tenantId,
                                                @Param("filter") StockMovementSearchFilter filter);

    long countMovementRows(@Param("tenantId") long tenantId,
                           @Param("filter") StockMovementSearchFilter filter);

    StockSummaryRow findStockSummaryRow(@Param("tenantId") long tenantId);

    boolean existsPositiveByItemId(@Param("tenantId") long tenantId,
                                   @Param("itemId") long itemId);
}
