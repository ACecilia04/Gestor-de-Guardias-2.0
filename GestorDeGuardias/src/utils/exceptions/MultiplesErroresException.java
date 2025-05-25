package utils.exceptions;

import java.util.ArrayList;
import java.util.List;

public class MultiplesErroresException extends Exception {
    private static final long serialVersionUID = 1L;

    private List<String> errores;

    public MultiplesErroresException() {
        super();
        this.errores = new ArrayList<>();
    }

    public MultiplesErroresException(String mensaje) {
        super(mensaje);
        this.errores = new ArrayList<>();
    }

    public MultiplesErroresException(String mensaje, List<String> errores) {
        super(mensaje);
        this.errores = errores;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }


}
