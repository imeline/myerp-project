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
            @Param("tenantId") long tenantId);

    boolean existsByDepartmentId(@Param("tenantId") long tenantId,
                                 @Param("departmentId") long departmentId);

    boolean existsByPositionId(@Param("tenantId") long tenantId,
                               @Param("positionId") long positionId);

    boolean existsById(@Param("tenantId") long tenantId,
                       @Param("employeeId") long employeeId);

    boolean existsByEmpNo(@Param("tenantId") long tenantId,
                          @Param("empNo") String empNo);

    boolean existsByPhone(@Param("tenantId") long tenantId,
                          @Param("phone") String phone);
}
