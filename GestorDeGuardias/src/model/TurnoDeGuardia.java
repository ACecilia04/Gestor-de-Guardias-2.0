package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TurnoDeGuardia {
    private Horario horario;
    private Persona personaAsignada;
    private boolean hecho;
    private LocalDate fecha;


    public TurnoDeGuardia(LocalDate fecha, Horario horario, Persona personaAsignada) {
        this.fecha = fecha;
        this.horario = horario;
        this.personaAsignada = personaAsignada;
        this.hecho = false;
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }

    public Horario getHorario() {
        return horario;
    }

    public Persona getPersonaAsignada() {
        return personaAsignada;
    }

    public Boolean getCumplimiento() {
        return hecho;
    }

    // Setters
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public void asignarPersona(Persona personaAsignada) {
        this.personaAsignada = personaAsignada;
    }

    public void borrarPersonaAsignada() {
        this.personaAsignada = null;
    }

    public void actualizarCumplimiento(Boolean hecho) {
        this.hecho = hecho;
    }

}
