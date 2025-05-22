package model;

public class Usuario {
    private final String nombre;
    private final String contrasenna;
    private Rol rol;

    public Usuario(String nombre, String contrasenna, Rol rol) {
        this.nombre = nombre;
        this.contrasenna = contrasenna;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
