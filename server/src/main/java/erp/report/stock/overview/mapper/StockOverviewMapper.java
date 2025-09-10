package erp.report.stock.overview.mapper;

import erp.report.stock.overview.dto.internal.StockOverviewRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockOverviewMapper {
    StockOverviewRow findStockOverviewRow(@Param("tenantId") long tenantId);
}
