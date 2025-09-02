package erp.auth.security.filter;

import erp.company.mapper.CompanyMapper;
import erp.global.exception.RestAccessDeniedHandler;
import erp.global.tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
/** 회사가 활성 상태인지 검사하는 필터 */
public class CompanyActiveGuardFilter extends OncePerRequestFilter {
    
    private final CompanyMapper companyMapper;
    private final RestAccessDeniedHandler accessDeniedHandler;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        String uri = req.getRequestURI();
        // 로그인/공개/헬스체크 등은 패스
        return uri.startsWith("/api/v1/auth")
                || uri.startsWith("/actuator/health")
                || uri.startsWith("/public/")
                || uri.startsWith("/swagger-ui")
                || uri.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
                                    @NonNull HttpServletResponse res,
                                    @NonNull FilterChain chain)
            throws IOException, ServletException {

        Long companyId = TenantContext.get();
        if (companyId != null && !companyMapper.isActiveById(companyId)) {
            accessDeniedHandler.handle(req, res,
                    new AccessDeniedException("Company is deactivated."));
            return;
        }
        chain.doFilter(req, res);
    }
}
