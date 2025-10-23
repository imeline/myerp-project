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

    int save(@Param("inbound") Inbound inbound);

    List<InboundFindAllRow> findAllInboundFindAllRow(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code,
            @Param("offset") int offset,
            @Param("size") int size
    );

    List<InboundCancelItemRow> findAllCancelItemRowById(
            @Param("inboundId") long inboundId
    );

    int updateStatusToCanceledIfActive(
            @Param("inboundId") long inboundId
    );

    long countByPeriodAndCode(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code
    );

    boolean existsById(
            @Param("inboundId") long inboundId
    );

    boolean existsActiveById(
            @Param("inboundId") long inboundId
    );
}
