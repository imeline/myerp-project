package erp.account.service;

import erp.account.dto.request.ErpAccountSaveRequest;

public interface ErpAccountService {
    Long saveErpAccount(ErpAccountSaveRequest request, long companyId);

    long findCompanyIdByUuid(String uuid);
}
