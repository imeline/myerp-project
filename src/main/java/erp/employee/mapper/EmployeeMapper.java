package erp.employee.mapper;

import erp.employee.domain.Employee;
import erp.employee.dto.internal.EmployeeIdAndNameRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    long nextId();

    int save(Employee employee);

    List<EmployeeIdAndNameRow> findAllIdAndNameByTenantId(
            @Param("tenantId") Long tenantId);

    boolean existsByDepartmentId(@Param("tenantId") Long tenantId,
                                 @Param("departmentId") Long departmentId);

    boolean existsByPositionId(@Param("tenantId") Long tenantId,
                               @Param("positionId") Long positionId);
}
