package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TurnoDeGuardia {
    private Horario horario;
    private ArrayList<Persona> personasAsignadas;
    private boolean hecho;
    private LocalDate fecha;


    public TurnoDeGuardia(LocalDate fecha, Horario horario, ArrayList<Persona> personasAsignadas) {
        this.fecha = fecha;
        this.horario = horario;
        this.personasAsignadas = personasAsignadas;
        this.hecho = false;
    }

    public TurnoDeGuardia(Horario horario, Long personaAsignada, boolean hecho, LocalDate fecha) {
        this.fecha = fecha;
        this.horario = horario;
//        asignarPersona(personaAsignada);
        this.hecho = hecho;
    }

    public TurnoDeGuardia() {

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

    public ArrayList<Persona> getpersonasAsignadas() {
        return personasAsignadas;
    }

    public Boolean getCumplimiento() {
        return hecho;
    }

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
    }


//    public List<Persona> getPersonasAsignadas() { return personasAsignadas; }
//    public void asignarPersona(Persona persona) { personasAsignadas.add(persona); }
//
//    public boolean esValidoParaAsignacion() {
//        return !diaEsReceso && personasAsignadas.size() < cantPersonas;
//    }

}
