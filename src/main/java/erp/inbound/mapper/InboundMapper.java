package erp.inbound.mapper;

import erp.inbound.domain.Inbound;
import erp.inbound.dto.internal.InboundCancelItemRow;
import erp.inbound.dto.internal.InboundFindAllRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface InboundMapper {

    long nextId();

    int save(@Param("tenantId") long tenantId,
             @Param("inbound") Inbound inbound);

    List<InboundFindAllRow> findAllInboundFindAllRow(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code,
            @Param("offset") int offset,
            @Param("size") int size
    );

    List<InboundCancelItemRow> findAllCancelItemRowById(
            @Param("tenantId") long tenantId,
            @Param("inboundId") long inboundId
    );

    int updateStatusToCanceledIfActive(
            @Param("tenantId") long tenantId,
            @Param("inboundId") long inboundId
    );

    long countByPeriodAndCode(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code
    );

    boolean existsById(
            @Param("tenantId") long tenantId,
            @Param("inboundId") long inboundId
    );

    boolean existsActiveById(
            @Param("tenantId") long tenantId,
            @Param("inboundId") long inboundId
    );
}
