package erp.company.controller;

import erp.company.dto.internal.CompanyRow;
import erp.company.dto.request.CompanyFindAllRequest;
import erp.company.dto.request.CompanySaveRequest;
import erp.company.dto.request.CompanyUpdateRequest;
import erp.company.dto.response.CompanyFindAllResponse;
import erp.company.dto.response.CompanyItemResponse;
import erp.company.service.CompanyService;
import erp.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sys/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public BaseResponse<Long> saveCompany(@Valid @RequestBody CompanySaveRequest request /*@AuthenticationPrincipal UserPrincipal user*/) {
        Long addCompanyId = companyService.saveCompany(request);

        return BaseResponse.onSuccess(addCompanyId);
    }

    @GetMapping("/{companyId}")
    public BaseResponse<CompanyItemResponse> findCompany(@PathVariable Long companyId) {
        return BaseResponse.onSuccess(companyService.findCompany(companyId));
    }

    @GetMapping
    public BaseResponse<CompanyFindAllResponse<CompanyRow>> findAllCompany(
            @Valid @RequestBody CompanyFindAllRequest request
    ) {
        return BaseResponse.onSuccess(companyService.findAllCompany(request));
    }

    @PutMapping("/{companyId}")
    public BaseResponse<Void> updateCompany(@PathVariable Long companyId, @Valid @RequestBody CompanyUpdateRequest request) {
        companyService.updateCompany(companyId, request);
        return BaseResponse.onSuccess(null);
    }

    @DeleteMapping("/{companyId}")
    public BaseResponse<Void> softDeleteCompany(@PathVariable Long companyId) {
        companyService.softDeleteCompany(companyId);
        return BaseResponse.onSuccess(null);
    }
}
