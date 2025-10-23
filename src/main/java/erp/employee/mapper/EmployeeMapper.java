package erp.employee.mapper;

import erp.employee.domain.Employee;
import erp.employee.dto.internal.EmployeeFindAllRow;
import erp.employee.dto.internal.EmployeeFindRow;
import erp.employee.dto.internal.EmployeeIdAndNameRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeMapper {

    long nextId();

    int save(Employee employee);

    List<EmployeeIdAndNameRow> findAllIdAndName();

    List<EmployeeFindAllRow> findAllEmployeeFindRow(@Param("departmentId") Long departmentId,
                                                    @Param("positionId") Long positionId,
                                                    @Param("name") String name,
                                                    @Param("offset") int offset,
                                                    @Param("size") int size);

    Optional<EmployeeFindRow> findEmployeeFindRowById(@Param("employeeId") long employeeId);

    int updateById(@Param("employee") Employee employee);

    int updateStatusToRetired(@Param("employeeId") long employeeId);

    long countByFilters(@Param("departmentId") Long departmentId,
                        @Param("positionId") Long positionId,
                        @Param("name") String name);

    boolean existsByDepartmentId(@Param("departmentId") long departmentId);

    boolean existsByPositionId(@Param("positionId") long positionId);

    boolean existsActiveById(@Param("employeeId") long employeeId);

    boolean existsAnyById(@Param("employeeId") long employeeId);

    boolean existsByEmpNo(@Param("empNo") String empNo,
                          @Param("excludeId") Long excludeId);

    boolean existsByPhone(@Param("phone") String phone,
                          @Param("excludeId") Long excludeId);
}
