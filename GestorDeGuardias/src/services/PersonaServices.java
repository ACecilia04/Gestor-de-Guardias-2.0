package services;

import model.Persona;
import model.TipoPersona;
import utils.dao.MainBaseDao;
import utils.dao.mappers.IntegerMapper;
import utils.dao.mappers.RowMapper;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    // INSERT
    public void insertPersona(Persona record) throws MultiplesErroresException {
        validarPersona(record);
        baseDao.spUpdate("sp_persona_create(?, ?, ?, ?, ?)",
                record.getNombre(),
                record.getApellido(),
                record.getSexo().toLowerCase().substring(0, 1),
                record.getCarnet(),
                record.getTipo().getNombre()
        );
    }

    // READ
    public List<Persona> getAllPersonas() {
        return baseDao.spQuery("sp_persona_read", new PersonaMapper());
    }

    public Persona getPersonaByCi(String ci) {
        return baseDao.spQuerySingleObject("sp_persona_read_by_ci(?)", new PersonaMapper(), ci);
    }

    public Persona getPersonaById(Long id) {
        return baseDao.spQuerySingleObject("sp_persona_read_by_id(?)", new PersonaMapper(), id);
    }

    public List<Persona> getPersonasByTipo(TipoPersona tipoPersona) {
        return baseDao.spQuery("sp_persona_read_by_tipo(?)", new PersonaMapper(), tipoPersona.getNombre());
    }

    public int getPersonaCountByTipo(String tipoPersona){
        return baseDao.spQuerySingleObject("sp_persona_count_by_tipo(?)", new IntegerMapper("total"), tipoPersona);
    }

    // UPDATE
    public void updatePersona(Persona record) {
        baseDao.spUpdate("sp_persona_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                record.getId(),
                record.getNombre(),
                record.getApellido(),
                record.getSexo().toLowerCase().substring(0, 1),
                record.getCarnet(),
                record.getUltimaGuardiaHecha(),
                record.getGuardiasDeRecuperacion(),
                record.getBaja(),
                record.getReincorporacion(),
                record.getTipo().getNombre()
        );
    }

    /*
     * no estoy segura de si validar eso, pero como asi es como estaba en facultad, igual se puede editar
     */
    public List<Persona> getPersonasDeBaja(LocalDate fecha) throws EntradaInvalidaException {
        if (fecha == null) {
            throw new EntradaInvalidaException("Fecha no especificada.");
        }
        return baseDao.spQuery("sp_persona_read_by_baja(?)", new PersonaMapper(), fecha);
    }

    public void darBaja(String ci, LocalDate fechaBaja) throws MultiplesErroresException {
        validarBaja(ci, fechaBaja);
        baseDao.spUpdate("sp_persona_update_baja(?, ?)", ci, fechaBaja);
    }

    public void darFechaDeReincorporacion(String ci, LocalDate fechaReincorporacion){

    }

    public void darBajaConReincorporacion(String ci, LocalDate fechaBaja, LocalDate fechaReincorporacion) throws MultiplesErroresException, EntradaInvalidaException {
        darBaja(ci, fechaBaja);
        darFechaDeReincorporacion(ci, fechaReincorporacion);
    }


    // DELETE
    public void deletePersonaByCi(String ci) {
        baseDao.spUpdate("sp_persona_delete_by_ci(?)", ci);
    }

    public void deletePersonaById(String id) {
        baseDao.spQuery("sp_persona_delete_by_id(?)", new PersonaMapper(), id);
    }



    // Internal Mapper
    private static class PersonaMapper implements RowMapper<Persona> {
        @Override
        public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
            Persona p = new Persona();

            p.setId(rs.getLong("id"));
            p.setNombre(rs.getString("nombre"));
            p.setApellido(rs.getString("apellido"));
            p.setSexo(rs.getString("sexo"));
            p.setCarnet(rs.getString("carnet"));
            p.setTipo(rs.getString("tipo"));
            p.setUltimaGuardiaHecha(rs.getDate("ultima_guardia_hecha") == null ? null : rs.getDate("ultima_guardia_hecha").toLocalDate());
            p.setBaja(rs.getDate("baja") == null ? null : rs.getDate("baja").toLocalDate());
            p.setReincorporacion(rs.getDate("reincorporacion") == null ? null : rs.getDate("reincorporacion").toLocalDate());
            p.setGuardiasDeRecuperacion(rs.getInt("guardias_de_recuperacion"));
            p.setBorrado(!rs.getBoolean("borrado"));
            return p;
        }
    }


    // VALIDACIONES
    private void validarBaja(String ci, LocalDate fechaBaja) throws MultiplesErroresException {
        List<String> errores = new ArrayList<>();

        if (!stringEsValido(ci))
            errores.add("ci de identidad no especificado.");

        if (fechaBaja == null)
            errores.add("Fecha de baja no especificada.");

        if (!errores.isEmpty())
            throw new MultiplesErroresException("Baja con datos erróneos:", errores);
    }

    private void validarPersona(Persona record) throws MultiplesErroresException {
        List<String> errores = new ArrayList<>();

        String errorValidarCarneIdentidad = validarCarnet(record.getCarnet());
        if (stringEsValido(errorValidarCarneIdentidad)) {
            errores.add(errorValidarCarneIdentidad);
        }
        if (!stringEsValido(record.getNombre()))
            errores.add("Nombre no especificado.");
        if (!stringEsValido(record.getApellido()))
            errores.add("Apellido no especificado.");
        if (record.getSexo() == null)
            errores.add("Sexo no especificado.");

        if (record.getSexo() != null && record.getCarnet() != null) {
            int paridad = Integer.parseInt(record.getCarnet().substring(9, 10)) % 2;
            if (((paridad == 0 && record.getSexo().toLowerCase().charAt(0) != 'm') || (paridad != 0 && record.getSexo().toLowerCase().charAt(0) != 'f')))
                errores.add("Sexo seleccionado no coincide con la información del carnet de identidad.");
        }

        if (record.getCarnet() != null && getPersonaByCi(record.getCarnet()) != null) {
            errores.add("La persona ya existe.");
        }

        if (!errores.isEmpty())
            throw new MultiplesErroresException("Persona con datos erróneos:", errores);
    }

    private String validarCarnet(String ci){
        if (!stringEsValido(ci)) {
            return "Carnet de identidad no especificado.";
        }

        if (ci.length() != 11) {
            return "Carnet de identidad no válido: Longitud incorrecta.";
        }

        if (!stringSoloNumeros(ci)) {
            return "Carnet de identidad no válido: Caracteres no numéricos.";
        }

        int dia = Integer.parseInt(ci.substring(4, 6));
        int mes = Integer.parseInt(ci.substring(2, 4));
        int anno = Integer.parseInt(ci.substring(0, 2));

        if (!(mes > 0 && mes <= 12)) {
            return "Carnet de identidad no válido: Mes incorrecto.";
        }

        boolean diaValido = true;
        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
            if (dia > 31 || dia == 0) {
                diaValido = false;
            }
        } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            if (dia > 30 || dia == 0) {
                diaValido = false;
            }
        } else {
            if (anno % 4 != 0 && dia > 28) {
                diaValido = false;
            } else if (anno % 4 == 0 && dia > 29) {
                diaValido = false;
            }
        }
        if (!diaValido) {
            return  "Carnet de identidad no válido: Dia incorrecto.";
        }

        return null;
    }


}
