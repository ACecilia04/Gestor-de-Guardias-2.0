package model;

import java.time.LocalTime;

public class Horario {
    private Long id;
    private LocalTime inicio;
    private LocalTime fin;

    public Horario(Long id,LocalTime inicio, LocalTime fin) {
        //TODO: check setter use
        this.inicio = inicio;
        this.fin = fin;
    }

    public Horario() {

    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    @Override
    public String toString() {
        return inicio.toString() + " - " + fin.toString();
    }

    public void setId(long horario) {
    }
}