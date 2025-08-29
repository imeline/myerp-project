package erp.global.config;

import erp.auth.security.CompanyActiveGuardFilter;
import erp.auth.security.jwt.JwtAuthenticationFilter;
import erp.global.exception.RestAccessDeniedHandler;
import erp.global.exception.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 활성화
@EnableMethodSecurity // @PreAuthorize, @Secured 같은 메서드 레벨 보안을 사용
@RequiredArgsConstructor
public class SecurityConfig {

    // *사용자의 로그인 정보 검증
    // oauth, FormLogin 같은 security 기능을 사용하지 않으니
    // JWT 발급으로 끝, 세션·쿠키 안 씀
    // Security의 로그인 프로세스를 탈 필요가 없어서 없앰
    // private final AuthenticationProvider authenticationProvider;

    // 클라이언트로부터 온 요청에 JWT가 포함됐는지 검사하고, 유저 인증 처리
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // 회사가 활성 상태인지 검사하는 필터
    private final CompanyActiveGuardFilter companyActiveGuardFilter;
    private final CorsConfig corsConfig;
    // 401 error 처리 핸들러
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    // 403 error 처리 핸들러
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 세션을 안 쓰기 때문에 CSRF 보호 비활성화
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**", "/api/v1/admin/department/** ").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/api/v1/sys/**").hasRole("SYS")
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated() // 위에 것 외 모든 요청은 로그인 사용자만 접근 가능
                )
                // 세션을 사용 안하므로 STATELESS 모드로 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 등록
                //→ 그래야 로그인 요청이 아니라도 JWT 인증을 먼저 처리할 수 있음
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(companyActiveGuardFilter, JwtAuthenticationFilter.class)
                // CORS 허용 필터 추가 (프론트에서 토큰 보낼 수 있게)
                .addFilter(corsConfig.corsFilter());

        return http.build(); // 설정된 모든 보안 정책을 적용한 SecurityFilterChain 반환
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
