package erp.department.dto.response;

import erp.department.dto.internal.DepartmentFindRow;

public record DepartmentInfoResponse(
        long departmentId,
        String name,
        boolean hasChildren   // ▶ 표시용
) {
    public static DepartmentInfoResponse from(DepartmentFindRow departmentFindRow) {
        return new DepartmentInfoResponse(
                departmentFindRow.departmentId(),
                departmentFindRow.name(),
                departmentFindRow.hasChildren() == 1
        );
    }
}
