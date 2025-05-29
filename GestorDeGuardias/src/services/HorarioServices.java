package services;

import model.Horario;
import utils.dao.MainBaseDao;
import utils.dao.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class HorarioServices {

    private final MainBaseDao baseDao;

    public HorarioServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertHorario(LocalTime inicio, LocalTime fin) {
        baseDao.getJdbcTemplate().executeProcedure("sp_create_horario(?, ?)", inicio, fin);
    }

    // READ all
    public List<Horario> getAllHorarios() {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_horario", new HorarioMapper());
    }

    // READ by primary key
    public Horario getHorarioByPk(LocalTime inicio, LocalTime fin) {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_horario_by_pk(?, ?)", new HorarioMapper(), inicio, fin)
                .stream().findFirst().orElse(null);
    }

    // UPDATE
    public void updateHorario(LocalTime inicio, LocalTime fin, LocalTime nuevoInicio, LocalTime nuevoFin) {
        baseDao.getJdbcTemplate().executeProcedure("sp_update_horario(?, ?, ?, ?)", inicio, fin, nuevoInicio, nuevoFin);
    }

    // DELETE
    public void deleteHorario(LocalTime inicio, LocalTime fin) {
        baseDao.getJdbcTemplate().executeProcedure("sp_delete_horario(?, ?)", inicio, fin);
    }

    // Internal Mapper
    private static class HorarioMapper implements RowMapper<Horario> {
        @Override
        public Horario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Horario horario = new Horario();
               horario.setId(rs.getLong("id"));
               horario.setInicio(rs.getTime("inicio").toLocalTime());
               horario.setFin(rs.getTime("fin").toLocalTime());
               return horario;
        }
    }
}
