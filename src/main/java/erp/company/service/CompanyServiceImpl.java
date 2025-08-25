package erp.company.service;

import erp.company.domain.Company;
import erp.company.dto.request.AddCompanyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    @Override
    public void addCompany(AddCompanyRequest request) {
        Company company = Company.register(
                request.name(),
                request.bizNo(),
                request.address(),
                request.phone());

    }
}
