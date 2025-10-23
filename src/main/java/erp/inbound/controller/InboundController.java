package erp.inbound.controller;

import erp.global.response.ApiResponse;
import erp.global.response.PageResponse;
import erp.inbound.dto.request.InboundFindAllRequest;
import erp.inbound.dto.request.InboundSaveRequest;
import erp.inbound.dto.response.InboundFindAllResponse;
import erp.inbound.service.InboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/inbound")
public class InboundController {

    private final InboundService inboundService;

    @PostMapping
    public ApiResponse<Long> saveInbound(@Valid @RequestBody InboundSaveRequest request) {
        return ApiResponse.onSuccess(inboundService.saveInbound(request));
    }

    @GetMapping
    public ApiResponse<PageResponse<InboundFindAllResponse>> findAllInbound(
            @Valid @RequestBody InboundFindAllRequest request) {
        return ApiResponse.onSuccess(inboundService.findAllInbound(request));
    }

    @DeleteMapping("/{inboundId}")
    public ApiResponse<Void> cancelInbound(@PathVariable("inboundId") long inboundId) {
        inboundService.cancelInbound(inboundId);
        return ApiResponse.onSuccess(null);
    }
}
