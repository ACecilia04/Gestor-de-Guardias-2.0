package utils.mapper;

import model.Persona;
import utils.abstracts.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaMapper implements RowMapper<PersonaMapper> {
    @Override
    public PersonaMapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs == null) return null;

        Persona retVal = new Persona();

        retVal.setCarnet(rs.getString("carnet"));
        retVal.setId(rs.getLong("id"));
        retVal.setNombre(rs.getString("nombre"));
        retVal.setApellidos(rs.getString("apellido"));
//        retVal.setSexo(rs.getc);
        retVal.setTipo();
        return retVal;
    }
}
