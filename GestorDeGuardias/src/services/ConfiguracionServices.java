package services;

import model.Configuracion;
import model.Horario;
import model.TipoPersona;
import utils.dao.MainBaseDao;
import utils.dao.SqlServerCustomException;
import utils.dao.mappers.RowMapper;
import utils.exceptions.MultiplesErroresException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static utils.Utilitarios.integerEsValido;

public class ConfiguracionServices {

    private final MainBaseDao baseDao;
    private static HorarioServices horarioServices = ServicesLocator.getInstance().getHorarioServices();
    private static TipoPersonaServices tipoPersonaServices = ServicesLocator.getInstance().getTipoPersonaServices();

    public ConfiguracionServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertConfiguracion(Configuracion record) throws SqlServerCustomException, MultiplesErroresException {
        validarConfiguracion(record);

        baseDao.spUpdate("sp_configuracion_create(?, ?, ?, ?, ?, ?)",
                record.getDiaSemana(),
                record.isDiaEsReceso(),
                record.getHorario().getId(),
                record.getTipoPersona().getNombre(),
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

    public Configuracion getConfiguracionById(Long id) {
        return baseDao.spQuerySingleObject("sp_configuracion_read_by_id(?)", new ConfiguracionMapper(), id);
    }

    // UPDATE
    public void updateConfiguracion(Configuracion record) throws SqlServerCustomException {
        baseDao.spUpdate("sp_configuracion_update(?, ?, ?, ?, ?, ?, ?)",
                record.getId(),
                record.getDiaSemana(),
                record.isDiaEsReceso(),
                record.getHorario().getId(),
                record.getTipoPersona().getNombre(),
                record.getSexo(),
                record.getCantPersonas()
        );
    }

    // DELETE
    public void deleteConfiguracion(Long id) throws SqlServerCustomException {
        baseDao.spUpdate("sp_configuracion_delete(?)", id);
    }

    // Internal Mapper
    private static class ConfiguracionMapper implements RowMapper<Configuracion> {
        @Override
        public Configuracion mapRow(ResultSet rs, int rowNum) throws SQLException {
            Configuracion config = new Configuracion();

            config.setId(rs.getLong("id"));
            config.setDiaSemana(rs.getInt("dia_semana"));
            config.setDiaEsReceso(rs.getBoolean("dia_es_receso"));
            config.setSexo(rs.getString("sexo"));
            config.setCantPersonas(rs.getInt("cant_personas"));

            Horario h = horarioServices.getHorarioById((rs.getLong("horario")));
            config.setHorario(h);

            TipoPersona tp = tipoPersonaServices.getTipoPersona(rs.getString("tipo_persona"));
            config.setTipoPersona(tp);

            return config;
        }
    }



    // ==============   VALIDACIONES   ==========================================
    private void validarConfiguracion(Configuracion record) throws MultiplesErroresException {
        List<String> errores = new ArrayList<>();

        if (!integerEsValido(record.getDiaSemana()))
            errores.add("Dia de semana no especificado.");
        if (record.isDiaEsReceso() == null)
            errores.add("Dia de receso no especificado.");
        if (record.getHorario() == null)
            errores.add("Horario no especificado.");

        if (!errores.isEmpty())
            throw new MultiplesErroresException("Configuración con datos erróneos:", errores);
    }
}

