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
    public void insertUsuario(Usuario record) {
        baseDao.spUpdate("sp_usuario_create(?, ?, ?)",
                record.getNombre(),
                record.getContrasenna(),
                record.getRol().getNombre()
        );
    }

    // READ all
    public List<Usuario> getAllUsuarios() {
        return baseDao.spQuery("sp_usuario_read", new UsuarioMapper());
    }

    // READ by primary key
    public Usuario getUsuarioByNombre(Long id) {
        return baseDao.spQuerySingleObject("sp_usuario_read_by_id(?)", new UsuarioMapper(), id);
    }

    // UPDATE
    public void updateUsuario(Usuario record) {
        baseDao.spUpdate("sp_usuario_update(?, ?, ?, ?)",
                record.getId(),
                record.getNombre(),
                record.getContrasenna(),
                record.getRol().getNombre()
        );
    }

    // DELETE
    public void deleteUsuario(Long id) {
        baseDao.spUpdate("sp_usuario_delete(?)", id);
    }

    // Internal Mapper
    private static class UsuarioMapper implements RowMapper<Usuario> {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Usuario(
                    rs.getString("nombre"),
                    rs.getString("contrasenna"),
                    new Rol(rs.getString("rol")));
        }
    }
}
