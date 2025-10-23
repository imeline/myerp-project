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

    Optional<LoginUserInfoRow> findLoginRowByLoginEmail(
            @Param("loginEmail") String loginEmail
    );

    boolean existsByLoginEmail(@Param("loginEmail") String loginEmail);

    int softDeleteByEmployeeId(@Param("employeeId") long employeeId);
}
