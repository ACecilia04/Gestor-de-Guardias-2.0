package model;

import logica.excepciones.EntradaInvalidaException;
import logica.principal.HorarioGuardia;
import logica.principal.TurnoGuardia;

import java.time.LocalDate;
import java.util.ArrayList;

public class DiaGuardia implements Comparable<DiaGuardia> {
    private LocalDate fecha;
    private ArrayList<TurnoGuardia> turnos;

    public DiaGuardia(LocalDate fecha, ArrayList<TurnoGuardia> turnos) {
        setFecha(fecha);
        setTurnos(turnos);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    private void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ArrayList<TurnoGuardia> getTurnos() {
        return turnos;
    }

    private void setTurnos(ArrayList<TurnoGuardia> arreglo) {

        this.turnos = arreglo;
    }

    @Override
    public int compareTo(DiaGuardia dia) {
        return this.getFecha().compareTo(dia.getFecha());
    }

    public TurnoGuardia buscarTurno(HorarioGuardia horario) throws EntradaInvalidaException {
        TurnoGuardia indicado = null;
        boolean encontrado = false;
        if (horario == null)
            throw new EntradaInvalidaException("Horario no especificado.");

        for (int i = 0; i < turnos.size() && !encontrado; i++) {
            if (turnos.get(i).getHorario().equals(horario)) {
                indicado = turnos.get(i);
                encontrado = true;
            }
        }
        return indicado;
    }

    public ArrayList<TurnoGuardia> getTurnosPorActualizar() {
        ArrayList<TurnoGuardia> turnosPorActualizar = new ArrayList<TurnoGuardia>();

        for (TurnoGuardia t : turnos)
            if (t.getCumplimiento() == null)
                turnosPorActualizar.add(t);
        return turnosPorActualizar;
    }

}
