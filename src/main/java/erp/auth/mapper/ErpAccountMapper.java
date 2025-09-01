package erp.auth.mapper;

import erp.auth.domain.ErpAccount;
import erp.auth.dto.internal.LoginRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface ErpAccountMapper {

    long nextId();

    int save(ErpAccount account);

    Optional<LoginRow> findLoginRowByLoginEmail(
            @Param("loginEmail") String loginEmail
    );

    Optional<ErpAccount> findByUuid(@Param("uuid") String uuid);

    void softDeleteByCompanyId(@Param("companyId") Long companyId);

    boolean existsByLoginEmail(@Param("loginEmail") String loginEmail);
}
