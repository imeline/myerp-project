package erp.account.service;

import erp.account.domain.ErpAccount;
import erp.account.dto.internal.LoginUserInfoRow;
import erp.account.dto.request.ErpAccountSaveRequest;
import erp.account.mapper.ErpAccountMapper;
import erp.account.validation.ErpAccountValidator;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.util.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static erp.global.util.RowCountGuards.requireOneRowAffected;

@Service
@RequiredArgsConstructor
public class ErpAccountServiceImpl implements ErpAccountService {

    private final ErpAccountMapper erpAccountMapper;
    private final PasswordEncoder passwordEncoder;
    private final ErpAccountValidator erpAccountValidator;

    @Override
    @Transactional
    public Long saveErpAccount(ErpAccountSaveRequest request, long companyId) {
        String loginEmail = Strings.normalizeOrNull(request.loginEmail());
        erpAccountValidator.validLoginEmailUnique(loginEmail);

        long erpAccountId = erpAccountMapper.nextId();
        String hashPassword = passwordEncoder.encode(request.rawPassword());

        ErpAccount account = ErpAccount.register(
                erpAccountId,
                request.employeeId(),
                loginEmail,
                hashPassword,
                request.role(),
                companyId
        );

        int affectedRowCount = erpAccountMapper.save(account);
        requireOneRowAffected(affectedRowCount, ErrorStatus.CREATE_ERP_ACCOUNT_FAIL);
        return erpAccountId;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginUserInfoRow findLoginRowByLoginEmail(String loginEmail) {
        String email = Strings.normalizeOrNull(loginEmail);
        return erpAccountMapper.findLoginRowByLoginEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorStatus.INVALID_LOGIN_CREDENTIALS));
    }

    @Override
    @Transactional(readOnly = true)
    public long findCompanyIdByUuid(String uuid) {
        return erpAccountMapper.findCompanyIdByUuid(uuid)
                .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_ERP_ACCOUNT));
    }
}
