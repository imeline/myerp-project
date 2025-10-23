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

    int save(@Param("stock") Stock stock);


    List<StockFindAllRow> findAllStockFindAllRow(@Param("filter") StockFindFilterRow filter);

    Optional<StockPriceFindRow> findStockPriceFindRowByItemId(
            @Param("itemId") long itemId
    );

    int updateWarehouse(@Param("itemId") long itemId,
                        @Param("warehouse") String warehouse);

    int increaseOnHand(@Param("itemId") long itemId,
                       @Param("delta") int delta);

    int decreaseOnHandIfEnough(@Param("itemId") long itemId,
                               @Param("delta") int delta);

    int increaseAllocatedIfEnoughOnHand(@Param("itemId") long itemId,
                                        @Param("quantity") int quantity);

    int decreaseAllocatedIfEnough(@Param("itemId") long itemId,
                                  @Param("quantity") int quantity);

    long countByStock(@Param("filter") StockFindFilterRow filter);

    StockMovementSummaryRow findMovementSummary(@Param("filter") StockMovementSearchFilter filter);

    List<StockMovementFindRow> findMovementRows(@Param("filter") StockMovementSearchFilter filter);

    long countMovementRows(@Param("filter") StockMovementSearchFilter filter);

    StockSummaryRow findStockSummaryRow();

    boolean existsPositiveByItemId(@Param("itemId") long itemId);
}
