package erp.user.mapper;

import erp.user.domain.ErpAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // @Param -> MyBatis에서 SQL XML 파일로 파라미터 이름을 전달
    ErpAccount findByUuid(@Param("uuid") String uuid);

}
