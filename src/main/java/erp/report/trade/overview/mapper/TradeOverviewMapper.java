package erp.report.trade.overview.mapper;

import erp.report.trade.overview.dto.internal.TradeOverviewMonthlyAmountRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TradeOverviewMapper {
    List<TradeOverviewMonthlyAmountRow> findAllMonthlyOrderPurchaseAmountRow(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    Integer sumOrderAmountByPeriod(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    Integer sumPurchaseAmountByPeriod(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
