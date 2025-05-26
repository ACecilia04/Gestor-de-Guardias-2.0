package utils.abstracts;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    private final String connUrl;
    private final String connUsername;
    private final String connPassword;

    public JdbcTemplate(String connUrl, String connUsername, String connPassword) {
        this.connUrl = connUrl;
        this.connUsername = connUsername;
        this.connPassword = connPassword;
    }

    private static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        return clazz.isInstance(o) ? clazz.cast(o) : null;
    }

    public <T> List<T> query(String query, RowMapper<T> mapper) {
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
            while (resultSet.next()) {
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

    public <T> List<T> query(String query, RowMapper<T> mapper, Object... parameters) {
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
            while (resultSet.next()) {
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
            while (resultSet.next()) {
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

    public <T> T executeProcedureWithResult(String procedureName, String resultName, int resultType, Object... parameters) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(connUrl, connUsername, connPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        CallableStatement statement;
        try {
            statement = connection.prepareCall("{CALL " + procedureName + "}");
            int i = 1;
            for (Object parameter : parameters) {
                setStatementField(statement, i++, parameter);
            }
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return getStatementField(resultSet, resultName, resultType);
            }
            return null;
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
            statement.setString(columnNr, null);
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
    private void setStatementInOutField(CallableStatement statement, int columnNr, Object value) throws SQLException {
        setStatementField(statement, columnNr, value);
    }

    private void setStatementOutField(CallableStatement statement, int columnNr, int type) throws SQLException {
        statement.registerOutParameter(columnNr, type);
    }

    private <T> T getStatementField(ResultSet resultSet, String resultName, int type) throws SQLException {
        switch (type) {
            case Types.VARCHAR:
            case Types.NVARCHAR:
            case Types.LONGVARCHAR:
            case Types.LONGNVARCHAR:
                return convertInstanceOfObject(resultSet.getString(resultName), (Class<T>) String.class);
            case Types.INTEGER:
            case Types.SMALLINT:
                return convertInstanceOfObject(resultSet.getInt(resultName), (Class<T>) Integer.class);
            case Types.BIGINT:
                return convertInstanceOfObject(resultSet.getLong(resultName), (Class<T>) Long.class);
            case Types.BOOLEAN:
                return convertInstanceOfObject(resultSet.getBoolean(resultName), (Class<T>) Boolean.class);
            case Types.TINYINT:
                return convertInstanceOfObject(resultSet.getByte(resultName), (Class<T>) Byte.class);
            case Types.NUMERIC:
            case Types.FLOAT:
                return convertInstanceOfObject(resultSet.getFloat(resultName), (Class<T>) Float.class);
            case Types.DECIMAL:
            case Types.DOUBLE:
                return convertInstanceOfObject(resultSet.getBigDecimal(resultName), (Class<T>) BigDecimal.class);
            case Types.DATE:
                return convertInstanceOfObject(resultSet.getDate(resultName), (Class<T>) java.util.Date.class);
            case Types.TIME:
                return convertInstanceOfObject(resultSet.getTime(resultName), (Class<T>) java.util.Date.class);
            case Types.TIMESTAMP:
                return convertInstanceOfObject(resultSet.getTime(resultName), (Class<T>) LocalDateTime.class);
            default:
                return null;
        }
    }

}
