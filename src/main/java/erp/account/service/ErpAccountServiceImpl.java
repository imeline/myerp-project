package erp.account.service;

import erp.account.mapper.ErpAccountMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ErpAccountServiceImpl implements ErpAccountService {

    private final ErpAccountMapper erpAccountMapper;

    @Override
    @Transactional(readOnly = true)
    public long findCompanyIdByUuid(String uuid) {
        return erpAccountMapper.findCompanyIdByUuid(uuid)
                .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_ERP_ACCOUNT));
    }
}
