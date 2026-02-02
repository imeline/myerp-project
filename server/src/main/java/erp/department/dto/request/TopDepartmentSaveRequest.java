package erp.department.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TopDepartmentSaveRequest(
        @NotBlank
        String name
) {
}
