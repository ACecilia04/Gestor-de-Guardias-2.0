package utils.abstracts;

import java.util.List;

public abstract class BaseDao {

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
            return list.get(0);
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


    private boolean isArrayValid(Object[] array) {
        return array != null && array.length > 0;
    }

    private boolean isListValid(List<?> list) {
        return list != null && !list.isEmpty();
    }
}
