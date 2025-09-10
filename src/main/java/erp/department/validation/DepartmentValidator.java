package erp.department.validation;

public interface DepartmentValidator {
    void validDepartmentIdIfPresent(Long departmentId, long tenantId);

    void validNameInParentUnique(long tenantId, Long parentId, String name, Long excludeId);

    void validNoChildDepartments(Long departmentId, long tenantId);
}
