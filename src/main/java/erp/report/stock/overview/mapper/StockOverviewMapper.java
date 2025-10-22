package erp.report.stock.overview.mapper;

import erp.report.stock.overview.dto.internal.StockOverviewItemRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StockOverviewMapper {
    
    // 품목별 (입고/출고/현재재고) 집계 - 요청 연/월 범위
    List<StockOverviewItemRow> findAllItemRowByMonth(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 전년 동월 비교용: 입고 총수량
    Integer sumInboundQuantityByMonth(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 전년 동월 비교용: 출고 총수량
    Integer sumOutboundQuantityByMonth(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
