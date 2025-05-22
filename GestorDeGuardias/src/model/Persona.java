package model;

import logica.excepciones.EntradaInvalidaException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Persona implements Comparable<Persona> {
    private Long id;
    private String ci;
    private String nombre;
    private String apellidos;
    private Character sexo;
    private Tipo_Persona tipoPersona;

    protected LocalDate ultimaGuardiaHecha;
    protected ArrayList<LocalDate> guardiasAsignadas;
    protected int cantGuardiasRecuperacion = 0;
    protected LocalDate fechaDeBaja;

    public Persona(String id, String nombre, String apellidos, char sexo, String tipo) {
        setCi(id);
        setNombre(nombre);
        setApellidos(apellidos);
        setSexo(sexo);
        setTipoPersona(new Tipo_Persona(tipo));
        guardiasAsignadas = new ArrayList<LocalDate>();
        ultimaGuardiaHecha = LocalDate.of(-999999999, 1, 1);
    }

    public Tipo_Persona getTipoPersona(){
        return tipoPersona;
    }
    private void setTipoPersona(Tipo_Persona tipoPersona) {
        this.tipoPersona = tipoPersona;
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

    public String getSexo() {
        if(sexo == 'f')
            return "Femenino";
        else
            return "Masculino";
    }

    private void setSexo(char sexo) {
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

    public int getCantGuardiasRecuperacion() {
        return cantGuardiasRecuperacion;
    }

    public void setCantGuardiasRecuperacion(int cant) {
        this.cantGuardiasRecuperacion += cant;
    }

    public LocalDate getFechaDeBaja() {
        return fechaDeBaja;
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

        if ((fechaDeBaja == null || fechaDeBaja.isAfter(fecha)) && !licenciaRelevanteEncontrada) {
            disponibilidad = Disponibilidad.DISPONIBLE;
        } else {
            disponibilidad = Disponibilidad.BAJA;
        }
        return disponibilidad;
    }

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

    public void annadirGuardiaAsignada(LocalDate fecha) {
        if (!guardiasAsignadas.contains(fecha)) ;
        this.guardiasAsignadas.add(fecha);
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

    public void actualizarCumplimiento(LocalDate fecha) throws EntradaInvalidaException {
        if (fecha == null)
            throw new EntradaInvalidaException("Fecha de la guardia no especificada.");
    }

    public int getCantGuardiasAsignadas() {
        int cant = 0;
        if (guardiasAsignadas.isEmpty())
            cant = 0;
        else {
            int i = this.guardiasAsignadas.size() - 1;
            LocalDate now = LocalDate.now();
            while (!this.guardiasAsignadas.get(i).isBefore(now)) {
                cant++;
                i--;
            }
        }
        return cant;
    }

}