package erp.report.stock.movement.mapper;

import erp.report.stock.movement.dto.internal.StockMovementFindRow;
import erp.report.stock.movement.dto.internal.StockMovementSearchFilter;
import erp.report.stock.movement.dto.internal.StockMovementSummaryRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockMovementMapper {
    StockMovementSummaryRow findMovementSummary(@Param("tenantId") long tenantId,
                                                @Param("filter") StockMovementSearchFilter filter);

    List<StockMovementFindRow> findMovementRows(@Param("tenantId") long tenantId,
                                                @Param("filter") StockMovementSearchFilter filter);

    long countMovementRows(@Param("tenantId") long tenantId,
                           @Param("filter") StockMovementSearchFilter filter);

}
