package rdb.entity;

import logica.excepciones.EntradaInvalidaException;

import java.time.LocalDate;
import java.util.ArrayList;

public class Trabajador extends Persona {
  //  private final ArrayList<LicenciaTrabajador> licencias;   //las licencias estan ordenadas segun su inicio
    private final ArrayList<LocalDate> diasDisponibleEnRecesoDocente;

    public Trabajador(String id, String nombre, String apellidos, Sexo sexo) {
        super(id, nombre, apellidos, sexo);
        //       licencias = new ArrayList<LicenciaTrabajador>();
        diasDisponibleEnRecesoDocente = new ArrayList<LocalDate>();
    }

    public boolean estaDisponibleEnRecesoDocente(LocalDate fecha) {
        boolean disponible = false;
        for (int i = 0; i < diasDisponibleEnRecesoDocente.size() && !disponible; i++)
            disponible = diasDisponibleEnRecesoDocente.get(i).equals(fecha);
        return disponible;
    }

//    /**
//     * Valida los datos de la licencia de un trabajador
//     *
//     * @param inicio
//     * @param fin
//     * @param tipo
//     * @throws EntradaInvalidaException
//     * @throws MultiplesErroresException
//     */
//    private void validarLicencia(LocalDate inicio, LocalDate fin, TipoLicencia tipo) throws EntradaInvalidaException, MultiplesErroresException {
//        ArrayList<String> errores = new ArrayList<String>();
//        boolean nuevaLicenciaSolapaOtra = false;
//
//        if (tipo == null)
//            errores.add("Tipo de licencia no especificado.");
//        if (inicio == null)
//            errores.add("Fecha de inicio no especificada.");
//        if (fin == null)
//            errores.add("Fecha de inicio no especificada.");
//        if (fin.equals(inicio))
//            errores.add("Fecha de inicio coincide con fecha de fin.");
//        if (fin.isBefore(inicio))
//            errores.add("Fecha de inicio precede fecha de fin.");
//        if (!errores.isEmpty())
//            throw new MultiplesErroresException("Licencia con fechas err�neas:", errores);
//        if (fechaDeBaja != null && !fechaDeBaja.isAfter(fin))
//            throw new EntradaInvalidaException("La licencia debe ser antes de la fecha de baja: ");
//        for (int i = 0; i < licencias.size() && !nuevaLicenciaSolapaOtra; i++) {
//            nuevaLicenciaSolapaOtra = (licencias.get(i).fechaEstaSolapada(inicio) || licencias.get(i).fechaEstaSolapada(fin));
//        }
//        if (nuevaLicenciaSolapaOtra)
//            throw new EntradaInvalidaException("Las fechas introducidas coinciden con una licencia ya registrada.");
//    }
//
//    public void annadirLicencia(LocalDate inicio, LocalDate fin, TipoLicencia tipo) throws EntradaInvalidaException, MultiplesErroresException {
//        validarLicencia(inicio, fin, tipo);
//        LicenciaTrabajador nuevaLicencia = new LicenciaTrabajador(inicio, fin, tipo);
//        licencias.add(nuevaLicencia);
//        Collections.sort(licencias);
//    }
//
//    public ArrayList<LicenciaTrabajador> getLicencias() {
//        return licencias;
//    }
//
//    /**
//     * Si la fecha dada no ha sido registrada como dia de disponibilidad del trabajador, la a�ade
//     *
//     * @param fecha
//     * @throws EntradaInvalidaException si la fecha ha sido previamente registrada
//     */
    public void annadirDiaDeDisponibilidad(LocalDate fecha) throws EntradaInvalidaException {
        if (this.diasDisponibleEnRecesoDocente.contains(fecha))
            throw new EntradaInvalidaException("Fecha de disponibilidad ya registrada.");
        this.diasDisponibleEnRecesoDocente.add(fecha);
    }
}
