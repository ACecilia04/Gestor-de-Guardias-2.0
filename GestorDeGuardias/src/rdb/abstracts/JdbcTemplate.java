package rdb.abstracts;

import java.sql.*;
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

    private void setStatementField(PreparedStatement statement, int columnNr, Object value) throws SQLException {
        if (value.getClass() == Integer.class) {
            statement.setInt(columnNr, (Integer) value);
            return;
        }

        if (value.getClass() == String.class) {
            statement.setString(columnNr, (String) value);
            return;
        }

        if (value.getClass() == Long.class) {
            statement.setLong(columnNr, (Long) value);
            return;
        }

        if (value.getClass() == Byte.class) {
            statement.setByte(columnNr, (Byte) value);
            return;
        }

        if (value.getClass() == Date.class) {
            statement.setDate(columnNr, (Date) value);
            return;
        }

        if (value.getClass() == Time.class) {
            statement.setTime(columnNr, (Time) value);
            return;
        }

        if (value.getClass() == Timestamp.class) {
            statement.setTimestamp(columnNr, (Timestamp) value);
        }
    }
}
