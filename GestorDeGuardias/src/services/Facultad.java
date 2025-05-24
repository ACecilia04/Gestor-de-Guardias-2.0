package services;

import logica.excepciones.EntradaInvalidaException;
import logica.excepciones.MultiplesErroresException;
import model.PeriodoNoPlanificable;
import model.Persona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static utils.Utilitarios.stringEsValido;
import static utils.Utilitarios.stringSoloNumeros;


public class Facultad {
    private final ArrayList<Persona> personas;
    private final ArrayList<PeriodoNoPlanificable> recesosDocentes;

    public Facultad() {
        personas = new ArrayList<>();
        recesosDocentes = new ArrayList<>();
    }

    // ************************************************get listas de la facultad**********************************************************

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

//    public ArrayList<Persona> getEstudiantes() {
//        ArrayList<Persona> estudiantes = new ArrayList<>();
//
////        for (Persona e : personas)
////            if (e instanceof Estudiante)
////                estudiantes.add(e);
//
//        return estudiantes;
//    }
//
//    public ArrayList<Persona> getTrabajadores() {
//        ArrayList<Persona> trabajadores = new ArrayList<>();
////
////        for (Persona e : personas)
////            if (e instanceof Trabajador)
////                trabajadores.add(e);
//
//        return trabajadores;
//    }

//    /**!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! la disponibilidad ya no estan en las personas,
//    como atributo asi que no se que hacer muy bien con esto XD
//
//     * Devuelve la lista de personas disponibles en un una fecha dada
//     * teniendo en cuenta si estan de baja o licencia en la fecha dada
//     *
//     * @param fecha fecha en la que se busca disponibilidad
//     * @return lista de personas disponibles ordenada alfab�ticamente
//     * @throws EntradaInvalidaException si la fecha no es especificada
//     */
//    public ArrayList<Persona> getPersonasDisponibles(LocalDate fecha) throws EntradaInvalidaException {
//        ArrayList<Persona> lista = new ArrayList<>();
//        if (fecha == null) {
//            throw new EntradaInvalidaException("Fecha no especificada.");
//        } else {
//            for (Persona p : personas)
//                if (p.getDisponibilidadParaFecha(fecha) == Disponibilidad.DISPONIBLE)
//                    lista.add(p);
//        }
//        return lista;
//    }
//
//    /**
//     * @param fecha
//     * @param personas
//     * @return lista de personas disponibles en la fecha dada
//     * @throws EntradaInvalidaException
//     */
//    public ArrayList<Persona> getPersonasDisponibles(LocalDate fecha, ArrayList<Persona> personas) throws EntradaInvalidaException {
//        if (fecha == null) {
//            throw new EntradaInvalidaException("Fecha no especificada.");
//        } else {
//            for (Persona p : personas)
//                if (p.getDisponibilidadParaFecha(fecha) == Disponibilidad.DISPONIBLE)
//                    personas.add(p);
//        }
//        return personas;
//    }

