package erp.auth.controller;

import erp.auth.dto.SignupRequest;
import erp.auth.dto.request.LoginRequest;
import erp.auth.dto.response.LoginResponse;
import erp.auth.enums.ErpAccountRole;
import erp.auth.service.AuthService;
import erp.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup/sys")
    public BaseResponse<Void> signupSys(@Valid @RequestBody SignupRequest request) {
        // 역할 지정 위치 - controller vs service vs 프론트 요청 dto
        // * sevice가 안되는 이유 -> 서비스는 “어떤 역할인지”에 대해서는 관심을 두지 않고,
        // 그 역할에 맞는 가입 로직만 수행하도록 만들면 관심사가 분리해야 함
        // * 프론트 요청이 안되는 이유 -> 악의적인 사용자가 요청 바디를 조작 가능. 보안상 위험
        authService.signup(request, ErpAccountRole.SYS);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/signup/admin")
    public BaseResponse<Void> signupAdmin(@Valid @RequestBody SignupRequest request) {
        authService.signup(request, ErpAccountRole.ADMIN);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/signup/user")
    public BaseResponse<Void> signupUser(@Valid @RequestBody SignupRequest request) {
        authService.signup(request, ErpAccountRole.USER);
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse dto = authService.login(request);
        return BaseResponse.onSuccess(dto);
    }
}
