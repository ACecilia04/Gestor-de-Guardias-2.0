package utils.abstracts.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aaguilera on 5/30/2016.
 */
public class LongMapper implements RowMapper<Long> {

    private String columnName;

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
