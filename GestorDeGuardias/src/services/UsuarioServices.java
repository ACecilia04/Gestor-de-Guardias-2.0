package services;

import model.Rol;
import model.Usuario;
import utils.dao.MainBaseDao;
import utils.dao.SqlServerCustomException;
import utils.dao.mappers.RowMapper;
import utils.exceptions.MultiplesErroresException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.Utilitarios.stringEsValido;

public class UsuarioServices {
    private final MainBaseDao baseDao;

    public UsuarioServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertUsuario(Usuario record) throws MultiplesErroresException, SqlServerCustomException {
        validarUsuario(record);

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
    public Usuario getUsuarioById(Long id) {
        return baseDao.spQuerySingleObject("sp_usuario_read_by_id(?)", new UsuarioMapper(), id);
    }

    // READ by nombre
    public Usuario getUsuarioByNombre(String nombre) {
        return baseDao.spQuerySingleObject("sp_usuario_read_by_nombre(?)", new UsuarioMapper(), nombre);
    }

    // UPDATE
    public void updateUsuario(Usuario record) throws SqlServerCustomException, MultiplesErroresException {
        validarUsuario(record);
        baseDao.spUpdate("sp_usuario_update(?, ?, ?, ?)",
                record.getId(),
                record.getNombre(),
                record.getContrasenna(),
                record.getRol().getNombre()
        );
    }

    // DELETE
    public void deleteUsuario(Long id) throws SqlServerCustomException {
        baseDao.spUpdate("sp_usuario_delete(?)", id);
    }

    // Internal Mapper
    private static class UsuarioMapper implements RowMapper<Usuario> {
        private static RolServices rolServices = ServicesLocator.getInstance().getRolServices();

        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario retVal = new Usuario();

            retVal.setId(rs.getLong("id"));
            retVal.setNombre(rs.getString("nombre"));
            retVal.setContrasenna(rs.getString("contrasenna"));

            Rol rol = rolServices.getRol(rs.getString("rol"));
            retVal.setRol(rol);

            return retVal;
        }
    }

    // ==============   VALIDACIONES   ==========================================
    private void validarUsuario(Usuario record) throws MultiplesErroresException {
        List<String> errores = new ArrayList<>();

        if (!stringEsValido(record.getNombre()))
            errores.add("Nombre no especificado.");
        if (!stringEsValido(record.getContrasenna()))
            errores.add("Contraseña no especificada.");
        if (record.getRol() == null)
            errores.add("Rol no especificado.");

        if (!errores.isEmpty())
            throw new MultiplesErroresException("Usuario con datos erróneos:", errores);
    }

}
