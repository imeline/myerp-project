package erp.auth.domain;

import erp.user.domain.ErpAccount;
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
        return List.of(new SimpleGrantedAuthority(erpAccount.getRole()));
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
