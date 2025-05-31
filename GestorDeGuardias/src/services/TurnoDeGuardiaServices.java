package services;

import model.Horario;
import model.Persona;
import model.TurnoDeGuardia;
import utils.dao.MainBaseDao;
import utils.dao.SqlServerCustomException;
import utils.dao.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TurnoDeGuardiaServices {

    private final MainBaseDao baseDao;

    public TurnoDeGuardiaServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertTurnoDeGuardia(Long horarioId, long personaAsignada, Boolean hecho, LocalDate fecha) throws SqlServerCustomException {
        baseDao.spUpdate("sp_create_turno_de_guardia(?, ?, ?, ?)", horarioId, personaAsignada, hecho, fecha);
    }

    // READ all
    public ArrayList<TurnoDeGuardia> getAllTurnosDeGuardia() {
        return (ArrayList<TurnoDeGuardia>) baseDao.spQuery("sp_read_turno_de_guardia", new TurnoDeGuardiaMapper());
    }
    public ArrayList<TurnoDeGuardia> getTurnosAPartirDe(LocalDate fecha) {
        return (ArrayList<TurnoDeGuardia>) baseDao.spQuery("sp_get_turnos_a_partir_de", new TurnoDeGuardiaMapper(), fecha);
    }

    // READ by primary key
    public TurnoDeGuardia getTurnoDeGuardiaByPk(Long horarioId, LocalDate fecha) {
        return baseDao.spQuery("sp_read_turno_de_guardia_by_pk( ?, ?)", new TurnoDeGuardiaMapper(),horarioId, fecha)
                .stream().findFirst().orElse(null);
    }

    // UPDATE
    public void updateTurnoDeGuardia(Long horarioId, LocalDate fecha, long personaAsignada, Boolean hecho) throws SqlServerCustomException {
        baseDao.spUpdate("sp_update_turno_de_guardia(?, ?, ?, ?)", horarioId, fecha, personaAsignada, hecho);
    }

    // DELETE
    public void deleteTurnoDeGuardia(Long horarioId, LocalDate fecha) throws SqlServerCustomException {
        baseDao.spUpdate("sp_delete_turno_de_guardia(?, ?)", horarioId, fecha);
    }

//    public ArrayList<DiaGuardia> crearPLantilla(boolean empezarHoy){
//        LocalDate inicio = null;
//        ArrayList<TurnoDeGuardia> turnosActuales = getAllTurnosDeGuardia();
//        if (empezarHoy)
//            inicio = LocalDate.now();
//        else {
//            if (turnosActuales.isEmpty()) {
//                inicio = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
//            } else {
//                inicio = turnosActuales.getLast().getFecha().plusDays(1);
//            }
//        }
//        int numDias = inicio.lengthOfMonth() - inicio.getDayOfMonth();
//
//
//    }


    // Internal Mapper
    public static class TurnoDeGuardiaMapper implements RowMapper<TurnoDeGuardia> {
        //TODO: check if personaMapper can be used in TurnoDeGuardiaMapper

        @Override
        public TurnoDeGuardia mapRow(ResultSet rs, int rowNum) throws SQLException {
            TurnoDeGuardia turno = new TurnoDeGuardia();
            turno.setFecha(rs.getDate("fecha").toLocalDate());
            turno.setHecho(rs.getObject("hecho", Boolean.class));

            Horario horario = new Horario();
            horario.setId(rs.getLong("horario_id"));
            horario.setInicio(rs.getTime("inicio").toLocalTime());
            horario.setFin(rs.getTime("fin").toLocalTime());
            turno.setHorario(horario);

            ArrayList<Persona> personas = new ArrayList<>();
            do {
                Persona p = new Persona();

                p.setId(rs.getLong("id"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setSexo(rs.getString("sexo"));
                p.setCarnet(rs.getString("carnet"));
                p.setTipo(rs.getString("tipo"));
                p.setUltimaGuardiaHecha(rs.getDate("ultima_guardia_hecha").toLocalDate());
                p.setBaja(rs.getDate("baja").toLocalDate());
                p.setReincorporacion(rs.getDate("reincorporacion").toLocalDate());
                p.setGuardiasDeRecuperacion(rs.getInt("guardias_de_recuperacion"));
                p.setBorrado(rs.getBoolean("borrado"));
                personas.add(p);
            } while (rs.next());

            turno.setPersonasAsignadas(personas);
            return turno;
        }
    }
}
