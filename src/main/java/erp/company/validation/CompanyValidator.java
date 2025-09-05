package erp.company.validation;

public interface CompanyValidator {
    void validCompanyIdIfPresent(Long companyId);

    void validBizNoUnique(String bizNo, Long excludeCompanyId);

    void validNameUnique(String name, Long excludeCompanyId);
}
