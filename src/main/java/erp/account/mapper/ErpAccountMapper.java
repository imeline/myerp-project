package erp.account.mapper;

import erp.account.domain.ErpAccount;
import erp.account.dto.internal.LoginUserInfoRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface ErpAccountMapper {

    long nextId();

    int save(ErpAccount account);

    Optional<Long> findCompanyIdByUuid(@Param("uuid") String uuid);

    Optional<LoginUserInfoRow> findLoginRowByLoginEmail(
            @Param("loginEmail") String loginEmail
    );

    Optional<ErpAccount> findByUuid(@Param("uuid") String uuid);

    void softDeleteByCompanyId(@Param("companyId") Long companyId);

    boolean existsByLoginEmail(@Param("loginEmail") String loginEmail);
}
