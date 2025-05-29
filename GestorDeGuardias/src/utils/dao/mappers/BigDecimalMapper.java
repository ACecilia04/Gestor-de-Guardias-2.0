package utils.dao.mappers;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aaguilera on 9/23/2016.
 */
public class BigDecimalMapper implements RowMapper<BigDecimal> {

    private String columnName;
    private boolean allowNull;

    public BigDecimalMapper() {
        this.columnName = null;
    }

    public BigDecimalMapper(String columnName) {
        this.columnName = columnName;
    }

    public BigDecimalMapper(String columnName, boolean allowNull) {
        this.columnName = columnName;
        this.allowNull = allowNull;
    }

    public BigDecimal mapRow(ResultSet rs, int i) throws SQLException {
        BigDecimal result = columnName != null ? rs.getBigDecimal(columnName) : rs.getBigDecimal(1);
        return allowNull || (result != null && !result.equals(java.math.BigDecimal.ZERO)) ? result : BigDecimal.ZERO;
    }
}
