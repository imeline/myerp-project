package erp.department.dto.response;

import erp.department.domain.Department;
import lombok.Builder;

@Builder
public record GetDepartmentResponse(
        long departmentId,
        String name,
        Long parentId
) {
    public static GetDepartmentResponse from(Department department) {
        return GetDepartmentResponse.builder()
                .departmentId(department.getDepartmentId())
                .name(department.getName())
                .parentId(department.getParentId() != null ? department.getParentId() : null)
                .build();
    }
}
