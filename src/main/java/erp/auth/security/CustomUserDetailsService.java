//package erp.auth.security;
//
//import erp.auth.domain.ErpAccount;
//import erp.auth.mapper.ErpAccountMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor

/// / 서비스 자체가 구현체 역할, serviceImpl 만들지 않음
//public class CustomUserDetailsService implements UserDetailsService {
//    private final ErpAccountMapper erpAccountMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
//        ErpAccount account = erpAccountMapper.findByUuid(uuid);
//        if (account == null) {
//            throw new UsernameNotFoundException("해당 계정 없음");
//        }
//        return new UserPrincipal(account);
//    }
//
//}
//public LoginResponse login(@RequestBody LoginRequest req) {
//    // 1) 시큐리티 표준 인증 흐름 실행 (여기서 UserDetailsService + PasswordEncoder 동작)
//    UsernamePasswordAuthenticationToken authReq =
//            new UsernamePasswordAuthenticationToken(req.loginEmail(), req.password());
//    Authentication auth = authenticationManager.authenticate(authReq);
//
//    // 2) 인증 성공 → Principal 꺼내기
//    UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
//
//    // 3) JWT 발급 (subject=uuid, role/tenant_id claim 포함)
//    String token = jwtTokenProvider.generateToken(principal);
//
//    // 4) 응답은 DB 원본값(=principal에서 안전 추출)으로 구성
//    return LoginResponse.from(token, principal.getUuid(), principal.getRole());
//}