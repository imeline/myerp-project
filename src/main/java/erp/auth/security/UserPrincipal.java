package erp.auth.security;

import erp.auth.domain.ErpAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private final ErpAccount erpAccount;

    public UserPrincipal(ErpAccount erpAccount) {
        this.erpAccount = erpAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = erpAccount.getRole().name();
        // security에서는 ROLE_ 접두사를 붙여야 인식하므로 (ex) ROLE_ADMIN 등)
        // ErpAccountRole의 이름에 ROLE_ 접두사를 붙임
        return List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
    }

    @Override
    public String getPassword() {
        return erpAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return erpAccount.getUuid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 추가 정보(이메일, 이름, 부서 등) 가 필요할 때는
    // ErpAccount 전체 객체에 접근해야 하므로 생성
    public ErpAccount getErpAccount() {
        return erpAccount;
    }
}
