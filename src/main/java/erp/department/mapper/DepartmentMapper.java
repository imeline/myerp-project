package erp.department.mapper;

import erp.department.domain.Department;
import erp.department.dto.internal.DepartmentFindRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DepartmentMapper {
    long nextId();

    int save(@Param("tenantId") Long tenantId,
             @Param("department") Department department);

    int updateById(@Param("tenantId") Long tenantId,
                   @Param("department") Department department);

    Optional<Department> findById(@Param("tenantId") Long tenantId,
                                  @Param("departmentId") Long departmentId);

    List<DepartmentFindRow> findAllTopLevelDepartmentRow(
            @Param("tenantId") long tenantId);

    List<DepartmentFindRow> findAllDepartmentRowByParentId(
            @Param("tenantId") long tenantId,
            @Param("parentId") long parentId);


    int deleteById(@Param("tenantId") Long tenantId,
                   @Param("departmentId") Long departmentId);

    boolean existsByNameAndParentId(@Param("tenantId") Long tenantId,
                                    // 한 부모 안에 같은 이름의 부서가 있는지 검사(수정 시 자기 자신은 제외)
                                    @Param("parentId") Long parentId,
                                    @Param("name") String name,
                                    @Param("excludeDepartmentId") Long excludeDepartmentId);

    boolean existsById(@Param("tenantId") Long tenantId,
                       @Param("departmentId") Long departmentId);

    boolean existsChildById(@Param("tenantId") Long tenantId,
                            @Param("departmentId") Long departmentId);
    // 해당 테넌트의 모든 부서 조회
//    List<DepartmentRowResponse> findAllDepartmentRow(@Param("tenantId") long tenantId);
}
