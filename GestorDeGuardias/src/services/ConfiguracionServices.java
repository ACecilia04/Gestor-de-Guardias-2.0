package services;

import model.Configuracion;
import model.Horario;
import model.TipoPersona;
import utils.dao.MainBaseDao;
import utils.dao.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class ConfiguracionServices {

    private final MainBaseDao baseDao;

    public ConfiguracionServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertConfiguracion(Configuracion record){
        baseDao.spUpdate("sp_configuracion_create(?, ?, ?, ?, ?, ?)",
                record.getDiaSemana(),
                record.isDiaEsReceso(),
                record.getHorario(),
                record.getTipoPersona(),
                record.getSexo(),
                record.getCantPersonas()
        );
    }

    // READ all
    public List<Configuracion> getAllConfiguraciones() {
        return baseDao.spQuery("sp_configuracion_read", new ConfiguracionMapper());
    }

    // READ by primary key
    public Configuracion getConfiguracionByPk(LocalTime horaInicio, LocalTime horaFin, int diaSemana, boolean diaEsReceso) {
        Horario horario = ServicesLocator.getInstance().getHorarioServices().getHorarioByPk(horaInicio, horaFin);
        return baseDao.spQuerySingleObject("sp_configuracion_read_by_pk(?, ?, ?)", new ConfiguracionMapper(), horario, diaSemana, diaEsReceso);
    }

    public Configuracion getConfiguracionByPk(Long horario, int diaSemana, boolean diaEsReceso) {
        return baseDao.spQuerySingleObject("sp_configuracion_read_by_pk(?, ?, ?)", new ConfiguracionMapper(), horario, diaSemana, diaEsReceso);
    }

    // UPDATE
    public void updateConfiguracion(Configuracion record){
        baseDao.spUpdate("sp_configuracion_update(?, ?, ?, ?, ?, ?, ?)",
                record.getId(),
                record.getHorario(),
                record.getDiaSemana(),
                record.isDiaEsReceso(),
                record.getTipoPersona(),
                record.getSexo(),
                record.getCantPersonas()
        );
    }

    // DELETE
    public void deleteConfiguracion(Configuracion record) {
        baseDao.spUpdate("sp_configuracion_delete(?, ?, ?)",
                record.getHorario(),
                record.getDiaSemana(),
                record.isDiaEsReceso()
        );
    }

    // Internal Mapper
    private static class ConfiguracionMapper implements RowMapper<Configuracion> {
        @Override
        public Configuracion mapRow(ResultSet rs, int rowNum) throws SQLException {
            Horario h = new Horario();
            Configuracion config = new Configuracion();

            config.setDiaSemana(rs.getInt("dia_semana"));
            config.setDiaEsReceso(rs.getBoolean("dia_es_receso"));
            config.setTipoPersona(new TipoPersona(rs.getString("tipo_persona")));
            config.setSexo(rs.getString("sexo"));
            config.setCantPersonas(rs.getInt("cant_personas"));
            config.setBorrado(rs.getBoolean("borrado"));

            h.setId(rs.getLong("horario_id"));
            h.setInicio(rs.getTime("inicio").toLocalTime());
            h.setFin(rs.getTime("fin").toLocalTime());

            config.setHorario(h);

            return config;
        }
    }
}

