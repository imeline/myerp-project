package erp.auth.security;

import erp.company.mapper.CompanyMapper;
import erp.global.tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CompanyActiveGuardFilter extends OncePerRequestFilter {
    private final CompanyMapper companyMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        String uri = req.getRequestURI();
        // 로그인/공개/헬스체크 등은 패스
        return uri.startsWith("/api/v1/auth")
                || uri.startsWith("/actuator/health")
                || uri.startsWith("/public/");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
                                    @NonNull HttpServletResponse res,
                                    @NonNull FilterChain chain)
            throws IOException, ServletException {

        Long companyId = TenantContext.get(); // JwtAuthenticationFilter에서 세팅됨
        if (companyId != null && !companyMapper.isActiveById(companyId)) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Company is deactivated.");
            return;
        }
        chain.doFilter(req, res);
    }
}
