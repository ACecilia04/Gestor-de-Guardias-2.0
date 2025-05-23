package services;

import model.Esquema;
import utils.abstracts.RowMapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EsquemaServices {
    private Connection connection;

    public EsquemaServices(Connection connection) {
        this.connection = connection;
    }

    private static class EsquemaMapper implements RowMapper<Esquema> {
        @Override
        public Esquema mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs == null) return null;
            return new Esquema(
                    rs.getInt("dia_semana"),
                    rs.getBoolean("dia_es_receso"),
                    rs.getString("tipo_persona"),
                    rs.getString("sexo").charAt(0),
                    rs.getInt("cant_personas")
            );
        }
    }

    // CREATE
    public void insertEsquema(Esquema esquema) throws SQLException {
        String sql = "{CALL InsertEsquema(?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setInt(1, esquema.getDiaSemana());
            stmt.setBoolean(2, esquema.diaEsReceso());
            stmt.setString(3, esquema.getTipoPersona());
            stmt.setString(4, String.valueOf(esquema.getSexo()));
            stmt.setInt(5, esquema.getCantPersonas());
            stmt.executeUpdate();
        }
    }

    // READ
    public Esquema getEsquemaByDia(int diaSemana, boolean diaEsReceso) throws SQLException {
        String sql = "{CALL GetEsquemaByDia(?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setInt(1, diaSemana);
            stmt.setBoolean(2, diaEsReceso);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? new EsquemaMapper().mapRow(rs, 1) : null;
        }
    }

    // READ
    public List<Esquema> getAllEsquemas() throws SQLException {
        List<Esquema> esquemas = new ArrayList<>();
        String sql = "{CALL GetAllEsquemas()}";
        try (CallableStatement stmt = connection.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            int rowNum = 0;
            while (rs.next()) {
                esquemas.add(new EsquemaMapper().mapRow(rs, rowNum++));
            }
        }
        return esquemas;
    }

    // UPDATE
    public void updateEsquema(Esquema esquema) throws SQLException {
        String sql = "{CALL UpdateEsquema(?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setInt(1, esquema.getDiaSemana());
            stmt.setBoolean(2, esquema.diaEsReceso());
            stmt.setString(3, esquema.getTipoPersona());
            stmt.setString(4, String.valueOf(esquema.getSexo()));
            stmt.setInt(5, esquema.getCantPersonas());
            stmt.executeUpdate();
        }
    }

    // DELETE (Using Stored Procedure)
    public void deleteEsquema(int diaSemana, boolean diaEsReceso) throws SQLException {
        String sql = "{CALL DeleteEsquema(?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setInt(1, diaSemana);
            stmt.setBoolean(2, diaEsReceso);
            stmt.executeUpdate();
        }
    }
}
