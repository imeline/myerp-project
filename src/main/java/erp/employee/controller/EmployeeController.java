package erp.employee.controller;

import erp.account.enums.ErpAccountRole;
import erp.auth.dto.request.SignupRequest;
import erp.auth.service.AuthService;
import erp.employee.dto.request.EmployeeFindAllRequest;
import erp.employee.dto.request.EmployeeUpdateRequest;
import erp.employee.dto.response.EmployeeFindAllResponse;
import erp.employee.dto.response.EmployeeFindResponse;
import erp.employee.dto.response.EmployeeIdAndNameResponse;
import erp.employee.service.EmployeeService;
import erp.global.response.ApiResponse;
import erp.global.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthService authService;

    @PostMapping
    public ApiResponse<Void> saveEmployeeAndAccount(@Valid @RequestBody SignupRequest request) {
        authService.signup(request, ErpAccountRole.USER);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/id-name")
    public ApiResponse<List<EmployeeIdAndNameResponse>> findAllIdAndName() {
        return ApiResponse.onSuccess(employeeService.findAllIdAndName());
    }

    @GetMapping
    public ApiResponse<PageResponse<EmployeeFindAllResponse>> findAllEmployees(
            @Valid @RequestBody EmployeeFindAllRequest request) {
        return ApiResponse.onSuccess(
                employeeService.findAllEmployees(request));
    }

    @GetMapping("/{employeeId}")
    public ApiResponse<EmployeeFindResponse> findEmployee(@PathVariable Long employeeId) {
        return ApiResponse.onSuccess(employeeService.findEmployee(employeeId));
    }

    @PutMapping("/{employeeId}")
    public ApiResponse<Void> updateEmployee(@PathVariable Long employeeId,
                                            @Valid @RequestBody EmployeeUpdateRequest request) {
        employeeService.updateEmployee(employeeId, request);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/{employeeId}/retire")
    public ApiResponse<Void> retireEmployee(@PathVariable Long employeeId) {
        employeeService.retireEmployee(employeeId);
        return ApiResponse.onSuccess(null);
    }
}
