package erp.employee.service;


import erp.employee.dto.internal.EmployeeIdAndNameRow;
import erp.employee.dto.response.EmployeeIdAndNameResponse;
import erp.employee.mapper.EmployeeMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

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
