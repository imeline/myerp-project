package erp.employee.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeUpdateRequest(
        @NotBlank
        String name,

        @NotBlank
        String empNo,

        @NotNull
        Long departmentId,

        @NotNull
        Long positionId,

        @NotBlank
        String phone
) {
}
