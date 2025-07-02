package model;

import java.time.LocalDateTime;

public class Auditoria {
    private Long id;
    private LocalDateTime timestamp;
    private String servicio;
    private String funcionalidad;
    private String parametros;
    private Usuario usuario;

    public Auditoria(Usuario usuario, LocalDateTime timestamp, String servicio, String funcionalidad, String parametros) {
        this.usuario = usuario;
        this.timestamp = timestamp;
        this.servicio = servicio;
        this.funcionalidad = funcionalidad;
        this.parametros = parametros;
    }

    public Auditoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(String funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }
}
