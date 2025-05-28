package services;

import model.Configuracion;
import model.Esquema;
import model.Horario;
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
    public void insertConfiguracion(LocalTime horaInicio, LocalTime horaFin, int diaSemana, boolean diaEsReceso, boolean actual) {
        baseDao.spUpdate("sp_create_configuracion(?, ?, ?, ?, ?)", horaInicio, horaFin, diaSemana, diaEsReceso, actual);
    }

    // READ all
    public List<Configuracion> getAllConfiguraciones() {
        return baseDao.spQuery("sp_read_configuracion", new ConfiguracionMapper());
    }

    // READ by primary key
    public Configuracion getConfiguracionByPk(LocalTime horaInicio, LocalTime horaFin, int diaSemana, boolean diaEsReceso) {
        return baseDao.spQuery("sp_read_configuracion_by_pk(?, ?, ?, ?)", new ConfiguracionMapper(), horaInicio, horaFin, diaSemana, diaEsReceso)
                .stream().findFirst().orElse(null);
    }

    // UPDATE
    public void updateConfiguracion(LocalTime horaInicio, LocalTime horaFin, int diaSemana, boolean diaEsReceso, boolean actual) {
        baseDao.spUpdate("sp_update_configuracion(?, ?, ?, ?, ?)", horaInicio, horaFin, diaSemana, diaEsReceso, actual);
    }

    // DELETE
    public void deleteConfiguracion(LocalTime horaInicio, LocalTime horaFin, int diaSemana, boolean diaEsReceso) {
        baseDao.spUpdate("sp_delete_configuracion(?, ?, ?, ?)", horaInicio, horaFin, diaSemana, diaEsReceso);
    }

    // Internal Mapper
    private static class ConfiguracionMapper implements RowMapper<Configuracion> {
        @Override
        public Configuracion mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Configuracion(new Horario(
                    rs.getTime("hora_inicio").toLocalTime(),
                    rs.getTime("hora_fin").toLocalTime()),
                    new Esquema(rs.getInt("dia_semana"),
                            rs.getBoolean("dia_es_receso")),
                    rs.getBoolean("actual")
            );
        }
    }
}

