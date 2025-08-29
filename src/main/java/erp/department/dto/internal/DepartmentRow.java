package erp.department.dto.internal;

public record DepartmentRow(
        long departmentId,
        String name,
        int hasChildren   // 1 or 0 - 오라클 boolean 지원 안됨
) {
}