    /**
     * Devuelve la lista de personas de baja en un una fecha dada
     *
     * @param fecha fecha en la que se busca disponibilidad
     * @return lista de personas de baja ordenada alfab�ticamente
     * @throws EntradaInvalidaException si la fecha no es especificada
     */
    public ArrayList<Persona> getPersonasDeBaja(LocalDate fecha) throws EntradaInvalidaException {
        ArrayList<Persona> lista = new ArrayList<Persona>();
        if (fecha == null) {
            throw new EntradaInvalidaException("Fecha no especificada.");
        } else {
            for (Persona p : personas)
                if (p.getBaja() == fecha)
                    lista.add(p);
        }
        return lista;
    }
//!!!!!!!!!!!esto no entendi muy xq es que esta si ya tienes una funcion ed obtener personas por baja, asi q la comento y te pregunto luego
//    /**
//     * Devuelve de una lista de personas, las que estan de baja en una fecha
//     * dada
//     *
//     * @param fecha    fecha en la que se busca disponibilidad
//     * @param personas lista de personas
//     * @return lista de personas disponibles ordenada alfab�ticamente
//     * @throws EntradaInvalidaException si la fecha no es especificada
//     */
//    public ArrayList<Persona> getPersonasDeBaja(LocalDate fecha, ArrayList<Persona> personas) throws EntradaInvalidaException {
//        if (fecha == null) {
//            throw new EntradaInvalidaException("Fecha no especificada.");
//        } else {
//            for (Persona p : personas)
//                if (p.getDisponibilidadParaFecha(fecha) == Disponibilidad.BAJA)
//                    personas.add(p);
//        }
//        return personas;
//    }

//    /**
//     * Devuelve la lista de personas de licencia en un una fecha dada
//     *
//     * @param fecha fecha en la que se busca disponibilidad
//     * @return lista de personas de licencia ordenada alfab�ticamente
//     * @throws EntradaInvalidaException si la fecha no es especificada
//     */
//    public ArrayList<Persona> getPersonasDeLicencia(LocalDate fecha) throws EntradaInvalidaException {
//        ArrayList<Persona> lista = new ArrayList<Persona>();
//        if (fecha == null)
//            throw new EntradaInvalidaException("Fecha no especificada.");
//        else {
//            for (Persona p : personas)
//                if (p.getDisponibilidadParaFecha(fecha) == Disponibilidad.LICENCIA)
//                    lista.add(p);
//        }
//        return lista;
//    }

//    /**
//     * Devuelve de una lista de personas. las que estan de licencia en un una
//     * fecha dada
//     *
//     * @param fecha fecha en la que se busca disponibilidad
//     * @return lista de personas de licencia ordenada alfab�ticamente
//     * @throws EntradaInvalidaException si la fecha no es especificada
//     */
//    public ArrayList<Persona> getPersonasDeLicencia(LocalDate fecha, ArrayList<Persona> personas) throws EntradaInvalidaException {
//        if (fecha == null)
//            throw new EntradaInvalidaException("Fecha no especificada.");
//        else {
//            for (Persona p : personas)
//                if (p.getDisponibilidadParaFecha(fecha) == Disponibilidad.LICENCIA)
//                    personas.add(p);
//        }
//        return personas;
//
//    }

    /**
     * Devuelve lista cronologica de los recesos docentes registrados
     */
    public ArrayList<PeriodoNoPlanificable> getRecesosDocentes() {
        return recesosDocentes;
    }

    // ************************************************************Buscar**********************************************************************************

    /**
     * Busca a una persona activa o no activa
     *
     * @param ci ci de identidad de persona buscada
     * @return persona encontrada o null si no la encuentra
     */
    public Persona buscarPersona(String ci) throws EntradaInvalidaException {
        Persona indicada = null;
        boolean encontrada = false;
        if (!stringEsValido(ci))
            throw new EntradaInvalidaException("ci de identidad no especificado.");
        for (int i = 0; i < personas.size() && !encontrada; i++) {
            if (personas.get(i).getCarnet().equals(ci)) {
                indicada = personas.get(i);
                encontrada = true;
            }
        }
        return indicada;
    }

    /**
     * Dado una fecha busca el receso docente correspondiente
     * * @return
     */
    public PeriodoNoPlanificable buscarRecesoDocente(LocalDate fecha) throws EntradaInvalidaException {
        PeriodoNoPlanificable indicado = null;
        boolean encontrado = false;

        if (fecha == null)
            throw new EntradaInvalidaException("Fecha a buscar no especificada.");
        for (int i = 0; i < recesosDocentes.size() && !encontrado; i++) {
            if (!fecha.isBefore(recesosDocentes.get(i).getInicio()) && !fecha.isAfter(recesosDocentes.get(i).getFin())) {
                indicado = recesosDocentes.get(i);
                encontrado = true;
            }
        }
        return indicado;
    }

    // ***********************************************************Validaciones*********************************************************************

