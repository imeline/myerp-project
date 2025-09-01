package erp.employee.mapper;

import erp.employee.domain.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeMapper {
    long nextId();

    int save(Employee employee);

    boolean existsByDepartmentId(@Param("tenantId") Long tenantId,
                                 @Param("departmentId") Long departmentId);

    boolean existsByPositionId(@Param("tenantId") Long tenantId,
                               @Param("positionId") Long positionId);
}
