package model;

import utils.exceptions.EntradaInvalidaException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Persona implements Comparable<Persona> {
    protected LocalDate ultimaGuardiaAsignada;
    private Long id;
    private String nombre;
    private String apellido;
    private String sexo;
    private String carnet;
    private int guardiasDeRecuperacion = 0;
    private LocalDate baja;
    private LocalDate reincorporacion;
    private TipoPersona tipo;
    private Boolean borrado;
    private ArrayList<LocalDate> guardiasAsignadas = new ArrayList<>();


    public Persona(String carnet, String nombre, String apellido, String sexo, TipoPersona tipo) {
        setCarnet(carnet);
        setNombre(nombre);
        setApellido(apellido);
        setSexo(sexo);
        setTipo(tipo);
    }

    public Persona(Long id, String nombre, String apellido, String sexo, String carnet, LocalDate ultimaGuardiaHecha, int cantGuardiasRecuperacion, LocalDate baja, LocalDate reincorporacion, String tipo) {
        setId(id);
        setCarnet(carnet);
        setNombre(nombre);
        setApellido(apellido);
        setSexo(sexo);
        setTipo(new TipoPersona(tipo));
        setBaja(baja);
        setReincorporacion(reincorporacion);
        guardiasDeRecuperacion = cantGuardiasRecuperacion;
        setUltimaGuardiaAsignada(ultimaGuardiaHecha);
    }

    public Persona() {

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
        return sexo != null && sexo.equals("f") ? "Femenino" :
                (sexo != null && sexo.equals("m") ? "Masculino" : sexo);
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public TipoPersona getTipo() {
        return tipo;
    }

    public void setTipo(TipoPersona tipo) {
        this.tipo = tipo;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String id) {
        this.carnet = id;
    }

    public LocalDate getUltimaGuardiaAsignada() {
        return ultimaGuardiaAsignada;
    }

    public void setUltimaGuardiaAsignada(LocalDate ultimaGuardia) {
//        if (ultimaGuardia == null)
//            throw new EntradaInvalidaException("Fecha de última guardia no especificada.");
//        if (this.ultimaGuardiaHecha != null) {
//            if (ultimaGuardia.isBefore(this.ultimaGuardiaHecha)) {
//                throw new EntradaInvalidaException("La fecha que desea ingresar precede a la fecha de la última guardia hecha por "
//                        + this.getNombre() + " " + this.getApellido() + ".");
//            }
//        }
        this.ultimaGuardiaAsignada = ultimaGuardia;
    }

    public int getDiasDesdeUltimaGuardiaAsignada() {
        return ultimaGuardiaAsignada == null ? 0 : (LocalDate.now().isBefore(ultimaGuardiaAsignada) ? 0 : Math.abs((int) ChronoUnit.DAYS.between(ultimaGuardiaAsignada, LocalDate.now())));
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

    public String getTipoPersonaAsString() {
        return tipo.toString();
    }

    public ArrayList<LocalDate> getGuardiasAsignadas() {
        return guardiasAsignadas;
    }

    public void setGuardiasAsignadas(ArrayList<LocalDate> guardiasAsignadas) {
        this.guardiasAsignadas = guardiasAsignadas;
    }

    public String getDisponibilidad(LocalDate fecha) {
        boolean disponible = true;

        if (this.baja != null) {
            if (reincorporacion == null) {
                disponible = fecha.isBefore(this.baja);
            } else
                disponible = fecha.isBefore(this.baja) || fecha.isAfter(reincorporacion);
        }
        return disponible ? "Disponible" : "Baja";
    }

    public Integer getCantGuardiasAsignadas() {
        int cant = 0;
        if (guardiasAsignadas != null && !guardiasAsignadas.isEmpty()) {
            int i = this.guardiasAsignadas.size() - 1;
            LocalDate now = LocalDate.now();
            while (!this.guardiasAsignadas.get(i).isBefore(now)) {
                cant++;
                i--;
            }
        }
        return cant;
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
        if (!guardiasAsignadas.contains(fecha)) {
            this.guardiasAsignadas.add(fecha);
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
                ", ultimaGuardiaHecha=" + ultimaGuardiaAsignada +
                ", guardiasDeRecuperacion=" + guardiasDeRecuperacion +
                ", baja=" + baja +
                ", reincorporacion=" + reincorporacion +
                ", tipoPersona=" + (tipo != null ? tipo.getNombre() : "N/A") +
                ", guardiasAsignadas=" + guardiasAsignadas +
                '}';
    }
}