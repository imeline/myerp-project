package erp.auth.mapper;

import erp.auth.domain.ErpAccount;
import erp.auth.dto.internal.LoginRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ErpAccountMapper {
    // @Param -> MyBatis에서 SQL XML 파일로 파라미터 이름을 전달
    // 객체 하나 일때는 필요 없지만, 파라미터가 여러 개거나 단일 값(String, int 등)일 때는 필요
    ErpAccount findByUuid(@Param("uuid") String uuid);

    int save(ErpAccount account);

    long nextId();

    boolean existsByLoginEmail(@Param("loginEmail") String loginEmail);

    LoginRow findLoginRowByLoginEmail(
            @Param("loginEmail") String loginEmail
    );

    void softDeleteByCompanyId(@Param("companyId") Long companyId);
}
