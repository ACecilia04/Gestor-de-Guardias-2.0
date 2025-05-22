package model;

public class Esquema {
    private int diaSemana;
    private boolean diaEsReceso;
    private TipoPersona tipoPersona;
    private Character sexo;
    private int cantPersonas;

    // Constructor
    public Esquema(int diaSemana, boolean diaEsReceso, TipoPersona tipoPersona, Character sexo, int cantPersonas) {
        this.diaSemana = diaSemana;
        this.diaEsReceso = diaEsReceso;
        this.tipoPersona = tipoPersona;
        this.sexo = sexo;
        this.cantPersonas = cantPersonas;
    }

    //Getters
    public int getDiaSemana() {
        return diaSemana;
    }

    public boolean diaEsReceso() {
        return diaEsReceso;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public Character getSexo() {
        return sexo;
    }

    public int getCantPersonas() {
        return cantPersonas;
    }

    // Setters
    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setDiaEsReceso(boolean diaEsReceso) {
        this.diaEsReceso = diaEsReceso;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public void setCantPersonas(int cantPersonas) {
        this.cantPersonas = cantPersonas;
    }
//    public Horario getHorario() { return horario; }
//    public void setHorario(Horario horario) { this.horario = horario; }
//
//    public List<Persona> getPersonasAsignadas() { return personasAsignadas; }
//    public void asignarPersona(Persona persona) { personasAsignadas.add(persona); }
//
//    public boolean esValidoParaAsignacion() {
//        return !diaEsReceso && personasAsignadas.size() < cantPersonas;
//    }
}

