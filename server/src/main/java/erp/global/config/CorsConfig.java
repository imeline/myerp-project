package erp.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {
    @Value("${cors.allowed-origin}")
    private String allowedOrigin;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 쿠키,토큰 같이 보낼 수 있게
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern(allowedOrigin);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        // 응답 헤더 중 클라이언트에게 노출할 것
        config.setExposedHeaders(List.of("Authorization"));
        // CORS 설정이 어떤 URL 경로에 적용될지 지정
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
