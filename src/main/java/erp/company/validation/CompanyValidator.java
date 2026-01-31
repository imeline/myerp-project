package erp.company.validation;

public interface CompanyValidator {
    void validCompanyIdIfPresent(Long companyId);

    void validBizNoUnique(String bizNo, Long excludeId);

    void validNameUnique(String name, Long excludeId);
}
