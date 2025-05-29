package logica.principal;

import utils.exceptions.EntradaInvalidaException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public abstract class Persona implements Comparable<Persona> {
    protected String ci;
    protected String nombre;
    protected String apellidos;
    protected Sexo sexo;
    protected LocalDate ultimaGuardiaHecha;
    protected ArrayList<LocalDate> guardiasAsignadas;
    protected int cantGuardiasDebidas;
    protected LocalDate fechaDeBaja;
    protected ArrayList<? extends Licencia> licencias;   //las licencias estan ordenadas segun su inicio


    public Persona(String id, String nombre, String apellidos, Sexo sexo) {
        setCi(id);
        setNombre(nombre);
        setApellidos(apellidos);
        setSexo(sexo);
        cantGuardiasDebidas = 0;
        guardiasAsignadas = new ArrayList<LocalDate>();
        ultimaGuardiaHecha = LocalDate.of(-999999999, 1, 1);
    }

    public String getCi() {
        return ci;
    }

    private void setCi(String id) {
        this.ci = id;
    }

    public String getNombre() {
        return nombre;
    }

    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    private void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Sexo getSexo() {
        return sexo;
    }

    private void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getUltimaGuardiaHecha() {
        return ultimaGuardiaHecha;
    }

    public void setUltimaGuardiaHecha(LocalDate ultimaGuardia) throws EntradaInvalidaException {
        if (ultimaGuardia == null)
            throw new EntradaInvalidaException("Fecha de �ltima guardia no especificada.");
        if (ultimaGuardia.isBefore(this.ultimaGuardiaHecha)) {
            throw new EntradaInvalidaException("La fecha que desea ingresar precede a la fecha de la ultima guardia hecha por " + this.getNombre() + this.getApellidos() + ".");
        }
        this.ultimaGuardiaHecha = ultimaGuardia;
    }

    public int getDiasDesdeUltimaGuardiaHecha(LocalDate fecha) throws EntradaInvalidaException {
        if (fecha == null)
            throw new EntradaInvalidaException("Fecha no especificada.");
        return Math.abs((int) ChronoUnit.DAYS.between(ultimaGuardiaHecha, fecha));
    }

    public int getDiasDesdeUltimaGuardiaAsignada(LocalDate fecha) throws EntradaInvalidaException {
        LocalDate fechaInicio;

        if (fecha == null)
            throw new EntradaInvalidaException("Fecha no especificada.");

        if (guardiasAsignadas.isEmpty())
            fechaInicio = LocalDate.of(-999999999, 1, 1);
        else
            fechaInicio = guardiasAsignadas.get(guardiasAsignadas.size() - 1);
        return Math.abs((int) ChronoUnit.DAYS.between(fechaInicio, fecha));
    }

    public int getCantGuardiasDebidas() {
        return cantGuardiasDebidas;
    }

    public LocalDate getFechaDeBaja() {
        return fechaDeBaja;
    }

    public ArrayList<? extends Licencia> getLicencias() {
        return licencias;
    }

    /**
     * @param persona persona a comparar
     * @return comparacion entre carnets de identidad
     */
    @Override
    public boolean equals(Object persona) {
        return ((Persona) persona).getCi().equals(getCi());
    }

    /**
     * @return comparaci�n entre apellidos y si estos son iguales, compara los nombres
     */
    @Override
    public int compareTo(Persona persona) {

        return getApellidos().equalsIgnoreCase(persona.getApellidos()) ? getNombre().compareTo(persona.getNombre()) : getApellidos().compareTo(persona.getApellidos());
    }

    /**
     * @param fecha fecha en la que se busca la disponibilidad
     * @return enum Disponibilidad
     */
    public Disponibilidad getDisponibilidadParaFecha(LocalDate fecha) {
        //fecha seria la fecha actual o un dia x
        Disponibilidad disponibilidad;
        boolean licenciaRelevanteEncontrada = false;

        //encontrar primero la licencia relevante (la que incluye la fecha)
        for (int i = 0; i < getLicencias().size() && !licenciaRelevanteEncontrada; i++) {
            LocalDate fInicio = getLicencias().get(i).getInicio();
            LocalDate fFinal = getLicencias().get(i).getFin();
            licenciaRelevanteEncontrada = (!fecha.isBefore(fInicio)) && (fFinal == null || !fecha.isAfter(fFinal));
        }

        if ((fechaDeBaja == null || fechaDeBaja.isAfter(fecha)) && !licenciaRelevanteEncontrada) {
            disponibilidad = Disponibilidad.DISPONIBLE;
        } else if (fechaDeBaja != null && fechaDeBaja.equals(fecha)) {
            disponibilidad = Disponibilidad.BAJA;
        } else {
            disponibilidad = Disponibilidad.LICENCIA;
        }
        return disponibilidad;
    }

    public abstract boolean estaDisponibleEnRecesoDocente(LocalDate fecha);

//	public abstract boolean getDisponibilidadParaGuardia( LocalDate dia, HorarioGuardia horario);

    /**
     * Si la persona no tiene una fecha de baja, le da baja a esta en
     * la fecha dada mientras la fecha sea la actual o una futura
     *
     * @param fecha
     */
    public void darBaja(LocalDate fecha) {
        if (!fecha.isBefore(LocalDate.now()) && fechaDeBaja == null) {
            fechaDeBaja = fecha;
        }
    }

    public void deshacerBaja() throws EntradaInvalidaException {
        if (fechaDeBaja == null)
            throw new EntradaInvalidaException("Esta persona no tiene fecha de baja registrada.");
        else
            fechaDeBaja = null;
    }

    public void eliminarLicencia(Licencia licencia) throws EntradaInvalidaException {
        if (licencia == null)
            throw new EntradaInvalidaException("Licencia a eliminar no especificada.");
        if (buscarLicencia(licencia) != null)
            getLicencias().remove(licencia);
        else {
            throw new EntradaInvalidaException("Licencia a eliminar no existe.");
        }
    }

    public void eliminarLicencia(LocalDate inicioLicencia) throws EntradaInvalidaException {
        if (inicioLicencia == null)
            throw new EntradaInvalidaException("Fecha de inicio de la licencia a eliminar no especificada.");
        Licencia indicada = buscarLicencia(inicioLicencia);

        if (indicada != null)
            getLicencias().remove(indicada);
        else {
            throw new EntradaInvalidaException("Licencia a eliminar no existe.");
        }
    }

    public Licencia buscarLicencia(Licencia licencia) throws EntradaInvalidaException {
        Licencia indicada = null;
        boolean encontrada = false;

        if (licencia == null)
            throw new EntradaInvalidaException("Licencia buscada no especificada.");
        for (int i = 0; i < getLicencias().size() && !encontrada; i++) {
            if (getLicencias().get(i).equals(licencia)) {
                indicada = getLicencias().get(i);
                encontrada = true;
            }
        }
        return indicada;
    }

    public Licencia buscarLicencia(LocalDate inicio) throws EntradaInvalidaException {
        Licencia indicada = null;
        boolean encontrada = false;

        if (inicio == null)
            throw new EntradaInvalidaException("Inicio de la licencia buscada no especificada.");
        for (int i = 0; i < getLicencias().size() && !encontrada; i++) {
            if (getLicencias().get(i).getInicio().equals(inicio)) {
                indicada = getLicencias().get(i);
                encontrada = true;
            }
        }
        return indicada;
    }

    public void annadirGuardiaAsignada(LocalDate fecha) {
        if (!guardiasAsignadas.contains(fecha)) {
            this.guardiasAsignadas.add(fecha);
            if (cantGuardiasDebidas > 0)
                cantGuardiasDebidas--;
        }
    }

    public LocalDate buscarGuardiaAsignada(LocalDate fecha) throws EntradaInvalidaException {
        LocalDate indicado = null;
        boolean encontrado = false;

        if (fecha == null)
            throw new EntradaInvalidaException("Guardia buscada no especificada.");
        for (int i = 0; i < guardiasAsignadas.size() && !encontrado; i++) {
            if (guardiasAsignadas.get(i).equals(fecha)) {
                indicado = guardiasAsignadas.get(i);
                encontrado = true;
            }
        }
        return indicado;
    }

    public void eliminarGuardiaAsignada(LocalDate fecha) throws EntradaInvalidaException {
        LocalDate indicado = buscarGuardiaAsignada(fecha);
        if (indicado != null)
            guardiasAsignadas.remove(indicado);
    }

    public void actualizarCumplimiento(LocalDate fecha, boolean cumplimiento) throws EntradaInvalidaException {
        if (fecha == null)
            throw new EntradaInvalidaException("Fecha de la guardia a actualizar no especificada. ");
        if (!cumplimiento)
            this.cantGuardiasDebidas++;
        else {
            if (fecha.isAfter(ultimaGuardiaHecha))
                ultimaGuardiaHecha = fecha;
        }
        guardiasAsignadas.remove(fecha);
    }

    public int getCantGuardiasAsignadas() {
        int cant = 0;
        if (guardiasAsignadas.isEmpty())
            cant = 0;
        else {
            int i = this.guardiasAsignadas.size() - 1;
            LocalDate now = LocalDate.now();
            while (i >= 0)
                if (!this.guardiasAsignadas.get(i).isBefore(now))
                    cant++;
            i--;

        }
        return cant;
    }
}