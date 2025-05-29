package utils.abstracts;

import utils.abstracts.mappers.RowMapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    private String connUrl;
    private String connUsername;
    private String connPassword;

    public JdbcTemplate(String connUrl, String connUsername, String connPassword) {
        this.connUrl = connUrl;
        this.connUsername = connUsername;
        this.connPassword = connPassword;
    }



    public <T> List<T> query(String query, RowMapper<T> mapper){
        Connection connection;
        try {
            connection = DriverManager.getConnection(connUrl, connUsername, connPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<T> retVal = new ArrayList<>();
            int i = 0;
            while(resultSet.next()){
                retVal.add(mapper.mapRow(resultSet, i++));
            }

            return retVal;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> List<T> query(String query, RowMapper<T> mapper, Object... parameters){
        Connection connection;
        try {
            connection = DriverManager.getConnection(connUrl, connUsername, connPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            int i = 1;
            for (Object parameter : parameters) {
                setStatementField(statement, i++, parameter);
            }
            ResultSet resultSet = statement.executeQuery();

            List<T> retVal = new ArrayList<>();
            i = 0;
            while(resultSet.next()){
                retVal.add(mapper.mapRow(resultSet, i++));
            }

            return retVal;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(String query) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(connUrl, connUsername, connPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(String query, Object... parameters) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(connUrl, connUsername, connPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);

            int i = 1;
            for (Object parameter : parameters) {
                setStatementField(statement, i++, parameter);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public <T> List<T> executeProcedureWithResults(String procedureName, RowMapper<T> mapper, Object... parameters) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(connUrl, connUsername, connPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        CallableStatement statement;
        try {
            statement = connection.prepareCall("{call " + procedureName + "}");
            int i = 1;
            for (Object parameter : parameters) {
                setStatementField(statement, i++, parameter);
            }
            ResultSet resultSet = statement.executeQuery();

            List<T> retVal = new ArrayList<>();
            i = 0;
            while(resultSet.next()){
                retVal.add(mapper.mapRow(resultSet, i++));
            }

            return retVal;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void executeProcedure(String procedureName, Object... parameters) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(connUrl, connUsername, connPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        CallableStatement statement;
        try {
            statement = connection.prepareCall("{call " + procedureName + "}");
            int i = 1;
            for (Object parameter : parameters) {
                setStatementField(statement, i++, parameter);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void setStatementField(PreparedStatement statement, int columnNr, Object value) throws SQLException {
        if (value == null){
            statement.setObject(columnNr, null);
            return;
        }

        if (value.getClass() == Integer.class) {
            statement.setInt(columnNr, (Integer)value);
            return;
        }

        if(value.getClass() == String.class){
            statement.setString(columnNr, (String)value);
            return;
        }

        if(value.getClass() == Long.class){
            statement.setLong(columnNr, (Long)value);
            return;
        }

        if(value.getClass() == Byte.class){
            statement.setByte(columnNr, (Byte)value);
            return;
        }

        if(value.getClass() == java.util.Date.class){
            java.sql.Date sqlDate = new java.sql.Date(((Date)value).getTime());
            statement.setDate(columnNr, sqlDate);
            return;
        }

        if(value.getClass() == Time.class){
            statement.setTime(columnNr, (Time)value);
            return;
        }

        if(value.getClass() == LocalDate.class){
            java.util.Date date = java.util.Date.from(((LocalDate)value).atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            statement.setDate(columnNr, sqlDate);
            return;
        }

        if(value.getClass() == LocalDateTime.class){
            statement.setTimestamp(columnNr, Timestamp.valueOf((LocalDateTime)value));
            return;
        }

        if(value.getClass() == Timestamp.class){
            statement.setTimestamp(columnNr, (Timestamp) value);
        }

        if(value.getClass() == Boolean.class){
            statement.setBoolean(columnNr, (Boolean)value);
        }

    }

}
