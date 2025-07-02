package tests;

import model.Configuracion;
import model.Horario;
import model.Usuario;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import services.ConfiguracionServices;
import services.HorarioServices;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;
import utils.exceptions.MultiplesErroresException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class ConfigurationTest {

    private static ConfiguracionServices configuracionServices;
    private static HorarioServices horarioServices;

    @BeforeClass
    public static void setup() {
        configuracionServices = ServicesLocator.getInstance().getConfiguracionServices();
        horarioServices = ServicesLocator.getInstance().getHorarioServices();
        Usuario usuarioLogueado = ServicesLocator.getInstance().getUsuarioServices().getUsuarioById(1L);
        ServicesLocator.getInstance().setUsuarioActual(usuarioLogueado);

        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario1 = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion nuevoRecord = new Configuracion(1, false, horario1, "Estudiante", "M", 3);
        try {
            configuracionServices.insertConfiguracion(nuevoRecord);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            e.printStackTrace();
        }

        inicio = LocalTime.of(14, 0, 0);
        fin = LocalTime.of(19, 0, 0);
        Horario horario2 = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion nuevoRecord2 = new Configuracion(1, false, horario2, "Estudiante", "F", 1);
        try {
            configuracionServices.insertConfiguracion(nuevoRecord2);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void done() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = configuracionServices.getConfiguracionByPk(horario.getId(), LocalDate.now(), false);
        try {
            configuracionServices.deleteConfiguracion(record.getId());
        } catch (SqlServerCustomException e) {
            e.printStackTrace();
        }

        inicio = LocalTime.of(14, 0, 0);
        fin = LocalTime.of(19, 0, 0);
        horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record2 = configuracionServices.getConfiguracionByPk(horario.getId(), LocalDate.now(), false);
        try {
            configuracionServices.deleteConfiguracion(record2.getId());
        } catch (SqlServerCustomException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void insertDiaSemanaEmpty_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion nuevoRecord = new Configuracion(0, false, horario, "Estudiante", "M", 3);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.insertConfiguracion(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Dia de semana no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertDiaSemanaIllegal_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion nuevoRecord = new Configuracion(9, false, horario, "Estudiante", "M", 3);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.insertConfiguracion(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Dia de semana no válido (1-7).");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertEsRecesoEmpty_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion nuevoRecord = new Configuracion(1, null, horario, "Estudiante", "M", 3);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.insertConfiguracion(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Dia de receso no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertHorarioNull_throwException() {
        Configuracion nuevoRecord = new Configuracion(1, false, null, "Estudiante", "M", 3);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.insertConfiguracion(nuevoRecord);
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
    public void insertExisting_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion nuevoRecord = new Configuracion(1, false, horario, "Estudiante", "M", 3);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.insertConfiguracion(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Configuracion existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void updateDiaSemanaEmpty_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = configuracionServices.getConfiguracionByPk(horario.getId(), LocalDate.now(), false);
        assertNotNull(record);
        record.setDiaSemana(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.updateConfiguracion(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Dia de semana no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateDiaSemanaIllegal_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = configuracionServices.getConfiguracionByPk(horario.getId(), LocalDate.now(), false);
        assertNotNull(record);
        record.setDiaSemana(9);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.updateConfiguracion(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Dia de semana no válido (1-7).");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateEsRecesoEmpty_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = configuracionServices.getConfiguracionByPk(horario.getId(), LocalDate.now(), false);
        assertNotNull(record);
        record.setDiaEsReceso(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.updateConfiguracion(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Dia de receso no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateHorarioNull_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = configuracionServices.getConfiguracionByPk(horario.getId(), LocalDate.now(), false);
        assertNotNull(record);
        record.setHorario(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.updateConfiguracion(record);
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
    public void updateNonExisting_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = new Configuracion(2, false, horario, "Estudiante", "M", 3);
        record.setId(99L);
        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.updateConfiguracion(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Configuracion inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateExisting_throwException() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = configuracionServices.getConfiguracionByPk(horario.getId(), LocalDate.now(), false);

        inicio = LocalTime.of(14, 0, 0);
        fin = LocalTime.of(19, 0, 0);
        Horario horario2 = horarioServices.getHorarioByPk(inicio, fin);
        record.setHorario(horario2);

        boolean validaError = false;
        boolean performed = false;
        try {
            configuracionServices.updateConfiguracion(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Configuracion existente");
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
            configuracionServices.deleteConfiguracion(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Configuracion inexistente");
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
            configuracionServices.deleteConfiguracion(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Configuracion inexistente");
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
            configuracionServices.deleteConfiguracion(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Configuracion inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void getNonExistingId_returnNull() {
        Long id = 1000L;
        Configuracion record = configuracionServices.getConfiguracionById(id);
        assertNull(record);
    }

    @Test
    public void getNonExistingPk_returnNull() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = configuracionServices.getConfiguracionByPk(horario.getId(), LocalDate.now().plusDays(1L), false);
        assertNull(record);
    }

    @Test
    public void getExistingPk_success() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = configuracionServices.getConfiguracionByPk(horario.getId(), LocalDate.now(), false);
        assertNotNull(record);
        Configuracion record2 = configuracionServices.getConfiguracionById(record.getId());
        assertNotNull(record2);
    }

    @Test
    public void getAll_success() {
        List<Configuracion> records = configuracionServices.getAllConfiguraciones();
        assertNotNull(records);
        assertFalse(records.isEmpty());
    }

    @Test
    public void insert_get_update_delete_success() {
        LocalTime inicio = LocalTime.of(9, 0, 0);
        LocalTime fin = LocalTime.of(14, 0, 0);
        Horario horario1 = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion nuevoRecord = new Configuracion(2, false, horario1, "Estudiante", "M", 3);
        try {
            configuracionServices.insertConfiguracion(nuevoRecord);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        Configuracion record = configuracionServices.getConfiguracionByPk(horario1.getId(), LocalDate.now().plusDays(1L), false);
        assertNotNull(record);

        Integer nuevoDiaSemana = 3;
        record.setDiaSemana(3);
        try {
            configuracionServices.updateConfiguracion(record);
        } catch (SqlServerCustomException | MultiplesErroresException e) {
            System.out.println(e.getMessage());
        }
        record = configuracionServices.getConfiguracionById(record.getId());
        assertEquals(nuevoDiaSemana, record.getDiaSemana());

        try {
            configuracionServices.deleteConfiguracion(record.getId());
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        record = configuracionServices.getConfiguracionById(record.getId());
        assertNull(record);
    }

}
