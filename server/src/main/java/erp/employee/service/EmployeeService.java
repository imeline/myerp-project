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

    Long saveEmployee(EmployeeSaveRequest request, long companyId);

    List<EmployeeIdAndNameResponse> findAllIdAndName(long tenantId);

    PageResponse<EmployeeFindAllResponse> findAllEmployees(
        EmployeeFindAllRequest request, long tenantId);

    EmployeeFindResponse findEmployee(long employeeId, long tenantId);

    void updateEmployee(long employeeId, EmployeeUpdateRequest request, long tenantId);

    void retireEmployee(long employeeId, long tenantId);
}
