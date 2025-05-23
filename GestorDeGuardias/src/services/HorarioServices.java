package services;

import model.Horario;
import utils.abstracts.RowMapper;
import java.time.LocalTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorarioServices {
    private Connection connection;

    public HorarioServices(Connection connection) {
        this.connection = connection;
    }

    private static class HorarioMapper implements RowMapper<Horario> {
        @Override
        public Horario mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs == null) return null;
            return new Horario(
                    rs.getTime("inicio").toLocalTime(),
                    rs.getTime("fin").toLocalTime()
            );
        }
    }

    // CREATE
    public void insertHorario(LocalTime inicio, LocalTime fin) throws SQLException {
        String sql = "{CALL InsertHorario(?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setTime(1, Time.valueOf(inicio));
            stmt.setTime(2, Time.valueOf(fin));
            stmt.executeUpdate();
        }
    }

    // READ
    public Horario getHorarioById(long id) throws SQLException {
        String sql = "{CALL GetHorarioById(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? new HorarioMapper().mapRow(rs, 1) : null;
        }
    }

    // READ
    public List<Horario> getAllHorarios() throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        String sql = "{CALL GetAllHorarios()}";
        try (CallableStatement stmt = connection.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            int rowNum = 0;
            while (rs.next()) {
                horarios.add(new HorarioMapper().mapRow(rs, rowNum++));
            }
        }
        return horarios;
    }

    // UPDATE
    public void updateHorario(long id, LocalTime inicio, LocalTime fin) throws SQLException {
        String sql = "{CALL UpdateHorario(?, ?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.setTime(2, Time.valueOf(inicio));
            stmt.setTime(3, Time.valueOf(fin));
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteHorario(long id) throws SQLException {
        String sql = "{CALL DeleteHorario(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
