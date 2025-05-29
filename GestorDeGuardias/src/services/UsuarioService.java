package services;

import model.Rol;
import model.Usuario;
import utils.dao.MainBaseDao;
import utils.dao.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final MainBaseDao baseDao;

    public UsuarioService(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertUsuario(String nombre, String email) {
        baseDao.spUpdate("sp_create_usuario(?, ?)", nombre, email);
    }

    // READ all
    public List<Usuario> getAllUsuarios() {
        return baseDao.spQuery("sp_read_usuario", new UsuarioMapper());
    }

    // READ by primary key
    public Usuario getUsuarioByNombre(String nombre) {
        return baseDao.spQuerySingleObject("sp_read_usuario_by_pk(?)", new UsuarioMapper(), nombre);
    }

    // UPDATE
    public void updateUsuario(int id, String nombre, String email) {
        baseDao.spUpdate("sp_update_usuario(?, ?, ?)", id, nombre, email);
    }

    // DELETE
    public void deleteUsuario(int id) {
        baseDao.spUpdate("sp_delete_usuario(?)", id);
    }

    // Internal Mapper
    private static class UsuarioMapper implements RowMapper<Usuario> {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Usuario(rs.getString("nombre"), rs.getString("email"), new Rol(rs.getString("rol")));
        }
    }
}
