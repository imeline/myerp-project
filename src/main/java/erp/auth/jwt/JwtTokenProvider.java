package erp.auth.jwt;

import erp.auth.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public static final String CLAIM_ROLE = "role";
    public static final String CLAIM_TENANT_ID = "tenant_id";
    public static final String BEARER_PREFIX = "Bearer ";
    private final Environment env;

    // *파라미터를 UserPrincipal로 안 받아도 되나?
    //  UserPrincipal은 UserDetails를 구현하므로
    // `UserDetails userDetails = principal;` - 업캐스팅 가능. 더 유연한 코드
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Long expMillis = env.getProperty("jwt.expiration-time", Long.class,
                1000L * 60 * 60); // 기본값: 1시간

        JwtBuilder builder = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(CLAIM_ROLE, userDetails.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256);

        // UserPrincipal 객체 타입이면 tenant_id 넣기
        if (userDetails instanceof UserPrincipal principal) {
            Long tenantId = principal.getTenantId();
            builder.claim(CLAIM_TENANT_ID, tenantId);
        }

        return BEARER_PREFIX + builder.compact();
    }

    // 보통 시큐리티에서 알아서 SecurityContextHolder.getContext().getAuthentication()
    // 내부에 있는 Authentication.getAuthorities()를 확인해서
    // SecurityConfig에서 requestMatchers 등에 사용하는데,
    // 우리는 userDetails.username 을 uuid로 바꾸기 위해
    // extractUuid을 JwtAuthenticationFilter에서 사용
    public String extractUuid(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // authService 등에서 사용하기 위해서 만듦
    public String extractRole(String token) {
        return extractAllClaims(token).get(CLAIM_ROLE, String.class);
    }

    public Long extractTenantId(String token) {
        return extractAllClaims(token).get(CLAIM_TENANT_ID, Long.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token) // 토큰 파싱 + 서명 검증이 동시에 일어남
                .getBody();
    }

//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String uuid = extractUuid(token);
//        return (uuid.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

    // 만료 검증
    public boolean validateToken(String token) {
        try {
            // extractAllClaims의 parseClaimsJws 중 서명 검증 수행하므로,
            // 만료만 추가 확인
            return !isTokenExpired(token);
        } catch (Exception e) {
            log.debug("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("jwt.secret-key"));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
