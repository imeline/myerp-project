package erp.company.controller;

import erp.company.dto.internal.CompanyRow;
import erp.company.dto.request.AddCompanyRequest;
import erp.company.dto.request.GetCompanyListRequest;
import erp.company.dto.request.ModifyCompanyRequest;
import erp.company.dto.response.CompanyInfoResponse;
import erp.company.dto.response.CompanyListResponse;
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
    public BaseResponse<Long> addCompany(@Valid @RequestBody AddCompanyRequest request /*@AuthenticationPrincipal UserPrincipal user*/) {
        Long addCompanyId = companyService.addCompany(request);

        return BaseResponse.onSuccess(addCompanyId);
    }

    @GetMapping("/{companyId}")
    public BaseResponse<CompanyInfoResponse> getCompany(@PathVariable Long companyId) {
        return BaseResponse.onSuccess(companyService.getCompany(companyId));
    }

    @GetMapping
    public BaseResponse<CompanyListResponse<CompanyRow>> getCompanyList(
            @Valid @RequestBody GetCompanyListRequest request
    ) {
        return BaseResponse.onSuccess(companyService.listCompany(request));
    }

    @PutMapping("/{companyId}")
    public BaseResponse<Void> modifyCompany(@PathVariable Long companyId, @Valid @RequestBody ModifyCompanyRequest request) {
        companyService.modifyCompany(companyId, request);
        return BaseResponse.onSuccess(null);
    }

    @DeleteMapping("/{companyId}")
    public BaseResponse<Void> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
        return BaseResponse.onSuccess(null);
    }
}
