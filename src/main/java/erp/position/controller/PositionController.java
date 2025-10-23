package erp.position.controller;

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
        return ApiResponse.onSuccess(positionService.savePosition(request));
    }

    @GetMapping
    public ApiResponse<List<PositionFindAllResponse>> findAllPosition() {
        return ApiResponse.onSuccess(positionService.findAllPosition());
    }

    @PutMapping("/{positionId}/name")
    public ApiResponse<Void> updatePositionName(@PathVariable Long positionId,
                                                @Valid @RequestBody PositionNameRequest request) {
        positionService.updatePositionName(positionId, request);
        return ApiResponse.onSuccess(null);
    }

    @PutMapping("/{positionId}/level-no")
    public ApiResponse<Void> updatePositionLevelNo(@PathVariable Long positionId,
                                                   @Valid @RequestBody PositionLevelNoRequest request) {
        positionService.updatePositionLevelNo(positionId, request);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{positionId}")
    public ApiResponse<Void> deletePosition(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return ApiResponse.onSuccess(null);
    }
}
