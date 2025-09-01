package erp.department.dto.response;

import erp.department.dto.internal.DepartmentFindRow;

public record DepartmentItemResponse(
        long departmentId,
        String name,
        boolean hasChildren   // ▶ 표시용
) {
    public static DepartmentItemResponse from(DepartmentFindRow departmentFindRow) {
        return new DepartmentItemResponse(
                departmentFindRow.departmentId(),
                departmentFindRow.name(),
                departmentFindRow.hasChildren() == 1
        );
    }
}