    /**
     * Colecciona lista de errores de datos de una persona
     *
     * @param ci        ci de cientidad de la persona
     * @param nombre    nombre de la persona
     * @param apellidos apellidos de la persona
     * @param sexo      sexo de la persona
     * @return lista de errores
     */
    private ArrayList<String> validarPersona(String ci, String nombre, String apellidos, Character sexo) {
        ArrayList<String> errores = new ArrayList<String>();
        boolean carneIdentidadValido = true;
        if (!stringEsValido(ci)) {
            errores.add("ci de identidad no especificado.");
            carneIdentidadValido = false;
        } else {
            if (ci.length() != 11) {
                errores.add("ci de identidad no v�lido: Longitud incorrecta.");
                carneIdentidadValido = false;
            } else {
                if (!stringSoloNumeros(ci)) {
                    errores.add("ci de identidad no v�lido: Caracteres no num�ricos.");
                    carneIdentidadValido = false;
                } else {
                    int dia = Integer.parseInt(ci.substring(4, 6));
                    int mes = Integer.parseInt(ci.substring(2, 4));
                    int anno = Integer.parseInt(ci.substring(0, 2));

                    if (mes > 0 && mes <= 12) {
                        boolean diaValido = true;
                        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                            if (dia > 31 || dia == 0) {
                                diaValido = false;
                            }
                        } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                            if (dia > 30 || dia == 0) {
                                diaValido = false;
                            }
                        } else if (mes == 2) {
                            if (anno % 4 != 0 && dia > 28) {
                                diaValido = false;
                            } else if (anno % 4 == 0 && dia > 29) {
                                diaValido = false;
                            }
                        }
                        if (!diaValido) {
                            errores.add("ci de identidad no v�lido: Dia incorrecto.");
                            carneIdentidadValido = false;
                        }
                    } else {
                        errores.add("ci de identidad no v�lido: Mes incorrecto.");
                        carneIdentidadValido = false;
                    }
                }
            }
        }
        if (!stringEsValido(nombre))
            errores.add("Nombre no especificado.");
        if (!stringEsValido(apellidos))
            errores.add("Apellido no especificado.");
        if (sexo == null)
            errores.add("Sexo no especificado.");
        else if (carneIdentidadValido && ((Integer.parseInt(ci.substring(9, 10)) % 2 == 0 && sexo != 'm') || (Integer.parseInt(ci.substring(9, 10)) % 2 != 0 && sexo != 'f')))
            errores.add("Sexo seleccionado no coincide con la informaci�n del ci de identidad.");

        return errores;
    }

    /**
     * Valida datos de un estudiante con ayuda de validarPersona(String, String,
     * String, Sexo)
     *
     * @param ci        ci de identidad del estudiante
     * @param nombre    nombre del estudiante
     * @param apellidos apellidos del estudiante
     * @param sexo      sexo del estudiante
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    private void validarEstudiante(String ci, String nombre, String apellidos, Character sexo) throws EntradaInvalidaException, MultiplesErroresException {
        ArrayList<String> errores = validarPersona(ci, nombre, apellidos, sexo);
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Estudiante con datos err�neos: ", errores);
        if (buscarPersona(ci) != null)
            throw new EntradaInvalidaException("Estudiante ya registrado.");
    }

    /**
     * Valida datos de un trabajador con ayuda de validarPersona(String, String,
     * String, Sexo)
     *
     * @param ci        ci de identidad del trabajador
     * @param nombre    nombre del trabajador
     * @param apellidos apellidos del trabajador
     * @param sexo      sexo del trabajador
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    private void validarTrabajador(String ci, String nombre, String apellidos, Character sexo) throws EntradaInvalidaException, MultiplesErroresException {
        ArrayList<String> errores = validarPersona(ci, nombre, apellidos, sexo);
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Trabajador con datos err�neos: ", errores);
        if (buscarPersona(ci) != null)
            throw new EntradaInvalidaException("Trabajador ya registrado.");
    }

    /**
     * * Colecciona lista de errores en datos al momento de dar baja a una
     * persona
     *
     * @return lista de errores
     */
    private ArrayList<String> validarBaja(String ci, LocalDate fechaBaja) {
        ArrayList<String> errores = new ArrayList<String>();
        if (!stringEsValido(ci))
            errores.add("ci de identidad no especificado.");
        if (fechaBaja == null)
            errores.add("Fecha de baja no especificada.");

        return errores;
    }

