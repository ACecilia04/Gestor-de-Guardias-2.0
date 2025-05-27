package utils.abstracts.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aaguilera on 5/30/2016.
 */
public class ByteMapper implements RowMapper<Byte> {

    private String columnName;

    public ByteMapper() {
        this.columnName = null;
    }

    public ByteMapper(String columnName) {
        this.columnName = columnName;
    }

    public Byte mapRow(ResultSet rs, int i) throws SQLException {
        return columnName != null ? rs.getByte(columnName) : rs.getByte(1);
    }
}
