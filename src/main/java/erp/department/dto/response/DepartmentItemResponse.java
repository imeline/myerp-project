package erp.department.dto.response;

import erp.department.dto.internal.DepartmentRow;

public record DepartmentItemResponse(
        long departmentId,
        String name,
        boolean hasChildren   // ▶ 표시용
) {
    public static DepartmentItemResponse from(DepartmentRow departmentRow) {
        return new DepartmentItemResponse(
                departmentRow.departmentId(),
                departmentRow.name(),
                departmentRow.hasChildren() == 1
        );
    }
}
