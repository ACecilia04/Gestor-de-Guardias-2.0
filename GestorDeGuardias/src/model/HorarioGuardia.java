package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class HorarioGuardia {
    private LocalTime inicio;
    private LocalTime fin;

    public HorarioGuardia(LocalTime inicio, LocalTime fin) {
        //TODO: check setter use
        this.inicio = inicio;
        this.fin = fin;
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
}