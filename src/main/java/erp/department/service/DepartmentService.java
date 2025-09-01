package erp.department.service;

import erp.department.dto.request.ChildDepartmentSaveRequest;
import erp.department.dto.request.DepartmentUpdateRequest;
import erp.department.dto.request.TopDepartmentSaveRequest;
import erp.department.dto.response.DepartmentInfoResponse;

import java.util.List;

public interface DepartmentService {
    long saveChildDepartment(ChildDepartmentSaveRequest request, long tenantId);

    long saveTopDepartment(TopDepartmentSaveRequest request, long tenantId);

    List<DepartmentInfoResponse> findAllTopLevelDepartment(
            long tenantId);

    List<DepartmentInfoResponse> findAllByParentId(
            long parentId, long tenantId);

    void updateDepartment(Long departmentId,
                          DepartmentUpdateRequest request, long tenantId);

    void deleteDepartment(Long departmentId, long tenantId);

}
