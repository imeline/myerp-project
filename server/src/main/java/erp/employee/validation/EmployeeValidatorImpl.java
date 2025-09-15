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
    public void validEmployeeIdIfPresent(Long employeeId, long tenantId) {
        if (employeeId == null) return;
        if (!employeeMapper.existsActiveById(tenantId, employeeId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_EMPLOYEE);
        }
    }

    @Override
    public void validEmpNoUnique(String empNo,
                                 long tenantId, Long excludeId) {
        if (empNo != null && employeeMapper.existsByEmpNo(tenantId, empNo, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_EMP_NO);
        }
    }

    @Override
    public void validPhoneUnique(String phone, long tenantId, Long excludeId) {
        if (phone != null && employeeMapper.existsByPhone(tenantId, phone, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_PHONE);
        }
    }

    @Override
    public void validNoEmployeesInDepartment(Long departmentId, long tenantId) {
        if (employeeMapper.existsByDepartmentId(tenantId, departmentId)) {
            throw new GlobalException(ErrorStatus.EXIST_EMPLOYEE_IN_DEPARTMENT);
        }
    }

    @Override
    public void validNoEmployeesInPosition(long positionId, long tenantId) {
        if (employeeMapper.existsByPositionId(tenantId, positionId)) {
            throw new GlobalException(ErrorStatus.EXIST_EMPLOYEE_IN_POSITION);
        }
    }
}