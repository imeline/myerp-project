package erp.global.config;


import erp.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    // 인증(로그인) 처리 컨포넌트 - 사용자의 아이디, 비밀번호를 검사해서 인증 여부를 판단
    public AuthenticationProvider authenticationProvider() {
        // DB 기반으로 인증 처리할 때 사용
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 사용자 정보는 이 서비스로부터 불러옴
        provider.setUserDetailsService(customUserDetailsService);
        // 비밀번호는 BCrypt로 암호화해서 비교
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    // 인증 처리를 직접 담당하는 매니저
    // 스프링 시큐리티가 내부적으로 사용할 수 있도록 빈으로 등록함
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}