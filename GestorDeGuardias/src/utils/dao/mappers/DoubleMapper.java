package utils.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleMapper implements RowMapper<Double> {

    private String columnName;

    public DoubleMapper() {
        this.columnName = null;
    }

    public DoubleMapper(String columnName) {
        this.columnName = columnName;
    }

    public Double mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null ? rs.getDouble(columnName) : rs.getDouble(1);
    }
}
