package services;

import model.DiaGuardia;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.util.ArrayList;


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

//    /**
//     * @param fecha
//     * @return lista de EsquemaGuardias de guardia que le corresponden a la fecha dada
//     * @throws EntradaInvalidaException
//     */
//    private ArrayList<EsquemaGuardia> getEsquemaGuardiasDeFecha(LocalDate fecha) throws EntradaInvalidaException {
//        //        es lo d getconfig en crearPlantilla y getPersonasDisponibles
//        DayOfWeek diaDeSemana = fecha.getDayOfWeek();
//        boolean fechaEnReceso = facultad.fechaEsRecesoDocente(fecha); //para saber si seran solo trabajores o no
//        boolean fechaEnSemanaPar = fechaEnSemanaPar(fecha);  // por si es fin de semana saber si seran estudiantes o trabajadores en el horario diurno
//
//        ArrayList<EsquemaGuardia> retVal = new ArrayList<EsquemaGuardia>(); //lista a return
////        for (EsquemaGuardia EsquemaGuardia : EnumSet.allOf(EsquemaGuardia.class)) {  //recorrer los valores del enum EsquemaGuardia
////            if ((EsquemaGuardia.getDiaSemana() == null || EsquemaGuardia.getDiaSemana().equals(diaDeSemana)) &&          //si el dia de la semana no importa o es igual al de la fecha
////                    (EsquemaGuardia.getEsReceso() == null || EsquemaGuardia.getEsReceso().equals(fechaEnReceso)) &&          //y no importa si es receso o coincide con la fecha
////                    (EsquemaGuardia.getEsSemanaPar() == null || EsquemaGuardia.getEsSemanaPar().equals(fechaEnSemanaPar))) { // si la paridad de la semana no importa p coincide con la de la fecha
////                retVal.add(EsquemaGuardia);
////
////            }
//        /* lo mismo pero con ifs anidados
//         * if(EsquemaGuardia.getDiaSemana() == null || EsquemaGuardia.getDiaSemana().equals(diaDeSemana))
//         * 	if(EsquemaGuardia.getEsReceso() == null || EsquemaGuardia.getEsReceso().equals(fechaEnReceso))
//         * 		if(EsquemaGuardia.getEsSemanaPar() == null || EsquemaGuardia.getEsSemanaPar().equals(fechaEnSemanaPar))
//         * 			retVal.add(EsquemaGuardia);
//
//         */
////        }
//        return retVal;
//    }

    //*******************************************************Para llenar la plantilla*****************************************************************

//    /**
//     * Guarda en el registro del gestor una lista de planificaciones solo si a
//     * todos los turnos de d�as lectivos se le han asignado una persona para
//     * hacerlos.
//     *
//     * @param nuevosDias
//     * @throws EntradaInvalidaException
//     */
//    public void guardar(ArrayList<DiaGuardia> nuevosDias) throws EntradaInvalidaException {
//        boolean vacio = false;
//
//        if (nuevosDias == null || nuevosDias.isEmpty())
//            throw new EntradaInvalidaException("No hay días para guardar.");
//
//        for (int i = 0; i < nuevosDias.size() && !vacio; i++) {
////            if (!facultad.fechaEsRecesoDocente(nuevosDias.get(i).getFecha())) // xq	si es receso docente puede estar vacia ya q parte de voluntariedad
////                for (int ii = 0; ii < nuevosDias.get(i).getTurnos().size() && !vacio; ii++)
//////                    vacio = nuevosDias.get(i).getTurnos().get(ii).getPersonaAsignada() == null;
//        }
//        if (vacio) {
//            throw new EntradaInvalidaException("Todo día lectivo debe tener una persona asignada.");
//        } else {
//            for (DiaGuardia dia : nuevosDias)
////                for (TurnoDeGuardia turno : dia.getTurnos())
////                    if (turno.getPersonaAsignada() != null)
////                        facultad.asignarGuardia(turno.getPersonaAsignada().getCarnet(), dia.getFecha());  //ya que se va a guardar se le puede poner la guardia asignada a la persona
//                // si la fecha del ultimo dia registrado esta antes el primer dia de
//                // los nuevos (es decir: estas haciendo una nueva planif)
//                if (this.planDeGuardias.isEmpty() || (planDeGuardias.get(planDeGuardias.size() - 1).getFecha().isBefore(nuevosDias.get(0).getFecha()))) {
//                    planDeGuardias.addAll(nuevosDias);
//                    // si la fecha del ultimo dia registrado esta antes el primer dia de
//                    // los nuevos (es decir: estas haciendo una nueva planif)
//                } else {
//                    //caso contrario es que ya esa planificacion estaba registrada
//                    int indicePlanificados = Collections.binarySearch(planDeGuardias, nuevosDias.get(0)); //google dice que esta magia debe funcionar
//                    for (DiaGuardia d : nuevosDias)
//                        planDeGuardias.set(indicePlanificados++, d);
//                }
//        }
//    }

    //*************************************************************Editar******************************************************************

