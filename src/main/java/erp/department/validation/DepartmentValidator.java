package erp.department.validation;

public interface DepartmentValidator {
    void validDepartmentIdIfPresent(Long departmentId, long tenantId);

    void validNameInParentUnique(long tenantId, Long parentId, String name, Long excludeDepartmentId);

    void validNoChildDepartments(Long departmentId, long tenantId);
}
