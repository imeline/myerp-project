package erp.employee.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EmployeeFindAllRow(
        @NotNull
        Long employeeId,
        @NotBlank
        String empNo,
        @NotBlank
        String name,
        @NotBlank
        String departmentName,
        @NotBlank
        String positionName,
        @NotBlank
        String phone,
        @NotBlank
        String email,
        @NotNull
        LocalDateTime createdAt
) {
}