//    /**
//     * Si los horarios son compatibles y las personas asignadas a ellos est�n
//     * disponibles se intercambian los turnos.
//     *
//     * @param turno1 primer turno seleccionado
//     * @param fecha1 fecha del primer turno
//     * @param turno2 segundo turno seleccionado
//     * @param fecha2 fecha del primer turno
//     * @throws EntradaInvalidaException
//     * @throws MultiplesErroresException
//     */
//    public void intercambiarTurnos(TurnoDeGuardia turno1, LocalDate fecha1, TurnoDeGuardia turno2, LocalDate fecha2) throws EntradaInvalidaException, MultiplesErroresException {
//        if (turno1.getHorario() != turno2.getHorario())
//            throw new EntradaInvalidaException("Estos dos turnos no son intercambiables porque sus horarios no son iguales.");
////        else if (getDisponibilidadPersona(fecha1, turno1.getHorario(), turno2.getPersonaAsignada()) && getDisponibilidadPersona(fecha2, turno2.getHorario(), turno1.getPersonaAsignada())) {
////            Persona aux = turno1.getPersonaAsignada();
////            turno1.asignarPersona(turno2.getPersonaAsignada());
////            turno2.asignarPersona(aux);
////        }
//        else {
//            throw new EntradaInvalidaException("Estos dos turnos no son intercambiables porque las personas asignadas a ellos no estan disponibles en la fecha alterna.");
//        }
//    }
//
//    public void borrarPersonaDeTurno(LocalDate fecha, Horario horario) throws EntradaInvalidaException {
//        DiaGuardia dia = buscarDiaGuardia(fecha);
//
//        if (dia == null)
//            throw new EntradaInvalidaException("No hay planificación para esta fecha.");
////        TurnoDeGuardia turno = dia.buscarTurno(horario);
//
////        if (turno == null)
////            throw new EntradaInvalidaException("Este d�a no tiene el horario deseado.");
////        turno.borrarPersonaAsignada();
//    }
//
//    public void borrarPersonasDeDia(LocalDate fecha) throws EntradaInvalidaException {
//        DiaGuardia dia = buscarDiaGuardia(fecha);
//
//        if (dia == null)
//            throw new EntradaInvalidaException("No hay planificación para esta fecha.");
//        for (TurnoDeGuardia turno : dia.getTurnos())
//            turno.borrarPersonaAsignada();
//    }

//    public void crearPlanificacionAutomaticamente(ArrayList<DiaGuardia> dias) throws MultiplesErroresException, EntradaInvalidaException {
//        ArrayList<Persona> personasDisponibles;
//
//        for (DiaGuardia dia : dias) {
////            for (TurnoDeGuardia turno : dia.getTurnos()) {
////                if (turno.getPersonaAsignada() == null) {
////
////                    personasDisponibles = getPersonasDisponibles(dia.getFecha(), turno.getHorario(), dias);
////                    if (!personasDisponibles.isEmpty())
//                      asignarPersona(dia, turno.getHorario(), personasDisponibles.get(0));
////
////                }
////            }
//        }
//    }

//    /**
//     * Luego de dar la baja via la facultad, asigna un sustituto
//     * adecuado en todas la guardias asignadas a la persona
//     *
//     * @param ci        carnet de identidad de la persona
//     * @param fechaBaja
//     * @throws EntradaInvalidaException
//     * @throws MultiplesErroresException
//     */
//    public void darBaj(String ci, LocalDate fechaBaja) throws EntradaInvalidaException, MultiplesErroresException {
//        Persona persona = ServicesLocator.getInstance().getPersonaServices().getPersonaByCi(ci);
//
//        facultad.darBaja(persona.getCarnet(), fechaBaja);
//        sustituirGuardias(fechaBaja, persona);
//    }

