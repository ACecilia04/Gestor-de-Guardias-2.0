package model;

import java.time.LocalDate;

public class PeriodoNoPlanificable implements Comparable<PeriodoNoPlanificable> {
    private LocalDate inicio;
    private LocalDate fin;


    public PeriodoNoPlanificable(LocalDate fechaIncio, LocalDate fechaFin) {
        setInicio(fechaIncio);
        setFin(fechaFin);
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate fechaIncio) {
        this.inicio = fechaIncio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fechaFin) {
        this.fin = fechaFin;
    }

    /**
     * @return true si inicio <= fecha dada <= fin
     */
    public boolean fechaEstaSolapada(LocalDate fecha) {
        return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
    }

    /**
     * @return true si el inicio y fin coinciden con los del receso docente dado
     */
    @Override
    public boolean equals(Object receso) {
        return ((PeriodoNoPlanificable) receso).getInicio().equals(inicio) && (((PeriodoNoPlanificable) receso).getFin().equals(fin));
    }

    @Override
    public int compareTo(PeriodoNoPlanificable receso) {

        return inicio.compareTo(receso.getInicio());
    }

    @Override
    public String toString() {
        return inicio.isEqual(fin) ? inicio.toString() : inicio + " - " + fin;
    }
}
