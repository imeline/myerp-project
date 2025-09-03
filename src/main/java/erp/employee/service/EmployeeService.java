package erp.employee.service;

import erp.employee.dto.response.EmployeeIdAndNameResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeIdAndNameResponse> findAllIdAndNameByCompanyId(long tenantId);
}
