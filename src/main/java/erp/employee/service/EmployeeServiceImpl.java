// EmployeeServiceImpl.java
package erp.employee.service;

import erp.department.validation.DepartmentValidator;
import erp.employee.domain.Employee;
import erp.employee.dto.internal.EmployeeIdAndNameRow;
import erp.employee.dto.request.EmployeeSaveRequest;
import erp.employee.dto.response.EmployeeIdAndNameResponse;
import erp.employee.enums.EmployeeStatus;
import erp.employee.mapper.EmployeeMapper;
import erp.employee.validation.EmployeeValidator;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.position.validation.PositionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static erp.global.util.RowCountGuards.requireOneRowAffected;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    // Employee 는 많은 다른 도메인에서 참조되므로,
    // 순환 참조를 방지하기 위해 다른 서비스를 주입하지 않음
    private final EmployeeMapper employeeMapper;
    private final DepartmentValidator departmentValidator;
    private final PositionValidator positionValidator;
    private final EmployeeValidator employeeValidator;

    @Override
    @Transactional
    public Long saveEmployee(EmployeeSaveRequest request, long companyId) {
        employeeValidator.validEmpNoUnique(request.empNo(), companyId);
        employeeValidator.validPhoneUnique(request.phone(), companyId);
        departmentValidator.validDepartmentIdIfPresent(request.departmentId(), companyId);
        positionValidator.validPositionIdIfPresent(request.positionId(), companyId);

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
}
