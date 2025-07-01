package services;

import model.PeriodoNoPlanificable;
import utils.dao.MainBaseDao;
import utils.dao.SqlServerCustomException;
import utils.dao.mappers.BooleanMapper;
import utils.dao.mappers.IntegerMapper;
import utils.dao.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PeriodoNoPlanificableServices {

    private final MainBaseDao baseDao;

    public PeriodoNoPlanificableServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertPeriodoNoPlanificable(LocalDate inicio, LocalDate fin) throws SqlServerCustomException {
        baseDao.spUpdate("sp_periodo_no_planificable_create(?, ?)", inicio, fin);
    }

    // READ all
    public ArrayList<PeriodoNoPlanificable> getAllPeriodosNoPlanificables() {
        return (ArrayList<PeriodoNoPlanificable>) baseDao.spQuery("sp_periodo_no_planificable_read", new PeriodoNoPlanificableMapper());
    }

    // READ by primary key
    public PeriodoNoPlanificable getPeriodoNoPlanificableByPk(LocalDate inicio, LocalDate fin) {
        return baseDao.spQuerySingleObject("sp_periodo_no_planificable_read_by_pk(?, ?)", new PeriodoNoPlanificableMapper(), inicio, fin);
    }

    // UPDATE
    public void updatePeriodoNoPlanificable(LocalDate inicio, LocalDate fin, LocalDate nuevoInicio, LocalDate nuevoFin) throws SqlServerCustomException {
        baseDao.spUpdate("sp_periodo_no_planificable_update(?, ?, ?, ?)", inicio, fin, nuevoInicio, nuevoFin);
    }

    // DELETE
    public void deletePeriodoNoPlanificable(LocalDate inicio, LocalDate fin) throws SqlServerCustomException {
        baseDao.spUpdate("sp_periodo_no_planificable_delete(?, ?)", inicio, fin);
    }

    public int countPeriodoNoPlanificable() {
        return baseDao.spQuerySingleObject("sp_periodo_no_planificable_count", new IntegerMapper("total"));
    }

    public PeriodoNoPlanificable getPeriodoEnFecha(LocalDate fecha) {
        return baseDao.spQuerySingleObject("sp_periodo_no_planificable_in_date(?)", new PeriodoNoPlanificableMapper(), fecha);
    }

    public Boolean fechaEsNoPlanificable(LocalDate fecha) {
        return baseDao.spQuerySingleObject("sp_periodo_no_planificable_has_date(?)", new BooleanMapper("existe"), fecha);
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
