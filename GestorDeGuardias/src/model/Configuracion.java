package model;

public class Configuracion {
    private int diaSemana;
    private boolean diaEsReceso;
    private Horario horario;
    private TipoPersona tipoPersona;
    private Character sexo;
    private int cantPersonas;
    private boolean borrado;

    public Configuracion(int diaSemana, boolean diaEsReceso, Horario horario, String tipoPersona, Character sexo, int cantPersonas, boolean borrado) {
        this.diaSemana = diaSemana;
        this.diaEsReceso = diaEsReceso;
        this.tipoPersona = new TipoPersona(tipoPersona);
        this.sexo = sexo;
        this.cantPersonas = cantPersonas;
        this.horario = horario;
        this.borrado = borrado;
    }

    public Configuracion() {

    }

    public int getDiaSemana() { return diaSemana; }
    public boolean isDiaEsReceso() { return diaEsReceso; }
    public Horario getHorario() { return horario; }
    public TipoPersona getTipoPersona() { return tipoPersona; }
    public Character getSexo() { return sexo; }
    public int getCantPersonas() { return cantPersonas; }
    public boolean isBorrado() { return borrado; }

    public void setDiaSemana(int diaSemana) { this.diaSemana = diaSemana; }
    public void setDiaEsReceso(Boolean diaEsReceso) { this.diaEsReceso = diaEsReceso; }
    public void setHorario(Horario horario) { this.horario = horario; }
    public void setTipoPersona(TipoPersona tipoPersona) { this.tipoPersona = tipoPersona; }
    public void setSexo(Character sexo) { this.sexo = sexo; }
    public void setCantPersonas(int cantPersonas) { this.cantPersonas = cantPersonas; }
    public void setBorrado(boolean borrado) { this.borrado = borrado; }

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

