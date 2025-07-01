package utils.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LocalDateMapper implements RowMapper<LocalDate> {

    private final String columnName;

    public LocalDateMapper() {
        this.columnName = null;
    }

    public LocalDateMapper(String columnName) {
        this.columnName = columnName;
    }

    public LocalDate mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null && rs.getTimestamp(columnName) != null ? rs.getTimestamp(columnName).toLocalDateTime().toLocalDate() : null;
    }
}
