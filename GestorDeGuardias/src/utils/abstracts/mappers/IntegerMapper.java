package utils.abstracts.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aaguilera on 5/30/2016.
 */
public class IntegerMapper implements RowMapper<Integer> {

    private String columnName;

    public IntegerMapper() {
        this.columnName = null;
    }

    public IntegerMapper(String columnName) {
        this.columnName = columnName;
    }

    public Integer mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null ? rs.getInt(columnName) : rs.getInt(1);
    }
}
