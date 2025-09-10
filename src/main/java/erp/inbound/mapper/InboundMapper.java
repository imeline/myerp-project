package erp.inbound.mapper;

import erp.inbound.domain.Inbound;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InboundMapper {

    long nextId();

    int save(@Param("tenantId") long tenantId,
             @Param("inbound") Inbound inbound);
}
