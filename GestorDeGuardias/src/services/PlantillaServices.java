package services;

import logica.principal.EsquemaGuardia;
import model.*;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static utils.Utilitarios.quickSort;

public class PlantillaServices {
    ServicesLocator sl;
    private HorarioServices horarioServices;
    private TurnoDeGuardiaServices turnoDeGuardiaServices;

    ConfiguracionServices configuracionServices;
    private PersonaServices personaServices;
    private PeriodoNoPlanificableServices periodoNoPlanificableServices;

    public PlantillaServices(ServicesLocator sl) {
        this.sl = sl;
        horarioServices = sl.getHorarioServices();
        turnoDeGuardiaServices = sl.getTurnoDeGuardiaServices();
        configuracionServices = sl.getConfiguracionServices();
        personaServices = sl.getPersonaServices();
        periodoNoPlanificableServices = sl.getPeriodoNoPlanificableServices();
    }
    /**
     * Crea la plantilla de guardias y sus datos sin asignar personas para
     * cumplir las guardias El parametro empezar hoy determina si la primera
     * fecha de la plantilla ser� la fecha actual si este es falso empezar�
     * desde
     *
     * @param empezarHoy
     * @return los de dias de guardia y sus horarios sin planificar
     * @throws EntradaInvalidaException
     */
    public ArrayList<DiaGuardia> crearPLantilla(boolean empezarHoy) {
        LocalDate inicio;
        ArrayList<DiaGuardia> dias = new ArrayList<>();
        ArrayList<TurnoDeGuardia> turnosActuales = turnoDeGuardiaServices.getAllTurnosDeGuardia();

        if (empezarHoy)
            inicio = LocalDate.now();

        else {
            inicio = (turnosActuales.isEmpty())
                    ? LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth())
                    : turnosActuales.getLast().getFecha().plusDays(1);
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
    public List<Persona> getPersonasDisponibles(LocalDate fecha, Horario horario, ArrayList<DiaGuardia> diasEnPantalla) throws MultiplesErroresException, EntradaInvalidaException {
        List<String> errores = new ArrayList<>();
        List<Persona> personasConDeuda = new ArrayList<>(); // 2 arreglos para poner a los endeudados primero
        List<Persona> personasSinDeuda = new ArrayList<>();

        if (fecha == null)
            errores.add("Fecha no especificada.");
        if (horario == null)
            errores.add("Horario no especificado.");
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Datos incorrectos:, errores");

        ArrayList<Persona> personasEnPantalla = getPersonasEnPantalla(diasEnPantalla);
        boolean fechaEsReceso = periodoNoPlanificableServices.fechaEsNoPlanificable(fecha);
        Configuracion configuracionDeFecha = configuracionServices.getConfiguracionByPk(horario.getId(), fecha, fechaEsReceso);  //esta lanza la EntradaInvalidaException y poor eso es parte de la declaracion pero en realidad no tendr� oportunidad de lanzarla aqui

       return personaServices.getPersonasDisponibles(fecha, configuracionDeFecha.getTipoPersona(), configuracionDeFecha.getSexo());

//                        //asegurando no tener alguien haciendo 2 guardias en 1 mes
//                        if ((persona.getDiasDesdeUltimaGuardiaAsignada(fecha) > 31 &&
//                                persona.getDiasDesdeUltimaGuardiaHecha(fecha) > 30) &&
//                                !(personasEnPantalla.contains(persona))) {
//                            //caso especial que que la fecha esta en receso docente
//                            if (fechaEsReceso) {
//                                if (persona.estaDisponibleEnRecesoDocente(fecha)) {
//                                    if (persona.getGuardiasDeRecuperacion() > 0) {
//                                        personasConDeuda.add(persona);
//                                    } else {
//                                        personasSinDeuda.add(persona);
//                                    }
//                                }
//                            } else if (persona.getGuardiasDeRecuperacion() > 0) {
//                                personasConDeuda.add(persona);
//                            } else {
//                                personasSinDeuda.add(persona);
//                            }
//                        }
//                    }
//                }
//            }
//        quickSort(personasConDeuda, 0, personasConDeuda.size() - 1, fecha);
//        quickSort(personasSinDeuda, 0, personasSinDeuda.size() - 1, fecha);
//        personasConDeuda.addAll(personasSinDeuda); //para no crear un 3er arreglo obvi
//        return personasConDeuda;
    }
    private ArrayList<Persona> getPersonasEnPantalla(ArrayList<DiaGuardia> diasEnPantalla) {
        ArrayList<Persona> personas = new ArrayList<Persona>();
        for (DiaGuardia dia : diasEnPantalla)
            for (TurnoDeGuardia turno : dia.getTurnos()) {
                if (!turno.getPersonasAsignadas().isEmpty())
                    personas.addAll(turno.getPersonasAsignadas());
            }

        return personas;
    }
}
