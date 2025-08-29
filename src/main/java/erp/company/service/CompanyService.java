package erp.company.service;

import erp.company.dto.internal.CompanyRow;
import erp.company.dto.request.CompanyFindAllRequest;
import erp.company.dto.request.CompanySaveRequest;
import erp.company.dto.request.CompanyUpdateRequest;
import erp.company.dto.response.CompanyFindAllResponse;
import erp.company.dto.response.CompanyItemResponse;

public interface CompanyService {
    Long saveCompany(CompanySaveRequest request);

    CompanyItemResponse findCompany(long companyId);

    CompanyFindAllResponse<CompanyRow> findAllCompany(CompanyFindAllRequest request);

    void updateCompany(Long companyId, CompanyUpdateRequest request);

    void softDeleteCompany(long companyId);

}
