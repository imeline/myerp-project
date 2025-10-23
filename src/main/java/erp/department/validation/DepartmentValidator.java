package erp.department.validation;

public interface DepartmentValidator {
    void validDepartmentIdIfPresent(Long departmentId);

    void validNameInParentUnique(Long parentId, String name, Long excludeId);

    void validNoChildDepartments(Long departmentId);
}
