package utils.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringMapper implements RowMapper<String> {

    private final String columnName;

    public StringMapper() {
        this.columnName = null;
    }

    public StringMapper(String columnName) {
        this.columnName = columnName;
    }

    public String mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null ? rs.getString(columnName) : rs.getString(1);
    }
}
