package erp.employee.dto.internal;

public record EmployeeFindRow(
        String name,
        String empNo,
        String departmentName,
        String positionName,
        String phone
) {
}
