package erp.auth.jwt;

import io.jsonwebtoken.Claims;
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

    private final Environment env;

    // *의문 - 파라미터를 UserPrincipal로 안 받아도 되나?
    // UserPrincipal은 UserDetails를 구현하므로
    // `UserDetails userDetails = principal;` - 업캐스팅 가능. 더 유연한 코드
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + env.getProperty("jwt.expiration-time", Long.class)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
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
        return extractAllClaims(token).get("role", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String uuid = extractUuid(token);
        return (uuid.equals(userDetails.getUsername()) && !isTokenExpired(token));
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
