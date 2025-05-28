package services;

import model.TipoPersona;
import utils.abstracts.MainBaseDao;
import utils.abstracts.mappers.RowMapper;

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
        baseDao.spUpdate("sp_create_tipo_persona(?)", nombre);
    }

    // READ all
    public List<TipoPersona> getAllTiposPersona() {
        return baseDao.spQuery("sp_read_tipo_persona", new TipoPersonaMapper());
    }

    // READ by nombre
    public TipoPersona getTipoPersonaByNombre(String nombre) {
        return baseDao.spQuerySingleObject("sp_read_tipo_persona_by_nombre(?)", new TipoPersonaMapper(), nombre);
    }

    // UPDATE
    public void updateTipoPersona(String nombre, String nuevoNombre) {
        baseDao.spUpdate("sp_update_tipo_persona(?, ?)", nombre, nuevoNombre);
    }

    // DELETE
    public void deleteTipoPersona(String nombre) {
        baseDao.spUpdate("sp_delete_tipo_persona(?)", nombre);
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