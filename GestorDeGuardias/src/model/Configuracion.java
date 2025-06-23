package model;

public class Configuracion {
    private Long id;
    private Integer diaSemana;
    private Boolean diaEsReceso;
    private Horario horario;
    private TipoPersona tipoPersona;
    private String sexo;
    private Integer cantPersonas;
    private Boolean borrado;

    public Configuracion(Integer diaSemana, Boolean diaEsReceso, Horario horario, String tipoPersona, String sexo, Integer cantPersonas) {
        this.diaSemana = diaSemana;
        this.diaEsReceso = diaEsReceso;
        this.tipoPersona = new TipoPersona(tipoPersona);
        this.sexo = sexo;
        this.cantPersonas = cantPersonas;
        this.horario = horario;
    }

    public Configuracion() {

    }



    public Long getId() { return id;}
    public Integer getDiaSemana() { return diaSemana; }
    public Boolean diaEsReceso() { return diaEsReceso; }
    public Horario getHorario() { return horario; }
    public TipoPersona getTipoPersona() { return tipoPersona; }
    public String getSexo() { return sexo; }
    public Integer getCantPersonas() { return cantPersonas; }
    public Boolean isBorrado() { return borrado; }

    public void setId(Long id) {this.id = id;}
    public void setDiaSemana(Integer diaSemana) { this.diaSemana = diaSemana; }
    public void setDiaEsReceso(Boolean diaEsReceso) { this.diaEsReceso = diaEsReceso; }
    public void setHorario(Horario horario) { this.horario = horario; }
    public void setTipoPersona(TipoPersona tipoPersona) { this.tipoPersona = tipoPersona; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public void setCantPersonas(Integer cantPersonas) { this.cantPersonas = cantPersonas; }
    public void setBorrado(Boolean borrado) { this.borrado = borrado; }

    // Display
    @Override
    public String toString() {
        return "Configuracion{" +
                "diaSemana=" + diaSemana +
                ", diaEsReceso=" + diaEsReceso +
                ", horario=" + (horario != null ? horario.getInicio() + " - " + horario.getFin() : "No definido") +
                ", tipoPersona=" + (tipoPersona != null ? tipoPersona.toString() : "No definido") +
                ", sexo=" + sexo +
                ", cantPersonas=" + cantPersonas +
                ", borrado=" + borrado +
                '}';
    }
}

