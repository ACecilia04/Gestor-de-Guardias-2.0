package utils.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aaguilera on 5/30/2016.
 */
public class StringMapper implements RowMapper<String> {

    private String columnName;

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
