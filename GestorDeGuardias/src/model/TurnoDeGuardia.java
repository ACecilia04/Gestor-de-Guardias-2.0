package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class TurnoDeGuardia {
    private Long id;
    private LocalDate fecha;
    private Horario horario;
    private Persona personaAsignada;
    private Boolean hecho;

    private ArrayList<Persona> personasAsignadas = new ArrayList<>();

    public TurnoDeGuardia(Long id, LocalDate fecha, Horario horario, Persona personaAsignada, Boolean hecho) {
        this.id = id;
        this.fecha = fecha;
        this.horario = horario;
        this.personaAsignada = personaAsignada;
        this.hecho = hecho;
    }

    public TurnoDeGuardia(LocalDate fecha, Horario horario, Persona personaAsignada) {
        this.fecha = fecha;
        this.horario = horario;
        this.personaAsignada = personaAsignada;
    }

    public TurnoDeGuardia() {
    }


    public TurnoDeGuardia(Horario horario) {
        this.horario = horario;
        personasAsignadas = new ArrayList<>();
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }

    // Setters
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public ArrayList<Persona> getPersonasAsignadas() {
        return personasAsignadas;
    }

    public Boolean getCumplimiento() {
        return hecho;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void asignarPersona(Persona personaAsignada) {
        personasAsignadas.add(personaAsignada);
    }

    public Persona getPersonaAsignada() {
        return personaAsignada;
    }

    public void setPersonaAsignada(Persona personaAsignada) {
        this.personaAsignada = personaAsignada;
    }

    public Boolean getHecho() {
        return hecho;
    }

    public void setHecho(Boolean hecho) {
        this.hecho = hecho;
    }
}
