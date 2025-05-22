package utils;

import utils.abstracts.BaseDao;
import utils.abstracts.JdbcTemplate;

public class MainBaseDao extends BaseDao {

    public JdbcTemplate getJdbcTemplate() {
        String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=guardia;integratedSecurity=false;encrypt=false;trustServerCertificate=true";
        String connUsername = "guardiaadm";
        String connPassword = "sa123";

        return new JdbcTemplate(connUrl, connUsername, connPassword);
    }
}
