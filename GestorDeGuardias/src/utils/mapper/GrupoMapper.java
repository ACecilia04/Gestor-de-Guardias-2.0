package utils.mapper;

import utils.abstracts.RowMapper;
import model.Grupo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GrupoMapper implements RowMapper<Grupo> {
    public Grupo mapRow(ResultSet rs, int i) throws SQLException {
        if (rs == null) return null;

        Grupo retVal = new Grupo();

        retVal.setId(rs.getLong("id"));
        retVal.setNumero(rs.getString("numero"));
        retVal.setCantEstudiantes(rs.getInt("cant_estudiantes"));

        return retVal;
    }

}
