package logica.principal;

import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Estudiante extends Persona {
    private final ArrayList<Licencia> licencias;   //las licencias estan ordenadas segun su inicio

    public Estudiante(String id, String nombre, String apellidos, Sexo sexo) {
        super(id, nombre, apellidos, sexo);
        licencias = new ArrayList<Licencia>();

    }

    public boolean estaDisponibleEnRecesoDocente(LocalDate fecha) {
        return false;
    }

    public ArrayList<Licencia> getLicencias() {
        return this.licencias;
    }

    /**
     * Valida licencia con inicio y fin
     * Toma en consideracion que ninguna fecha
     * este vacia y que los valores de estas no sean iguales ni esten invertidos
     * y verifica que estas no coincidan con ninguna otra licencia
     *
     * @param inicio fecha de inicio de la licencia
     * @param fin    fecha del final de la licencia
     */
    private void validarLicencia(LocalDate inicio, LocalDate fin)
            throws EntradaInvalidaException, MultiplesErroresException {
        ArrayList<String> errores = new ArrayList<String>();
        boolean nuevaLicenciaSolapaOtra = false;

        if (inicio == null)
            errores.add("Fecha de inicio no especificada.");
        if (fin == null)
            errores.add("Fecha de fin no especificada.");
        if (fin.equals(inicio))
            errores.add("Fecha de inicio coincide con fecha de fin.");
        if (fin.isBefore(inicio))
            errores.add("Fecha de fin precede fecha de inicio.");
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Licencia con fechas err�neas:", errores);

        for (int i = 0; i < licencias.size() && !nuevaLicenciaSolapaOtra; i++) {
            nuevaLicenciaSolapaOtra = ((licencias.get(i).fechaEstaSolapada(inicio) || licencias.get(i).fechaEstaSolapada(fin)))
                    && (!licencias.get(i).getInicio().isBefore(inicio) || licencias.get(i).getInicio().isAfter(fin));
            // &&(!recesosDocentes.get(i).getInicio().isBefore(inicio) || !recesosDocentes.get(i).getInicio().isAfter(fin))); iwalito
        }
        if (nuevaLicenciaSolapaOtra)
            throw new EntradaInvalidaException(
                    "Las fechas introducidas coinciden con una licencia ya registrada.");
    }

    /**
     * Valida licencia con inicio tomando en consideracion que no este vacia y
     * que no coincida con una licencia ya registrada
     *
     * @param inicio fecha de inicio de la licencia
     */
    private void validarLicencia(LocalDate inicio)
            throws EntradaInvalidaException {
        boolean nuevaLicenciaSolapaOtra = false;

        if (inicio == null)
            throw new EntradaInvalidaException("Fecha de inicio no especificada.");
        for (int i = 0; i < licencias.size() && !nuevaLicenciaSolapaOtra; i++) {
            nuevaLicenciaSolapaOtra = licencias.get(i).fechaEstaSolapada(inicio)
                    && !inicio.isAfter(licencias.get(i).getFin());
        }
        if (nuevaLicenciaSolapaOtra)
            throw new EntradaInvalidaException("La fecha introducida coincide con una licencia ya registrada.");
    }

    /**
     * A�ade una licencia con inicio y fin conocido
     *
     * @param inicio inicio de la licencia
     * @param fin    fin de la licencia
     * @throws EntradaInvalidaException
     * @throws MultiplesErroresException
     */
    public void annadirLicencia(LocalDate inicio, LocalDate fin)
            throws EntradaInvalidaException, MultiplesErroresException {
        validarLicencia(inicio, fin);
        Licencia nuevaLicencia = new Licencia(inicio, fin);
        licencias.add(nuevaLicencia);
        Collections.sort(licencias);
    }

    /**
     * A�ade una licencia con solo el inicio conocido
     *
     * @param inicio inicio de la licencia
     * @throws EntradaInvalidaException
     * @throws MultiplesErroresException
     */
    public void annadirLicencia(LocalDate inicio)
            throws EntradaInvalidaException, MultiplesErroresException {
        validarLicencia(inicio);
        Licencia nuevaLicencia = new Licencia(inicio);
        licencias.add(nuevaLicencia);
        Collections.sort(licencias);
    }

}
