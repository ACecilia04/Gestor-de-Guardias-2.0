package utils.dao;

import utils.dao.mappers.RowMapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public void executeProcedure(String procedureName, Object... parameters) throws SQLException {
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
            throw e;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void setStatementField(PreparedStatement statement, int columnNr, Object value) throws SQLException {
        if (value instanceof Integer) {
            statement.setInt(columnNr, (Integer) value);
        } else if (value instanceof String) {
            statement.setString(columnNr, (String) value);
        } else if (value instanceof Long) {
            statement.setLong(columnNr, (Long) value);
        } else if (value instanceof Character) {
            statement.setString(columnNr, String.valueOf(value));
        } else if (value instanceof Byte) {
            statement.setByte(columnNr, (Byte) value);
        } else if (value instanceof java.util.Date) {
            java.sql.Date sqlDate = new java.sql.Date(((Date) value).getTime());
            statement.setDate(columnNr, sqlDate);
        } else if (value instanceof Time) {
            statement.setTime(columnNr, (Time) value);
        } else if (value instanceof LocalDate) {
            java.util.Date date = java.util.Date.from(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            statement.setDate(columnNr, sqlDate);
        } else if (value instanceof LocalTime) {
            java.sql.Time sqlTime = Time.valueOf((LocalTime) value);
            statement.setTime(columnNr, sqlTime);
        } else if (value instanceof LocalDateTime) {
            statement.setTimestamp(columnNr, Timestamp.valueOf((LocalDateTime) value));
        } else if (value instanceof Timestamp) {
            statement.setTimestamp(columnNr, (Timestamp) value);
        } else if (value instanceof Boolean) {
            statement.setBoolean(columnNr, (Boolean) value);
        } else

            statement.setObject(columnNr, null);
    }

}
