package erp.department.controller;

import erp.department.dto.request.ChildDepartmentSaveRequest;
import erp.department.dto.request.DepartmentUpdateRequest;
import erp.department.dto.request.TopDepartmentSaveRequest;
import erp.department.dto.response.DepartmentInfoResponse;
import erp.department.service.DepartmentService;
import erp.global.response.ApiResponse;
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
    public ApiResponse<Long> saveTopDepartment(@RequestBody TopDepartmentSaveRequest request) {
        long tenantId = TenantContext.get();
        Long saveDepartmentId = departmentService.saveTopDepartment(request, tenantId);
        return ApiResponse.onSuccess(saveDepartmentId);
    }

    // 자식 부서 생성 (parent_id != NULL)
    @PostMapping("/child")
    public ApiResponse<Long> saveChildDepartment(@RequestBody ChildDepartmentSaveRequest request) {
        long tenantId = TenantContext.get();
        Long saveDepartmentId = departmentService.saveChildDepartment(request, tenantId);
        return ApiResponse.onSuccess(saveDepartmentId);
    }

    // 최상위(parent_id IS NULL)만
    @GetMapping("/top")
    public ApiResponse<List<DepartmentInfoResponse>> findAllTopDepartment() {
        long tenantId = TenantContext.get();
        List<DepartmentInfoResponse> departmentInfoResponse =
                departmentService.findAllTopLevelDepartment(tenantId);
        return ApiResponse.onSuccess(departmentInfoResponse);
    }

    // 특정 부모의 직계 자식
    @GetMapping("/child/{parentId}")
    public ApiResponse<List<DepartmentInfoResponse>> findChildrenDepartment(
            @PathVariable long parentId) {
        long tenantId = TenantContext.get();
        List<DepartmentInfoResponse> departmentInfoResponse =
                departmentService.findAllByParentId(parentId, tenantId);
        return ApiResponse.onSuccess(departmentInfoResponse);
    }

    @PutMapping("/{departmentId}")
    public ApiResponse<Void> updateDepartment(@PathVariable Long departmentId,
                                              @RequestBody DepartmentUpdateRequest request) {
        long tenantId = TenantContext.get();
        departmentService.updateDepartment(departmentId, request, tenantId);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{departmentId}")
    public ApiResponse<Void> deleteDepartment(@PathVariable Long departmentId) {
        long tenantId = TenantContext.get();
        departmentService.deleteDepartment(departmentId, tenantId);
        return ApiResponse.onSuccess(null);
    }
}
