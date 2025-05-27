package services;

import model.Persona;
import model.TipoPersona;
import utils.abstracts.MainBaseDao;
import utils.abstracts.mappers.IntegerMapper;
import utils.abstracts.mappers.RowMapper;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static utils.Utilitarios.stringEsValido;
import static utils.Utilitarios.stringSoloNumeros;

public class PersonaServices {

    private final MainBaseDao baseDao;

    public PersonaServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertPersona(String nombre, String apellido, char sexo, String carnet,
                              LocalDate ultimaGuardiaHecha, int guardiasDeRecuperacion,
                              LocalDate baja, LocalDate reincorporacion, String tipo, boolean activo) {
        baseDao.getJdbcTemplate().executeProcedure("sp_create_persona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                nombre, apellido, String.valueOf(sexo), carnet, ultimaGuardiaHecha, guardiasDeRecuperacion,
                baja, reincorporacion, tipo, activo);
    }
    //TODO: manejar validacion de tipo

    public void insertPersona(String nombre, String apellido, char sexo, String carnet,
                              String tipo) {
        baseDao.getJdbcTemplate().executeProcedure("sp_create_persona(?, ?, ?, ?, ?)",
                nombre, apellido, String.valueOf(sexo), carnet, tipo);
    }

    // READ
    public ArrayList<Persona> getAllPersonas() {
        return (ArrayList<Persona>) baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_persona", new PersonaMapper());
    }

    public Persona getPersonaByCi(String ci) {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_persona_by_ci(?)", new PersonaMapper(), ci)
                .stream().findFirst().orElse(null);
    }

    public Persona getPersonaById(Long id) {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_persona_by_id(?)", new PersonaMapper(), id)
                .stream().findFirst().orElse(null);
    }

    public ArrayList<Persona> getPersonaByTipo(TipoPersona tipoPersona) {
        return (ArrayList<Persona>) baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_persona_by_tipo(?)", new PersonaMapper(), tipoPersona.getNombre());
    }
//changed
    public int getPersonaCountByTipo(TipoPersona tipoPersona){
        return baseDao.spQuerySingleObject("count_personas(?)", new IntegerMapper("total"),tipoPersona.getNombre());
    }

    /*
     * no estoy segura de si validar eso pero como asi es como estaba en facultad, igual se puede editar
     */
    public List<Persona> getPersonasDeBaja(LocalDate fecha) throws EntradaInvalidaException {
        if (fecha == null) {
            throw new EntradaInvalidaException("Fecha no especificada.");
        }

        return baseDao.getJdbcTemplate()
                .executeProcedureWithResults("sp_read_persona_by_baja(?)", new PersonaMapper(), fecha);
    }

    // UPDATE
    public void updatePersona(long id, String nombre, String apellido, Character sexo, String carnet,
                              LocalDate ultimaGuardiaHecha, int guardiasDeRecuperacion, LocalDate baja,
                              LocalDate reincorporacion, String tipo, Boolean activo) {
        baseDao.getJdbcTemplate().executeProcedure("sp_update_persona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id, nombre, apellido, String.valueOf(sexo), carnet, ultimaGuardiaHecha,
                guardiasDeRecuperacion, baja, reincorporacion, tipo, activo);
    }

    // DELETE
    public void deletePersonaByCi(String ci) {
        baseDao.getJdbcTemplate().executeProcedure("sp_delete_persona_by_ci(?)", ci);
    }

    public void deletePersonaById(String id) {
        baseDao.getJdbcTemplate().executeProcedure("sp_delete_persona_by_id(?)", id);
    }

    private ArrayList<String> validarPersona(String ci, String nombre, String apellidos, Character sexo) {
        ArrayList<String> errores = new ArrayList<String>();
        boolean carneIdentidadValido = true;
        if (!stringEsValido(ci)) {
            errores.add("Carnet de identidad no especificado.");
            carneIdentidadValido = false;
        } else {
            if (ci.length() != 11) {
                errores.add("Carnet de identidad no válido: Longitud incorrecta.");
                carneIdentidadValido = false;
            } else {
                if (!stringSoloNumeros(ci)) {
                    errores.add("Carnet de identidad no válido: Caracteres no numéricos.");
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
                            errores.add("Carnet de identidad no válido: Dia incorrecto.");
                            carneIdentidadValido = false;
                        }
                    } else {
                        errores.add("Carnet de identidad no válido: Mes incorrecto.");
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
            errores.add("Sexo seleccionado no coincide con la información del carnet de identidad.");

        return errores;
    }

    public void darBaja(String ci, LocalDate fechaBaja) throws MultiplesErroresException, EntradaInvalidaException {
        ArrayList<String> errores = validarBaja(ci, fechaBaja);
        if (!errores.isEmpty())
            throw new MultiplesErroresException("Baja con datos erróneos:", errores);

        baseDao.getJdbcTemplate().executeProcedure("sp_dar_baja(?,?)", ci, fechaBaja);
    }
    public void darBajaConReincorporacion(String ci, LocalDate fechaBaja, LocalDate fechaReincorporacion) throws MultiplesErroresException, EntradaInvalidaException {
        darBaja(ci,fechaBaja);


    }

    public void darFechaDeReincorporacion(String ci, LocalDate fechaReincorporacion){}

    private ArrayList<String> validarBaja(String ci, LocalDate fechaBaja) {
        ArrayList<String> errores = new ArrayList<String>();
        if (!stringEsValido(ci))
            errores.add("ci de identidad no especificado.");
        if (fechaBaja == null)
            errores.add("Fecha de baja no especificada.");

        return errores;
    }

    // Internal Mapper
    private static class PersonaMapper implements RowMapper<Persona> {
        @Override
        public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Persona(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("sexo").charAt(0),
                    rs.getString("carnet"),
                    rs.getDate("ultima_guardia_hecha") != null ? rs.getDate("ultima_guardia_hecha").toLocalDate() : null,
                    rs.getInt("guardias_de_recuperacion"),
                    rs.getDate("baja") != null ? rs.getDate("baja").toLocalDate() : null,
                    rs.getDate("reincorporacion") != null ? rs.getDate("reincorporacion").toLocalDate() : null,
                    rs.getString("tipo"),
                    rs.getBoolean("activo")
            );
        }
    }


}
