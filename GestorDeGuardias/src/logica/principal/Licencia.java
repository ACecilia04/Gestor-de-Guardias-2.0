package logica.principal;

import java.time.LocalDate;

public class Licencia implements Comparable<Licencia> {
    protected LocalDate inicio;
    protected LocalDate fin;

    public Licencia(LocalDate inicio, LocalDate fin) {
        setInicio(inicio);
        setFin(fin);
    }

    public Licencia(LocalDate inicio) {
        setInicio(inicio);
        fin = null;
    }

    //TODO acaguilera ver nivel de acceso a los setters
    //TODO acaguilera modificar atributos?
    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    /**
     * @return true si los inicios coinciden, y los finales son iguales o ambos son nulos
     */
    @Override
    public boolean equals(Object licencia) {
        return ((Licencia) licencia).getInicio().equals(inicio) && ((((Licencia) licencia).getFin() == null) || (((Licencia) licencia).getFin().equals(fin)));
    }

    /**
     * @return true si sus inicios son iguales
     */
    @Override
    public int compareTo(Licencia licencia) {

        return inicio.compareTo(licencia.getInicio());
    }

    /**
     * @param fecha
     * @return true si inicio <= fecha dada <= fin o no hay fin
     */
    public boolean fechaEstaSolapada(LocalDate fecha) {
        return !fecha.isBefore(inicio) && (fin != null && !fecha.isAfter(fin));
    }

}
