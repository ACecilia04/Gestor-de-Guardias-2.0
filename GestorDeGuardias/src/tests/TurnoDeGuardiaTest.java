package tests;

import org.junit.BeforeClass;
import services.ServicesLocator;
import services.TurnoDeGuardiaServices;

public class TurnoDeGuardiaTest {

    private static TurnoDeGuardiaServices turnoDeGuardiaServices;

    @BeforeClass
    public static void setup() {
        turnoDeGuardiaServices = ServicesLocator.getInstance().getTurnoDeGuardiaServices();
    }


//    @Test
//    public void insertHoraInicialNull_throwException() {
//        LocalTime inicio = null;
//        LocalTime fin = LocalTime.of(20, 0, 0);
//        Horario horario = new Horario(inicio, fin);
//        LocalDate fecha = null;
//        Persona personaAsignada =
//        TurnoDeGuardia nuevoRecord = new TurnoDeGuardia();
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            turnoDeGuardiaServices.insertTurnoDeGuardia(nuevoRecord);
//            performed = true;
//        } catch (MultiplesErroresException e) {
//            validaError = e.getErrores().contains("Hora inicial no especificada.");
//        } catch (SqlServerCustomException e) {
//            System.out.println(e.getMessage());
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//    @Test
//    public void insertHoraFinallNull_throwException() {
//        LocalTime inicio = LocalTime.of(8, 0, 0);
//        LocalTime fin = null;
//        Horario nuevoRecord = new Horario(inicio, fin);
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            horarioServices.insertHorario(nuevoRecord);
//            performed = true;
//        } catch (MultiplesErroresException e) {
//            validaError = e.getErrores().contains("Hora final no especificada.");
//        } catch (SqlServerCustomException e) {
//            System.out.println(e.getMessage());
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//    @Test
//    public void insertExisting_throwException() {
//        LocalTime inicio = LocalTime.of(8, 0, 0);
//        LocalTime fin = LocalTime.of(20, 0, 0);
//        Horario nuevoRecord = new Horario(inicio, fin);
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            horarioServices.insertHorario(nuevoRecord);
//            performed = true;
//        } catch (MultiplesErroresException e) {
//            System.out.println(e.getMessage());
//        } catch (SqlServerCustomException e) {
//            validaError = e.getMessage().equals("Horario existente");
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//
//    @Test
//    public void updateHoraInicialNull_throwException() {
//        LocalTime inicio = LocalTime.of(8, 0, 0);
//        LocalTime fin = LocalTime.of(20, 0, 0);
//        Horario record = horarioServices.getHorarioByPk(inicio, fin);
//        record.setInicio(null);
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            horarioServices.updateHorario(record);
//            performed = true;
//        } catch (MultiplesErroresException e) {
//            validaError = e.getErrores().contains("Hora inicial no especificada.");
//        } catch (SqlServerCustomException e) {
//            System.out.println(e.getMessage());
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//    @Test
//    public void updateHoraFinalNull_throwException() {
//        LocalTime inicio = LocalTime.of(8, 0, 0);
//        LocalTime fin = LocalTime.of(20, 0, 0);
//        Horario record = horarioServices.getHorarioByPk(inicio, fin);
//        record.setFin(null);
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            horarioServices.updateHorario(record);
//            performed = true;
//        } catch (MultiplesErroresException e) {
//            validaError = e.getErrores().contains("Hora final no especificada.");
//        } catch (SqlServerCustomException e) {
//            System.out.println(e.getMessage());
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//    @Test
//    public void updateNonExisting_throwException() {
//        LocalTime inicio = LocalTime.of(8, 0, 0);
//        LocalTime fin = LocalTime.of(20, 0, 0);
//        Horario record = new Horario(inicio, fin);
//        record.setId(100L);
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            horarioServices.updateHorario(record);
//            performed = true;
//        } catch (MultiplesErroresException e) {
//            System.out.println(e.getMessage());
//        } catch (SqlServerCustomException e) {
//            validaError = e.getMessage().equals("Horario inexistente");
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//    @Test
//    public void updateExisting_throwException() {
//        LocalTime inicio = LocalTime.of(8, 0, 0);
//        LocalTime fin = LocalTime.of(20, 0, 0);
//        Horario record = horarioServices.getHorarioByPk(inicio, fin);
//        record.setInicio(LocalTime.of(20, 0, 0));
//        record.setFin(LocalTime.of(8, 0, 0));
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            horarioServices.updateHorario(record);
//            performed = true;
//        } catch (MultiplesErroresException e) {
//            System.out.println(e.getMessage());
//        } catch (SqlServerCustomException e) {
//            validaError = e.getMessage().equals("Horario existente");
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//
//    @Test
//    public void deleteIdEmpty_throwException() {
//        Long id = 0L;
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            horarioServices.deleteHorario(id);
//            performed = true;
//        } catch (SqlServerCustomException e) {
//            validaError = e.getMessage().equals("Horario inexistente");
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//    @Test
//    public void deleteIdNull_throwException() {
//        Long id = null;
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            horarioServices.deleteHorario(id);
//            performed = true;
//        } catch (SqlServerCustomException e) {
//            validaError = e.getMessage().equals("Horario inexistente");
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//    @Test
//    public void deleteIdNonExisting_throwException() {
//        Long id = 1000L;
//        boolean validaError = false;
//        boolean performed = false;
//        try {
//            horarioServices.deleteHorario(id);
//            performed = true;
//        } catch (SqlServerCustomException e) {
//            validaError = e.getMessage().equals("Horario inexistente");
//        }
//        assertTrue(validaError);
//        assertFalse(performed);
//    }
//
//
//    @Test
//    public void getNonExistingId_returnNull() {
//        Long id = 1000L;
//        Horario record = horarioServices.getHorarioById(id);
//        assertNull(record);
//    }
//
//    @Test
//    public void getHoraInicialNull_returnNull() {
//        LocalTime inicio = null;
//        LocalTime fin = LocalTime.of(20, 0, 0);
//        Horario record = horarioServices.getHorarioByPk(inicio, fin);
//        assertNull(record);
//    }
//
//    @Test
//    public void getHoraFinalNull_returnNull() {
//        LocalTime inicio = LocalTime.of(8, 0, 0);
//        LocalTime fin = null;
//        Horario record = horarioServices.getHorarioByPk(inicio, fin);
//        assertNull(record);
//    }
//
//    @Test
//    public void getExistingHorario_success() {
//        LocalTime inicio = LocalTime.of(8, 0, 0);
//        LocalTime fin = LocalTime.of(20, 0, 0);
//        Horario record = horarioServices.getHorarioByPk(inicio, fin);
//        assertNotNull(record);
//        Horario record2 = horarioServices.getHorarioById(record.getId());
//        assertNotNull(record2);
//    }
//
//
//    @Test
//    public void insert_get_delete_success() {
//        LocalTime inicio = LocalTime.of(10, 0, 0);
//        LocalTime fin = LocalTime.of(22, 0, 0);
//        Horario nuevoRecord = new Horario(inicio, fin);
//        try {
//            horarioServices.insertHorario(nuevoRecord);
//        } catch (MultiplesErroresException | SqlServerCustomException e) {
//            System.out.println(e.getMessage());
//        }
//        Horario record = horarioServices.getHorarioByPk(inicio, fin);
//        assertNotNull(record);
//
//        LocalTime nuevoInicio = LocalTime.of(12, 0, 0);
//        record.setInicio(nuevoInicio);
//        try {
//            horarioServices.updateHorario(record);
//        } catch (SqlServerCustomException | MultiplesErroresException e) {
//            System.out.println(e.getMessage());
//        }
//        record = horarioServices.getHorarioById(record.getId());
//        assertEquals(nuevoInicio, record.getInicio());
//
//        try {
//            horarioServices.deleteHorario(record.getId());
//        } catch (SqlServerCustomException e) {
//            System.out.println(e.getMessage());
//        }
//        record = horarioServices.getHorarioById(record.getId());
//        assertNull(record);
//    }

//    @Test
//    public void getAll_success() {
//        List<TurnoDeGuardia> records = turnoDeGuardiaServices.getAllTurnosDeGuardia();
//        assertNotNull(records);
//        assertFalse(records.isEmpty());
//    }
}
