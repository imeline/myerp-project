package erp.position.mapper;

import erp.position.domain.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PositionMapper {
    long nextId();

    int save(@Param("tenantId") long tenantId,
             @Param("position") Position position);

    List<Position> findAll(@Param("tenantId") long tenantId);

    // 아무 직급도 없을 경우, 0 반환
    int findLastLevelNo(@Param("tenantId") long tenantId);

    Optional<Integer> findLevelNoById(@Param("tenantId") long tenantId,
                                      @Param("positionId") long positionId);

    int updateNameById(@Param("tenantId") long tenantId,
                       @Param("positionId") long positionId,
                       @Param("name") String name);

    int updateLevelNoById(@Param("tenantId") long tenantId,
                          @Param("positionId") long positionId,
                          @Param("levelNo") int levelNo);

    // newLevelNo ~ (oldLevelNo - 1) 범위의 직급들을 +1 씩 증가시킴
    void shiftUpRange(@Param("tenantId") long tenantId,
                      @Param("newLevelNo") int newLevelNo,
                      @Param("oldLevelNo") int oldLevelNo);

    // (oldLevelNo + 1) ~ newLevelNo 범위의 직급들을 -1 씩 감소시킴
    void shiftDownRange(@Param("tenantId") long tenantId,
                        @Param("oldLevelNo") int oldLevelNo,
                        @Param("newLevelNo") int newLevelNo);

    int deleteById(@Param("tenantId") long tenantId,
                   @Param("positionId") long positionId);

    boolean existsByName(@Param("tenantId") long tenantId,
                         @Param("name") String name,
                         @Param("excludePositionId") Long excludePositionId);

    boolean existsById(@Param("tenantId") long tenantId,
                       @Param("positionId") long positionId);

}
