package logica;

import logica.excepciones.EntradaInvalidaException;
import logica.excepciones.MultiplesErroresException;
import rdb.entity.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;

import static logica.comunes.Utilitarios.*;


public class Gestor {
    private static Gestor gestor;
    private final Facultad facultad;
    private final ArrayList<DiaGuardia> planDeGuardias;

    // ********************************************************Singleton ;)************************************************

    private Gestor() {
        facultad = new Facultad();
        planDeGuardias = new ArrayList<DiaGuardia>();
    }

    public static Gestor getInstance() {
        if (gestor == null) {
            gestor = new Gestor();
        }
        return gestor;
    }

    // *********************************************************Para crear la plantilla******************************************************

    /**
     * Crea la plantilla de guardias y sus datos sin asignar personas para
     * cumplir las guardias El parametro empezar hoy determina si la primera
     * fecha de la plantilla ser� la fecha actual si este es falso empezar�
     * desde
     *
     * @param empezarHoy
     * @return lis de dias de guardia y us horarios sin planificar
     * @throws EntradaInvalidaException
     */
    public ArrayList<DiaGuardia> crearPlantilla(boolean empezarHoy) throws EntradaInvalidaException {
        ArrayList<DiaGuardia> dias = new ArrayList<DiaGuardia>();
        LocalDate inicio;

        if (empezarHoy)
            inicio = LocalDate.now();
        else {
            if (this.planDeGuardias.isEmpty()) {
                inicio = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
                // inicio = LocalDate.now().withMonth(LocalDate.now().getMonthValue() +1 ).withDayOfMonth(1); lo mismo pero el d arriba es mas sofisticado
            } else {
                inicio = this.planDeGuardias.get(planDeGuardias.size() - 1).getFecha().plusDays(1);
            }
        }
        int numDias = inicio.lengthOfMonth() - inicio.getDayOfMonth();

        for (int i = 0; i <= numDias; i++) {
            LocalDate fecha = inicio.plusDays(i);
            ArrayList<HorarioGuardia> horarios = getHorariosDeFecha(fecha);
            ArrayList<TurnoGuardia> turnos = new ArrayList<TurnoGuardia>();
            for (HorarioGuardia horario : horarios) {
                turnos.add(new TurnoGuardia(horario));
            }
            dias.add(new DiaGuardia(fecha, turnos));
        }

        return dias;
    }

    /**
     * Dado una fecha busca los esquemas correspondientes a esa fecha y con esto
     * devuelve lo horarios de esa fecha
     *
     * @param fecha
     * @return lista de horarios de es fecha
     * @throws EntradaInvalidaException
     */
    public ArrayList<HorarioGuardia> getHorariosDeFecha(LocalDate fecha) throws EntradaInvalidaException {
        if (fecha == null)
            throw new EntradaInvalidaException("Fecha no especificada.");
        ArrayList<HorarioGuardia> horarios = new ArrayList<HorarioGuardia>();
        ArrayList<EsquemaGuardia> esquemas = getEsquemasDeFecha(fecha);

        if (esquemas != null && !esquemas.isEmpty()) {
            for (EsquemaGuardia esquema : esquemas) { // recorrer la lista dada	if (!horarios.contains(esquema.getHorario()))
                //si la lista a retornar no tiene ya el horario de ese esquema
                horarios.add(esquema.getHorario()); // a�adirlo
            }
        }
        return horarios;
    }

