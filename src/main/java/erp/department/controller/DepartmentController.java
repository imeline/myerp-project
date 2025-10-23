package erp.department.controller;

import erp.department.dto.request.ChildDepartmentSaveRequest;
import erp.department.dto.request.DepartmentUpdateRequest;
import erp.department.dto.request.TopDepartmentSaveRequest;
import erp.department.dto.response.DepartmentInfoResponse;
import erp.department.service.DepartmentService;
import erp.global.response.ApiResponse;
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
        return ApiResponse.onSuccess(
                departmentService.saveTopDepartment(request));
    }

    // 자식 부서 생성 (parent_id != NULL)
    @PostMapping("/child")
    public ApiResponse<Long> saveChildDepartment(@RequestBody ChildDepartmentSaveRequest request) {
        return ApiResponse.onSuccess(
                departmentService.saveChildDepartment(request));
    }

    // 최상위(parent_id IS NULL)만
    @GetMapping("/top")
    public ApiResponse<List<DepartmentInfoResponse>> findAllTopDepartment() {
        return ApiResponse.onSuccess(
                departmentService.findAllTopLevelDepartment());
    }

    // 특정 부모의 직계 자식
    @GetMapping("/child/{parentId}")
    public ApiResponse<List<DepartmentInfoResponse>> findChildrenDepartment(
            @PathVariable Long parentId) {
        return ApiResponse.onSuccess(
                departmentService.findAllByParentId(parentId));
    }

    @PutMapping("/{departmentId}")
    public ApiResponse<Void> updateDepartment(@PathVariable Long departmentId,
                                              @RequestBody DepartmentUpdateRequest request) {
        departmentService.updateDepartment(departmentId, request);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{departmentId}")
    public ApiResponse<Void> deleteDepartment(@PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ApiResponse.onSuccess(null);
    }
}
