package utils.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by aaguilera on 02/05/2022.
 */
public class LocalDateMapper implements RowMapper<LocalDate> {

    private String columnName;

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
