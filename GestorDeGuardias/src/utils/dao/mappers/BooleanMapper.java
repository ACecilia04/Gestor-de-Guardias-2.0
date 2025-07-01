package utils.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanMapper implements RowMapper<Boolean> {

    private final String columnName;

    public BooleanMapper() {
        this.columnName = null;
    }

    public BooleanMapper(String columnName) {
        this.columnName = columnName;
    }

    public Boolean mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null ? rs.getBoolean(columnName) : rs.getBoolean(1);
    }
}
