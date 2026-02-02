package erp.employee.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeFindRow(
        @NotBlank
        String name,
        @NotBlank
        String empNo,
        @NotNull
        Long departmentId,
        @NotBlank
        String departmentName,
        @NotNull
        Long positionId,
        @NotBlank
        String positionName,
        @NotBlank
        String phone
) {
}
