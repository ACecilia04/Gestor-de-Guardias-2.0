package model;

public class Configuracion {
    private Horario horario;
    private Esquema esquema;
    private boolean actual;

    public Configuracion(Horario horario, Esquema esquema, boolean actual) {
        this.horario = horario;
        this.esquema = esquema;
        this.actual = actual;
    }

    // Getters
    public boolean isActual() {
        return actual;
    }

    // Setters
    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Esquema getEsquema() {
        return esquema;
    }

    public void setEsquema(Esquema esquema) {
        this.esquema = esquema;
    }

    // Display
    @Override
    public String toString() {
        return "Configuracion{" +
                "hora de inicio=" + horario.getInicio().toString() +
                ", hora de fin=" + horario.getFin().toString() +
                ", dia de semana=" + esquema.getDiaSemana() +
                ", dia es receso=" + esquema.diaEsReceso() +
                ", actual=" + actual +
                '}';
    }
}

