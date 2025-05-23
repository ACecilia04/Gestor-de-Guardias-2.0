package utils.abstracts;

import java.util.List;

public abstract class BaseService {

    public abstract JdbcTemplate getJdbcTemplate();

    public <T> List<T> query(String query, RowMapper<T> mapper, Object... parameters) {
        if (isArrayValid(parameters)) {
            return getJdbcTemplate().query(query, mapper, parameters);
        }
        return getJdbcTemplate().query(query, mapper);
    }


    public <T> T querySingleObject(String query, RowMapper<T> mapper, Object... parameters) {
        List<T> list = null;
        if (isArrayValid(parameters)) {
            list = getJdbcTemplate().query(query, mapper, parameters);
        } else {
            list = getJdbcTemplate().query(query, mapper);
        }

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
        List<T> list = null;
        if (isArrayValid(parameters)) {
            list = getJdbcTemplate().executeProcedureWithResults(procedureName, mapper, parameters);
        } else {
            list = getJdbcTemplate().executeProcedureWithResults(procedureName, mapper);
        }

        if (isListValid(list)) {
            return list.getFirst();
        }
        return null;
    }

    public <T> T spQuerySingleResult(String procedureName, String resultName, int resultType, Object... parameters) {
        if (isArrayValid(parameters)) {
            return getJdbcTemplate().executeProcedureWithResult(procedureName, resultName, resultType, parameters);
        }
        return getJdbcTemplate().executeProcedureWithResult(procedureName, resultName, resultType);
    }

    public void spUpdate(String query, Object... parameters) {
        if (isArrayValid(parameters)) {
            getJdbcTemplate().executeProcedure(query, parameters);
        } else {
            getJdbcTemplate().executeProcedure(query);
        }
    }


    private boolean isArrayValid(Object[] array) {
        return array != null && array.length > 0;
    }

    private boolean isListValid(List<?> list) {
        return list != null && !list.isEmpty();
    }
}
