package erp.company.controller;

import erp.company.dto.internal.CompanyRow;
import erp.company.dto.request.AddCompanyRequest;
import erp.company.dto.request.GetCompanyListRequest;
import erp.company.dto.request.ModifyCompanyRequest;
import erp.company.dto.response.CompanyInfoResponse;
import erp.company.dto.response.GetCompanyListResponse;
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
    public BaseResponse<Void> addCompany(@Valid @RequestBody AddCompanyRequest request /*@AuthenticationPrincipal UserPrincipal user*/) {
        companyService.addCompany(request);

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/{companyId}")
    public BaseResponse<CompanyInfoResponse> getCompany(@PathVariable long companyId) {
        return BaseResponse.onSuccess(companyService.getCompany(companyId));
    }

    @GetMapping
    public BaseResponse<GetCompanyListResponse<CompanyRow>> getCompanyList(
            @Valid @RequestBody GetCompanyListRequest request
    ) {
        return BaseResponse.onSuccess(companyService.listCompany(request));
    }

    @PutMapping
    public BaseResponse<Void> modifyCompany(@Valid @RequestBody ModifyCompanyRequest request) {
        companyService.modifyCompany(request);
        return BaseResponse.onSuccess(null);
    }

    @DeleteMapping("/{companyId}")
    public BaseResponse<Void> deleteCompany(@PathVariable long companyId) {
        companyService.deleteCompany(companyId);
        return BaseResponse.onSuccess(null);
    }
}
