package utils.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import utils.dao.mappers.RowMapper;

import java.sql.SQLException;
import java.util.List;

public abstract class SqlServerBaseDao {

    public abstract JdbcTemplate getJdbcTemplate();


    public <T> List<T> query(String query, RowMapper<T> mapper, Object... parameters) {
        if (isArrayValid(parameters)) {
            return getJdbcTemplate().query(query, mapper, parameters);
        }
        return getJdbcTemplate().query(query, mapper);
    }

    public <T> T querySingleObject(String query, RowMapper<T> mapper, Object... parameters) {
        List<T> list = query(query, mapper, parameters);

        if (isListValid(list)) {
            return list.getFirst();
        }
        return null;
    }

    public void update(String query, Object... parameters) {
        if (isArrayValid(parameters)) {
            getJdbcTemplate().update(query, parameters);
        } else {
            getJdbcTemplate().update(query);
        }
    }


    public <T> List<T> spQuery(String procedureName, RowMapper<T> mapper, Object... parameters) {
        if (isArrayValid(parameters)) {
            return getJdbcTemplate().executeProcedureWithResults(procedureName, mapper, parameters);
        }
        return getJdbcTemplate().executeProcedureWithResults(procedureName, mapper);
    }

    public <T> T spQuerySingleObject(String procedureName, RowMapper<T> mapper, Object... parameters) {
        List<T> list = spQuery(procedureName, mapper, parameters);

        if (isListValid(list)) {
            return list.getFirst();
        }
        return null;
    }

    public void spUpdate(String query, Object... parameters) throws SqlServerCustomException {
        try {
            if (isArrayValid(parameters)) {
                getJdbcTemplate().executeProcedure(query, parameters);
            } else {
                getJdbcTemplate().executeProcedure(query);
            }
        } catch (SQLException e) {
            if (e instanceof SQLServerException){
                if (((SQLServerException)e).getSQLServerError().getErrorNumber() == 51000)
                    throw new SqlServerCustomException(((SQLServerException)e).getSQLServerError().getErrorMessage());
                else
                    throw new RuntimeException(e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }


    private boolean isArrayValid(Object[] array) {
        return array != null && array.length > 0;
    }

    private boolean isListValid(List<?> list) {
        return list != null && !list.isEmpty();
    }
}
