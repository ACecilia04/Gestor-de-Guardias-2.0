package model;

public class TipoPersona {
    private final String nombre;

    public TipoPersona(String tipo) {
        this.nombre = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public boolean equals(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return false;
        }
        return nombre.equalsIgnoreCase(tipo);
    }

    @Override
    public int hashCode() {
        return nombre.toLowerCase().hashCode();
    }

}