//    /**
//     * Colecciona lista de errores en datos al momento de dar licencia a un
//     * trabajador
//     *
//     * @param inicio inicio de la licencia
//     * @param fin    fin de la licencia
//     * @param tipo   tipo de la licencia
//     * @return lista de errores
//     */
//    private ArrayList<String> validarLicencia(LocalDate inicio, LocalDate fin, String ci, TipoLicencia tipo) {
//        ArrayList<String> errores = validarLicencia(inicio, fin, ci);
//        if (tipo == null)
//            errores.add("Tipo de licencia no especificado.");
//
//        return errores;
//    }
//
//    /**
//     * Colecciona lista de errores en datos al momento de dar licencia a un
//     * estudiante
//     *
//     * @param inicio inicio de la licencia
//     * @param fin    fin de la licencia
//     * @param id     ci de identidad del trabajador
//     * @return lista de errores
//     */
//    private ArrayList<String> validarLicencia(LocalDate inicio, LocalDate fin, String ci) {
//        ArrayList<String> errores = validarLicencia(inicio, ci);
//        if (fin == null)
//            errores.add("Fecha de fin no especificada.");
//        else if (inicio != null && !fin.isAfter(inicio))
//            errores.add("La fecha final de la licencia debe proceder a su fecha de inicio.");
//        return errores;
//    }
//
//    /**
//     * Colecciona lista de errores en datos al momento de dar licencia a un
//     * estudiante
//     *
//     * @param inicio inicio de la licencia
//     * @param id     ci de identidad del trabajador
//     * @return lista de errores
//     */
//    private ArrayList<String> validarLicencia(LocalDate inicio, String ci) {
//        ArrayList<String> errores = new ArrayList<String>();
//        if (!stringEsValido(ci))
//            errores.add("ci de identidad no especificado.");
//        if (inicio == null)
//            errores.add("Fecha de inicio no especificada.");
//
//        return errores;
//    }

    /**
     * Valida datos de un receso docente
     *
     * @param inicio inicio del receso
     * @param fin    fin del receso
     * @throws MultiplesErroresException si algun dato no es especificado
     * @throws EntradaInvalidaException
     */
    private void validarRecesoDocente(LocalDate inicio, LocalDate fin) throws MultiplesErroresException, EntradaInvalidaException {
        ArrayList<String> errores = new ArrayList<String>();
        boolean nuevoRecesoSolapaOtro = false;

        if (inicio == null)
            errores.add("Fecha de inicio no especificada.");
        if (fin == null)
            errores.add("Fecha de fin no especificada.");
        else {
            if (inicio != null) {
                if (fin.equals(inicio))
                    errores.add("Fecha de inicio coincide con fecha de fin.");
                if (fin.isBefore(inicio))
                    errores.add("Fecha de fin precede fecha de inicio.");
            }
        }
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Receso docente con fechas err�neas: ", errores);

        for (int i = 0; i < recesosDocentes.size() && !nuevoRecesoSolapaOtro; i++) {
            nuevoRecesoSolapaOtro = ((recesosDocentes.get(i).fechaEstaSolapada(inicio) || recesosDocentes.get(i).fechaEstaSolapada(fin)) || (!recesosDocentes.get(i).getInicio().isBefore(inicio) && !recesosDocentes.get(i).getInicio().isAfter(fin)));
        }
        if (nuevoRecesoSolapaOtro)
            throw new EntradaInvalidaException("Las fechas introducidas coinciden con un receso docente ya registrado.");
    }

    // ****************************************************************Hacer cambios a las listas de la Facultad*******************************************************************

    /**
     * A�ade un estudiante a la lista de personas de la facultad tomando en
     * conscieraci�n su ordenamiento alfab�tico
     *
     * @param ci        ci de identidad del estudiante
     * @param nombre    nombre del estudiante
     * @param apellidos apellidos del estudiante
     * @param sexo      sexo del estudiante
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public void annadirEstudiante(String ci, String nombre, String apellidos, Character sexo) throws EntradaInvalidaException, MultiplesErroresException {
        validarEstudiante(ci, nombre, apellidos, sexo);
        personas.add(new Persona(ci, nombre, apellidos, sexo, "Estudiante"));
        Collections.sort(personas);
    }

    /**
     * A�ade un trabajador a la lista de personas de la facultad tomando en
     * consideraci�n su ordenamiento alfab�tico
     *
     * @param ci        ci de identidad del trabajador
     * @param nombre    nombre del trabajador
     * @param apellidos apellidos del trabajador
     * @param sexo      sexo del trabajador
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public void annadirTrabajador(String ci, String nombre, String apellidos, Character sexo) throws EntradaInvalidaException, MultiplesErroresException {
        validarTrabajador(ci, nombre, apellidos, sexo);
        personas.add(new Persona(ci, nombre, apellidos, sexo, "Trabajador"));
        Collections.sort(personas);
    }

    /**
     * Elimina persona de la facultad si esta existe
     *
     * @param ci ci de identidad de persona buscada
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public void eliminarPersona(String ci) throws EntradaInvalidaException {
        Persona p = buscarPersona(ci);
        if (p == null)
            throw new EntradaInvalidaException("Persona no registrada.");
        else {
            personas.remove(p);
        }
    }

    /**
     * A�ade un receso docente a la facultad
     *
     * @param inicio
     * @param fin
     * @param nombre
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public void annadirRecesoDocente(LocalDate inicio, LocalDate fin, String nombre) throws MultiplesErroresException, EntradaInvalidaException {
        validarRecesoDocente(inicio, fin);
        PeriodoNoPlanificable rd = new PeriodoNoPlanificable(inicio, fin);
        recesosDocentes.add(rd);
        Collections.sort(recesosDocentes);
    }

    /**
     * Dado una fecha elimina el receso docente correspondiente si este existe
     *
     * @param inicio
     * @throws EntradaInvalidaException
     */
    public void eliminarRecesoDocente(LocalDate inicio) throws EntradaInvalidaException {
        PeriodoNoPlanificable rd = buscarRecesoDocente(inicio);
        if (rd == null)
            throw new EntradaInvalidaException("Receso docente no registrado.");
        else {
            recesosDocentes.remove(rd);
        }
    }

    // **************************************************************Hacer cambios a Persona*******************************************************************

    /**
     * Pone fecha de baja a una persona dada
     *
     * @param ci        ci de identidad de la persona
     * @param fechaBaja fecha de la baja
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public void darBaja(String ci, LocalDate fechaBaja) throws EntradaInvalidaException, MultiplesErroresException {
        ArrayList<String> errores = validarBaja(ci, fechaBaja);
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Baja con datos err�neos:", errores);
        Persona p = buscarPersona(ci);
        if (p == null)
            throw new EntradaInvalidaException("Persona no registrada.");
        p.darBaja(fechaBaja);
    }

//    /**
//     * A�ade una licencia a un trabajador dado
//     *
//     * @param inicio inicio de la licencia
//     * @param fin    fin de la licencia
//     * @param ci     ci de identidad del trabajador
//     * @param tipo   tipo de la licencia
//     * @throws EntradaInvalidaException
//     * @throws MultiplesErroresException
//     */
//    public void darLicenciaTrabajador(LocalDate inicio, LocalDate fin, String ci, TipoLicencia tipo) throws EntradaInvalidaException, MultiplesErroresException {
//        ArrayList<String> errores = validarLicencia(inicio, fin, ci, tipo);
//        if (!errores.isEmpty())
//            throw new MultiplesErroresException("Licencia con datos err�neos: ", errores);
//        Persona p = buscarPersona(ci);
//        if (p == null)
//            throw new EntradaInvalidaException("Trabajador no registrado.");
//        ((Trabajador) p).annadirLicencia(inicio, fin, tipo);
//    }
//
//    /**
//     * A�ade una licencia a un estudiante dado
//     *
//     * @param inicio inicio de la licencia
//     * @param fin    fin de la licencia
//     * @param ci     ci de identidad del trabajador
//     * @throws EntradaInvalidaException
//     * @throws MultiplesErroresException
//     */
//    public void darLicenciaEstudiante(LocalDate inicio, LocalDate fin, String ci) throws EntradaInvalidaException, MultiplesErroresException {
//        ArrayList<String> errores = validarLicencia(inicio, fin, ci);
//        if (!errores.isEmpty())
//            throw new MultiplesErroresException("Licencia con datos err�neos: ", errores);
//        Persona p = buscarPersona(ci);
//        if (p == null)
//            throw new EntradaInvalidaException("Estudiante no registrado.");
//        ((Estudiante) p).annadirLicencia(inicio, fin);
//    }
//
//    /**
//     * A�ade una licencia a un estudiante dado
//     *
//     * @param inicio inicio de la licencia
//     * @param ci     ci de identidad del trabajador
//     * @throws EntradaInvalidaException
//     * @throws MultiplesErroresException
//     */
//    public void darLicenciaEstudiante(LocalDate inicio, String ci) throws EntradaInvalidaException, MultiplesErroresException {
//        ArrayList<String> errores = validarLicencia(inicio, ci);
//        if (!errores.isEmpty())
//            throw new MultiplesErroresException("Licencia con datos err�neos: ", errores);
//        Persona p = buscarPersona(ci);
//        if (p == null)
//            throw new EntradaInvalidaException("Estudiante no registrado.");
//        ((Estudiante) p).annadirLicencia(inicio);
//    }

    /***
     * A�ade a la persona dada una guardia asignada en una fecha
     *
     * @param ci
     *            ci de identidad de la persona
     * @param fecha
     *            fecha de la guardia
     * @throws EntradaInvalidaException
     */
    public void asignarGuardia(String ci, LocalDate fecha) throws EntradaInvalidaException {
        if (fecha == null)
            throw new EntradaInvalidaException("Fecha de guardia no espeficicada.");

        Persona persona = buscarPersona(ci);
        persona.annadirGuardiaAsignada(fecha);
    }

    /**
     * Si el ci dado corresponde a un trabajador y la fecha a una dia de receso docente
     * a�ade la fecha a los dias de disponibilidad de este
     *
     * @param id
     * @param fecha
     * @throws EntradaInvalidaException
     * @throws MultiplesErroresException
     */
