package services;

import model.PeriodoNoPlanificable;
import utils.abstracts.MainBaseDao;
import utils.abstracts.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeriodoNoPlanificableServices {

    private final MainBaseDao baseDao;

    public PeriodoNoPlanificableServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertPeriodoNoPlanificable(LocalDate inicio, LocalDate fin) {
        baseDao.getJdbcTemplate().executeProcedure("sp_create_periodo_no_planificable(?, ?)", inicio, fin);
    }

    // READ all
    public ArrayList<PeriodoNoPlanificable> getAllPeriodosNoPlanificables() {
        return (ArrayList<PeriodoNoPlanificable>) baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_periodo_no_planificable", new PeriodoNoPlanificableMapper());
    }

    // READ by primary key
    public PeriodoNoPlanificable getPeriodoNoPlanificableByPk(LocalDate inicio, LocalDate fin) {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_periodo_no_planificable_by_pk(?, ?)", new PeriodoNoPlanificableMapper(), inicio, fin)
                .stream().findFirst().orElse(null);
    }

    // UPDATE
    public void updatePeriodoNoPlanificable(LocalDate inicio, LocalDate fin, LocalDate nuevoInicio, LocalDate nuevoFin) {
        baseDao.getJdbcTemplate().executeProcedure("sp_update_periodo_no_planificable(?, ?, ?, ?)", inicio, fin, nuevoInicio, nuevoFin);
    }

    // DELETE
    public void deletePeriodoNoPlanificable(LocalDate inicio, LocalDate fin) {
        baseDao.getJdbcTemplate().executeProcedure("sp_delete_periodo_no_planificable(?, ?)", inicio, fin);
    }

    public int countPeriodoNoPlanificable(){
       return baseDao.getJdbcTemplate().executeProcedureWithResult("sp_count_periodo_no_planificable(?)", "total", Types.INTEGER);
    }

    public PeriodoNoPlanificable getPeriodosEnFecha(LocalDate fecha){
        return baseDao.getJdbcTemplate().executeProcedureWithResults(
                "sp_periodos_no_planificables_in_date",new PeriodoNoPlanificableMapper(), fecha)
                .stream().findFirst().orElse(null);
    }
    // Internal Mapper
    private static class PeriodoNoPlanificableMapper implements RowMapper<PeriodoNoPlanificable> {
        @Override
        public PeriodoNoPlanificable mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PeriodoNoPlanificable(
                    rs.getDate("inicio").toLocalDate(),
                    rs.getDate("fin").toLocalDate()
            );
        }
    }
}
