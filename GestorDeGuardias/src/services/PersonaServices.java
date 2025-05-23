package services;

import model.Persona;
import model.TipoPersona;
import utils.abstracts.MainBaseDao;
import utils.abstracts.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PersonaServices {

    private MainBaseDao baseDao;

    public PersonaServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // Internal Mapper
    private static class PersonaMapper implements RowMapper<Persona> {
        @Override
        public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Persona(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("sexo").charAt(0),
                    rs.getString("carnet"),
                    rs.getDate("ultima_guardia_hecha") != null ? rs.getDate("ultima_guardia_hecha").toLocalDate() : null,
                    rs.getInt("guardias_de_recuperacion"),
                    rs.getDate("baja") != null ? rs.getDate("baja").toLocalDate() : null,
                    rs.getDate("reincorporacion") != null ? rs.getDate("reincorporacion").toLocalDate() : null,
                    rs.getString("tipo"),
                    rs.getBoolean("activo")
            );
        }
    }

    // CREATE
    public void insertPersona(String nombre, String apellido, char sexo, String carnet,
                              LocalDate ultimaGuardiaHecha, int guardiasDeRecuperacion,
                              LocalDate baja, LocalDate reincorporacion, String tipo, boolean activo) {
        baseDao.getJdbcTemplate().executeProcedure("sp_create_persona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                nombre, apellido, String.valueOf(sexo), carnet, ultimaGuardiaHecha, guardiasDeRecuperacion,
                baja, reincorporacion, tipo, activo);
    }

    // READ
    public List<Persona> getAllPersonas() {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_persona", new PersonaMapper());
    }

    public Persona getPersonaById(long id) {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_persona_by_id(?)", new PersonaMapper(), id)
                .stream().findFirst().orElse(null);
    }

    public List<Persona> getPersonaByTipo(TipoPersona tipoPersona) {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_persona_by_tipo(?)", new PersonaMapper(), tipoPersona.getNombre());
    }

    // UPDATE
    public void updatePersona(long id, String nombre, String apellido, char sexo, String carnet,
                              LocalDate ultimaGuardiaHecha, int guardiasDeRecuperacion, LocalDate baja,
                              LocalDate reincorporacion, String tipo, boolean activo) {
        baseDao.getJdbcTemplate().executeProcedure("sp_update_persona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id, nombre, apellido, String.valueOf(sexo), carnet, ultimaGuardiaHecha,
                guardiasDeRecuperacion, baja, reincorporacion, tipo, activo);
    }

    // DELETE
    public void deletePersona(long id) {
        baseDao.getJdbcTemplate().executeProcedure("sp_delete_persona(?)", id);
    }
}
