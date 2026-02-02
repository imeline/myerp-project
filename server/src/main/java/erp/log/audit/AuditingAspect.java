package erp.log.audit;

import erp.global.exception.GlobalException;
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

            // 정책: WORK는 성공일 때만 기록
            if (aud.type() == LogType.WORK) {
                safeSave(LogType.WORK, true, msg, payload);
            } else {
                safeSave(aud.type(), true, msg, payload);
            }
            return result;

        } catch (Throwable ex) {
            payload.put("result", "FAIL");
            payload.put("exception", ex.getClass().getSimpleName());
            payload.put("errorMessage", ex.getMessage());

            // 정책: WORK 실패는 기록하지 않음
            if (aud.type() != LogType.WORK) {
                String msg = resolveMessage(aud, pjp, null, ex);
                safeSave(aud.type(), false, msg, payload);
            }

            // 비즈니스 예외(4xx)는 ERROR 중복 방지 플래그만
            if (ex instanceof GlobalException) {
                markBusinessFailureAudited();
            }
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
        String el = aud.messageEl();
        if (el != null && !el.isBlank()) {
            try {
                StandardEvaluationContext ctx = new StandardEvaluationContext();
                ctx.setVariable("args", pjp.getArgs());
                ctx.setVariable("result", result);
                ctx.setVariable("ex", ex);
                ctx.setVariable("method", pjp.getSignature().getName());
                Expression expression = parser.parseExpression(el);
                Object value = expression.getValue(ctx);
                if (value != null) return String.valueOf(value);
            } catch (Exception ignore) {
            }
        }
        if (aud.message() != null && !aud.message().isBlank()) {
            return aud.message();
        }
        return aud.type().name() + (ex == null ? " success" : " fail");
    }
}
