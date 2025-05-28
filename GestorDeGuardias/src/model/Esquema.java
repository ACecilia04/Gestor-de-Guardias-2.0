package model;

public class Esquema {
    private int diaSemana;
    private boolean diaEsReceso;
    private TipoPersona tipoPersona;
    private Character sexo;
    private int cantPersonas;

    // Constructor
    public Esquema(int diaSemana, boolean diaEsReceso, String tipoPersona, Character sexo, int cantPersonas) {
        this.diaSemana = diaSemana;
        this.diaEsReceso = diaEsReceso;
        this.tipoPersona = new TipoPersona(tipoPersona);
        this.sexo = sexo;
        this.cantPersonas = cantPersonas;
    }

    public Esquema(int diaSemana, boolean diaEsReceso) {
        this.diaSemana = diaSemana;
        this.diaEsReceso = diaEsReceso;
    }

    public Esquema() {

    }

    //Getters
    public int getDiaSemana() {
        return diaSemana;
    }

    // Setters
    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public boolean diaEsReceso() {
        return diaEsReceso;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public int getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(int cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    public void setDiaEsReceso(boolean diaEsReceso) {
        this.diaEsReceso = diaEsReceso;
    }


}

