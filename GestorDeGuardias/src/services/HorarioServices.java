package services;

import model.Horario;
import utils.dao.MainBaseDao;
import utils.dao.SqlServerCustomException;
import utils.dao.mappers.RowMapper;
import utils.exceptions.MultiplesErroresException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioServices {

    private final MainBaseDao baseDao;

    public HorarioServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertHorario(Horario record) throws SqlServerCustomException, MultiplesErroresException {
        validarHorario(record);

        baseDao.spUpdate("sp_horario_create(?, ?)",
                record.getInicio(),
                record.getFin()
        );
    }

    // READ all
    public List<Horario> getAllHorarios() {
        return baseDao.spQuery("sp_horario_read", new HorarioMapper());
    }

    // READ by primary key
    public Horario getHorarioByPk(LocalTime inicio, LocalTime fin) {
        return baseDao.spQuerySingleObject("sp_horario_read_by_pk(?, ?)", new HorarioMapper(), inicio, fin);
    }

    // READ by id
    public Horario getHorarioById(Long id) {
        return baseDao.spQuerySingleObject("sp_horario_read_by_id(?)", new HorarioMapper(), id);
    }

    // UPDATE
    public void updateHorario(Horario record) throws SqlServerCustomException, MultiplesErroresException {
        validarHorario(record);

        baseDao.spUpdate("sp_horario_update(?, ?, ?)",
                record.getId(),
                record.getInicio(),
                record.getFin()
        );
    }

    // DELETE
    public void deleteHorario(Long id) throws SqlServerCustomException {
        baseDao.spUpdate("sp_horario_delete(?)", id);
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


    // ==============   VALIDACIONES   ==========================================
    private void validarHorario(Horario record) throws MultiplesErroresException {
        List<String> errores = new ArrayList<>();

        if (record.getInicio() == null)
            errores.add("Hora inicial no especificada.");
        if (record.getFin() == null)
            errores.add("Hora final no especificada.");

        if (!errores.isEmpty())
            throw new MultiplesErroresException("Horario con datos erróneos:", errores);
    }
}
