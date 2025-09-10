package erp.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// 기본 생성자가 public 생성되는 거 방지
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Strings {

    public static String normalizeOrNull(String value) {
        if (value == null) return null;
        String t = value.trim();
        return t.isEmpty() ? null : t;
    }
}
