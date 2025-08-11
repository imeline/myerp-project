package erp.auth.service;

import erp.auth.domain.ErpAccount;
import erp.auth.dto.SignupRequest;
import erp.auth.dto.request.LoginRequest;
import erp.auth.dto.response.LoginResponse;
import erp.auth.enums.ErpAccountRole;
import erp.auth.jwt.JwtTokenProvider;
import erp.auth.mapper.ErpAccountMapper;
import erp.auth.security.UserPrincipal;
import erp.employee.domain.Employee;
import erp.employee.mapper.EmployeeMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ErpAccountMapper accountMapper;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public void signup(SignupRequest dto, ErpAccountRole role) {
        validateLoginEmailDuplicate(dto.loginEmail());

        // Employee 생성
        long employeeId = employeeMapper.getNextEmployeeId();

        Employee employee = Employee.register(
                employeeId,
                dto.companyId(),
                dto.empNo(),
                dto.name(),
                dto.department(),
                dto.position(),
                dto.phone()
        );

        employeeMapper.saveEmployee(employee);

        // ErpAccount 생성
        long erpAccountId = accountMapper.getNextErpAccountId();

        ErpAccount account = ErpAccount.register(
                erpAccountId,
                dto.loginEmail(),
                passwordEncoder.encode(dto.loginPassword()),
                role,
                employee.getEmployeeId()
        );

        accountMapper.saveErpAccount(account);
    }


    // 로그인 이메일 중복 검사
    private void validateLoginEmailDuplicate(String loginEmail) {
        if (accountMapper.findByLoginEmail(loginEmail) != null) {
            throw new GlobalException(ErrorStatus.EXIST_LOGIN_EMAIL);
        }
    }

    @Transactional
    @Override
    public LoginResponse login(LoginRequest dto) {
        ErpAccount account = accountMapper.findByLoginEmail(dto.loginEmail());

        // 아이디 또는 비밀번호 가 잘못된 경우 예외 처리
        if (account == null || !passwordEncoder.matches(dto.password(),
                account.getPassword())) {
            throw new GlobalException(ErrorStatus.INVALID_LOGIN_CREDENTIALS);
        }

        // 로그인 성공 시 JWT 토큰 생성
        UserDetails userDetails = new UserPrincipal(account);
        String token = jwtTokenProvider.generateToken(userDetails);

        return LoginResponse.from(token, account.getUuid(), account.getRole());
    }
}
