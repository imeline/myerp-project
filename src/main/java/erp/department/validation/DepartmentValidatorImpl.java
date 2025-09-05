package erp.department.validation;

import erp.department.mapper.DepartmentMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentValidatorImpl implements DepartmentValidator {

    private final DepartmentMapper departmentMapper;

    @Override
    public void validDepartmentIdIfPresent(Long departmentId, long tenantId) {
        if (departmentId == null) return;
        if (!departmentMapper.existsById(tenantId, departmentId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_DEPARTMENT);
        }
    }

    @Override
    public void validNameInParentUnique(long tenantId, Long parentId, String name, Long excludeDepartmentId) {
        if (name != null && departmentMapper.existsByNameAndParentId(tenantId, parentId, name, excludeDepartmentId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_DEPARTMENT_NAME);
        }
    }

    @Override
    public void validNoChildDepartments(Long departmentId, long tenantId) {
        if (departmentMapper.existsChildById(tenantId, departmentId)) {
            throw new GlobalException(ErrorStatus.EXIST_CHILD_DEPARTMENT);
        }
    }
}
