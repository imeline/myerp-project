package erp.account.validation;

import erp.account.mapper.ErpAccountMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErpAccountValidatorImpl implements ErpAccountValidator {

    private final ErpAccountMapper erpAccountMapper;

    @Override
    public void validLoginEmailUnique(String loginEmail) {
        if (loginEmail != null && erpAccountMapper.existsByLoginEmail(loginEmail)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_LOGIN_EMAIL);
        }
    }
}