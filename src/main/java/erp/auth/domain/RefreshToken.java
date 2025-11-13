package erp.auth.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken extends TimeStamped {
    private Long refreshTokenId;
    private Long erpAccountId;
    private String token;

    // 새 리프레시 토큰 발급 시 사용
    public static RefreshToken issue(Long refreshTokenId,
                                     Long erpAccountId,
                                     String token) {
        return RefreshToken.builder()
                .refreshTokenId(refreshTokenId)
                .erpAccountId(erpAccountId)
                .token(token)
                .build();
    }
}