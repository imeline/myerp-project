package erp.global.context;

public final class AuditContext {
    private static final ThreadLocal<Boolean> BIZ_FAILURE_AUDITED =
            ThreadLocal.withInitial(() -> false);

    private AuditContext() {
    }

    public static boolean isBusinessFailureAudited() {
        return Boolean.TRUE.equals(BIZ_FAILURE_AUDITED.get());
    }

    public static void markBusinessFailureAudited() {
        BIZ_FAILURE_AUDITED.set(true);
    }

    public static void clear() {
        BIZ_FAILURE_AUDITED.remove();
    }
}
