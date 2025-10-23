package erp.account.service;

import erp.account.dto.internal.LoginUserInfoRow;
import erp.account.dto.request.ErpAccountSaveRequest;

public interface ErpAccountService {

    Long saveErpAccount(ErpAccountSaveRequest request);

    LoginUserInfoRow findLoginRowByLoginEmail(String loginEmail);

    void softDeleteById(long employeeId);
}
