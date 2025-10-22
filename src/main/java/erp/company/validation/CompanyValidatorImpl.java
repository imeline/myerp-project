package erp.company.validation;

import erp.company.mapper.CompanyMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyValidatorImpl implements CompanyValidator {

    private final CompanyMapper companyMapper;

    @Override
    public void validCompanyIdIfPresent(Long companyId) {
        if (companyId == null) return;
        if (!companyMapper.existsCompanyById(companyId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_COMPANY);
        }
    }

    @Override
    public void validBizNoUnique(String bizNo, Long excludeId) {
        if (bizNo != null && companyMapper.existsByBizNo(bizNo, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_BIZ_NO);
        }
    }

    @Override
    public void validNameUnique(String name, Long excludeId) {
        if (name != null && companyMapper.existsByName(name, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_NAME);
        }
    }
}
