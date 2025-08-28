package erp.position.mapper;

import erp.position.domain.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PositionMapper {
    long nextId();

    int create(@Param("tenantId") Long tenantId,
               @Param("position") Position position);

    int update(@Param("tenantId") Long tenantId,
               @Param("position") Position position);

    Position findById(@Param("tenantId") Long tenantId,
                      @Param("positionId") Long positionId);

    boolean existsByName(@Param("tenantId") Long tenantId,
                         @Param("name") String name,
                         @Param("excludePositionId") Long excludePositionId);

    List<Position> findRows(@Param("tenantId") Long tenantId,
                            @Param("name") String name,
                            @Param("offset") int offset,
                            @Param("size") int size);

    long countByName(@Param("tenantId") Long tenantId,
                     @Param("name") String name);

    int deleteById(@Param("tenantId") Long tenantId,
                   @Param("positionId") Long positionId);
}
