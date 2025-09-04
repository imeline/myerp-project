package erp.account.service;

import erp.account.domain.ErpAccount;
import erp.account.dto.request.ErpAccountSaveRequest;
import erp.account.mapper.ErpAccountMapper;
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

    @Override
    @Transactional
    public Long saveErpAccount(ErpAccountSaveRequest request, long companyId) {
        String loginEmail = Strings.normalizeOrNull(request.loginEmail());
        validateLoginEmailUnique(loginEmail);

        long erpAccountId = erpAccountMapper.nextId();
        String hashPassword = passwordEncoder.encode(request.rawPassword());

        // 3) 도메인 생성 및 저장
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
    public long findCompanyIdByUuid(String uuid) {
        return erpAccountMapper.findCompanyIdByUuid(uuid)
                .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_ERP_ACCOUNT));
    }

    // 로그인 이메일 중복 검사
    private void validateLoginEmailUnique(String loginEmail) {
        if (erpAccountMapper.existsByLoginEmail(loginEmail)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_LOGIN_EMAIL);
        }
    }
}
