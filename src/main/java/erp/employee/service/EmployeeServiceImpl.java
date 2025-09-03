package erp.employee.service;


import erp.department.mapper.DepartmentMapper;
import erp.employee.domain.Employee;
import erp.employee.dto.internal.EmployeeIdAndNameRow;
import erp.employee.dto.request.EmployeeSaveRequest;
import erp.employee.dto.response.EmployeeIdAndNameResponse;
import erp.employee.enums.EmployeeStatus;
import erp.employee.mapper.EmployeeMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.position.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static erp.global.util.RowCountGuards.requireOneRowAffected;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;
    private final PositionMapper positionMapper;

    @Override
    @Transactional
    public Long saveEmployee(EmployeeSaveRequest request, long companyId) {
        validEmpNoUnique(request.empNo(), companyId);
        validPhoneUnique(request.phone(), companyId);
        validDepartmentIdIfPresent(request.departmentId(), companyId);
        validPositionIdIfPresent(request.positionId(), companyId);

        long employeeId = employeeMapper.nextId();

        Employee employee = Employee.register(
                employeeId,
                request.empNo(),
                request.name(),
                request.phone(),
                EmployeeStatus.ACTIVE,
                request.departmentId(),
                request.positionId(),
                companyId
        );

        int affectedRowCount = employeeMapper.save(employee);
        requireOneRowAffected(affectedRowCount, ErrorStatus.CREATE_EMPLOYEE_FAIL);

        return employeeId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeIdAndNameResponse> findAllIdAndNameByCompanyId(long tenantId) {
        List<EmployeeIdAndNameRow> employeeIdAndNameRows =
                employeeMapper.findAllIdAndNameByTenantId(tenantId);

        if (employeeIdAndNameRows.isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_EMPLOYEE);
        }

        return employeeIdAndNameRows.stream()
                .map(EmployeeIdAndNameResponse::from)
                .toList();
    }

    private void validEmpNoUnique(String empNo, long tenantId) {
        if (employeeMapper.existsByEmpNo(tenantId, empNo)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_EMP_NO);
        }
    }

    private void validPhoneUnique(String phone, long tenantId) {
        if (employeeMapper.existsByPhone(tenantId, phone)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_PHONE);
        }
    }

    private void validDepartmentIdIfPresent(Long departmentId, long tenantId) {
        if (departmentId != null && !departmentMapper.existsById(tenantId,
                departmentId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_DEPARTMENT);
        }
    }

    private void validPositionIdIfPresent(Long positionId, long tenantId) {
        if (positionId != null && !positionMapper.existsById(tenantId,
                positionId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_POSITION);
        }
    }
}