    /**
     * @param fecha
     * @return lista de esquemas de guardia que le corresponden a la fecha dada
     * @throws EntradaInvalidaException
     */
    private ArrayList<EsquemaGuardia> getEsquemasDeFecha(LocalDate fecha) throws EntradaInvalidaException {
        DayOfWeek diaDeSemana = fecha.getDayOfWeek();
        boolean fechaEnReceso = facultad.fechaEsRecesoDocente(fecha); //para saber si seran solo trabajores o no
        boolean fechaEnSemanaPar = fechaEnSemanaPar(fecha);  // por si es fin de semana saber si seran estudiantes o trabajadores en el horario diurno

        ArrayList<EsquemaGuardia> retVal = new ArrayList<EsquemaGuardia>(); //lista a return
        for (EsquemaGuardia esquema : EnumSet.allOf(EsquemaGuardia.class)) {  //recorrer los valores del enum esquema
            if ((esquema.getDiaSemana() == null || esquema.getDiaSemana().equals(diaDeSemana)) &&          //si el dia de la semana no importa o es igual al de la fecha
                    (esquema.getEsReceso() == null || esquema.getEsReceso().equals(fechaEnReceso)) &&          //y no importa si es receso o coincide con la fecha
                    (esquema.getEsSemanaPar() == null || esquema.getEsSemanaPar().equals(fechaEnSemanaPar))) { // si la paridad de la semana no importa p coincide con la de la fecha
                retVal.add(esquema);

            }
            /* lo mismo pero con ifs anidados
             * if(esquema.getDiaSemana() == null || esquema.getDiaSemana().equals(diaDeSemana))
             * 	if(esquema.getEsReceso() == null || esquema.getEsReceso().equals(fechaEnReceso))
             * 		if(esquema.getEsSemanaPar() == null || esquema.getEsSemanaPar().equals(fechaEnSemanaPar))
             * 			retVal.add(esquema);

             */
        }
        return retVal;
    }

    //*******************************************************Para llenar la plantilla*****************************************************************

    /**
     * Busca los esquemas de esa fecha y recorre ese arreglo. Cuando el horario
     * de uno de esos esquemas sea igual al dado, recorre la lista de personas
     * de la facultad buscando a los que encajen segun su tipo, sexo y
     * disponibilidad
     *
     * @param fecha
     * @param horario
     * @param diasEnPantalla
     * @return lista de persona disponibles en esa fecha para ese horario
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public ArrayList<Persona> getPersonasDisponibles(LocalDate fecha, HorarioGuardia horario, ArrayList<DiaGuardia> diasEnPantalla) throws MultiplesErroresException, EntradaInvalidaException {
        ArrayList<String> errores = new ArrayList<String>();
        ArrayList<Persona> personasConDeuda = new ArrayList<Persona>(); // 2 arreglos para poner a los endeudados primero
        ArrayList<Persona> personasSinDeuda = new ArrayList<Persona>();

        if (fecha == null)
            errores.add("Fecha no especificada.");
        if (horario == null)
            errores.add("Horario no especificado.");
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Datos incorrectos:, errores");
        ArrayList<EsquemaGuardia> esquemas = getEsquemasDeFecha(fecha);  //esta lanza la EntradaInvalidaException y poor eso es parte de la declaracion pero en realidad no tendr� oportunidad de lanzarla aqui
        ArrayList<Persona> personasEnPantalla = getPersonasEnPantalla(diasEnPantalla);
        boolean fechaEsReceso = facultad.fechaEsRecesoDocente(fecha);
        for (EsquemaGuardia esquema : esquemas) {
            if (esquema.getHorario() == horario) {
                for (Persona persona : facultad.getPersonas()) {
                    if ( //si las clases y  sexos coinciden y la persona esta disponible
                            (esquema.getClase().equals(persona.getClass())) &&
                                    (esquema.getSexo() == null || esquema.getSexo().equals(persona.getSexo())) &&
                                    (persona.getDisponibilidadParaFecha(fecha) == Disponibilidad.DISPONIBLE)
                    ) {
                        //asegurando no tener alguien haciendo 2 guardias en 1 mes
                        if ((persona.getDiasDesdeUltimaGuardiaAsignada(fecha) > 31 &&
                                persona.getDiasDesdeUltimaGuardiaHecha(fecha) > 30) &&
                                !(personasEnPantalla.contains(persona))) {
                            //caso especial que que la fecha esta en receso docente
                            if (fechaEsReceso) {
                                if (persona.estaDisponibleEnRecesoDocente(fecha)) {
                                    if (persona.getCantGuardiasRecuperacion() > 0) {
                                        personasConDeuda.add(persona);
                                    } else {
                                        personasSinDeuda.add(persona);
                                    }
                                }
                            } else if (persona.getCantGuardiasRecuperacion() > 0) {
                                personasConDeuda.add(persona);
                            } else {
                                personasSinDeuda.add(persona);
                            }
                        }
                    }
                }
            }
        }
        quickSort(personasConDeuda, 0, personasConDeuda.size() - 1, fecha);
        quickSort(personasSinDeuda, 0, personasSinDeuda.size() - 1, fecha);
        personasConDeuda.addAll(personasSinDeuda); //para no crear un 3er arreglo obvi
        return personasConDeuda;
    }

    private ArrayList<Persona> getPersonasEnPantalla(ArrayList<DiaGuardia> diasEnPantalla) {
        ArrayList<Persona> personas = new ArrayList<Persona>();
        for (DiaGuardia dia : diasEnPantalla)
            for (TurnoGuardia turno : dia.getTurnos()) {
                if (turno.getPersonaAsignada() != null)
                    personas.add(turno.getPersonaAsignada());
            }

        return personas;
    }

    /**
     * Asigna la persona dada al turno dado si ya hay una persona asignada
     * cambiar� una por la otra
     *
     * @param dia
     * @param turno
     * @param persona
     * @throws EntradaInvalidaException
     */
    public void asignarPersona(DiaGuardia dia, HorarioGuardia horario, Persona persona) throws EntradaInvalidaException, MultiplesErroresException {
        ArrayList<String> errores = new ArrayList<String>();

        if (dia == null)
            errores.add("D�a a planificar no especificado.");
        else if (dia.getFecha().isBefore(LocalDate.now()))
            errores.add("No se pueden hacer cambios a fechas pasadas.");

        if (horario == null)
            errores.add("Turno a planificar no especificado.");
        if (persona == null)
            errores.add("Persona a asignar no especificada.");
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Datos err�neos para la asignaci�n de guardia:", errores);

        TurnoGuardia turno = dia.buscarTurno(horario);

        if (turno == null)
            throw new EntradaInvalidaException("Este d�a no tiene el horario deseado.");
        turno.asignarPersona(persona);
    }

