package erp.global.context;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// ThreadLocal을 써서 현재 스레드(=이번 HTTP 요청) 안에서만 request 정보를 저장
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestContext {

    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> IP = new ThreadLocal<>();
    private static final ThreadLocal<String> METHOD = new ThreadLocal<>();
    private static final ThreadLocal<String> PATH = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_AGENT = new ThreadLocal<>();

    public static void set(String requestId, String ip, String method, String path, String userAgent) {
        REQUEST_ID.set(requestId);
        IP.set(ip);
        METHOD.set(method);
        PATH.set(path);
        USER_AGENT.set(userAgent);
    }

    public static String requestId() {
        return REQUEST_ID.get();
    }

    public static String ip() {
        return IP.get();
    }

    public static String method() {
        return METHOD.get();
    }

    public static String path() {
        return PATH.get();
    }

    public static String userAgent() {
        return USER_AGENT.get();
    }

    public static void clear() {
        REQUEST_ID.remove();
        IP.remove();
        METHOD.remove();
        PATH.remove();
        USER_AGENT.remove();
    }
}
