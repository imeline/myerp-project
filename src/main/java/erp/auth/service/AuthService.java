package erp.auth.service;

import erp.auth.dto.request.LoginRequest;
import erp.auth.dto.request.SignupRequest;
import erp.auth.dto.response.LoginResponse;
import erp.auth.enums.ErpAccountRole;

public interface AuthService {
    void signup(SignupRequest dto, ErpAccountRole role);

    LoginResponse login(LoginRequest dto);
}
