package erp.employee.validation;

public interface EmployeeValidator {
    void validEmployeeIdIfPresent(Long employeeId, long tenantId);

    void validEmpNoUnique(String empNo, long tenantId);

    void validPhoneUnique(String phone, long tenantId);

    void validNoEmployeesInDepartment(Long departmentId, long tenantId);

    void validNoEmployeesInPosition(long positionId, long tenantId);
}
