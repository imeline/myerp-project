package erp.employee.validation;

public interface EmployeeValidator {
    void validEmployeeIdIfPresent(Long employeeId);

    void validEmpNoUnique(String empNo, Long excludeId);

    void validPhoneUnique(String phone, Long excludeId);

    void validNoEmployeesInDepartment(Long departmentId);

    void validNoEmployeesInPosition(long positionId);
}
