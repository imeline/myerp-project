//package erp.global.tenant;
//
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.ParameterMapping;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.type.JdbcType;
//
//import java.sql.Connection;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
//public class TenantSqlInterceptor implements Interceptor {
//
//    private static final String TENANT_COLUMN = "company_id";
//    private static final String TENANT_PARAM = "__tenantId";
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        StatementHandler handler = (StatementHandler) unwrap(invocation.getTarget());
//        BoundSql boundSql = handler.getBoundSql();
//        String sql = boundSql.getSql();
//        long tenantId = TenantContext.get();
//
//        if (tenantId != null && needsTenantInjection(sql)) {
//            String newSql = injectTenant(sql);
//            injectParam(boundSql, tenantId);
//            setSql(handler, newSql);
//        }
//        return invocation.proceed();
//    }
//
//    /** 프록시 체인 해제 */
//    private Object unwrap(Object target) {
//        MetaObject meta = SystemMetaObject.forObject(target);
//        while (meta.hasGetter("h")) {
//            Object object = meta.getValue("h");
//            meta = SystemMetaObject.forObject(object);
//        }
//        while (meta.hasGetter("target")) {
//            Object object = meta.getValue("target");
//            meta = SystemMetaObject.forObject(object);
//        }
//        return meta.getOriginalObject();
//    }
//
//    /** DDL/프로시저 호출 등은 패스 */
//    private boolean needsTenantInjection(String sql) {
//        if (sql == null) return false;
//        String s = sql.trim().toLowerCase(Locale.ROOT);
//        return s.startsWith("select") || s.startsWith("update") || s.startsWith("delete") || s.startsWith("insert");
//    }
//
//    /** SQL 재작성 (심플/보수적) */
//    private String injectTenant(String sql) {
//        String s = sql.trim();
//        String lower = s.toLowerCase(Locale.ROOT);
//
//        if (lower.startsWith("insert")) {
//            // INSERT INTO table (col1, col2) VALUES (#{...}, #{...})
//            // 컬럼 목록이 있는 형태에 한해 안전하게 주입
//            int colStart = lower.indexOf('(');
//            int colEnd   = lower.indexOf(')');
//            int valuesIdx = lower.indexOf("values");
//            if (colStart > 0 && colEnd > colStart && valuesIdx > colEnd) {
//                String cols = s.substring(colStart + 1, colEnd).trim();
//                boolean hasTenantCol = containsColumn(cols, TENANT_COLUMN);
//                if (!hasTenantCol) {
//                    // VALUES (...) 블록만 찾아서 하나 더 추가
//                    int valTupleStart = lower.indexOf('(', valuesIdx);
//                    int valTupleEnd   = lower.lastIndexOf(')');
//                    if (valTupleStart > 0 && valTupleEnd > valTupleStart) {
//                        String newCols = cols.isEmpty() ? TENANT_COLUMN : cols + ", " + TENANT_COLUMN;
//                        String vals = s.substring(valTupleStart + 1, valTupleEnd).trim();
//                        String newVals = vals.isEmpty() ? "#{"+TENANT_PARAM+"}" : vals + ", #{" + TENANT_PARAM + "}";
//                        return s.substring(0, colStart+1) + newCols + s.substring(colEnd, valTupleStart+1) + newVals + s.substring(valTupleEnd);
//                    }
//                }
//            }
//            // INSERT ... SELECT ... 등 복잡형은 애플리케이션 레벨에서 반드시 company_id를 넣도록 규약
//            return s;
//        }
//
//        if (lower.startsWith("update")) {
//            // UPDATE table SET ... WHERE ...
//            int whereIdx = lower.indexOf(" where ");
//            if (whereIdx > 0) {
//                return s + " AND " + TENANT_COLUMN + " = #{" + TENANT_PARAM + "}";
//            } else {
//                return s + " WHERE " + TENANT_COLUMN + " = #{" + TENANT_PARAM + "}";
//            }
//        }
//
//        if (lower.startsWith("delete")) {
//            // DELETE FROM table WHERE ...
//            int whereIdx = lower.indexOf(" where ");
//            if (whereIdx > 0) {
//                return s + " AND " + TENANT_COLUMN + " = #{" + TENANT_PARAM + "}";
//            } else {
//                return s + " WHERE " + TENANT_COLUMN + " = #{" + TENANT_PARAM + "}";
//            }
//        }
//
//        if (lower.startsWith("select")) {
//            // 최상위 WHERE에만 붙이는 보수적 방식
//            int whereIdx = indexOfTopLevelWhere(lower);
//            if (whereIdx > 0) {
//                return s + " AND " + TENANT_COLUMN + " = #{" + TENANT_PARAM + "}";
//            } else {
//                // FROM 뒤 첫 WHERE 전까지라면 WHERE 구문 신설
//                return s + " WHERE " + TENANT_COLUMN + " = #{" + TENANT_PARAM + "}";
//            }
//        }
//
//        return s;
//    }
//
//    private boolean containsColumn(String colsCsv, String column) {
//        for (String c : colsCsv.split(",")) {
//            if (c.trim().replace("\"","").equalsIgnoreCase(column)) return true;
//        }
//        return false;
//    }
//
//    /** 매우 단순화: 문자열 내 소괄호 균형 무시하고 where 존재만 판단 — 규약 준수 전제 */
//    private int indexOfTopLevelWhere(String lowerSql) {
//        return lowerSql.indexOf(" where ");
//        // 필요시 토큰 파서로 개선 가능
//    }
//
//    /** 바인딩 파라미터에 __tenantId 주입 */
//    private void injectParam(BoundSql boundSql, long tenantId) {
//        // 파라미터 오브젝트가 Map/DTO 등 무엇이든 MetaObject로 주입
//        Object paramObj = boundSql.getParameterObject();
//        MetaObject paramMeta = SystemMetaObject.forObject(paramObj);
//        if (!paramMeta.hasGetter(TENANT_PARAM)) {
//            // 동적 추가가 어려운 경우 BoundSql의 추가 파라미터 API 사용
//            boundSql.setAdditionalParameter(TENANT_PARAM, tenantId);
//        }
//        // 파라미터 매핑에도 추가 (일부 드라이버/설정에서 필요)
//        List<ParameterMapping> original = boundSql.getParameterMappings();
//        List<ParameterMapping> copy = new ArrayList<>(original);
//        copy.add(new ParameterMapping.Builder(
//            getConfiguration(boundSql), TENANT_PARAM, Long.class).jdbcType(JdbcType.BIGINT).build());
//        MetaObject boundMeta = SystemMetaObject.forObject(boundSql);
//        boundMeta.setValue("parameterMappings", copy);
//    }
//
//    private void setSql(StatementHandler handler, String newSql) {
//        MetaObject meta = SystemMetaObject.forObject(handler);
//        BoundSql bs = (BoundSql) meta.getValue("boundSql");
//        MetaObject bsMeta = SystemMetaObject.forObject(bs);
//        bsMeta.setValue("sql", newSql);
//    }
//
//    private Configuration getConfiguration(BoundSql bs) {
//        try {
//            java.lang.reflect.Field f = BoundSql.class.getDeclaredField("configuration");
//            f.setAccessible(true);
//            return (Configuration) f.get(bs);
//        } catch (Exception e) {
//            throw new RuntimeException("Cannot access BoundSql.configuration", e);
//        }
//    }
//}
