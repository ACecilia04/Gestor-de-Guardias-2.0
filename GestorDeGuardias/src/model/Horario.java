package model;

import java.time.LocalTime;

public class Horario {
    private LocalTime inicio;
    private LocalTime fin;

    public Horario(LocalTime inicio, LocalTime fin) {
        //TODO: check setter use
        this.inicio = inicio;
        this.fin = fin;
    }

    public LocalTime getFin() {
        return fin;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    @Override
    public String toString(){
        return inicio.toString() + " - " + fin.toString();
    }
}