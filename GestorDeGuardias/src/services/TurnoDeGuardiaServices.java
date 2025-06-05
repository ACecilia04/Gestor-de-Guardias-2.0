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
    public void insertTurnoDeGuardia(TurnoDeGuardia record) throws SqlServerCustomException {
        baseDao.spUpdate("sp_turno_de_guardia_create(?, ?, ?, ?)",
                record.getPersonaAsignada().getId(),
                record.getFecha(),
                record.getHorario().getId()
        );
    }

    // READ all
    public ArrayList<TurnoDeGuardia> getAllTurnosDeGuardia() {
        return (ArrayList<TurnoDeGuardia>) baseDao.spQuery("sp_turno_de_guardia_read", new TurnoDeGuardiaMapper());
    }
    public ArrayList<TurnoDeGuardia> getTurnosAPartirDe(LocalDate fecha) {
        return (ArrayList<TurnoDeGuardia>) baseDao.spQuery("sp_turno_de_guardia_read_a_partir_de(?)", new TurnoDeGuardiaMapper(), fecha);
    }

    // READ by primary key
    public TurnoDeGuardia getTurnoDeGuardiaByPk(Long horarioId, LocalDate fecha) {
        return baseDao.spQuerySingleObject("sp_turno_de_guardia_read_by_pk( ?, ?)", new TurnoDeGuardiaMapper(),horarioId, fecha);
    }

    // UPDATE
    public void updateTurnoDeGuardia(TurnoDeGuardia record) throws SqlServerCustomException {
        baseDao.spUpdate("sp_turno_de_guardia_update(?, ?, ?, ?, ?)",
                record.getId(),
                record.getHorario().getId(),
                record.getFecha(),
                record.getPersonaAsignada().getId(),
                record.getCumplimiento()
        );
    }

    // DELETE
    public void deleteTurnoDeGuardia(Long turnoId) throws SqlServerCustomException {
        baseDao.spUpdate("sp_turno_de_guardia_delete(?)", turnoId);
    }

    public void deleteTurnosDeGuardiaAPartirDe(LocalDate fecha) throws SqlServerCustomException {
        if (fecha.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("No se pueden borrar turnos de fechas pasadas.");

        baseDao.spUpdate("sp_turno_de_guardia_delete_a_partir_de_fecha(?)", fecha);
    }


    public void guardarCumplimientoTurnos(ArrayList<DiaGuardia> dias) {
        for (DiaGuardia dia : dias) {
            if (dia.getTurnos() == null || dia.getTurnos().isEmpty()) continue;
            for (TurnoDeGuardia turno : dia.getTurnos()) {
                ArrayList<Persona> personasAsignadas = turno.getPersonasAsignadas();
                for (Persona persona : personasAsignadas) {
                    try {
                        turno.setPersonaAsignada(persona);
                        updateTurnoDeGuardia(turno);
                    } catch (SqlServerCustomException ex) {
                        throw new RuntimeException("Error al actualizar el turno: " + turno, ex);
                    }
                }
            }
        }
    }

    public void guardarTurnos(ArrayList<DiaGuardia> dias) {
        for (DiaGuardia dia : dias) {
            if (dia.getTurnos() == null || dia.getTurnos().isEmpty()) continue;
            for (TurnoDeGuardia turno : dia.getTurnos()) {
                ArrayList<Persona> personasAsignadas = turno.getPersonasAsignadas();
                for (Persona persona : personasAsignadas) {
                    turno.setPersonaAsignada(persona);
                    try {
                        insertTurnoDeGuardia(turno);
                    } catch (SqlServerCustomException e) {
                        // Si falla por clave duplicada, intenta actualizar en su lugar
                        try {
                            updateTurnoDeGuardia(turno);
                        } catch (SqlServerCustomException ex) {
                            throw new RuntimeException("Error al actualizar el turno: " + turno, ex);
                        }
                    }
                }
            }
        }
    }

    public static class TurnoDeGuardiaMapper implements RowMapper<TurnoDeGuardia> {
        @Override
        public TurnoDeGuardia mapRow(ResultSet rs, int rowNum) throws SQLException {
            TurnoDeGuardia turno = new TurnoDeGuardia();

            turno.setId(rs.getLong("id"));
            turno.setFecha(rs.getDate("fecha") == null ? null : rs.getDate("fecha").toLocalDate());
            turno.setHecho(rs.getBoolean("hecho"));

            Horario horario = new Horario();
            horario.setId(rs.getLong("horario_id"));
            horario.setInicio(rs.getTime("inicio").toLocalTime());
            horario.setFin(rs.getTime("fin").toLocalTime());
            turno.setHorario(horario);

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
            turno.asignarPersona(p);

            return turno;
        }
    }
}
