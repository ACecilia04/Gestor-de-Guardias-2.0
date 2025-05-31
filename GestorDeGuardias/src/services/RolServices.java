package services;

import model.Rol;
import utils.dao.MainBaseDao;
import utils.dao.mappers.IntegerMapper;
import utils.dao.mappers.RowMapper;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.Utilitarios.stringEsValido;

public class RolServices {

    private final MainBaseDao baseDao;

    public RolServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertRol(String nombre) throws EntradaInvalidaException, MultiplesErroresException {
        validarRol(nombre);

        if (getRol(nombre) != null){
            throw new EntradaInvalidaException("Rol existente");
        }

        baseDao.spUpdate("sp_rol_create(?)", nombre);
    }

    // READ all
    public List<Rol> getAllRoles() {
        return baseDao.spQuery("sp_rol_read", new RolMapper());
    }

    // READ by nombre
    public Rol getRol(String nombre) {
        return baseDao.spQuerySingleObject("sp_rol_read_by_nombre(?)", new RolMapper(), nombre);
    }

    // UPDATE
    public void updateRol(String nombre, String nuevoNombre) {
        baseDao.spUpdate("sp_rol_update(?, ?)", nombre, nuevoNombre);
    }

    // DELETE
    public void deleteRol(String nombre) throws EntradaInvalidaException {
        if (getRol(nombre) == null){
            throw new EntradaInvalidaException("Rol inexistente");
        }

        if (rolExists(nombre))
            throw new EntradaInvalidaException("El rol no se puede borrar porque está en uso");

        baseDao.spUpdate("sp_rol_delete(?)", nombre);
    }

    public boolean rolExists(String nombre) {
        return baseDao.spQuerySingleObject("sp_rol_check_existence(?)", new IntegerMapper("Total"), nombre) > 0;
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


    // ==============   VALIDACIONES   ==========================================
    private void validarRol(String nombre) throws MultiplesErroresException {
        List<String> errores = new ArrayList<>();

        if (!stringEsValido(nombre))
            errores.add("Nombre no especificado.");

        if (!errores.isEmpty())
            throw new MultiplesErroresException("Rol con datos erróneos:", errores);
    }
}
