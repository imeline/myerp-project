package erp.log.audit;

import erp.log.dto.request.LogSaveRequest;
import erp.log.enums.LogType;
import erp.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

import static erp.global.context.AuditContext.markBusinessFailureAudited;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditingAspect {

    private final LogService logService;
    private final ExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(aud)")
    public Object around(ProceedingJoinPoint pjp, Auditable aud) throws Throwable {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("method", pjp.getSignature().toShortString());

        try {
            Object result = pjp.proceed();

            payload.put("result", "SUCCESS");
            String msg = resolveMessage(aud, pjp, result, null);
            safeSave(aud.type(), true, msg, payload);

            return result;

        } catch (Throwable ex) {
            payload.put("result", "FAIL");
            payload.put("exception", ex.getClass().getSimpleName());
            payload.put("errorMessage", ex.getMessage());

            String msg = resolveMessage(aud, pjp, null, ex);
            safeSave(aud.type(), false, msg, payload);
            markBusinessFailureAudited(); // 중복(ERROR) 차단 플래그

            throw ex;
        }
    }

    private void safeSave(LogType type, boolean success, String message, Map<String, Object> payload) {
        try {
            logService.save(new LogSaveRequest(type, success, message, payload));
        } catch (Exception e) {
            log.warn("audit log write failed: {}", e.toString());
        }
    }

    private String resolveMessage(Auditable aud, ProceedingJoinPoint pjp, Object result, Throwable ex) {
        // 1) SpEL 우선
        String el = aud.messageEl();
        if (el != null && !el.isBlank()) {
            try {
                StandardEvaluationContext ctx = new StandardEvaluationContext();
                ctx.setVariable("args", pjp.getArgs());                   // #args[0]...
                ctx.setVariable("result", result);                        // #result
                ctx.setVariable("ex", ex);                                // #ex
                ctx.setVariable("method", pjp.getSignature().getName());  // #method
                Expression expression = parser.parseExpression(el);
                Object value = expression.getValue(ctx);
                if (value != null) return String.valueOf(value);
            } catch (Exception ignore) { /* SpEL 실패 시 아래 fallback */ }
        }
        // 2) 고정 메시지
        if (aud.message() != null && !aud.message().isBlank()) {
            return aud.message();
        }
        // 3) 기본 포맷
        return aud.type().name() + (ex == null ? " success" : " fail");
    }
}
