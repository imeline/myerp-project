package erp.department.controller;

import erp.department.dto.request.ChildDepartmentSaveRequest;
import erp.department.dto.request.DepartmentUpdateRequest;
import erp.department.dto.request.TopDepartmentSaveRequest;
import erp.department.dto.response.DepartmentItemResponse;
import erp.department.service.DepartmentService;
import erp.global.response.BaseResponse;
import erp.global.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    // 루트 부서 생성 (parent_id = NULL)
    @PostMapping("/top")
    public BaseResponse<Long> saveTopDepartment(@RequestBody TopDepartmentSaveRequest request) {
        long tenantId = TenantContext.get();
        Long saveDepartmentId = departmentService.saveTopDepartment(request, tenantId);
        return BaseResponse.onSuccess(saveDepartmentId);
    }

    // 자식 부서 생성 (parent_id != NULL)
    @PostMapping("/child")
    public BaseResponse<Long> saveChildDepartment(@RequestBody ChildDepartmentSaveRequest request) {
        long tenantId = TenantContext.get();
        Long saveDepartmentId = departmentService.saveChildDepartment(request, tenantId);
        return BaseResponse.onSuccess(saveDepartmentId);
    }

    // 최상위(parent_id IS NULL)만
    @GetMapping("/top")
    public BaseResponse<List<DepartmentItemResponse>> findAllTopDepartment() {
        long tenantId = TenantContext.get();
        List<DepartmentItemResponse> departmentItemResponse =
                departmentService.findAllTopLevelDepartment(tenantId);
        return BaseResponse.onSuccess(departmentItemResponse);
    }

    // 특정 부모의 직계 자식
    @GetMapping("/child/{parentId}")
    public BaseResponse<List<DepartmentItemResponse>> findChildrenDepartment(
            @PathVariable long parentId) {
        long tenantId = TenantContext.get();
        List<DepartmentItemResponse> departmentItemResponse =
                departmentService.findAllByParentId(parentId, tenantId);
        return BaseResponse.onSuccess(departmentItemResponse);
    }

    @PutMapping("/{departmentId}")
    public BaseResponse<Void> updateDepartment(@PathVariable Long departmentId,
                                               @RequestBody DepartmentUpdateRequest request) {
        long tenantId = TenantContext.get();
        departmentService.updateDepartment(departmentId, request, tenantId);
        return BaseResponse.onSuccess(null);
    }
}
