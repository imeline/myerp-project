package erp.department.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepartmentFindRow(
        @NotNull
        long departmentId,
        @NotBlank
        String name,
        @NotNull
        int hasChildren   // 1 or 0 - 오라클 boolean 지원 안됨
) {
}
