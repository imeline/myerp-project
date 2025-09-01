package erp.department.service;

import erp.department.domain.Department;
import erp.department.dto.request.ChildDepartmentSaveRequest;
import erp.department.dto.request.DepartmentUpdateRequest;
import erp.department.dto.request.TopDepartmentSaveRequest;
import erp.department.dto.response.DepartmentItemResponse;
import erp.department.mapper.DepartmentMapper;
import erp.employee.mapper.EmployeeMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper EmployeeMapper;

    @Override
    @Transactional
    // 부모가 있는 부서 생성
    public long saveChildDepartment(ChildDepartmentSaveRequest request,
                                    long tenantId) {
        long newDepartmentId = departmentMapper.nextId();
        String name = request.name();
        Long requestedParentId = request.parentId();

        // 중복 이름 검사
        validNameInParentUnique(tenantId, requestedParentId, name, null);
        // 존재하는 부모 id인지 검사
        validParentIdIfPresent(requestedParentId, tenantId);

        Department department = Department.of(
                newDepartmentId, name, tenantId, requestedParentId);

        int affectedRowCount = departmentMapper.save(tenantId, department);
        assertAffected(affectedRowCount, ErrorStatus.CREATE_DEPARTMENT_FAIL);
        return newDepartmentId;
    }

    @Override
    @Transactional
    // 부모가 없는 루트 부서 생성
    public long saveTopDepartment(TopDepartmentSaveRequest request,
                                  long tenantId) {
        long newDepartmentId = departmentMapper.nextId();

        String name = request.name();
        Long parentId = null; // 루트 부서는 parent_id 가 NULL

        validNameInParentUnique(tenantId, parentId, name, null);

        Department department = Department.of(
                newDepartmentId,
                name,
                tenantId,
                null
        );

        int affectedRowCount = departmentMapper.save(tenantId, department);
        assertAffected(affectedRowCount, ErrorStatus.CREATE_DEPARTMENT_FAIL);

        return newDepartmentId;
    }

    // 첫 페이지 로딩: 최상위(parent_id IS NULL)만
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentItemResponse> findAllTopLevelDepartment(long tenantId) {
        List<DepartmentItemResponse> list =
                departmentMapper.findAllTopLevelDepartmentRow(tenantId)
                        .stream().map(DepartmentItemResponse::from).toList();
        if (list.isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_DEPARTMENT);
        }
        return list;
    }

    // 펼침 클릭: 특정 parent의 직계 자식만
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentItemResponse> findAllByParentId(long parentId,
                                                          long tenantId) {
        List<DepartmentItemResponse> list =
                departmentMapper.findAllDepartmentRowByParentId(tenantId, parentId)
                        .stream().map(DepartmentItemResponse::from).toList();
        if (list.isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_DEPARTMENT);
        }
        return list;
    }

    @Override
    @Transactional
    public void updateDepartment(Long departmentId,
                                 DepartmentUpdateRequest request,
                                 long tenantId) {

        Department curDepartment = departmentMapper.findById(tenantId, departmentId)
                .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_DEPARTMENT));
        Long parentId = curDepartment.getParentId();
        String name = request.name();
        validNameInParentUnique(tenantId, parentId, name, departmentId);

        Department department = Department.of(departmentId, name, tenantId, parentId);
        int affectedRowCount = departmentMapper.updateById(tenantId, department);
        assertAffected(affectedRowCount, ErrorStatus.UPDATE_DEPARTMENT_FAIL);
    }

    @Override
    @Transactional
    public void deleteDepartment(Long departmentId, long tenantId) {
        validChildIfPresent(departmentId, tenantId);
        validEmployeeInDepartmentIfPresent(departmentId, tenantId);

        int affectedRowCount = departmentMapper.deleteById(tenantId, departmentId);
        assertAffected(affectedRowCount, ErrorStatus.DELETE_DEPARTMENT_FAIL);
    }

    // 중복 이름 검사
    private void validNameInParentUnique(long tenantId, Long parentId,
                                         String name, Long excludeDepartmentId) {
        if (name != null && departmentMapper.existsByNameAndParentId(
                tenantId, parentId, name, excludeDepartmentId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_DEPARTMENT_NAME);
        }
    }

    // 존재하는 부모 id인지 검사
    private void validParentIdIfPresent(Long parentId, long tenantId) {
        if (parentId != null && !departmentMapper.existsById(tenantId,
                parentId)) {
            throw new GlobalException(ErrorStatus.NO_FOUND_PARENT_DEPARTMENT);
        }
    }

    // 하위 부서가 있는지 검사
    private void validChildIfPresent(Long departmentId, long tenantId) {
        if (departmentMapper.existsChildById(tenantId, departmentId)) {
            throw new GlobalException(ErrorStatus.EXIST_CHILD_DEPARTMENT);
        }
    }

    // 해당 부서에 속한 직원이 있는지 검사
    private void validEmployeeInDepartmentIfPresent(Long departmentId, long tenantId) {
        if (EmployeeMapper.existsByDepartmentId(tenantId, departmentId)) {
            throw new GlobalException(ErrorStatus.EXIST_EMPLOYEE_IN_DEPARTMENT);
        }
    }

    private void assertAffected(int affected, ErrorStatus status) {
        if (affected != 1) {
            throw new GlobalException(status);
        }
    }
}
