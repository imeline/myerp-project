package erp.log.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import erp.auth.security.model.UserPrincipal;
import erp.global.context.RequestContext;
import erp.global.context.TenantContext;
import erp.global.exception.ErrorStatus;
import erp.global.util.SensitiveMasker;
import erp.log.domain.Log;
import erp.log.dto.request.LogSaveRequest;
import erp.log.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static erp.global.util.RowCountGuards.requireOneRowAffected;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private static final int MAX_PAYLOAD_LEN = 32_000;

    private final LogMapper logMapper;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(LogSaveRequest request) {
        UserPrincipal principal = currentUserOrNull();
        Long tenantId = resolveTenantId(principal);

        String ip = RequestContext.ip();
        String requestId = RequestContext.requestId();

        String employeeUuid = principal != null ? principal.getUuid() : null;
        String employeeName = principal != null ? principal.getName() : null;

        String json = toSafeJson(request.payload());

        long logId = logMapper.nextId();
        Log log = Log.register(
                logId,
                request.type(),
                request.success(),
                employeeUuid,
                employeeName,
                request.message(),
                ip,
                requestId,
                json
        );

        int affected = logMapper.save(tenantId, log);
        requireOneRowAffected(affected, ErrorStatus.LOG_SAVE_FAIL);
    }

    private Long resolveTenantId(UserPrincipal principal) {
        Long t = TenantContext.get(); // "/auth" 등 TenantContext를 설정하지 않는 곳에서는 null
        return (t != null) ? t : (principal != null ? principal.getTenantId() : null);
    }

    private UserPrincipal currentUserOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        if (auth instanceof AnonymousAuthenticationToken) return null;
        Object principal = auth.getPrincipal();
        return (principal instanceof UserPrincipal p) ? p : null;
    }

    /**
     * 민감정보 마스킹 + 직렬화 + 길이 제한
     */
    private String toSafeJson(Object payload) {
        if (payload == null) return null;
        try {
            Object masked = SensitiveMasker.mask(payload);
            String json = objectMapper.writeValueAsString(masked);
            return json.length() > MAX_PAYLOAD_LEN ? json.substring(0, MAX_PAYLOAD_LEN) : json;
        } catch (Exception e) {
            log.warn("payload serialize failed: {}", e.toString());
            return "{\"error\":\"payload-serialize-failed\"}";
        }
    }


}
