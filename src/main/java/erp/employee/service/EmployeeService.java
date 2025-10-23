package erp.employee.service;

import erp.employee.dto.request.EmployeeFindAllRequest;
import erp.employee.dto.request.EmployeeSaveRequest;
import erp.employee.dto.request.EmployeeUpdateRequest;
import erp.employee.dto.response.EmployeeFindAllResponse;
import erp.employee.dto.response.EmployeeFindResponse;
import erp.employee.dto.response.EmployeeIdAndNameResponse;
import erp.global.response.PageResponse;

import java.util.List;

public interface EmployeeService {

    Long saveEmployee(EmployeeSaveRequest request);

    List<EmployeeIdAndNameResponse> findAllIdAndName();

    PageResponse<EmployeeFindAllResponse> findAllEmployees(
            EmployeeFindAllRequest request);

    EmployeeFindResponse findEmployee(long employeeId);

    void updateEmployee(long employeeId, EmployeeUpdateRequest request);

    void retireEmployee(long employeeId);
}
