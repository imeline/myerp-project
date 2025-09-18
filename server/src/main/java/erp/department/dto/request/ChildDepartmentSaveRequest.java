package erp.department.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChildDepartmentSaveRequest(
        @NotNull
        Long parentId,
        @NotBlank
        String name
) {
}
