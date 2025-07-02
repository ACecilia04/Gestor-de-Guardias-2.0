package services;

import model.Auditoria;
import model.Usuario;
import utils.dao.MainBaseDao;
import utils.dao.SqlServerCustomException;
import utils.dao.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AuditoriaServices {
    private final MainBaseDao baseDao;

    public AuditoriaServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertAuditoria(String servicio, String funcionalidad, String parametros) throws SqlServerCustomException {
        if (ServicesLocator.getInstance().getUsuarioActual() != null)
            baseDao.spUpdate("sp_auditoria_create(?, ?, ?, ?, ?)",
                    ServicesLocator.getInstance().getUsuarioActual().getId(),
                    LocalDateTime.now(),
                    servicio,
                    funcionalidad,
                    parametros
            );
    }

    // READ all
    public List<Auditoria> getTraza(Long idUsuario, LocalDateTime inicio, LocalDateTime fin, String servicio, String funcionalidad) {
        return baseDao.spQuery("sp_auditoria_read", new AuditoriaMapper(), idUsuario, inicio, fin, servicio, funcionalidad);
    }

    // Internal Mapper
    private static class AuditoriaMapper implements RowMapper<Auditoria> {
        private static final UsuarioServices usuarioServices = ServicesLocator.getInstance().getUsuarioServices();

        @Override
        public Auditoria mapRow(ResultSet rs, int rowNum) throws SQLException {
            Auditoria retVal = new Auditoria();

            retVal.setId(rs.getLong("id"));
            retVal.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            retVal.setServicio(rs.getString("servicio"));
            retVal.setFuncionalidad(rs.getString("funcionalidad"));
            retVal.setParametros(rs.getString("parametros"));

            Usuario usuario = usuarioServices.getUsuarioById(rs.getLong("id_usuario"));
            retVal.setUsuario(usuario);

            return retVal;
        }
    }

}
