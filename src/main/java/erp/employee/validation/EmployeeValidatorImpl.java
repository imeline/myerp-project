package erp.employee.validation;

import erp.employee.mapper.EmployeeMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeValidatorImpl implements EmployeeValidator {

    private final EmployeeMapper employeeMapper;

    @Override
    public void validEmployeeIdIfPresent(Long employeeId) {
        if (employeeId == null) return;
        if (!employeeMapper.existsActiveById(employeeId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_EMPLOYEE);
        }
    }

    @Override
    public void validEmpNoUnique(String empNo, Long excludeId) {
        if (empNo != null && employeeMapper.existsByEmpNo(empNo, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_EMP_NO);
        }
    }

    @Override
    public void validPhoneUnique(String phone, Long excludeId) {
        if (phone != null && employeeMapper.existsByPhone(phone, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_PHONE);
        }
    }

    @Override
    public void validNoEmployeesInDepartment(Long departmentId) {
        if (employeeMapper.existsByDepartmentId(departmentId)) {
            throw new GlobalException(ErrorStatus.EXIST_EMPLOYEE_IN_DEPARTMENT);
        }
    }

    @Override
    public void validNoEmployeesInPosition(long positionId) {
        if (employeeMapper.existsByPositionId(positionId)) {
            throw new GlobalException(ErrorStatus.EXIST_EMPLOYEE_IN_POSITION);
        }
    }
}