package services;

import model.Configuracion;
import model.Horario;
import model.TipoPersona;
import utils.abstracts.MainBaseDao;
import utils.abstracts.mappers.RowMapper;

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
    public void insertConfiguracion(int diaSemana, boolean diaEsReceso, Long horario, String tipoPersona, Character sexo, int cantPersonas) {
        baseDao.spUpdate("sp_create_configuracion(?, ?, ?, ?, ?, ?)", diaSemana, diaEsReceso, horario, tipoPersona, sexo, cantPersonas);
    }

    // READ all
    public List<Configuracion> getAllConfiguraciones() {
        return baseDao.spQuery("sp_read_configuracion", new ConfiguracionMapper());
    }

    // READ by primary key
    public Configuracion getConfiguracionByPk(LocalTime horaInicio, LocalTime horaFin, int diaSemana, boolean diaEsReceso) {
        return baseDao.spQuerySingleObject("sp_read_configuracion_by_pk(?, ?, ?, ?)", new ConfiguracionMapper(), horaInicio, horaFin, diaSemana, diaEsReceso);
    }
    public Configuracion getConfiguracionByPk(Long horario, int diaSemana, boolean diaEsReceso) {
        return baseDao.spQuerySingleObject("sp_read_configuracion_by_pk(?, ?, ?)", new ConfiguracionMapper(), horario, diaSemana, diaEsReceso);
    }

    // UPDATE
    public void updateConfiguracion(Long horario, int diaSemana, boolean diaEsReceso, boolean actual) {
        baseDao.spUpdate("sp_update_configuracion(?, ?, ?, ?)", horario, diaSemana, diaEsReceso, actual);
    }

    // DELETE
    public void deleteConfiguracion(Long horario, int diaSemana, boolean diaEsReceso) {
        baseDao.spUpdate("sp_delete_configuracion(?, ?, ?)", horario, diaSemana, diaEsReceso);
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
            config.setSexo(rs.getString("sexo").charAt(0));
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

