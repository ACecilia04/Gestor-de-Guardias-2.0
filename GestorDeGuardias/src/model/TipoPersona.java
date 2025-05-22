package model;

public class TipoPersona {
    private String tipo;

    public TipoPersona(String tipo) {
        if (tipo.equalsIgnoreCase("estudiante")) {
            this.tipo = "Estudiante";
        } else if (tipo.equalsIgnoreCase("trabajador")) {
            this.tipo = "Trabajador";
        } else {
            throw new IllegalArgumentException("Tipo de persona no válido: " + tipo);
        }
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoPersona)) return false;
        TipoPersona that = (TipoPersona) o;
        return tipo.equalsIgnoreCase(that.tipo);
    }

    @Override
    public int hashCode() {
        return tipo.toLowerCase().hashCode();
    }
}
