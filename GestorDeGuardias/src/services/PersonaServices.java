package services;

import model.Persona;
import utils.abstracts.RowMapper;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class PersonaServices {
    private Connection connection;

    public PersonaServices(Connection connection) {
        this.connection = connection;
    }

    // Internal Mapper Class
    private static class PersonaMapper implements RowMapper<Persona> {
        @Override
        public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs == null) return null;

            return new Persona(
                    rs.getLong("id"),
                    rs.getString("carnet"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("sexo").charAt(0),
                    rs.getString("tipo")
            );
        }
    }

    // CREATE (Insert using Stored Procedure)
    public void insertPersona(String carnet, String nombre, String apellido, char sexo, String tipoPersona, boolean activo) throws SQLException {
        String sql = "{CALL InsertPersona(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, carnet);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido);
            stmt.setString(4, String.valueOf(sexo));
            stmt.setString(5, tipoPersona);
            stmt.setBoolean(6, activo);
            stmt.executeUpdate();
        }
    }


    // READ (Get Persona by ID using Stored Procedure)
    public Persona getPersonaById(long id) throws SQLException {
        String sql = "{CALL GetPersonaById(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? new PersonaMapper().mapRow(rs, 1) : null;
        }
    }

    // READ (Get All Personas using Stored Procedure)
    public List<Persona> getAllPersonas() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String sql = "{CALL GetAllPersonas()}";
        try (CallableStatement stmt = connection.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            int rowNum = 0;
            while (rs.next()) {
                personas.add(new PersonaMapper().mapRow(rs, rowNum++));
            }
        }
        return personas;
    }

    // UPDATE (Using Stored Procedure)
    public void updatePersona(long id, String carnet, String nombre, String apellido, char sexo, String tipoPersona, boolean activo) throws SQLException {
        String sql = "{CALL UpdatePersona(?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.setString(2, carnet);
            stmt.setString(3, nombre);
            stmt.setString(4, apellido);
            stmt.setString(5, String.valueOf(sexo));
            stmt.setString(6, tipoPersona);
            stmt.setBoolean(7, activo);
            stmt.executeUpdate();
        }
    }


    // DELETE (Using Stored Procedure)
    public void deletePersona(long id) throws SQLException {
        String sql = "{CALL DeletePersona(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
