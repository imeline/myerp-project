package erp.global.tenant;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class TenantSqlInterceptor implements Interceptor {

    private static final String TENANT_COLUMN = "company_id";
    private static final String TENANT_PARAM = "__tenantId";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) unwrap(invocation.getTarget());
        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql();
        Long tenantId = TenantContext.get();

        if (tenantId != null && needsFilter(sql)) {
            String newSql = addTenantCondition(sql);
            Configuration cfg = getConfiguration(handler);
            rewrite(boundSql, cfg, newSql, tenantId);
        }
        return invocation.proceed();
    }

    private boolean needsFilter(String sql) {
        String s = sql.trim().toLowerCase(Locale.ROOT);
        if (!(s.startsWith("select") || s.startsWith("update") || s.startsWith("delete")))
            return false;
        // 이미 company_id가 들어있으면 스킵(최소 체크)
        return !s.contains(" " + TENANT_COLUMN + " ");
    }

    private String addTenantCondition(String sql) {
        String lower = sql.toLowerCase(Locale.ROOT);

        if (lower.startsWith("select")) {
            // SELECT는 ORDER BY 위치도 고려 (WHERE 없으면 ORDER BY 앞에 WHERE 삽입)
            int whereIdx = lower.indexOf(" where ");
            if (whereIdx >= 0) {
                return sql + " AND " + TENANT_COLUMN + " = ?";
            }
            int orderIdx = lower.lastIndexOf(" order by ");
            if (orderIdx >= 0) {
                return sql.substring(0, orderIdx)
                        + " WHERE " + TENANT_COLUMN + " = ? "
                        + sql.substring(orderIdx);
            }
            return sql + " WHERE " + TENANT_COLUMN + " = ?";
        }

        // UPDATE/DELETE: WHERE 있으면 AND, 없으면 WHERE
        if (lower.contains(" where ")) {
            return sql + " AND " + TENANT_COLUMN + " = ?";
        } else {
            return sql + " WHERE " + TENANT_COLUMN + " = ?";
        }
    }

    private void rewrite(BoundSql bs, Configuration cfg, String newSql, Long tenantId) {
        MetaObject meta = SystemMetaObject.forObject(bs);
        meta.setValue("sql", newSql);

        List<ParameterMapping> pm = new ArrayList<>(bs.getParameterMappings());
        pm.add(new ParameterMapping.Builder(cfg, TENANT_PARAM, Long.class)
                .jdbcType(JdbcType.NUMERIC).build());
        meta.setValue("parameterMappings", pm);

        bs.setAdditionalParameter(TENANT_PARAM, tenantId);
    }

    private Configuration getConfiguration(StatementHandler handler) {
        MetaObject meta = SystemMetaObject.forObject(handler);
        MappedStatement ms = (MappedStatement) meta.getValue("delegate.mappedStatement");
        if (ms == null) ms = (MappedStatement) meta.getValue("mappedStatement");
        if (ms == null)
            throw new IllegalStateException("MappedStatement not found");
        return ms.getConfiguration();
    }

    private Object unwrap(Object target) {
        MetaObject meta = SystemMetaObject.forObject(target);
        while (meta.hasGetter("h")) {
            Object o = meta.getValue("h");
            meta = SystemMetaObject.forObject(o);
        }
        while (meta.hasGetter("target")) {
            Object o = meta.getValue("target");
            meta = SystemMetaObject.forObject(o);
        }
        return meta.getOriginalObject();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(java.util.Properties properties) {
    }
}
