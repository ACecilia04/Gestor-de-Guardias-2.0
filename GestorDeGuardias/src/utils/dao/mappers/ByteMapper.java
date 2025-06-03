package utils.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

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
