package erp.position.controller;

import erp.global.context.TenantContext;
import erp.global.response.ApiResponse;
import erp.position.dto.request.PositionLevelNoRequest;
import erp.position.dto.request.PositionNameRequest;
import erp.position.dto.response.PositionFindAllResponse;
import erp.position.service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/position")
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public ApiResponse<Long> savePosition(@Valid @RequestBody PositionNameRequest request) {
        long tenantId = TenantContext.get();
        return ApiResponse.onSuccess(positionService.savePosition(request, tenantId));
    }

    @GetMapping
    public ApiResponse<List<PositionFindAllResponse>> findAllPosition() {
        long tenantId = TenantContext.get();
        return ApiResponse.onSuccess(positionService.findAllPosition(tenantId));
    }

    @PutMapping("/{positionId}/name")
    public ApiResponse<Void> updatePositionName(@PathVariable Long positionId,
                                                @Valid @RequestBody PositionNameRequest request) {
        long tenantId = TenantContext.get();
        positionService.updatePositionName(positionId, request, tenantId);
        return ApiResponse.onSuccess(null);
    }

    @PutMapping("/{positionId}/level-no")
    public ApiResponse<Void> updatePositionLevelNo(@PathVariable Long positionId,
                                                   @Valid @RequestBody PositionLevelNoRequest request) {
        long tenantId = TenantContext.get();
        positionService.updatePositionLevelNo(positionId, request, tenantId);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{positionId}")
    public ApiResponse<Void> deletePosition(@PathVariable Long positionId) {
        long tenantId = TenantContext.get();
        positionService.deletePosition(positionId, tenantId);
        return ApiResponse.onSuccess(null);
    }
}