    /**
     * Guarda en el registro del gestor una lista de planificaciones solo si a
     * todos los turnos de d�as lectivos se le han asignado una persona para
     * hacerlos.
     *
     * @param nuevosDias
     * @throws EntradaInvalidaException
     */
    public void guardar(ArrayList<DiaGuardia> nuevosDias) throws EntradaInvalidaException {
        boolean vacio = false;

        if (nuevosDias == null || nuevosDias.isEmpty())
            throw new EntradaInvalidaException("No hay d�as para guardar.");

        for (int i = 0; i < nuevosDias.size() && !vacio; i++) {
            if (!facultad.fechaEsRecesoDocente(nuevosDias.get(i).getFecha())) // xq	si es receso docente puede estar vacia ya q parte de voluntariedad
                for (int ii = 0; ii < nuevosDias.get(i).getTurnos().size() && !vacio; ii++)
                    vacio = nuevosDias.get(i).getTurnos().get(ii).getPersonaAsignada() == null;
        }
        if (vacio) {
            throw new EntradaInvalidaException("Todo d�a lectivo debe tener una persona asignada.");
        } else {
            for (DiaGuardia dia : nuevosDias)
                for (TurnoGuardia turno : dia.getTurnos())
                    if (turno.getPersonaAsignada() != null)
                        facultad.asignarGuardia(turno.getPersonaAsignada().getCi(), dia.getFecha());  //ya que se va a guardar se le puede poner la guardia asignada a la persona
            // si la fecha del ultimo dia registrado esta antes el primer dia de
            // los nuevos (es decir: estas haciendo una nueva planif)
            if (this.planDeGuardias.isEmpty() || (planDeGuardias.get(planDeGuardias.size() - 1).getFecha().isBefore(nuevosDias.get(0).getFecha()))) {
                planDeGuardias.addAll(nuevosDias);
                // si la fecha del ultimo dia registrado esta antes el primer dia de
                // los nuevos (es decir: estas haciendo una nueva planif)
            } else {
                //caso contrario es que ya esa planificacion estaba registrada
                int indicePlanificados = Collections.binarySearch(planDeGuardias, nuevosDias.get(0)); //google dice que esta magia debe funcionar
                for (DiaGuardia d : nuevosDias)
                    planDeGuardias.set(indicePlanificados++, d);
            }
        }
    }

