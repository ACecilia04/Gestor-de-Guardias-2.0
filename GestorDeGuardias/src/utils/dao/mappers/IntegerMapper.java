package utils.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerMapper implements RowMapper<Integer> {

    private String columnName;

    public IntegerMapper() {
        this.columnName = null;
    }

    public IntegerMapper(String columnName) {
        this.columnName = columnName;
    }

    public Integer mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null ? rs.getInt(columnName) : rs.getInt(1);
    }
}
