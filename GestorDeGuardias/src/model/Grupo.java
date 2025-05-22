package model;

public class Grupo {
    private Long id;
    private String numero;
    private Integer cantEstudiantes;

    // Constructor con todos los campos
    public Grupo(Long id, String numero, Integer cantEstudiantes) {
        this.id = id;
        this.numero = numero;
        this.cantEstudiantes = cantEstudiantes;
    }

    // Constructor con todos los campos menos el id
    public Grupo(String numero, Integer cantEstudiantes) {
        this.numero = numero;
        this.cantEstudiantes = cantEstudiantes;
    }

    // Constructor vacio
    public Grupo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getCantEstudiantes() {
        return cantEstudiantes;
    }

    public void setCantEstudiantes(Integer cantEstudiantes) {
        this.cantEstudiantes = cantEstudiantes;
    }
}