//    /**
//     * Luego de dar la licencia via la facultad, asigna un sustituto
//     * adecuado en todas la guardias asignadas al estudiante
//     *
//     * @param ci
//     * @param inicio
//     * @throws EntradaInvalidaException
//     * @throws MultiplesErroresException
//     */
//    public void darLicenciaEstudiante(String ci, LocalDate inicio) throws EntradaInvalidaException, MultiplesErroresException {
//        Persona persona = facultad.buscarPersona(ci);
//        facultad.darLicenciaEstudiante(inicio, persona.getCarnet());
//        sustituirGuardias(inicio, persona);
//
//    }
//
//    /**
//     * Luego de dar la licencia via la facultad, asigna un sustituto
//     * adecuado en todas la guardias asignadas al estudiante
//     *
//     * @param ci
//     * @param inicio
//     * @param fin
//     * @throws EntradaInvalidaException
//     * @throws MultiplesErroresException
//     */
//    public void darLicenciaEstudiante(String ci, LocalDate inicio, LocalDate fin) throws EntradaInvalidaException, MultiplesErroresException {
//        Persona persona = facultad.buscarPersona(ci);
//        facultad.darLicenciaEstudiante(inicio, fin, persona.getCarnet());
//        sustituirGuardias(inicio, fin, persona);
//
//    }
//
//    /**
//     * Luego de dar la licencia via la facultad, asigna un sustituto
//     * adecuado en todas la guardias asignadas al trabajador
//     *
//     * @param ci
//     * @param inicio
//     * @param fin
//     * @param tipo
//     * @throws EntradaInvalidaException
//     * @throws MultiplesErroresException
//     */
//    public void darLicenciaTrabajador(String ci, LocalDate inicio, LocalDate fin, TipoLicencia tipo) throws EntradaInvalidaException, MultiplesErroresException {
//        Persona persona = facultad.buscarPersona(ci);
//        facultad.darLicenciaTrabajador(inicio, fin, persona.getCarnet(), tipo);
//        sustituirGuardias(inicio, fin, persona);
//
//    }

    /**
     * Asigna un sustituto adecuado en todas la guardias planificadas
     * asignadas a la persona entre las 2 fechas dadas
     *
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
//    private void sustituirGuardias(LocalDate inicio, LocalDate fin, Persona persona) throws MultiplesErroresException, EntradaInvalidaException {
//        ArrayList<Persona> personasDisponibles;
//        for (DiaGuardia dia : this.planDeGuardias) {
//            //si la fecha de ese dia de guardia esta entre las 2 fechas dadas
//            // y esta despues de hoy
//            if ((!dia.getFecha().isBefore(inicio) && !dia.getFecha().isAfter(fin))
//                    && dia.getFecha().isAfter(LocalDate.now())) {
////                for (TurnoDeGuardia turno : dia.getTurnos()) {
////                    if (turno.getPersonaAsignada().equals(persona)) {
////                        personasDisponibles = getPersonasDisponibles(dia.getFecha(), turno.getHorario(), new ArrayList<DiaGuardia>());
////                        if (!personasDisponibles.isEmpty())
////                            asignarPersona(dia, turno.getHorario(), personasDisponibles.get(0));
////                    }
////                }
//            }
//        }
//    }
//
//    /**
//     * Asigna un sustituto adecuado en todas la guardias planificadas
//     * asignadas a la persona para esa fecha y las proximas
//     *
//     * @param fecha
//     * @param persona
//     * @throws MultiplesErroresException
//     * @throws EntradaInvalidaException
//     */
//    private void sustituirGuardias(LocalDate fecha, Persona persona) throws MultiplesErroresException, EntradaInvalidaException {
//        ArrayList<Persona> personasDisponibles;
//        for (DiaGuardia dia : this.planDeGuardias) {
//            if (!dia.getFecha().isBefore(fecha) && dia.getFecha().isAfter(LocalDate.now())) {
////                for (TurnoDeGuardia turno : dia.getTurnos()) {
////                    if (turno.getPersonaAsignada().equals(persona)) {
////                        personasDisponibles = getPersonasDisponibles(dia.getFecha(), turno.getHorario(), new ArrayList<DiaGuardia>());
////                        if (!personasDisponibles.isEmpty())
////                            asignarPersona(dia, turno.getHorario(), personasDisponibles.get(0));
////                    }
////                }
//            }
//        }
//    }