    //*************************************************************Editar******************************************************************

    /**
     * Si los horarios son compatibles y las personas asignadas a ellos est�n
     * disponibles se intercambian los turnos.
     *
     * @param turno1 primer turno seleccionado
     * @param fecha1 fecha del primer turno
     * @param turno2 segundo turno seleccionado
     * @param fecha2 fecha del primer turno
     * @throws EntradaInvalidaException
     * @throws MultiplesErroresException
     */
    public void intercambiarTurnos(TurnoGuardia turno1, LocalDate fecha1, TurnoGuardia turno2, LocalDate fecha2) throws EntradaInvalidaException, MultiplesErroresException {
        if (turno1.getHorario() != turno2.getHorario())
            throw new EntradaInvalidaException("Estos dos turnos no son intercambiables porque sus horarios no son iguales.");
        else if (getDisponibilidadPersona(fecha1, turno1.getHorario(), turno2.getPersonaAsignada()) && getDisponibilidadPersona(fecha2, turno2.getHorario(), turno1.getPersonaAsignada())) {
            Persona aux = turno1.getPersonaAsignada();
            turno1.asignarPersona(turno2.getPersonaAsignada());
            turno2.asignarPersona(aux);
        } else {
            throw new EntradaInvalidaException("Estos dos turnos no son intercambiables porque las personas asignadas a ellos no estan disponibles en la fecha alterna.");
        }
    }

    public void borrarPersonaDeTurno(LocalDate fecha, HorarioGuardia horario) throws EntradaInvalidaException {
        DiaGuardia dia = buscarDiaGuardia(fecha);

        if (dia == null)
            throw new EntradaInvalidaException("No hay planificaci�n para esta fecha.");
        TurnoGuardia turno = dia.buscarTurno(horario);

        if (turno == null)
            throw new EntradaInvalidaException("Este d�a no tiene el horario deseado.");
        turno.borrarPersonaAsignada();
    }

    public void borrarPersonasDeDia(LocalDate fecha) throws EntradaInvalidaException {
        DiaGuardia dia = buscarDiaGuardia(fecha);

        if (dia == null)
            throw new EntradaInvalidaException("No hay planificaci�n para esta fecha.");
        for (TurnoGuardia turno : dia.getTurnos())
            turno.borrarPersonaAsignada();
    }

    public void crearPlanificacionAutomaticamente(ArrayList<DiaGuardia> dias) throws MultiplesErroresException, EntradaInvalidaException {
        ArrayList<Persona> personasDisponibles;

        for (DiaGuardia dia : dias) {
            for (TurnoGuardia turno : dia.getTurnos()) {
                if (turno.getPersonaAsignada() == null) {

                    personasDisponibles = getPersonasDisponibles(dia.getFecha(), turno.getHorario(), dias);
                    if (!personasDisponibles.isEmpty())
                        asignarPersona(dia, turno.getHorario(), personasDisponibles.get(0));

                }
            }
        }
    }

    /**
     * Luego de dar la baja via la facultad, asigna un sustituto
     * adecuado en todas la guardias asignadas a la persona
     *
     * @param ci        carnet de identidad de la persona
     * @param fechaBaja
     * @throws EntradaInvalidaException
     * @throws MultiplesErroresException
     */
    public void darBaja(String ci, LocalDate fechaBaja) throws EntradaInvalidaException, MultiplesErroresException {
        Persona persona = facultad.buscarPersona(ci);

        facultad.darBaja(persona.getCi(), fechaBaja);
        sustituirGuardias(fechaBaja, persona);
    }

