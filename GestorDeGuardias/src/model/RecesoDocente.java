package model;

import java.time.LocalDate;

public class RecesoDocente implements Comparable<RecesoDocente> {
    private LocalDate inicio;
    private LocalDate fin;


    public RecesoDocente(LocalDate fechaIncio, LocalDate fechaFin) {
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
     * @param fecha
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
        return ((RecesoDocente) receso).getInicio().equals(inicio) && (((RecesoDocente) receso).getFin().equals(fin));
    }

    @Override
    public int compareTo(RecesoDocente receso) {

        return inicio.compareTo(receso.getInicio());
    }

}
