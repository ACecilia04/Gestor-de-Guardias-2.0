package services;

import model.TipoPersona;
import utils.dao.MainBaseDao;
import utils.dao.SqlServerCustomException;
import utils.dao.mappers.RowMapper;
import utils.exceptions.MultiplesErroresException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.Utilitarios.stringEsValido;

public class TipoPersonaServices {

    private final MainBaseDao baseDao;
    private static final AuditoriaServices auditoriaServices = ServicesLocator.getInstance().getAuditoriaServices();

    public TipoPersonaServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertTipoPersona(String nombre) throws MultiplesErroresException, SqlServerCustomException {
        validarTipoPersona(nombre);

        baseDao.spUpdate("sp_tipo_persona_create(?)", nombre);
        auditoriaServices.insertAuditoria("Tipo de persona", "Insertar tipo", "Tipo = " + nombre);
    }

    // READ all
    public List<TipoPersona> getAllTiposPersona() {
        return baseDao.spQuery("sp_tipo_persona_read", new TipoPersonaMapper());
    }

    // READ by nombre
    public TipoPersona getTipoPersona(String nombre) {
        return baseDao.spQuerySingleObject("sp_tipo_persona_read_by_nombre(?)", new TipoPersonaMapper(), nombre);
    }

    // UPDATE
    public void updateTipoPersona(String nombre, String nuevoNombre) throws SqlServerCustomException {
        baseDao.spUpdate("sp_tipo_persona_update(?, ?)", nombre, nuevoNombre);
        auditoriaServices.insertAuditoria("Tipo de persona", "Modificar tipo", String.format("Tipo = %1$s, Nuevo tipo = %2$s", nombre, nuevoNombre));
    }

    // DELETE
    public void deleteTipoPersona(String nombre) throws SqlServerCustomException {
        baseDao.spUpdate("sp_tipo_persona_delete(?)", nombre);
        auditoriaServices.insertAuditoria("Tipo de persona", "Eliminar tipo", "Tipo = " + nombre);
    }

    // ==============   VALIDACIONES   ==========================================
    private void validarTipoPersona(String nombre) throws MultiplesErroresException {
        List<String> errores = new ArrayList<>();

        if (!stringEsValido(nombre))
            errores.add("Nombre no especificado.");

        if (!errores.isEmpty())
            throw new MultiplesErroresException("Tipo de persona con datos erróneos:", errores);
    }

    // Internal Mapper
    private static class TipoPersonaMapper implements RowMapper<TipoPersona> {
        @Override
        public TipoPersona mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TipoPersona(
                    rs.getString("nombre")
            );
        }
    }
}