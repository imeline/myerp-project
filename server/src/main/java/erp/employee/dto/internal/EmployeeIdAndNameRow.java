package erp.employee.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EmployeeIdAndNameRow(
        @NotNull
        Long employeeId,
        @NotBlank
        String name
) {
}
