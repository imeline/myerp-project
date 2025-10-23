package erp.department.service;

import erp.department.dto.request.ChildDepartmentSaveRequest;
import erp.department.dto.request.DepartmentUpdateRequest;
import erp.department.dto.request.TopDepartmentSaveRequest;
import erp.department.dto.response.DepartmentInfoResponse;

import java.util.List;

public interface DepartmentService {
    long saveChildDepartment(ChildDepartmentSaveRequest request);

    long saveTopDepartment(TopDepartmentSaveRequest request);

    List<DepartmentInfoResponse> findAllTopLevelDepartment();

    List<DepartmentInfoResponse> findAllByParentId(
            long parentId);

    void updateDepartment(Long departmentId,
                          DepartmentUpdateRequest request);

    void deleteDepartment(Long departmentId);

}