    /**
     * Luego de dar la licencia via la facultad, asigna un sustituto
     * adecuado en todas la guardias asignadas al estudiante
     *
     * @param ci
     * @param inicio
     * @throws EntradaInvalidaException
     * @throws MultiplesErroresException
     */
    public void darLicenciaEstudiante(String ci, LocalDate inicio) throws EntradaInvalidaException, MultiplesErroresException {
        Persona persona = facultad.buscarPersona(ci);
        facultad.darLicenciaEstudiante(inicio, persona.getCi());
        sustituirGuardias(inicio, persona);

    }

    /**
     * Luego de dar la licencia via la facultad, asigna un sustituto
     * adecuado en todas la guardias asignadas al estudiante
     *
     * @param ci
     * @param inicio
     * @param fin
     * @throws EntradaInvalidaException
     * @throws MultiplesErroresException
     */
    public void darLicenciaEstudiante(String ci, LocalDate inicio, LocalDate fin) throws EntradaInvalidaException, MultiplesErroresException {
        Persona persona = facultad.buscarPersona(ci);
        facultad.darLicenciaEstudiante(inicio, fin, persona.getCi());
        sustituirGuardias(inicio, fin, persona);

    }

    /**
     * Luego de dar la licencia via la facultad, asigna un sustituto
     * adecuado en todas la guardias asignadas al trabajador
     *
     * @param ci
     * @param inicio
     * @param fin
     * @param tipo
     * @throws EntradaInvalidaException
     * @throws MultiplesErroresException
     */
    public void darLicenciaTrabajador(String ci, LocalDate inicio, LocalDate fin, TipoLicencia tipo) throws EntradaInvalidaException, MultiplesErroresException {
        Persona persona = facultad.buscarPersona(ci);
        facultad.darLicenciaTrabajador(inicio, fin, persona.getCi(), tipo);
        sustituirGuardias(inicio, fin, persona);

    }

