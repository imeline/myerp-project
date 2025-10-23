package erp.log.mapper;

import erp.log.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LogMapper {

    long nextId();

    int save(@Param("log") Log log);

    int deleteOlderThan(@Param("type") String type,
                        @Param("days") int days);
}
