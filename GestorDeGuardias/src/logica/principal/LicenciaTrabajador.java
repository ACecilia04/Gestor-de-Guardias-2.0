package logica.principal;

import java.time.LocalDate;

public class LicenciaTrabajador extends Licencia {
    private TipoLicencia tipo;

    public LicenciaTrabajador(LocalDate inicio, LocalDate fin, TipoLicencia tipo) {
        super(inicio, fin);
        setTipo(tipo);
    }

    public TipoLicencia getTipo() {
        return tipo;
    }

    //seguro no es tan asi
    public void setTipo(TipoLicencia tipo) {
        this.tipo = tipo;
    }

}
