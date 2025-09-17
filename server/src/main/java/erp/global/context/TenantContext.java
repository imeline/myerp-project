package erp.global.context;

// ThreadLocal을 써서 현재 스레드(=이번 HTTP 요청) 안에서만 tenantId를 저장
// JWT 필터가 토큰에서 tenantId를 꺼내서 TenantContext.set(tenantId)로 심어줌
// 이후 서비스/매퍼/인터셉터에서 필요할 때 TenantContext.get()으로 꺼냄
// 요청이 끝나면 TenantContext.clear()로 반드시 지워야 함 (스레드 풀 재사용 때문에)
public final class TenantContext {

    private static final ThreadLocal<Long> TENANT = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void set(Long tenantId) {
        TENANT.set(tenantId);
    }

    public static Long get() {
        return TENANT.get();
    }

    public static void clear() {
        TENANT.remove();
    }
}
