package services;

import model.DiaGuardia;
import model.Horario;
import model.Persona;
import model.TurnoDeGuardia;
import utils.dao.MainBaseDao;
import utils.dao.SqlServerCustomException;
import utils.dao.mappers.RowMapper;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TurnoDeGuardiaServices {

    private final MainBaseDao baseDao;

    public TurnoDeGuardiaServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertTurnoDeGuardia(TurnoDeGuardia record) throws SqlServerCustomException, MultiplesErroresException {
        validarTurnoDeGuardia(record);

        baseDao.spUpdate("sp_turno_de_guardia_create(?, ?, ?)",
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
    public TurnoDeGuardia getTurnoDeGuardiaByPk(Long horarioId, LocalDate fecha, Long personaAsignadaId) {
        return baseDao.spQuerySingleObject("sp_turno_de_guardia_read_by_pk(?, ?, ?)", new TurnoDeGuardiaMapper(), fecha, horarioId, personaAsignadaId);
    }

    // READ by id
    public TurnoDeGuardia getTurnoDeGuardiaById(Long id) {
        return baseDao.spQuerySingleObject("sp_turno_de_guardia_read_by_id(?)", new TurnoDeGuardiaMapper(), id);
    }

    // UPDATE
    public void updateTurnoDeGuardia(TurnoDeGuardia record) throws SqlServerCustomException, MultiplesErroresException {
        validarTurnoDeGuardia(record);

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

    public void deleteTurnosDeGuardiaAPartirDe(LocalDate fecha) throws SqlServerCustomException, EntradaInvalidaException {
        if (fecha == null)
            throw new EntradaInvalidaException("Fecha no especificada.");

        if (fecha.isBefore(LocalDate.now()))
            throw new EntradaInvalidaException("No se pueden borrar turnos de fechas pasadas.");

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
                    } catch (SqlServerCustomException | MultiplesErroresException ex) {
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
                    } catch (SqlServerCustomException | MultiplesErroresException e) {
                        // Si falla por clave duplicada, intenta actualizar en su lugar
                        try {
                            updateTurnoDeGuardia(turno);
                        } catch (SqlServerCustomException | MultiplesErroresException ex) {
                            throw new RuntimeException("Error al actualizar el turno: " + turno, ex);
                        }
                    }
                }
            }
        }
    }

    public static class TurnoDeGuardiaMapper implements RowMapper<TurnoDeGuardia> {

        private static HorarioServices horarioServices = ServicesLocator.getInstance().getHorarioServices();
        private static PersonaServices personaServices = ServicesLocator.getInstance().getPersonaServices();

        @Override
        public TurnoDeGuardia mapRow(ResultSet rs, int rowNum) throws SQLException {
            TurnoDeGuardia turno = new TurnoDeGuardia();

            turno.setId(rs.getLong("id"));
            turno.setFecha(rs.getDate("fecha") == null ? null : rs.getDate("fecha").toLocalDate());
            turno.setHecho(rs.getBoolean("hecho"));

            Horario horario = horarioServices.getHorarioById(rs.getLong("horario"));
            turno.setHorario(horario);

            Persona p = personaServices.getPersonaById(rs.getLong("persona_asignada"));
            turno.setPersonaAsignada(p);

            return turno;
        }
    }

    // ==============   VALIDACIONES   ==========================================
    private void validarTurnoDeGuardia(TurnoDeGuardia record) throws MultiplesErroresException {
        List<String> errores = new ArrayList<>();

        if (record.getFecha() == null)
            errores.add("Fecha no especificada.");
        if (record.getHorario() == null)
            errores.add("Horario no especificado.");
        if (record.getPersonaAsignada() == null)
            errores.add("Persona no especificada.");

        if (!errores.isEmpty())
            throw new MultiplesErroresException("Turno de guardia con datos erróneos:", errores);
    }
}
