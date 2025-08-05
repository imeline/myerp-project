package erp.auth.service;

import erp.auth.domain.UserPrincipal;
import erp.user.domain.ErpAccount;
import erp.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// 서비스 자체가 구현체 역할, serviceImpl 만들지 않음
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        ErpAccount account = userMapper.findByUuid(uuid);
        if (account == null) {
            throw new UsernameNotFoundException("해당 계정 없음");
        }
        return new UserPrincipal(account);
    }

}
