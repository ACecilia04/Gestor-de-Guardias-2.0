package utils.abstracts.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by aaguilera on 8/23/2016.
 */
public class DateMapper implements RowMapper<Date> {

    private String columnName;

    public DateMapper() {
        this.columnName = null;
    }

    public DateMapper(String columnName) {
        this.columnName = columnName;
    }

    public Date mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null ? rs.getTimestamp(columnName) : rs.getTimestamp(1);
    }
}
