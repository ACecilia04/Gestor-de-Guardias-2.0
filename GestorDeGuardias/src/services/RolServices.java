package services;

import model.Rol;
import utils.abstracts.RowMapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolServices {
    private Connection connection;

    public RolServices(Connection connection) {
        this.connection = connection;
    }

    // Internal Mapper
    private static class RolMapper implements RowMapper<Rol> {
        @Override
        public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs == null) return null;
            return new Rol(
                    rs.getString("nombre")
            );
        }
    }

    // CREATE
    public void insertRol(String nombre) throws SQLException {
        String sql = "{CALL InsertRol(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        }
    }

    // READ by ID
    public Rol getRolById(long id) throws SQLException {
        String sql = "{CALL GetRolById(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? new RolMapper().mapRow(rs, 1) : null;
        }
    }

    // READ all
    public List<Rol> getAllRoles() throws SQLException {
        List<Rol> roles = new ArrayList<>();
        String sql = "{CALL GetAllRoles()}";
        try (CallableStatement stmt = connection.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            int rowNum = 0;
            while (rs.next()) {
                roles.add(new RolMapper().mapRow(rs, rowNum++));
            }
        }
        return roles;
    }

    // UPDATE
    public void updateRol(long id, String nombre) throws SQLException {
        String sql = "{CALL UpdateRol(?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.setString(2, nombre);
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteRol(long id) throws SQLException {
        String sql = "{CALL DeleteRol(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