//    public void annadirFechaDeDisponibilidad(String id, LocalDate fecha) throws EntradaInvalidaException, MultiplesErroresException {
//        ArrayList<String> errores = new ArrayList<String>();
//        if (fecha == null)
//            throw new EntradaInvalidaException("Fecha no espeficicada.");
//        Persona persona = buscarPersona(id);
//        if (!(persona.getTipoPersona().getTipo().equalsIgnoreCase("trabajador")))
//            errores.add("La persona que corresponde al ci dado no es un trabajador.");
//        if (!fechaEsRecesoDocente(fecha))
//            errores.add("Fecha no corresponde a ningún receso docente registrado.");
//        if (!errores.isEmpty())
//            throw new MultiplesErroresException("Datos erróneos: ", errores);
//
//        persona.annadirDiaDeDisponibilidad(fecha);
//    }

    // *********************************************************Informacion********************************************************************************

    /**
     * Dado una fecha devuelve si esta pertenece o no a un receso docente
     *
     * @param fecha
     * @return true si la fecha pertenece a un receso docente
     */
    public boolean fechaEsRecesoDocente(LocalDate fecha) {
        boolean es = false;
        for (int i = 0; i < recesosDocentes.size() && !es; i++) {
            if (recesosDocentes.get(i).fechaEstaSolapada(fecha)) {
                es = true;
            }
        }
        return es;
    }

    public int getCantGuardiasAsignadas(String id) throws EntradaInvalidaException {
        Persona p = buscarPersona(id);
        return p.getCantGuardiasAsignadas();
    }

    public PeriodoNoPlanificable buscarRecesoDocente(String selectedItem) {
        PeriodoNoPlanificable aux = null;
//        for (int i = 0; i < recesosDocentes.size() && aux == null; i++) {
//            if (recesosDocentes.get(i).getNombre().equalsIgnoreCase(selectedItem)) {
//                aux = recesosDocentes.get(i);
//            }
//        }
        return aux;
    }

}
