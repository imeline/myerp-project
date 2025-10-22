package erp.global.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SensitiveMasker {

    private static final Set<String> SENSITIVE_KEYS = Set.of(
            "password", "pwd", "pass",
            "authorization", "auth",
            "token", "access_token", "refresh_token",
            "secret", "apiKey", "apikey",
            "card", "cardNumber", "ssn"
    );

    /**
     * Map/List/배열/문자열만 처리. POJO는 그대로 반환
     */
    public static Object mask(Object input) {
        if (input == null) return null;

        if (input instanceof Map<?, ?> map) {
            Map<String, Object> out = new LinkedHashMap<>(map.size());
            for (Map.Entry<?, ?> e : map.entrySet()) {
                String key = String.valueOf(e.getKey());
                Object value = e.getValue();
                if (isSensitiveKey(key)) {
                    out.put(key, maskValue(value));
                } else {
                    out.put(key, mask(value));
                }
            }
            return out;
        }

        if (input instanceof List<?> list) {
            List<Object> out = new ArrayList<>(list.size());
            for (Object o : list) out.add(mask(o));
            return out;
        }

        if (input.getClass().isArray()) {
            int len = java.lang.reflect.Array.getLength(input);
            List<Object> out = new ArrayList<>(len);
            for (int i = 0; i < len; i++) {
                out.add(mask(java.lang.reflect.Array.get(input, i)));
            }
            return out;
        }

        if (input instanceof CharSequence cs) {
            return maskString(cs.toString());
        }

        return input; // POJO는 그대로 (원하면 maskWithObjectMapper 사용)
    }

    /**
     * POJO도 마스킹하고 싶을 때 사용 (Jackson 필요)
     */
    public static Object maskWithObjectMapper(Object input, ObjectMapper objectMapper) {
        if (input == null) return null;

        if (input instanceof Map || input instanceof List || input instanceof CharSequence || input.getClass().isArray()) {
            return mask(input);
        }

        try {
            Map<String, Object> pojoAsMap = objectMapper.convertValue(
                    input, new TypeReference<>() {
                    });
            return mask(pojoAsMap);
        } catch (IllegalArgumentException ex) {
            // 변환 실패 시 원본 리턴
            return input;
        }
    }

    // ---- helpers ----

    private static boolean isSensitiveKey(String key) {
        if (key == null) return false;
        String k = normalize(key);
        for (String s : SENSITIVE_KEYS) {
            if (k.contains(normalize(s))) return true;
        }
        return false;
    }

    private static String normalize(String s) {
        return s.toLowerCase(Locale.ROOT).replace("-", "").replace("_", "");
    }

    private static Object maskValue(Object value) {
        if (value == null) return null;
        String s = String.valueOf(value);
        if (s.length() <= 6) return "***";
        return s.substring(0, 3) + "***" + s.substring(s.length() - 3);
    }

    private static String maskString(String s) {
        String v = s.trim();
        if (looksLikeAuthorization(v)) return redactToken(v);
        // 이메일 간단 마스킹: a****@domain
        int at = v.indexOf('@');
        if (at > 1) {
            String local = v.substring(0, at);
            String domain = v.substring(at);
            String head = local.charAt(0) + "****";
            return head + domain;
        }
        return v;
    }

    private static boolean looksLikeAuthorization(String s) {
        String v = s.toLowerCase(Locale.ROOT);
        return v.startsWith("bearer ") || v.startsWith("basic ") || v.startsWith("token ");
    }

    private static String redactToken(String s) {
        String t = s.trim();
        if (t.length() <= 10) return "***";
        return t.substring(0, 6) + "***";
    }
}
