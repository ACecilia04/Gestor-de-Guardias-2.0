package services;

import model.Horario;
import model.TurnoDeGuardia;
import utils.abstracts.MainBaseDao;
import utils.abstracts.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TurnoDeGuardiaServices {

    private final MainBaseDao baseDao;

    public TurnoDeGuardiaServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertTurnoDeGuardia(Long horarioId, long personaAsignada, Boolean hecho, LocalDate fecha) {
        baseDao.getJdbcTemplate().executeProcedure("sp_create_turno_de_guardia(?, ?, ?, ?)", horarioId, personaAsignada, hecho, fecha);
    }

    // READ all
    public List<TurnoDeGuardia> getAllTurnosDeGuardia() {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_turno_de_guardia", new TurnoDeGuardiaMapper());
    }

    // READ by primary key
    public TurnoDeGuardia getTurnoDeGuardiaByPk(Long horarioId, LocalDate fecha) {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_turno_de_guardia_by_pk( ?, ?)", new TurnoDeGuardiaMapper(),horarioId, fecha)
                .stream().findFirst().orElse(null);
    }

    // UPDATE
    public void updateTurnoDeGuardia(Long horarioId, LocalDate fecha, long personaAsignada, Boolean hecho) {
        baseDao.getJdbcTemplate().executeProcedure("sp_update_turno_de_guardia(?, ?, ?, ?)", horarioId, fecha, personaAsignada, hecho);
    }

    // DELETE
    public void deleteTurnoDeGuardia(Long horarioId, LocalDate fecha) {
        baseDao.getJdbcTemplate().executeProcedure("sp_delete_turno_de_guardia(?, ?)", horarioId, fecha);
    }

    // Internal Mapper
    private static class TurnoDeGuardiaMapper implements RowMapper<TurnoDeGuardia> {
        @Override
        public TurnoDeGuardia mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TurnoDeGuardia(new Horario(
                    rs.getTime("hora_inicio").toLocalTime(),
                    rs.getTime("hora_fin").toLocalTime()),
                    rs.getLong("persona_asignada"),
                    rs.getBoolean("hecho"),
                    rs.getDate("fecha").toLocalDate()
            );
        }
    }
}
