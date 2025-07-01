package utils.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LocalDateTimeMapper implements RowMapper<LocalDateTime> {

    private final String columnName;

    public LocalDateTimeMapper() {
        this.columnName = null;
    }

    public LocalDateTimeMapper(String columnName) {
        this.columnName = columnName;
    }

    public LocalDateTime mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null && rs.getTimestamp(columnName) != null ? rs.getTimestamp(columnName).toLocalDateTime() : null;
    }
}
