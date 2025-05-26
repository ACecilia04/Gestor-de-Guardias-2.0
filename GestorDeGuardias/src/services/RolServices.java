package services;

import model.Rol;
import utils.abstracts.MainBaseDao;
import utils.abstracts.RowMapper;

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
        baseDao.getJdbcTemplate().executeProcedure("sp_create_rol(?)", nombre);
    }

    // READ all
    public List<Rol> getAllRoles() {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_rol", new RolMapper());
    }

    // READ by nombre
    public Rol getRolByNombre(String nombre) {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_rol_by_nombre(?)", new RolMapper(), nombre)
                .stream().findFirst().orElse(null);
    }

    // UPDATE
    public void updateRol(String nombre, String nuevoNombre) {
        baseDao.getJdbcTemplate().executeProcedure("sp_update_rol(?, ?)", nombre, nuevoNombre);
    }

    // DELETE
    public void deleteRol(String nombre) {
        baseDao.getJdbcTemplate().executeProcedure("sp_delete_rol(?)", nombre);
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
