// AuthServiceImpl.java
package erp.auth.service;

import erp.account.dto.internal.LoginUserInfoRow;
import erp.account.dto.request.ErpAccountSaveRequest;
import erp.account.enums.ErpAccountRole;
import erp.account.service.ErpAccountService;
import erp.auth.dto.request.LoginRequest;
import erp.auth.dto.request.SignupRequest;
import erp.auth.dto.response.LoginResponse;
import erp.auth.security.jwt.JwtTokenProvider;
import erp.auth.security.model.UserPrincipal;
import erp.company.validation.CompanyValidator;
import erp.employee.dto.request.EmployeeSaveRequest;
import erp.employee.service.EmployeeService;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.log.audit.Auditable;
import erp.log.enums.LogType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmployeeService employeeService;
    private final ErpAccountService erpAccountService;
    private final CompanyValidator companyValidator;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signup(SignupRequest request, ErpAccountRole role) {
        // 회원가입이 아닌 경우는 security 에서 검사하니까 회원가입 때만 따로 검사
        companyValidator.validCompanyIdIfPresent(request.companyId());

        // Employee 생성
        EmployeeSaveRequest employeeSaveRequest =
                EmployeeSaveRequest.of(
                        request.empNo(),
                        request.name(),
                        request.phone(),
                        request.departmentId(),
                        request.positionId()
                );
        long employeeId = employeeService.saveEmployee(
                employeeSaveRequest, request.companyId());

        // ErpAccount 생성
        ErpAccountSaveRequest erpAccountSaveRequest =
                ErpAccountSaveRequest.of(
                        employeeId,
                        request.loginEmail(),
                        request.password(),
                        role
                );
        erpAccountService.saveErpAccount(erpAccountSaveRequest, request.companyId());
    }

    @Auditable(type = LogType.LOGIN, messageEl = "'로그인 처리: ' + #args[0].loginEmail()")
    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        final String email = request.loginEmail().trim().toLowerCase();
        // 왜 비밀 번호 검증 전에 jwt를 생성하기 위한 데이터도 같이(join) 조회했는가?
        // -> 실패까지 JOIN해도 단일 인덱스+PK 조인이라 성능상 손해 거의 없음
        // -> 두 단계 분리는 실패가 매우 많을 때만 이득, 정상 로그인은 오히려 느림
        final LoginUserInfoRow row = erpAccountService.findLoginRowByLoginEmail(email);

        // 아이디 또는 비밀번호 가 잘못된 경우 예외 처리
        if (!passwordEncoder.matches(request.password(), row.passwordHash())) {
            throw new GlobalException(ErrorStatus.INVALID_LOGIN_CREDENTIALS);
        }

        // 로그인 성공 시 JWT 토큰 생성
        UserDetails userDetails = UserPrincipal.of(
                row.uuid(),
                row.role(),
                null, // 사용되지 않는 민감정보 제거
                row.uuid(),
                row.name(),
                row.tenantId()
        );
        String token = jwtTokenProvider.generateToken(userDetails);
        // 왜 userDetails가 아니라 LoginRow에서 데이터를 가져오는가?
        // -> 응답(LoginResponse)은 DB 조회 결과(row) 기준으로 채워야 책임이 명확함
        // -> UserPrincipal은 인증 컨텍스트 전용이므로 API 응답에 끌고 오면 역할이 섞임
        return LoginResponse.of(token, row.uuid(), row.role(), row.name());
    }
}
