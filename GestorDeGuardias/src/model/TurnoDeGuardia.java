package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class TurnoDeGuardia {
    private Horario horario;
    private ArrayList<Persona> personasAsignadas;
    private
    Boolean hecho;
    private LocalDate fecha;
    private Long id;


    public TurnoDeGuardia(LocalDate fecha, Horario horario, ArrayList<Persona> personasAsignadas) {
        this.fecha = fecha;
        this.horario = horario;
        this.personasAsignadas = personasAsignadas;
        this.hecho = false;
    }

    public TurnoDeGuardia() {
        personasAsignadas = new ArrayList<>();
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
    public Long getId(){return id;}

    public void asignarPersona(Persona personaAsignada) {
        personasAsignadas.add(personaAsignada);
    }

    public void borrarpersonasAsignadas() {
        this.personasAsignadas = null;
    }

    public void actualizarCumplimiento(Boolean hecho) {
        this.hecho = hecho;
    }

    public void setPersonasAsignadas(ArrayList<Persona> personas) {
     personas.forEach(this::asignarPersona);
    }

    public void setHecho(Boolean hecho) {
        this.hecho = hecho;
    }

    public void setId(Long id){this.id = id;}

}
