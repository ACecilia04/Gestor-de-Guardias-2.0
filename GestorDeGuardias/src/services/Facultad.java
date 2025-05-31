package services;

import model.PeriodoNoPlanificable;
import model.Persona;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.time.LocalDate;
import java.util.ArrayList;


public class Facultad {
    private final ArrayList<Persona> personas;
    private final ArrayList<PeriodoNoPlanificable> recesosDocentes;

    public Facultad() {
        personas = new ArrayList<>();
        recesosDocentes = new ArrayList<>();
    }

    // ************************************************get listas de la facultad**********************************************************

//    public ArrayList<Persona> getPersonas() {
//        return personas;
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

    // ***********************************************************Validaciones*********************************************************************

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
     * Elimina persona de la facultad si esta existe
     *
     * @param ci ci de identidad de persona buscada
     * @throws MultiplesErroresException
     * @throws EntradaInvalidaException
     */
    public void eliminarPersona(String ci) throws EntradaInvalidaException {
        Persona p = ServicesLocator.getInstance().getPersonaServices().getPersonaByCi(ci);
        if (p == null)
            throw new EntradaInvalidaException("Persona no registrada.");
        else {
            personas.remove(p);
        }
    }

    /**
     * Dado una fecha elimina el receso docente correspondiente si este existe
     *
     * @param inicio
     * @throws EntradaInvalidaException
     */
    public void eliminarRecesoDocente(LocalDate inicio) throws EntradaInvalidaException {
        PeriodoNoPlanificable rd = ServicesLocator.getInstance().getPeriodoNoPlanificableServices().getPeriodosEnFecha(inicio);
        if (rd == null)
            throw new EntradaInvalidaException("Receso docente no registrado.");
        else {
            recesosDocentes.remove(rd);
        }
    }

    // **************************************************************Hacer cambios a Persona*******************************************************************

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

        Persona persona = ServicesLocator.getInstance().getPersonaServices().getPersonaByCi(ci);
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
        Persona p = ServicesLocator.getInstance().getPersonaServices().getPersonaByCi(id);
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
