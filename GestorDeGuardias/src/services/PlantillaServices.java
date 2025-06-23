package services;

import model.*;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class PlantillaServices {
    private final HorarioServices horarioServices;
    private final TurnoDeGuardiaServices turnoDeGuardiaServices;

    private final ConfiguracionServices configuracionServices;
    private final PersonaServices personaServices;
    private final PeriodoNoPlanificableServices periodoNoPlanificableServices;

    public PlantillaServices(ServicesLocator sl) {
        horarioServices = sl.getHorarioServices();
        turnoDeGuardiaServices = sl.getTurnoDeGuardiaServices();
        configuracionServices = sl.getConfiguracionServices();
        personaServices = sl.getPersonaServices();
        periodoNoPlanificableServices = sl.getPeriodoNoPlanificableServices();
    }
    /**
     * Crea la plantilla de guardias y sus datos sin asignar personas para
     * cumplir las guardias. El parametro empezarHoy determina si la primera
     * fecha de la plantilla será la fecha actual, si este es falso empezará
     * desde el primer día del próximo mes
     *
     * @return los de dias de guardia y sus horarios sin planificar
     */
    public ArrayList<DiaGuardia> crearPLantilla(boolean empezarHoy) {
        LocalDate inicio;
        ArrayList<DiaGuardia> dias = new ArrayList<>();

        if (empezarHoy)
            inicio = LocalDate.now();

        else {
            inicio = turnoDeGuardiaServices.getUltimoTurnoDeGuardia().getFecha().plusDays(1);

        }

        int numDias = inicio.lengthOfMonth() - inicio.getDayOfMonth();

        for (int i = 0; i <= numDias; i++) {
            LocalDate fecha = inicio.plusDays(i);
            List<Horario> horarios = horarioServices.getHorariosDeFecha(fecha);
            ArrayList<TurnoDeGuardia> turnos = new ArrayList<>();
            for (Horario horario : horarios) {
                turnos.add(new TurnoDeGuardia(horario));
            }
            dias.add(new DiaGuardia(fecha, turnos));
        }
        return dias;
    }

    public List<Persona> getPersonasDisponibles(LocalDate fecha, Horario horario, ArrayList<DiaGuardia> diasEnPantalla) throws MultiplesErroresException {
        List<String> errores = new ArrayList<>();

        if (fecha == null)
            errores.add("Fecha no especificada.");
        if (horario == null)
            errores.add("Horario no especificado.");
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Datos incorrectos:, errores");

        Boolean fechaEsReceso = periodoNoPlanificableServices.fechaEsNoPlanificable(fecha);
        Configuracion configuracionDeFecha = configuracionServices.getConfiguracionByPk(horario.getId(), fecha, fechaEsReceso);  //esta lanza la EntradaInvalidaException y poor eso es parte de la declaracion pero en realidad no tendr� oportunidad de lanzarla aqui
        List<Persona> personasDisponibles = personaServices.getPersonasDisponibles(fecha, configuracionDeFecha.getTipoPersona(), configuracionDeFecha.getSexo());

        personasDisponibles.removeAll(getPersonasEnPantalla(diasEnPantalla));
        return personasDisponibles;
    }

    private ArrayList<Persona> getPersonasEnPantalla(ArrayList<DiaGuardia> diasEnPantalla) {
        ArrayList<Persona> personas = new ArrayList<>();
        for (DiaGuardia dia : diasEnPantalla)
            for (TurnoDeGuardia turno : dia.getTurnos()) {
                if (!turno.getPersonasAsignadas().isEmpty())
                    personas.addAll(turno.getPersonasAsignadas());
            }

        return personas;
    }

    public void asignarPersona(DiaGuardia dia, Horario horario, Persona persona) throws EntradaInvalidaException, MultiplesErroresException {
        ArrayList<String> errores = new ArrayList<>();

        if (dia == null)
            errores.add("Día a planificar no especificado.");
        else if (dia.getFecha().isBefore(LocalDate.now()))
            errores.add("No se pueden hacer cambios a fechas pasadas.");

        if (horario == null)
            errores.add("Turno a planificar no especificado.");
        if (persona == null)
            errores.add("Persona a asignar no especificada.");
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Datos erróneos para la asignación de guardia:", errores);

        TurnoDeGuardia turno = dia.buscarTurno(horario);

        if (turno == null)
            throw new EntradaInvalidaException("Este día no tiene el horario deseado.");

        turno.asignarPersona(persona);
    }

    public void crearPlanificacionAutomaticamente(ArrayList<DiaGuardia> dias) throws MultiplesErroresException, EntradaInvalidaException {
        List<Persona> personasDisponibles;
        List<String> errores = new ArrayList<>();
        boolean hayPersonasDisponibles;

        for (DiaGuardia dia : dias) {
            LocalDate fechaDia = dia.getFecha();

            if (fechaDia == null) {
                errores.add("Fecha no especificada");
                continue;
            }
            Boolean fechaEsReceso = periodoNoPlanificableServices.fechaEsNoPlanificable(fechaDia);
            List<Configuracion> configList = configuracionServices.getConfiguracionesDeFecha(fechaDia);

            for (TurnoDeGuardia turno : dia.getTurnos()) {
                hayPersonasDisponibles = true;
                Horario horario = turno.getHorario();
                if (horario == null) {
                    errores.add(String.format("Horario no especificado. Fecha: %1$s", fechaDia));
                    continue;
                }
                Optional<Configuracion> configuracionDeTurno = configList.stream()
                        .filter(c -> c.getHorario().getId().equals(horario.getId()) && c.diaEsReceso().equals(fechaEsReceso))
                        .findFirst();

                if (configuracionDeTurno.isEmpty()) {
                    errores.add(String.format("Configuración no encontrada. Fecha: %1$s, Horario: %2$s", fechaDia, horario));
                    continue;
                }
                int cantPersonasAsignables = configuracionServices.getCantPersonasAsignables(turno.getHorario().getId(), dia.getFecha());
                while (turno.getPersonasAsignadas().size() < cantPersonasAsignables && hayPersonasDisponibles) {
                    personasDisponibles = getPersonasDisponibles2(fechaDia, dias, configuracionDeTurno.get().getTipoPersona(), configuracionDeTurno.get().getSexo());
                    if (!personasDisponibles.isEmpty()) {
                        turno.asignarPersona(personasDisponibles.getFirst());
                    } else {
                        hayPersonasDisponibles = false;
                    }
                }
            }
        }
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Datos incorrectos:", errores);
    }

    public List<Persona> getPersonasDisponibles2(LocalDate fecha, ArrayList<DiaGuardia> diasEnPantalla,TipoPersona tipoPersona, String sexo) {
        List<Persona> personasDisponibles = personaServices.getPersonasDisponibles(fecha, tipoPersona,sexo);

        personasDisponibles.removeAll(getPersonasEnPantalla(diasEnPantalla));
        return personasDisponibles;
    }

    public ArrayList<DiaGuardia> getPlanificacionesAPartirDe(LocalDate fecha) {
        return agruparPorDia(turnoDeGuardiaServices.getTurnosAPartirDe(fecha));
    }

    public ArrayList<DiaGuardia> agruparPorDia(List<TurnoDeGuardia> turnos) {
        Map<LocalDate, ArrayList<TurnoDeGuardia>> mapa = new LinkedHashMap<>();
        for (TurnoDeGuardia turno : turnos) {
            LocalDate fecha = turno.getFecha();
            mapa.computeIfAbsent(fecha, k -> new ArrayList<>()).add(turno);
        }

        ArrayList<DiaGuardia> diasGuardia = new ArrayList<>();
        for (Map.Entry<LocalDate, ArrayList<TurnoDeGuardia>> entry : mapa.entrySet()) {
            diasGuardia.add(new DiaGuardia(entry.getKey(), entry.getValue()));
        }

        return diasGuardia;
    }
    public ArrayList<DiaGuardia> getPlanDeGuardias() {
        return agruparPorDia(turnoDeGuardiaServices.getAllTurnosDeGuardia());
    }

    public ArrayList<DiaGuardia> getDiasPorActualizarCumplimiento() throws EntradaInvalidaException {
        ArrayList<DiaGuardia> planDeGuardias = getPlanDeGuardias();
        ArrayList<DiaGuardia> diasPorActualizar = new ArrayList<>();
        int i = 0;
        if (planDeGuardias.isEmpty())
            throw new EntradaInvalidaException("No hay guardias planificadas.");

        while (i < planDeGuardias.size() && planDeGuardias.get(i).getFecha().isBefore(LocalDate.now())) {
            DiaGuardia dia = planDeGuardias.get(i);
            if (!(dia.getTurnosPorActualizar().isEmpty())) {
                diasPorActualizar.add(dia);
            }
            i++;
        }
        return diasPorActualizar;

    }
}
