package erp.global.config;

import erp.auth.infra.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 활성화
@EnableMethodSecurity // @PreAuthorize, @Secured 같은 메서드 레벨 보안을 사용
@RequiredArgsConstructor
public class SecurityConfig {

    // 사용자의 로그인 정보 검증
    private final AuthenticationProvider authenticationProvider;
    // 클라이언트로부터 온 요청에 JWT가 포함됐는지 검사하고, 유저 인증 처리
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfig corsConfig;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 세션을 안 쓰기 때문에 CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/manager/**").hasRole("MANAGER")
                        .requestMatchers("/user/**").hasAnyRole("USER", "MANAGER")
                        .anyRequest().authenticated() // 위에 것 외 모든 요청은 로그인 사용자만 접근 가능
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) // 세션을 사용 안하므로 STATELESS 모드로 설정
                // 커스텀 AuthenticationProvider 설정
                .authenticationProvider(authenticationProvider)
                // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 등록
                //→ 그래야 로그인 요청이 아니라도 JWT 인증을 먼저 처리할 수 있음
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // CORS 허용 필터 추가 (프론트에서 토큰 보낼 수 있게)
                .addFilter(corsConfig.corsFilter());

        return http.build(); // 설정된 모든 보안 정책을 적용한 SecurityFilterChain 반환
    }
}
