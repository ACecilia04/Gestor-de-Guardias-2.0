package model;

import utils.exceptions.EntradaInvalidaException;

import java.time.LocalDate;
import java.util.ArrayList;

public class DiaGuardia implements Comparable<DiaGuardia> {
    private LocalDate fecha;
    private ArrayList<TurnoDeGuardia> turnos;

    public DiaGuardia(LocalDate fecha, ArrayList<TurnoDeGuardia> turnos) {
        setFecha(fecha);
        setTurnos(turnos);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    private void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ArrayList<TurnoDeGuardia> getTurnos() {
        return turnos;
    }

    private void setTurnos(ArrayList<TurnoDeGuardia> arreglo) {

        this.turnos = arreglo;
    }

    @Override
    public int compareTo(DiaGuardia dia) {
        return this.getFecha().compareTo(dia.getFecha());
    }

    public TurnoDeGuardia buscarTurno(Horario horario) throws EntradaInvalidaException {
        TurnoDeGuardia indicado = null;
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

    public ArrayList<TurnoDeGuardia> getTurnosPorActualizar() {
        ArrayList<TurnoDeGuardia> turnosPorActualizar = new ArrayList<TurnoDeGuardia>();

        for (TurnoDeGuardia t : turnos)
            if (t.getCumplimiento() == null)
                turnosPorActualizar.add(t);
        return turnosPorActualizar;
    }

}
