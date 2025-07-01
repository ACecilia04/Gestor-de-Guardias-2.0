package utils.dao;

public class MainBaseDao extends SqlServerBaseDao {

    public MainBaseDao() {
        super();
    }

    public JdbcTemplate getJdbcTemplate() {
        String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=Gestor_de_Guardias;integratedSecurity=false;encrypt=false;trustServerCertificate=true";
        String connUsername = "guardiasadm";
        String connPassword = "sa123";

        return new JdbcTemplate(connUrl, connUsername, connPassword);
    }
}
