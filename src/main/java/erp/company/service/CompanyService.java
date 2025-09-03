package erp.company.service;

import erp.company.dto.request.CompanyFindAllRequest;
import erp.company.dto.request.CompanySaveRequest;
import erp.company.dto.request.CompanyUpdateRequest;
import erp.company.dto.response.CompanyFindResponse;
import erp.company.dto.response.CompanyInfoResponse;
import erp.global.shared.dto.PageResponse;

public interface CompanyService {
    Long saveCompany(CompanySaveRequest request);

    CompanyInfoResponse findCompany(long companyId);

    PageResponse<CompanyFindResponse> findAllCompany(CompanyFindAllRequest request);

    void updateCompany(Long companyId, CompanyUpdateRequest request);

    void softDeleteCompany(long companyId);

}
