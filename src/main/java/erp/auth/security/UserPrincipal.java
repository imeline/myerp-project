package erp.auth.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "password") // 보안상 ToString에서 제외
public class UserPrincipal implements UserDetails {
    // 로그인 시 전부 주입
    private final String uuid;
    // GrantedAuthority가 String 기반이라 String으로 사용
    private final String role;
    @JsonIgnore // 외부에 노출되지 않게 보안
    private final String password;
    private final Long tenantId;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return uuid;
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

    public static UserPrincipal of(String uuid, String role,
                                   String password, Long tenantId) {

        // null/공백 방어 + 접두사 정규화
        String r = (role == null || role.isBlank()) ? "USER" : role.trim();
        String normalized = r.startsWith("ROLE_") ? r : "ROLE_" + r;

        return UserPrincipal.builder()
                .uuid(uuid)
                // security에서는 ROLE_ 접두사를 붙여야 인식하므로 (ex) ROLE_ADMIN 등)
                // ErpAccountRole의 이름에 ROLE_ 접두사를 붙임
                .role(normalized)
                .password(password)
                .tenantId(tenantId)
                .build();
    }
}
