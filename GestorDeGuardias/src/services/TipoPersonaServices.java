package services;

import model.TipoPersona;
import utils.dao.MainBaseDao;
import utils.dao.mappers.RowMapper;

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
    public void deleteTipoPersona(String nombre) {
        baseDao.spUpdate("sp_tipo_persona_delete(?)", nombre);
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