package erp.company.service;

import erp.company.dto.internal.CompanyRow;
import erp.company.dto.request.AddCompanyRequest;
import erp.company.dto.request.GetCompanyListRequest;
import erp.company.dto.request.ModifyCompanyRequest;
import erp.company.dto.response.CompanyInfoResponse;
import erp.company.dto.response.GetCompanyListResponse;

public interface CompanyService {
    void addCompany(AddCompanyRequest request);

    CompanyInfoResponse getCompany(long companyId);

    GetCompanyListResponse<CompanyRow> listCompany(GetCompanyListRequest request);

    void modifyCompany(ModifyCompanyRequest request);

    void deleteCompany(long companyId);
}
