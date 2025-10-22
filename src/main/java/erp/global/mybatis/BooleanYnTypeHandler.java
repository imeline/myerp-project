package erp.global.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// boolean <-> 'Y'/'N' 매핑
@MappedTypes({Boolean.class, boolean.class})
@MappedJdbcTypes({JdbcType.CHAR, JdbcType.VARCHAR})
public class BooleanYnTypeHandler extends BaseTypeHandler<Boolean> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, Boolean.TRUE.equals(parameter) ? "Y" : "N");
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String v = rs.getString(columnName);
        return "Y".equalsIgnoreCase(v);
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String v = rs.getString(columnIndex);
        return "Y".equalsIgnoreCase(v);
    }

    @Override
    public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String v = cs.getString(columnIndex);
        return "Y".equalsIgnoreCase(v);
    }
}