    /**
     * Asigna un sustituto adecuado en todas la guardias planificadas
     * asignadas a la persona entre las 2 fechas dadas
     *
     * @param fecha
     * @param persona
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    private void sustituirGuardias(LocalDate inicio, LocalDate fin, Persona persona) throws MultiplesErroresException, EntradaInvalidaException {
        ArrayList<Persona> personasDisponibles;
        for (DiaGuardia dia : this.planDeGuardias) {
            //si la fecha de ese dia de guardia esta entre las 2 fechas dadas
            // y esta despues de hoy
            if ((!dia.getFecha().isBefore(inicio) && !dia.getFecha().isAfter(fin))
                    && dia.getFecha().isAfter(LocalDate.now())) {
                for (TurnoGuardia turno : dia.getTurnos()) {
                    if (turno.getPersonaAsignada().equals(persona)) {
                        personasDisponibles = getPersonasDisponibles(dia.getFecha(), turno.getHorario(), new ArrayList<DiaGuardia>());
                        if (!personasDisponibles.isEmpty())
                            asignarPersona(dia, turno.getHorario(), personasDisponibles.get(0));
                    }
                }
            }
        }
    }

    /**
     * Asigna un sustituto adecuado en todas la guardias planificadas
     * asignadas a la persona para esa fecha y las proximas
     *
     * @param fecha
     * @param persona
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    private void sustituirGuardias(LocalDate fecha, Persona persona) throws MultiplesErroresException, EntradaInvalidaException {
        ArrayList<Persona> personasDisponibles;
        for (DiaGuardia dia : this.planDeGuardias) {
            if (!dia.getFecha().isBefore(fecha) && dia.getFecha().isAfter(LocalDate.now())) {
                for (TurnoGuardia turno : dia.getTurnos()) {
                    if (turno.getPersonaAsignada().equals(persona)) {
                        personasDisponibles = getPersonasDisponibles(dia.getFecha(), turno.getHorario(), new ArrayList<DiaGuardia>());
                        if (!personasDisponibles.isEmpty())
                            asignarPersona(dia, turno.getHorario(), personasDisponibles.get(0));
                    }
                }
            }
        }
    }

    /**
     * @param fecha
     * @param horario
     * @param persona
     * @return true si a la persona le coresponde el horario dado y est�
     * disponible para ese dia
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public boolean getDisponibilidadPersona(LocalDate fecha, HorarioGuardia horario, Persona persona) throws MultiplesErroresException, EntradaInvalidaException {
        ArrayList<String> errores = new ArrayList<String>();

        if (fecha == null)
            errores.add("Fecha no especificada.");
        if (horario == null)
            errores.add("Horario no especificado.");
        if (persona == null)
            errores.add("Persona no especificada."); // ESTE ES EL Q PIDEN
        // ESPECIFICAMENTE
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Datos incorrectos:, errores");
        if (facultad.buscarPersona(persona.getCi()) == null)
            throw new EntradaInvalidaException("Persona no registrada.");

        ArrayList<EsquemaGuardia> esquemas = getEsquemasDeFecha(fecha);
        // esta lanza la EntradaInvalidaException y poor eso es partede la declaracion pero en
        // realidad no tendr� oportunidad de lanzarla aqui
        boolean disponible = false;
        for (int i = 0; i < esquemas.size() && !disponible; i++) {
            if (esquemas.get(i).getHorario() == horario) {
                disponible = ((esquemas.get(i).getClase().equals(persona.getClass())) && (esquemas.get(i).getSexo() == null || esquemas.get(i).getSexo().equals(persona.getSexo())) && (persona.getDisponibilidadParaFecha(fecha) == Disponibilidad.DISPONIBLE));
            }
        }
        return disponible;
    }

    //******************************************************Cumplimiento**************************************************************************************


    public ArrayList<DiaGuardia> getDiasPorActualizarCumplimiento() throws EntradaInvalidaException {
        ArrayList<DiaGuardia> diasPorActualizar = new ArrayList<DiaGuardia>();
        int i = 0;
        if (this.planDeGuardias.isEmpty())
            throw new EntradaInvalidaException("No hay guardias planificadas.");

        while (planDeGuardias.get(i).getFecha().isBefore(LocalDate.now())) {
            DiaGuardia dia = planDeGuardias.get(i);
            if (!(dia.getTurnosPorActualizar().isEmpty()))
                diasPorActualizar.add(dia);
            i++;
        }
        return diasPorActualizar;

    }

    //	public void actualizarCumplimiento(LocalDate fecha, HorarioGuardia horario, Boolean hecho) throws EntradaInvalidaException, MultiplesErroresException{
//		ArrayList<String> errores = new ArrayList<String>();
//
//		if (fecha == null)
//			errores.add("Fecha no especificada.");
//		if (horario == null)
//			errores.add("Horario no especificado.");
//		if (!errores.isEmpty())
//			throw new MultiplesErroresException("Datos incorrectos:, errores");
//
//		DiaGuardia dia = buscarDiaGuardia(fecha);
//		TurnoGuardia turno = dia.buscarTurno(horario);
//		if (turno.getCumplimiento() == null){
//			turno.actualizarCumplimiento(hecho);
//		} else
//			throw new EntradaInvalidaException("Ya se ha actualizado el cumplimiento de esta guardia.");
//
    //	}
    public void actualizarCumplimiento(ArrayList<DiaGuardia> diasPorActualizar) throws EntradaInvalidaException, MultiplesErroresException {
        for (DiaGuardia dia : diasPorActualizar)
            for (TurnoGuardia turno : dia.getTurnos())
                if (turno.getCumplimiento() != null) {
//					turno.getPersonaAsignada().actualizarCumplimiento(dia.getFecha(), turno.getCumplimiento());
                } else
                    throw new EntradaInvalidaException("Ya se ha actualizado el cumplimiento de esta guardia.");
    }

    // *********************************************************************Filtros y otros**********************************************************************************

    /**
     * Busca los esquemas de esa fecha y recorre ese arreglo. Cuando el horario
     * de uno de esos esquemas sea igual al dado, recorre la lista de personas
     * de la facultad buscando a los que encajen segun su tipo, sexo y
     * disponibilidad
     *
     * @param fecha
     * @param horario
     * @return lista de persona disponibles con alguna guardia de recuperaci�n
     * que cumplir en esa fecha para ese horario
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public ArrayList<Persona> getPersonasDisponiblesConGuardiasDeRecuperacion(LocalDate fecha, HorarioGuardia horario, ArrayList<DiaGuardia> diasEnPantalla) throws MultiplesErroresException, EntradaInvalidaException {
        ArrayList<Persona> personasDisponibles = getPersonasDisponibles(fecha, horario, diasEnPantalla);
        ArrayList<Persona> personasConDeudas = new ArrayList<Persona>();
        int i = 0;

        while (personasDisponibles.get(i).getCantGuardiasRecuperacion() > 0) {
            personasConDeudas.add(personasDisponibles.get(i));
            i++;
        }
        return personasConDeudas;
    }

    /**
     * Busca los esquemas de esa fecha y recorre ese arreglo. Cuando el horario
     * de uno de esos esquemas sea igual al dado, recorre la lista de personas
     * de la facultad buscando a los que encajen segun su tipo, sexo y
     * disponibilidad
     *
     * @param fecha
     * @param horario
     * @return lista de persona disponibles sin guardias de recuperaci�n que
     * cumplir en esa fecha para ese horario
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public ArrayList<Persona> getPersonasDisponiblesSinGuardiasDeRecuperacion(LocalDate fecha, HorarioGuardia horario, ArrayList<DiaGuardia> diasEnPantalla) throws MultiplesErroresException, EntradaInvalidaException {
        ArrayList<Persona> personasDisponibles = getPersonasDisponibles(fecha, horario, diasEnPantalla);
        ArrayList<Persona> personasSinDeudas = new ArrayList<Persona>();
        int i = personasDisponibles.size() - 1;

        while (personasDisponibles.get(i).getCantGuardiasRecuperacion() == 0) {
            personasSinDeudas.add(0, personasDisponibles.get(i));
            i--;
        }
        return personasSinDeudas;
    }

    /**
     * @param fecha
     * @return indice del dia planificado o -1 si no ha sido planificado
     */
    public DiaGuardia buscarDiaGuardia(LocalDate fecha) {
        int indice;
        indice = binaryDateSearch(planDeGuardias, fecha, 0, planDeGuardias.size() - 1);

        return indice == -1 ? null : planDeGuardias.get(indice);
    }

    public Facultad getFacultad() {
        return facultad;
    }

    /**
     * Dado una fecha devuelve los dias ya planificados de el mes
     * correspondiente
     *
     * @param fecha
     * @return arreglo de dias de guardia planificados
     */
    public ArrayList<DiaGuardia> getPlanificaciones(LocalDate fecha) {
        int diasEncontrados = 0;
        ArrayList<DiaGuardia> dias = new ArrayList<DiaGuardia>();
        for (int i = 0; i < this.planDeGuardias.size() && diasEncontrados <= 31; i++) {
            if (fecha.getMonth() == this.planDeGuardias.get(i).getFecha().getMonth()) {
                dias.add(this.planDeGuardias.get(i));
                diasEncontrados++;
            }
        }
        return dias;
    }

    public ArrayList<DiaGuardia> getPlanDeGuardias() {
        return planDeGuardias;
    }

    public void borrarPlanificacion(LocalDate fechaCorte) {
        Iterator<DiaGuardia> iterator = planDeGuardias.iterator();
        while (iterator.hasNext()) {
            DiaGuardia dia = iterator.next();
            if (dia.getFecha().isAfter(fechaCorte)) {
                iterator.remove();
            }
        }
    }
}

