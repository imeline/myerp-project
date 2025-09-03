package erp.auth.service;

import erp.account.enums.ErpAccountRole;
import erp.auth.dto.request.LoginRequest;
import erp.auth.dto.request.SignupRequest;
import erp.auth.dto.response.LoginResponse;

public interface AuthService {
    void signup(SignupRequest dto, ErpAccountRole role);

    LoginResponse login(LoginRequest dto);
}
