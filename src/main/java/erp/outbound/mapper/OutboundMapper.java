package erp.outbound.mapper;

import erp.outbound.domain.Outbound;
import erp.outbound.dto.internal.OutboundCancelItemRow;
import erp.outbound.dto.internal.OutboundFindRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OutboundMapper {

    long nextId();

    int save(@Param("outbound") Outbound outbound);

    List<OutboundFindRow> findAllOutboundFindRow(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code,
            @Param("offset") int offset,
            @Param("size") int size
    );

    List<OutboundCancelItemRow> findAllCancelItemRowById(@Param("outboundId") long outboundId);

    int updateStatusToCanceledIfActive(@Param("outboundId") long outboundId);

    long countByPeriodAndCode(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code
    );

    boolean existsById(@Param("outboundId") long outboundId);

}