//    /**
//     * @param fecha
//     * @param horario
//     * @param persona
//     * @return true si a la persona le coresponde el horario dado y est�
//     * disponible para ese dia
//     * @throws MultiplesErroresException
//     * @throws EntradaInvalidaException
//     */
//    public boolean getDisponibilidadPersona(LocalDate fecha, Horario horario, Persona persona) throws MultiplesErroresException, EntradaInvalidaException {
//        ArrayList<String> errores = new ArrayList<String>();
//
//        if (fecha == null)
//            errores.add("Fecha no especificada.");
//        if (horario == null)
//            errores.add("Horario no especificado.");
//        if (persona == null)
//            errores.add("Persona no especificada."); // ESTE ES EL Q PIDEN
//        // ESPECIFICAMENTE
//        if (!errores.isEmpty())
//            throw new MultiplesErroresException("Datos incorrectos:, errores");
//        if (ServicesLocator.getInstance().getPersonaServices().getPersonaByCi(persona.getCarnet()) == null)
//            throw new EntradaInvalidaException("Persona no registrada.");
//
//        ArrayList<EsquemaGuardia> EsquemaGuardias = getEsquemaGuardiasDeFecha(fecha);
//        // esta lanza la EntradaInvalidaException y poor eso es partede la declaracion pero en
//        // realidad no tendr� oportunidad de lanzarla aqui
//        boolean disponible = false;
//        for (int i = 0; i < EsquemaGuardias.size() && !disponible; i++) {
////            if (EsquemaGuardias.get(i).getHorario() == horario) {
////                disponible = ((EsquemaGuardias.get(i).getClase().equals(persona.getClass())) && (EsquemaGuardias.get(i).getSexo() == null || EsquemaGuardias.get(i).getSexo().equals(persona.getSexo())) && (persona.getDisponibilidadParaFecha(fecha) == Disponibilidad.DISPONIBLE));
////            }
//        }
//        return disponible;
//    }

    //******************************************************Cumplimiento**************************************************************************************


//    public ArrayList<DiaGuardia> getDiasPorActualizarCumplimiento() throws EntradaInvalidaException {
//        ArrayList<DiaGuardia> diasPorActualizar = new ArrayList<DiaGuardia>();
//        int i = 0;
//        if (this.planDeGuardias.isEmpty())
//            throw new EntradaInvalidaException("No hay guardias planificadas.");
//
//        while (planDeGuardias.get(i).getFecha().isBefore(LocalDate.now())) {
//            DiaGuardia dia = planDeGuardias.get(i);
//            if (!(dia.getTurnosPorActualizar().isEmpty()))
//                diasPorActualizar.add(dia);
//            i++;
//        }
//        return diasPorActualizar;
//
//    }

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
//		TurnoDeGuardia turno = dia.buscarTurno(horario);
//		if (turno.getCumplimiento() == null){
//			turno.actualizarCumplimiento(hecho);
//		} else
//			throw new EntradaInvalidaException("Ya se ha actualizado el cumplimiento de esta guardia.");
//
    //	}
//    public void actualizarCumplimiento(ArrayList<DiaGuardia> diasPorActualizar) throws EntradaInvalidaException, MultiplesErroresException {
//        for (DiaGuardia dia : diasPorActualizar) {
//        }
//            for (TurnoDeGuardia turno : dia.getTurnos())
//                if (turno.getCumplimiento() != null) {
////					turno.getPersonaAsignada().actualizarCumplimiento(dia.getFecha(), turno.getCumplimiento());
//                } else
//                    throw new EntradaInvalidaException("Ya se ha actualizado el cumplimiento de esta guardia.");
//    }

    // *********************************************************************Filtros y otros**********************************************************************************

