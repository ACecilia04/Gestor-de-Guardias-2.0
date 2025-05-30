package services;

import model.TipoPersona;
import utils.dao.MainBaseDao;
import utils.dao.mappers.IntegerMapper;
import utils.dao.mappers.RowMapper;
import utils.exceptions.EntradaInvalidaException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TipoPersonaServices {

    private final MainBaseDao baseDao;

    public TipoPersonaServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertTipoPersona(String nombre) {
        baseDao.spUpdate("sp_tipo_persona_create(?)", nombre);
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
    public void updateTipoPersona(String nombre, String nuevoNombre) {
        baseDao.spUpdate("sp_tipo_persona_update(?, ?)", nombre, nuevoNombre);
    }

    // DELETE
    public void deleteTipoPersona(String nombre) throws EntradaInvalidaException {
        if (tipoPersonaExists(nombre))
            throw new EntradaInvalidaException("El tipo de persona no se puede borrar porque está en uso");
        baseDao.spUpdate("sp_tipo_persona_delete(?)", nombre);
    }

    public boolean tipoPersonaExists(String nombre) {
        return baseDao.spQuerySingleObject("sp_tipo_persona_check_existence(?)", new IntegerMapper("Total"), nombre) > 0;
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