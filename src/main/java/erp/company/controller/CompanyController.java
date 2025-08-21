package erp.company.controller;

import erp.auth.security.UserPrincipal;
import erp.company.dto.request.AddCompanyRequest;
import erp.company.service.CompanyService;
import erp.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sys/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/add")
    public BaseResponse<Void> addCompany(@Valid @RequestBody AddCompanyRequest request, @AuthenticationPrincipal UserPrincipal user) {
        companyService.addCompany(request);

        return BaseResponse.onSuccess(null);
    }

    /*@GetMapping("/list")
    public BaseResponse<Void> getCompanyList() {

    }*/

}
