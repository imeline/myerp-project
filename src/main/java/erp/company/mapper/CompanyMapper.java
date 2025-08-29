package erp.company.mapper;

import erp.company.domain.Company;
import erp.company.dto.internal.CompanyRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {
    long nextId();

    int save(Company company);

    int update(Company company);

    Company findById(@Param("companyId") Long companyId);

    // excludeId는 제외하고 검사(자기 자신 제외)
    boolean existsByBizNo(@Param("bizNo") String bizNo, @Param("excludeId") Long excludeId);

    boolean existsByName(@Param("name") String name, @Param("excludeId") Long excludeId);

    List<CompanyRow> findAllCompanyRow(
            @Param("name") String name,
            @Param("offset") int offset,
            @Param("size") int size
    );

    long countByName(@Param("name") String name);

    int softDeleteById(@Param("companyId") Long companyId);

    Boolean isActive(@Param("companyId") Long companyId);

    // 연관 데이터 존재 여부
    long countEmployees(@Param("companyId") long companyId);

    long countOrders(@Param("companyId") long companyId);

    long countOutbounds(@Param("companyId") long companyId);
}
