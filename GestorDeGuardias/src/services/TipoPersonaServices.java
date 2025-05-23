package services;

import model.TipoPersona;
import utils.abstracts.RowMapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoPersonaServices {
    private Connection connection;

    public TipoPersonaServices(Connection connection) {
        this.connection = connection;
    }

    // Internal Mapper
    private static class TipoPersonaMapper implements RowMapper<TipoPersona> {
        @Override
        public TipoPersona mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs == null) return null;
            return new TipoPersona(
                    rs.getString("nombre")
            );
        }
    }

    // CREATE
    public void insertTipoPersona(String nombre) throws SQLException {
        String sql = "{CALL InsertTipoPersona(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        }
    }

    // READ by ID
    public TipoPersona getTipoPersonaById(long id) throws SQLException {
        String sql = "{CALL GetTipoPersonaById(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? new TipoPersonaMapper().mapRow(rs, 1) : null;
        }
    }

    // READ all
    public List<TipoPersona> getAllTiposPersona() throws SQLException {
        List<TipoPersona> tiposPersona = new ArrayList<>();
        String sql = "{CALL GetAllTiposPersona()}";
        try (CallableStatement stmt = connection.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            int rowNum = 0;
            while (rs.next()) {
                tiposPersona.add(new TipoPersonaMapper().mapRow(rs, rowNum++));
            }
        }
        return tiposPersona;
    }

    // UPDATE
    public void updateTipoPersona(long id, String nombre) throws SQLException {
        String sql = "{CALL UpdateTipoPersona(?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.setString(2, nombre);
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteTipoPersona(long id) throws SQLException {
        String sql = "{CALL DeleteTipoPersona(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
