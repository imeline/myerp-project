package erp.global.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;

/**
 * 사람이 읽기 쉬운(Base32 유사) 랜덤 토큰 유틸.
 * - I, O, 0, 1 제거로 오독 방지
 * - 기본 길이 8, 필요 시 길이/알파벳 커스터마이징 가능
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Codes {

    public static final int DEFAULT_MAX_TRY = 5;
    private static final String DEFAULT_ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int DEFAULT_LENGTH = 8;
    // SecureRandom은 스레드 안전. 한 번만 만들어 재사용.
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String randomToken() {
        return randomToken(DEFAULT_LENGTH, DEFAULT_ALPHABET);
    }

    public static String randomToken(int length) {
        return randomToken(length, DEFAULT_ALPHABET);
    }

    public static String randomToken(int length, String alphabet) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }
        if (alphabet == null || alphabet.isEmpty()) {
            throw new IllegalArgumentException("alphabet must not be empty");
        }
        StringBuilder builder = new StringBuilder(length);
        int bound = alphabet.length();
        for (int i = 0; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(bound);
            builder.append(alphabet.charAt(index));
        }
        return builder.toString();
    }
}