//    /**
//     * Busca los EsquemaGuardias de esa fecha y recorre ese arreglo. Cuando el horario
//     * de uno de esos EsquemaGuardias sea igual al dado, recorre la lista de personas
//     * de la facultad buscando a los que encajen segun su tipo, sexo y
//     * disponibilidad
//     *
//     * @param fecha
//     * @param horario
//     * @return lista de persona disponibles con alguna guardia de recuperaci�n
//     * que cumplir en esa fecha para ese horario
//     * @throws MultiplesErroresException
//     * @throws EntradaInvalidaException
//     */
//    public ArrayList<Persona> getPersonasDisponiblesConGuardiasDeRecuperacion(LocalDate fecha, Horario horario, ArrayList<DiaGuardia> diasEnPantalla) throws MultiplesErroresException, EntradaInvalidaException {
////        List<Persona> personasDisponibles = getPersonasDisponibles(fecha, horario, diasEnPantalla);
//        ArrayList<Persona> personasConDeudas = new ArrayList<>();
//        int i = 0;
//
////        while (personasDisponibles.get(i).getGuardiasDeRecuperacion() > 0) {
////            personasConDeudas.add(personasDisponibles.get(i));
////            i++;
////        }
//        return personasConDeudas;
//    }
//
//    /**
//     * Busca los EsquemaGuardias de esa fecha y recorre ese arreglo. Cuando el horario
//     * de uno de esos EsquemaGuardias sea igual al dado, recorre la lista de personas
//     * de la facultad buscando a los que encajen segun su tipo, sexo y
//     * disponibilidad
//     *
//     * @param fecha
//     * @param horario
//     * @return lista de persona disponibles sin guardias de recuperaci�n que
//     * cumplir en esa fecha para ese horario
//     * @throws MultiplesErroresException
//     * @throws EntradaInvalidaException
//     */
//    public ArrayList<Persona> getPersonasDisponiblesSinGuardiasDeRecuperacion(LocalDate fecha, Horario horario, ArrayList<DiaGuardia> diasEnPantalla) throws MultiplesErroresException, EntradaInvalidaException {
////        List<Persona> personasDisponibles = getPersonasDisponibles(fecha, horario, diasEnPantalla);
//        ArrayList<Persona> personasSinDeudas = new ArrayList<Persona>();
////        int i = personasDisponibles.size() - 1;
////
////        while (personasDisponibles.get(i).getGuardiasDeRecuperacion() == 0) {
////            personasSinDeudas.add(0, personasDisponibles.get(i));
////            i--;
////        }
//        return personasSinDeudas;
//    }

//    /**
//     * @param fecha
//     * @return indice del dia planificado o -1 si no ha sido planificado
//     */
//    private DiaGuardia buscarDiaGuardia(LocalDate fecha) {
//        int indice;
////        indice = binaryDateSearch(planDeGuardias, fecha, 0, planDeGuardias.size() - 1);
////
////        return indice == -1 ? null : planDeGuardias.get(indice);
//        return null;
//    }
    public Facultad getFacultad() {
        return facultad;
    }

//    /**
//     * Dado una fecha devuelve los dias ya planificados de el mes
//     * correspondiente
//     *
//     * @param fecha
//     * @return arreglo de dias de guardia planificados
//     */
//    public ArrayList<DiaGuardia> getPlanificacionesAPartirDe(LocalDate fecha) {
//        int diasEncontrados = 0;
//        ArrayList<DiaGuardia> dias = new ArrayList<DiaGuardia>();
//        for (int i = 0; i < this.planDeGuardias.size() && diasEncontrados <= 31; i++) {
//            if (fecha.getMonth() == this.planDeGuardias.get(i).getFecha().getMonth()) {
//                dias.add(this.planDeGuardias.get(i));
//                diasEncontrados++;
//            }
//        }
//        return dias;
//    }

//    public ArrayList<DiaGuardia> getPlanDeGuardias() {
//        return planDeGuardias;
//    }

//    public void borrarPlanificacion(LocalDate fechaCorte) {
//        Iterator<DiaGuardia> iterator = planDeGuardias.iterator();
//        while (iterator.hasNext()) {
//            DiaGuardia dia = iterator.next();
//            if (dia.getFecha().isAfter(fechaCorte)) {
//                iterator.remove();
//            }
//        }
//    }
}

