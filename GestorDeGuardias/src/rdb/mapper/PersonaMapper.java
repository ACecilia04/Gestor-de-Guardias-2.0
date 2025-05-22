package rdb.mapper;

import rdb.abstracts.RowMapper;
import rdb.entity.Grupo;
import rdb.entity.Persona;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaMapper implements RowMapper<PersonaMapper> {
    @Override
    public PersonaMapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs == null) return null;

        Persona retVal = new Persona();

        retVal.setCi(rs.getString("carnet"));
        retVal.setId(rs.getLong("id"));
        retVal.setNombre(rs.getString("nombre"));
        retVal.setApellidos(rs.getString("apellido"));
//        retVal.setSexo(rs.getc);
        retVal.setTipo();
        return retVal;
    }
}
