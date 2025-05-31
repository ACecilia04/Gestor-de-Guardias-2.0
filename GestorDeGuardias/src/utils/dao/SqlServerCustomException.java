package utils.dao;

public class SqlServerCustomException extends Exception {
    private static final long serialVersionUID = 1L;

    public SqlServerCustomException(String mensajeError) {
        super(mensajeError);
    }

}
