package logica.principal;

public class TurnoGuardia {
    private Boolean cumplido;
    private HorarioGuardia horario;
    private Persona personaAsignada;

    public TurnoGuardia(HorarioGuardia horario) {
        setHorario(horario);
        cumplido = null;
    }

    public Boolean getCumplimiento() {
        return cumplido;
    }

    public HorarioGuardia getHorario() {
        return horario;
    }

    public void setHorario(HorarioGuardia horario) {
        this.horario = horario;
    }

    public Persona getPersonaAsignada() {
        return personaAsignada;
    }

    public void asignarPersona(Persona personaAsignada) {
        this.personaAsignada = personaAsignada;
    }

    public void borrarPersonaAsignada() {
        this.personaAsignada = null;
    }

    public void actualizarCumplimiento(Boolean cumplido) {
        this.cumplido = cumplido;
    }

}
