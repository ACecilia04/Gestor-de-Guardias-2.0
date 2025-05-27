package model;

import utils.exceptions.EntradaInvalidaException;

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
    private Boolean activo;
    private ArrayList<LocalDate> guardiasAsignadas;


    public Persona(String id, String nombre, String apellido, char sexo, String tipo) {
        setCarnet(id);
        setNombre(nombre);
        setApellido(apellido);
        setSexo(sexo);
        setTipoPersona(new TipoPersona(tipo));
        guardiasAsignadas = new ArrayList<>();
        ultimaGuardiaHecha = LocalDate.MIN;
        activo = true;
    }

    public Persona(Long id, String nombre, String apellido, char sexo, String carnet, LocalDate ultimaGuardiaHecha, int cantGuardiasRecuperacion, LocalDate baja, LocalDate reincorporacion, String tipo, Boolean activo) throws EntradaInvalidaException {
        setId(id);
        setCarnet(carnet);
        setNombre(nombre);
        setApellido(apellido);
        setSexo(sexo);
        setTipoPersona(new TipoPersona(tipo));
        guardiasAsignadas = new ArrayList<>();
        guardiasDeRecuperacion = cantGuardiasRecuperacion;
        setUltimaGuardiaHecha(ultimaGuardiaHecha);
        setActivo(activo);
    }

    // Getters
    public Long getId() {
        return id;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return (sexo == 'f') ? "Femenino" : "Masculino";
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public TipoPersona getTipo() {
        return tipoPersona;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String id) {
        this.carnet = id;
    }

    public LocalDate getUltimaGuardiaHecha() {
        return ultimaGuardiaHecha;
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

    public void setGuardiasDeRecuperacion(int cant) {
        this.guardiasDeRecuperacion += cant;
    }

    public LocalDate getBaja() {
        return baja;
    }

    public void setBaja(LocalDate fecha) {
        this.baja = fecha;
    }

    public LocalDate getReincorporacion() {
        return reincorporacion;
    }

    public void setReincorporacion(LocalDate reincorporacion) {
        this.reincorporacion = reincorporacion;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getTipoPersonaAsString() {
        return tipoPersona.toString();
    }

    public ArrayList<LocalDate> getGuardiasAsignadas() {
        return guardiasAsignadas;
    }

    public void setGuardiasAsignadas(ArrayList<LocalDate> guardiasAsignadas) {
        this.guardiasAsignadas = guardiasAsignadas;
    }

    public String getDisponibilidad(LocalDate fecha){
        boolean disponible = true;

        if(this.baja != null) {
            if (reincorporacion == null) {
                disponible = fecha.isBefore(this.baja);
            } else
                disponible =  fecha.isBefore(this.baja) || fecha.isAfter(reincorporacion);
        }
         return disponible ? "Disponible" : "Baja";
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

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
     * @return comparación entre apellido y si estos son iguales, compara los nombres
     */
    @Override
    public int compareTo(Persona persona) {

        return getApellido().equalsIgnoreCase(persona.getApellido()) ? getNombre().compareTo(persona.getNombre()) : getApellido().compareTo(persona.getApellido());
    }

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

    @Override
    public String toString() {
        return "Persona {" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", sexo=" + sexo +
                ", carnet='" + carnet + '\'' +
                ", ultimaGuardiaHecha=" + ultimaGuardiaHecha +
                ", guardiasDeRecuperacion=" + guardiasDeRecuperacion +
                ", baja=" + baja +
                ", reincorporacion=" + reincorporacion +
                ", tipoPersona=" + (tipoPersona != null ? tipoPersona.getNombre() : "N/A") +
                ", activo=" + activo +
                ", guardiasAsignadas=" + guardiasAsignadas +
                '}';
    }

}