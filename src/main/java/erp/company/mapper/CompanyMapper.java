package erp.company.mapper;

import erp.company.domain.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CompanyMapper {
    Long createCompany(Company company);

    Long updateCompany(Company company);

    Long deleteCompany(Long companyId);

    Optional<Company> findById(@Param("companyId") Long companyId);

    Boolean existsByBizNo(@Param("bizNo") String bizNo, @Param("excludeId") Long excludeId);

    Boolean existsByName(@Param("name") String name, @Param("excludeId") Long excludeId);

    List<Company> findPage(
            @Param("keyword") String keyword,
            @Param("offset") int offset,
            @Param("size") int size
    );

    long count(@Param("keyword") String keyword);
}
