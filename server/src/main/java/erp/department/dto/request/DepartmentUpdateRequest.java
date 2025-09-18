package erp.department.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DepartmentUpdateRequest(
        @NotBlank
        String name
) {
}
