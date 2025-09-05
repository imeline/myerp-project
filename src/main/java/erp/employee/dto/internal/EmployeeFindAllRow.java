package erp.employee.dto.internal;

import java.time.LocalDateTime;

public record EmployeeFindAllRow(
        Long employeeId,
        String empNo,
        String name,
        String departmentName,
        String positionName,
        String phone,
        String email,
        LocalDateTime createdAt
) {
}
