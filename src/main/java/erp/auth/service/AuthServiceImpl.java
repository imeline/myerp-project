package erp.auth.service;

import erp.auth.domain.ErpAccount;
import erp.auth.dto.internal.LoginRow;
import erp.auth.dto.request.LoginRequest;
import erp.auth.dto.request.SignupRequest;
import erp.auth.dto.response.LoginResponse;
import erp.auth.enums.ErpAccountRole;
import erp.auth.mapper.ErpAccountMapper;
import erp.auth.security.UserPrincipal;
import erp.auth.security.jwt.JwtTokenProvider;
import erp.employee.domain.Employee;
import erp.employee.enums.EmployeeStatus;
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
    public void signup(SignupRequest request, ErpAccountRole role) {
        validateLoginEmailUnique(request.loginEmail());

        // Employee 생성
        long employeeId = employeeMapper.nextId();

        Employee employee = Employee.register(
                employeeId,
                request.empNo(),
                request.name(),
                request.phone(),
                EmployeeStatus.ACTIVE,
                request.departmentId(),
                request.positionId(),
                request.companyId()
        );

        int affectedRowCount = employeeMapper.save(employee);
        assertAffected(affectedRowCount, ErrorStatus.SIGNUP_FAIL);

        // ErpAccount 생성
        long erpAccountId = accountMapper.nextId();

        ErpAccount account = ErpAccount.register(
                erpAccountId,
                employee.getEmployeeId(),
                request.loginEmail(),
                passwordEncoder.encode(request.password()),
                role,
                request.companyId()
        );

        affectedRowCount = accountMapper.save(account);
        assertAffected(affectedRowCount, ErrorStatus.SIGNUP_FAIL);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        final String email = request.loginEmail().trim().toLowerCase();
        // 왜 비밀 번호 검증 전에 jwt를 생성하기 위한 데이터도 같이(join) 조회했는가?
        // -> 실패까지 JOIN해도 단일 인덱스+PK 조인이라 성능상 손해 거의 없음
        // -> 두 단계 분리는 실패가 매우 많을 때만 이득, 정상 로그인은 오히려 느림
        final LoginRow row = accountMapper.findLoginRowByLoginEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorStatus.INVALID_LOGIN_CREDENTIALS));

        // 아이디 또는 비밀번호 가 잘못된 경우 예외 처리
        if (!passwordEncoder.matches(request.password(), row.passwordHash())) {
            throw new GlobalException(ErrorStatus.INVALID_LOGIN_CREDENTIALS);
        }

        // 로그인 성공 시 JWT 토큰 생성
        UserDetails userDetails = UserPrincipal.of(
                row.uuid(),
                row.role(),
                null, // 사용되지 않는 민감정보 제거
                row.tenantId()
        );
        String token = jwtTokenProvider.generateToken(userDetails);
        // 왜 userDetails가 아니라 LoginRow에서 데이터를 가져오는가?
        // -> 응답(LoginResponse)은 DB 조회 결과(row) 기준으로 채워야 책임이 명확함
        // -> UserPrincipal은 인증 컨텍스트 전용이므로 API 응답에 끌고 오면 역할이 섞임
        return LoginResponse.of(token, row.uuid(), row.role(), row.name());
    }

    // create, update, delete SQL 실패 검사
    private void assertAffected(int affected, ErrorStatus status) {
        if (affected != 1) {
            throw new GlobalException(status);
        }
    }

    // 로그인 이메일 중복 검사
    private void validateLoginEmailUnique(String loginEmail) {
        if (accountMapper.existsByLoginEmail(loginEmail)) {
            throw new GlobalException(ErrorStatus.EXIST_LOGIN_EMAIL);
        }
    }
}
