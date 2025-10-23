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
    public void validDepartmentIdIfPresent(Long departmentId) {
        if (departmentId == null) return;
        if (!departmentMapper.existsById(departmentId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_DEPARTMENT);
        }
    }

    @Override
    public void validNameInParentUnique(Long parentId, String name, Long excludeId) {
        if (name != null && departmentMapper.existsByNameAndParentId(parentId, name, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_DEPARTMENT_NAME);
        }
    }

    @Override
    public void validNoChildDepartments(Long departmentId) {
        if (departmentMapper.existsChildById(departmentId)) {
            throw new GlobalException(ErrorStatus.EXIST_CHILD_DEPARTMENT);
        }
    }
}
