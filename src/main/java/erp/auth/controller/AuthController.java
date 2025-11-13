package erp.auth.controller;

import erp.account.enums.ErpAccountRole;
import erp.auth.dto.request.LoginRequest;
import erp.auth.dto.request.RefreshTokenRequest;
import erp.auth.dto.request.SignupRequest;
import erp.auth.dto.response.LoginResponse;
import erp.auth.security.model.UserPrincipal;
import erp.auth.service.AuthService;
import erp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup/admin")
    public ApiResponse<Void> signupAdmin(@Valid @RequestBody SignupRequest request) {
        authService.signup(request, ErpAccountRole.ADMIN);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.onSuccess(authService.login(request));
    }

    /** 엑세스 토큰 재발급 */
    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ApiResponse.onSuccess(authService.refresh(request));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody RefreshTokenRequest request) {
        String uuid = principal.getUuid();
        authService.logout(uuid, request);
        return ApiResponse.onSuccess(null);
    }
}
