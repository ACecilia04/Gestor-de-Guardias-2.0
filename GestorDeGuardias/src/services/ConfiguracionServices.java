package services;

import logica.principal.DiaGuardia;
import model.Configuracion;
import model.Esquema;
import model.Horario;
import utils.abstracts.MainBaseDao;
import utils.abstracts.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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
        return baseDao.spQuerySingleObject("sp_read_configuracion_by_pk(?, ?, ?, ?)", new ConfiguracionMapper(), horaInicio, horaFin, diaSemana, diaEsReceso);
    }
    public ArrayList<Configuracion> crearPLantilla(boolean empezarHoy){
        ArrayList<Configuracion> dias = new ArrayList<>();
        LocalDate inicio;

        if (empezarHoy)
            inicio = LocalDate.now();
        else {
            if (this.planDeGuardias.isEmpty()) {
                inicio = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
            } else {
                inicio = this.planDeGuardias.get(planDeGuardias.size() - 1).getFecha().plusDays(1);
            }
        }
        int numDias = inicio.lengthOfMonth() - inicio.getDayOfMonth();


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
            Configuracion c = new Configuracion();
            Esquema e = new Esquema();
            Horario h = new Horario();
            return c;
        }
    }
}

