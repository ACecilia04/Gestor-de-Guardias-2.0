package utils.abstracts.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Created by aaguilera on 02/05/2022.
 */
public class LocalDateTimeMapper implements RowMapper<LocalDateTime> {

    private String columnName;

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
