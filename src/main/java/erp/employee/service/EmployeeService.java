package erp.employee.service;

import erp.employee.dto.request.EmployeeSaveRequest;
import erp.employee.dto.response.EmployeeIdAndNameResponse;

import java.util.List;

public interface EmployeeService {
    Long saveEmployee(EmployeeSaveRequest request, long companyId);

    List<EmployeeIdAndNameResponse> findAllIdAndNameByCompanyId(long tenantId);
}
