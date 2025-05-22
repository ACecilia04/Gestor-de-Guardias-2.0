package utils.abstracts;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LongMapper implements RowMapper<Long> {

    private final String columnName;

    public LongMapper() {
        this.columnName = null;
    }

    public LongMapper(String columnName) {
        this.columnName = columnName;
    }

    public Long mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null ? rs.getLong(columnName) : rs.getLong(1);
    }
}
