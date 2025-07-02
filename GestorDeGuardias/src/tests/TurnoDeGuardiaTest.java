package tests;

import model.Horario;
import model.Persona;
import model.TurnoDeGuardia;
import model.Usuario;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import services.HorarioServices;
import services.PersonaServices;
import services.ServicesLocator;
import services.TurnoDeGuardiaServices;
import utils.dao.SqlServerCustomException;
import utils.exceptions.MultiplesErroresException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class TurnoDeGuardiaTest {

    private static TurnoDeGuardiaServices turnoDeGuardiaServices;
    private static HorarioServices horarioServices;
    private static PersonaServices personaServices;

    @BeforeClass
    public static void setup() {
        turnoDeGuardiaServices = ServicesLocator.getInstance().getTurnoDeGuardiaServices();
        horarioServices = ServicesLocator.getInstance().getHorarioServices();
        personaServices = ServicesLocator.getInstance().getPersonaServices();
        Usuario usuarioLogueado = ServicesLocator.getInstance().getUsuarioServices().getUsuarioById(1L);
        ServicesLocator.getInstance().setUsuarioActual(usuarioLogueado);

        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia nuevoRecord = new TurnoDeGuardia(fecha, horario, personaAsignada);
        try {
            turnoDeGuardiaServices.insertTurnoDeGuardia(nuevoRecord);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            throw new RuntimeException(e);
        }

        personaAsignada = personaServices.getPersonaById(2L);
        TurnoDeGuardia nuevoRecord2 = new TurnoDeGuardia(fecha, horario, personaAsignada);
        try {
            turnoDeGuardiaServices.insertTurnoDeGuardia(nuevoRecord2);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void done() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, personaAsignada.getId());
        try {
            turnoDeGuardiaServices.deleteTurnoDeGuardia(record.getId());
        } catch (SqlServerCustomException e) {
            throw new RuntimeException(e);
        }

        personaAsignada = personaServices.getPersonaById(2L);
        TurnoDeGuardia record2 = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, personaAsignada.getId());
        try {
            turnoDeGuardiaServices.deleteTurnoDeGuardia(record2.getId());
        } catch (SqlServerCustomException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void insertFechaNull_throwException() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = null;
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia nuevoRecord = new TurnoDeGuardia(fecha, horario, personaAsignada);
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.insertTurnoDeGuardia(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Fecha no especificada.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertHorarioNull_throwException() {
        Horario horario = null;
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia nuevoRecord = new TurnoDeGuardia(fecha, horario, personaAsignada);
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.insertTurnoDeGuardia(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Horario no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertPersonaNull_throwException() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = null;
        TurnoDeGuardia nuevoRecord = new TurnoDeGuardia(fecha, horario, personaAsignada);
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.insertTurnoDeGuardia(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Persona no especificada.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertExisting_throwException() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia nuevoRecord = new TurnoDeGuardia(fecha, horario, personaAsignada);
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.insertTurnoDeGuardia(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Turno de guardia existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void updateFechalNull_throwException() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, personaAsignada.getId());
        record.setFecha(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.updateTurnoDeGuardia(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Fecha no especificada.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateHorarioNull_throwException() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, personaAsignada.getId());
        record.setHorario(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.updateTurnoDeGuardia(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Horario no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updatePersonaNull_throwException() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, personaAsignada.getId());
        record.setPersonaAsignada(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.updateTurnoDeGuardia(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Persona no especificada.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNonExisting_throwException() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, personaAsignada.getId());
        record.setId(100L);
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.updateTurnoDeGuardia(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Turno de guardia inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateExisting_throwException() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, personaAsignada.getId());
        Persona personaAsignada2 = personaServices.getPersonaById(2L);
        record.setPersonaAsignada(personaAsignada2);
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.updateTurnoDeGuardia(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Turno de guardia existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void deleteIdEmpty_throwException() {
        Long id = 0L;
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.deleteTurnoDeGuardia(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Turno de guardia inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void deleteIdNull_throwException() {
        Long id = null;
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.deleteTurnoDeGuardia(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Turno de guardia inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void deleteIdNonExisting_throwException() {
        Long id = 1000L;
        boolean validaError = false;
        boolean performed = false;
        try {
            turnoDeGuardiaServices.deleteTurnoDeGuardia(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Turno de guardia inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void getNonExistingId_returnNull() {
        Long id = 1000L;
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaById(id);
        assertNull(record);
    }

    @Test
    public void getHorarioNull_returnNull() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(null, fecha, personaAsignada.getId());
        assertNull(record);
    }

    @Test
    public void getFechaNull_returnNull() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), null, personaAsignada.getId());
        assertNull(record);
    }

    @Test
    public void getPersonaNull_returnNull() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, null);
        assertNull(record);
    }

    @Test
    public void getExistingTurnoDeGuardia_success() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(1L);
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, personaAsignada.getId());
        assertNotNull(record);
        TurnoDeGuardia record2 = turnoDeGuardiaServices.getTurnoDeGuardiaById(record.getId());
        assertNotNull(record2);
    }

    @Test
    public void insert_get_delete_success() {
        Horario horario = horarioServices.getHorarioById(1L);
        LocalDate fecha = LocalDate.now();
        Persona personaAsignada = personaServices.getPersonaById(3L);
        TurnoDeGuardia nuevoRecord = new TurnoDeGuardia(fecha, horario, personaAsignada);
        try {
            turnoDeGuardiaServices.insertTurnoDeGuardia(nuevoRecord);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        TurnoDeGuardia record = turnoDeGuardiaServices.getTurnoDeGuardiaByPk(horario.getId(), fecha, personaAsignada.getId());
        assertNotNull(record);

        Horario nuevoHorario = horarioServices.getHorarioById(2L);
        record.setHorario(nuevoHorario);
        try {
            turnoDeGuardiaServices.updateTurnoDeGuardia(record);
        } catch (SqlServerCustomException | MultiplesErroresException e) {
            System.out.println(e.getMessage());
        }
        record = turnoDeGuardiaServices.getTurnoDeGuardiaById(record.getId());
        assertEquals(nuevoHorario.getId(), record.getHorario().getId());

        try {
            turnoDeGuardiaServices.deleteTurnoDeGuardia(record.getId());
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        record = turnoDeGuardiaServices.getTurnoDeGuardiaById(record.getId());
        assertNull(record);
    }

    @Test
    public void getAll_success() {
        List<TurnoDeGuardia> records = turnoDeGuardiaServices.getAllTurnosDeGuardia();
        assertNotNull(records);
        assertFalse(records.isEmpty());
    }
}
