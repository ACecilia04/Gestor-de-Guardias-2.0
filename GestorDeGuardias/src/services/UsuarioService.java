package services;

import model.Rol;
import model.Usuario;
import utils.abstracts.MainBaseDao;
import utils.abstracts.RowMapper;

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
        baseDao.getJdbcTemplate().executeProcedure("sp_create_usuario(?, ?)", nombre, email);
    }

    // READ all
    public List<Usuario> getAllUsuarios() {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_usuario", new UsuarioMapper());
    }

    // READ by primary key
    public Usuario getUsuarioByNombre(String nombre) {
        return baseDao.getJdbcTemplate().executeProcedureWithResults("sp_read_usuario_by_pk(?)", new UsuarioMapper(), nombre)
                .stream().findFirst().orElse(null);
    }

    // UPDATE
    public void updateUsuario(int id, String nombre, String email) {
        baseDao.getJdbcTemplate().executeProcedure("sp_update_usuario(?, ?, ?)", id, nombre, email);
    }

    // DELETE
    public void deleteUsuario(int id) {
        baseDao.getJdbcTemplate().executeProcedure("sp_delete_usuario(?)", id);
    }

    // Internal Mapper
    private static class UsuarioMapper implements RowMapper<Usuario> {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Usuario(rs.getString("nombre"), rs.getString("email"), new Rol(rs.getString("rol")));
        }
    }
}
