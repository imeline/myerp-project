package erp.department.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ModifyDepartmentRequest(
        Long parentId,
        @NotBlank
        String name
) {
}
