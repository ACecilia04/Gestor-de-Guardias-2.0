package utils.abstracts;

public class MainBaseDao extends BaseService {

    public JdbcTemplate getJdbcTemplate(){
        String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=guardia;integratedSecurity=false;encrypt=false;trustServerCertificate=true";
        String connUsername = "guardiaadm";
        String connPassword = "sa123";

        return new JdbcTemplate(connUrl, connUsername, connPassword);
    }
    public MainBaseDao(){
        super();
    }
}
