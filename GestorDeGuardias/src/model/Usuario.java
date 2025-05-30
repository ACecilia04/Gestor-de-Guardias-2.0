package model;

public class Usuario {
    private Long id;
    private String nombre;
    private String contrasenna;
    private Rol rol;

    public Usuario(String nombre, String contrasenna, Rol rol) {
        this.nombre = nombre;
        this.contrasenna = contrasenna;
        this.rol = rol;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
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
