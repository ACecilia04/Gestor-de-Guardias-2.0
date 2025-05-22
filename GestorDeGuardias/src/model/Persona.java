package model;

import logica.excepciones.EntradaInvalidaException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Persona implements Comparable<Persona> {
    protected LocalDate ultimaGuardiaHecha;
    private Long id;
    private String nombre;
    private String apellido;
    private Character sexo;
    private String carnet;
    private int guardiasDeRecuperacion = 0;
    private LocalDate baja;
    private LocalDate reincorporacion;
    private TipoPersona tipoPersona;
    private ArrayList<LocalDate> guardiasAsignadas;


    public Persona(String id, String nombre, String apellido, char sexo, String tipo) {
        setCarnet(id);
        setNombre(nombre);
        setApellido(apellido);
        setSexo(sexo);
        setTipoPersona(new TipoPersona(tipo));
        guardiasAsignadas = new ArrayList<LocalDate>();
        ultimaGuardiaHecha = LocalDate.of(-999999999, 1, 1);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getSexo() {
        return (sexo == 'f') ? "Femenino" : "Masculino";
    }

    public TipoPersona getTipo() {
        return tipoPersona;
    }

    public String getCarnet() {
        return carnet;
    }

    public LocalDate getUltimaGuardiaHecha() {
        return ultimaGuardiaHecha;
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
        fechaInicio = guardiasAsignadas.isEmpty() ? LocalDate.of(-999999999, 1, 1) : guardiasAsignadas.get(guardiasAsignadas.size() - 1);
        return Math.abs((int) ChronoUnit.DAYS.between(fechaInicio, fecha));
    }

    public int getGuardiasDeRecuperacion() {
        return guardiasDeRecuperacion;
    }

    public LocalDate getBaja() {
        return baja;
    }

    public LocalDate getReincorporacion() {
        return reincorporacion;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public ArrayList<LocalDate> getGuardiasAsignadas() {
        return guardiasAsignadas;
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

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    private void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public void setCarnet(String id) {
        this.carnet = id;
    }

    public void setUltimaGuardiaHecha(LocalDate ultimaGuardia) throws EntradaInvalidaException {
        if (ultimaGuardia == null)
            throw new EntradaInvalidaException("Fecha de última guardia no especificada.");
        if (ultimaGuardia.isBefore(this.ultimaGuardiaHecha)) {
            throw new EntradaInvalidaException("La fecha que desea ingresar precede a la fecha de la última guardia hecha por "
                    + this.getNombre() + " " + this.getApellido() + ".");
        }
        this.ultimaGuardiaHecha = ultimaGuardia;
    }

    public void setGuardiasDeRecuperacion(int cant) {
        this.guardiasDeRecuperacion += cant;
    }

    public void setBaja(LocalDate fecha) {
        this.baja = fecha;
    }

    public void setReincorporacion(LocalDate reincorporacion) {
        this.reincorporacion = reincorporacion;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public void setGuardiasAsignadas(ArrayList<LocalDate> guardiasAsignadas) {
        this.guardiasAsignadas = guardiasAsignadas;
    }
    /**
     * @param persona persona a comparar
     * @return comparacion entre carnets de identidad
     */
    @Override
    public boolean equals(Object persona) {
        return ((Persona) persona).getCarnet().equals(getCarnet());
    }

    /**
     * @return comparaci�n entre apellido y si estos son iguales, compara los nombres
     */
    @Override
    public int compareTo(Persona persona) {

        return getApellido().equalsIgnoreCase(persona.getApellido()) ? getNombre().compareTo(persona.getNombre()) : getApellido().compareTo(persona.getApellido());
    }

//    /**
//     * @param fecha fecha en la que se busca la disponibilidad
//     * @return enum Disponibilidad
//     */
//    public Disponibilidad getDisponibilidadParaFecha(LocalDate fecha) {
//        //fecha seria la fecha actual o un dia x
//        Disponibilidad disponibilidad;
//        boolean licenciaRelevanteEncontrada = false;
//
//        if ((baja == null || baja.isAfter(fecha)) && !licenciaRelevanteEncontrada) {
//            disponibilidad = Disponibilidad.DISPONIBLE;
//        } else {
//            disponibilidad = Disponibilidad.BAJA;
//        }
//        return disponibilidad;
//    }

    public void darBaja(LocalDate fecha) {
        if (!fecha.isBefore(LocalDate.now()) && baja == null) {
            baja = fecha;
        }
    }

    public void deshacerBaja() throws EntradaInvalidaException {
        if (baja == null)
            throw new EntradaInvalidaException("Esta persona no tiene fecha de baja registrada.");
        else
            baja = null;
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

}