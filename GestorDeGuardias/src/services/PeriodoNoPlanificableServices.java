package services;

import model.PeriodoNoPlanificable;
import utils.abstracts.RowMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeriodoNoPlanificableServices {
    private Connection connection;

    public PeriodoNoPlanificableServices(Connection connection) {
        this.connection = connection;
    }

    // Internal Mapper
    private static class PeriodoNoPlanificableMapper implements RowMapper<PeriodoNoPlanificable> {
        @Override
        public PeriodoNoPlanificable mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs == null) return null;
            return new PeriodoNoPlanificable(
                    rs.getDate("inicio").toLocalDate(),
                    rs.getDate("fin").toLocalDate()
            );
        }
    }

    // CREATE
    public void insertPeriodoNoPlanificable(LocalDate inicio, LocalDate fin) throws SQLException {
        String sql = "{CALL InsertPeriodoNoPlanificable(?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setDate(1, Date.valueOf(inicio));
            stmt.setDate(2, Date.valueOf(fin));
            stmt.executeUpdate();
        }
    }

    // READ by ID
    public PeriodoNoPlanificable getPeriodoNoPlanificableById(long id) throws SQLException {
        String sql = "{CALL GetPeriodoNoPlanificableById(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? new PeriodoNoPlanificableMapper().mapRow(rs, 1) : null;
        }
    }

    // READ all
    public List<PeriodoNoPlanificable> getAllPeriodosNoPlanificables() throws SQLException {
        List<PeriodoNoPlanificable> periodos = new ArrayList<>();
        String sql = "{CALL GetAllPeriodosNoPlanificables()}";
        try (CallableStatement stmt = connection.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            int rowNum = 0;
            while (rs.next()) {
                periodos.add(new PeriodoNoPlanificableMapper().mapRow(rs, rowNum++));
            }
        }
        return periodos;
    }

    // UPDATE
    public void updatePeriodoNoPlanificable(long id, LocalDate inicio, LocalDate fin) throws SQLException {
        String sql = "{CALL UpdatePeriodoNoPlanificable(?, ?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.setDate(2, Date.valueOf(inicio));
            stmt.setDate(3, Date.valueOf(fin));
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deletePeriodoNoPlanificable(long id) throws SQLException {
        String sql = "{CALL DeletePeriodoNoPlanificable(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
