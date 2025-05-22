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

        retVal.setId(rs.getLong("id"));
        retVal.setNumero(rs.getString("numero"));
        retVal.setCantEstudiantes(rs.getInt("cant_estudiantes"));

        return retVal;
    }
}
