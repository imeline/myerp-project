package erp.auth.security.filter;

import erp.account.service.ErpAccountService;
import erp.auth.security.model.UserPrincipal;
import erp.global.exception.GlobalException;
import erp.global.exception.RestAccessDeniedHandler;
import erp.global.tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * TenantScopeGuardFilter
 * - 사용자의 uuid로 DB에서 실제 companyId(=tenantId)를 조회해
 * 토큰의 tenantId와 다르면 차단하는 필터 (위변조/오염 방지)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TenantScopeGuardFilter extends OncePerRequestFilter {

    private final ErpAccountService erpAccountService;
    private final RestAccessDeniedHandler accessDeniedHandler;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        // 로그인/헬스/공개/스웨거 경로는 필터 건너뜀
        return uri.startsWith("/api/v1/auth")
                || uri.startsWith("/actuator/health")
                || uri.startsWith("/public/")
                || uri.startsWith("/swagger-ui")
                || uri.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 비인증 요청이면 패스
            if (authentication == null ||
                    !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
                filterChain.doFilter(request, response);
                return;
            }

            // JwtAuthenticationFilter가 토큰에서 추출해서 채운 값
            String uuid = principal.getUsername();
            Long tokenTenantId = principal.getTenantId();

            // 1) DB 기준으로 '진짜 company_id' 확정 (uuid → erp_account.company_id)
            long dbCompanyId;
            try {
                dbCompanyId = erpAccountService.findCompanyIdByUuid(uuid);
            } catch (GlobalException ex) {
                accessDeniedHandler.handle(request, response,
                        new AccessDeniedException("ACCOUNT_NOT_FOUND"));
                return;
            }

            // 2) 토큰 tenantId가 존재할 때만 DB값과 불일치 시 차단(위변조/오염 방지)
            // 토큰의 tenantId가 없으면, DB 기준으로 tenantId를 강제 세팅
            if (tokenTenantId != null && !tokenTenantId.equals(dbCompanyId)) {
                accessDeniedHandler.handle(request, response,
                        new AccessDeniedException("TENANT_MISMATCH"));
                return;
            }

            // 3) 테넌트 컨텍스트 세팅 (요청 범위를 DB값으로 고정)
            TenantContext.set(dbCompanyId);

            // 4) 다음 필터로 요청을 넘김
            // doFilter 안에서 컨트롤러 → 서비스 → 매퍼 → SQL 실행이 모두 일어남
            filterChain.doFilter(request, response);

        } finally {
            // ThreadLocal은 요청 단위 컨텍스트 저장소로 쓰지만, 스레드가 재사용되므로 반드시 해제 필요
            // 해제를 안 하면 다른 사용자의 요청에 값이 섞여 보안 이슈 생김
            // 클리어 - ThreadLocal 누수 방지
            TenantContext.clear();
        }
    }
}
