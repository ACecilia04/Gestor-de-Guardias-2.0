package services;

import model.*;
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
        baseDao.spUpdate("sp_turno_de_guardia_create(?, ?, ?, ?)", horarioId, personaAsignada, hecho, fecha);
    }

    // READ all
    public ArrayList<TurnoDeGuardia> getAllTurnosDeGuardia() {
        return (ArrayList<TurnoDeGuardia>) baseDao.spQuery("sp_turno_de_guardia_read", new TurnoDeGuardiaMapper());
    }
    public ArrayList<TurnoDeGuardia> getTurnosAPartirDe(LocalDate fecha) {
        return (ArrayList<TurnoDeGuardia>) baseDao.spQuery("sp_get_turnos_a_partir_de(?)", new TurnoDeGuardiaMapper(), fecha);
    }

    // READ by primary key
    public TurnoDeGuardia getTurnoDeGuardiaByPk(Long horarioId, LocalDate fecha) {
        return baseDao.spQuery("sp_turno_de_guardia_read_by_pk( ?, ?)", new TurnoDeGuardiaMapper(),horarioId, fecha)
                .stream().findFirst().orElse(null);
    }

    // UPDATE
    public void updateTurnoDeGuardia(Long horarioId, LocalDate fecha, long personaAsignada, Boolean hecho) throws SqlServerCustomException {
        baseDao.spUpdate("sp_turno_de_guardia_update(?, ?, ?, ?)", horarioId, fecha, personaAsignada, hecho);
    }

    // DELETE
    public void deleteTurnoDeGuardia(Long horarioId, LocalDate fecha) throws SqlServerCustomException {
        baseDao.spUpdate("sp_turno_de_guardia_delete(?, ?)", horarioId, fecha);
    }

    public void deleteTurnosDeGuardia(LocalDate fecha) throws SqlServerCustomException {
        baseDao.spUpdate("sp_turno_de_guardia_delete_mes(?)", fecha);
    }

    public void guardarTurnos(ArrayList<DiaGuardia> dias) {
        for (DiaGuardia dia : dias) {
            ArrayList<TurnoDeGuardia> turnos = dia.getTurnos();
            if (turnos == null) continue;
            for (TurnoDeGuardia turno : turnos) {
                Long horarioId = turno.getHorario().getId();
                ArrayList<Persona> personasAsignadas = turno.getPersonasAsignadas();
                LocalDate fecha = turno.getFecha();
                for (Persona persona : personasAsignadas) {
                    try {
                        // Intenta crear el turno
                        baseDao.spUpdate(
                                "sp_turno_de_guardia_create(?, ?, ?)",
                                persona.getId(),
                                fecha,
                                horarioId
                        );
                    } catch (Exception e) {
                        // Si falla por clave duplicada, intenta actualizar en su lugar
                        try {
                            updateTurnoDeGuardia(horarioId,fecha,persona.getId(), turno.getCumplimiento());

                        } catch (Exception ex) {
                            throw new RuntimeException("Error al actualizar el turno: " + turno, ex);
                        }
                    }
                }
            }
        }
    }


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

                p.setId(rs.getLong("persona_id"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setSexo(rs.getString("sexo"));
                p.setCarnet(rs.getString("carnet"));
                p.setTipo(new TipoPersona(rs.getString("tipo")));
                p.setUltimaGuardiaHecha(rs.getDate("ultima_guardia_hecha") == null ? null : rs.getDate("ultima_guardia_hecha").toLocalDate());
                p.setBaja(rs.getDate("baja") == null ? null : rs.getDate("baja").toLocalDate());
                p.setReincorporacion(rs.getDate("reincorporacion") == null ? null : rs.getDate("reincorporacion").toLocalDate());
                p.setGuardiasDeRecuperacion(rs.getInt("guardias_de_recuperacion"));
                personas.add(p);
            } while (rs.next());

            turno.setPersonasAsignadas(personas);
            return turno;
        }
    }
}
