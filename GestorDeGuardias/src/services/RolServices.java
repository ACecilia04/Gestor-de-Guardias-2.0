package services;

import model.Rol;
import utils.dao.MainBaseDao;
import utils.dao.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RolServices {

    private final MainBaseDao baseDao;

    public RolServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertRol(String nombre) {
        baseDao.spUpdate("sp_rol_create(?)", nombre);
    }

    // READ all
    public List<Rol> getAllRoles() {
        return baseDao.spQuery("sp_rol_read", new RolMapper());
    }

    // READ by nombre
    public Rol getRolByNombre(String nombre) {
        return baseDao.spQuerySingleObject("sp_rol_read_by_nombre(?)", new RolMapper(), nombre);
    }

    // UPDATE
    public void updateRol(String nombre, String nuevoNombre) {
        baseDao.spUpdate("sp_rol_update(?, ?)", nombre, nuevoNombre);
    }

    // DELETE
    public void deleteRol(String nombre) {
        baseDao.spUpdate("sp_rol_delete(?)", nombre);
    }

    // Internal Mapper
    private static class RolMapper implements RowMapper<Rol> {
        @Override
        public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Rol(
                    rs.getString("nombre")
            );
        }
    }
}
