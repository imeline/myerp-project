package erp.employee.mapper;

import erp.employee.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
    void saveEmployee(Employee employee);

    Long getNextEmployeeId();
}
