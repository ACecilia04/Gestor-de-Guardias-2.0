package tests;

import model.Configuracion;
import model.Horario;
import org.junit.BeforeClass;
import org.junit.Test;
import services.ConfiguracionServices;
import services.HorarioServices;
import services.ServicesLocator;

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
    }

    @Test
    public void insertConfiguracion() {
        LocalTime inicio = LocalTime.of(8, 0, 0);
        LocalTime fin = LocalTime.of(20, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = new Configuracion(1, false, horario, "Estudiante", "M", 1);
        if (configuracionServices.getConfiguracionByPk(record.getHorario().getId(), record.getDiaSemana(), record.isDiaEsReceso()) == null) {
            configuracionServices.insertConfiguracion(record);
        }
        Configuracion recordInsertado = configuracionServices.getConfiguracionByPk(record.getHorario().getId(), record.getDiaSemana(), record.isDiaEsReceso());
        assertNotNull(recordInsertado);
    }


    @Test
    public void getConfiguracionByPk() {
        LocalTime inicio = LocalTime.of(8, 0, 0);
        LocalTime fin = LocalTime.of(20, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = new Configuracion(1, false, horario, "Estudiante", "M", 1);
        if (configuracionServices.getConfiguracionByPk(record.getHorario().getId(), record.getDiaSemana(), record.isDiaEsReceso()) == null) {
            configuracionServices.insertConfiguracion(record);
        }

        Configuracion configuracion = configuracionServices.getConfiguracionByPk(horario.getId(), 1, false);
        assertNotNull(configuracion);
        assertEquals("Estudiante", configuracion.getTipoPersona().getNombre());
        configuracionServices.deleteConfiguracion(configuracion.getId());
    }

    @Test
    public void getConfiguracionById() {
        LocalTime inicio = LocalTime.of(8, 0, 0);
        LocalTime fin = LocalTime.of(20, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion record = new Configuracion(1, false, horario, "Estudiante", "M", 1);
        if (configuracionServices.getConfiguracionByPk(record.getHorario().getId(), record.getDiaSemana(), record.isDiaEsReceso()) == null) {
            configuracionServices.insertConfiguracion(record);
        }

        Configuracion configuracion = configuracionServices.getConfiguracionByPk(horario.getId(), 1, false);
        Configuracion configuracion2 = configuracionServices.getConfiguracionById(configuracion.getId());
        assertNotNull(configuracion);
        assertEquals("Estudiante", configuracion.getTipoPersona().getNombre());
        configuracionServices.deleteConfiguracion(configuracion2.getId());
    }

    @Test
    public void deleteConfiguracion() {
        LocalTime inicio = LocalTime.of(8, 0, 0);
        LocalTime fin = LocalTime.of(20, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion configuracion = configuracionServices.getConfiguracionByPk(horario.getId(), 1, false);
        assertNotNull(configuracion);
        configuracionServices.deleteConfiguracion(configuracion.getId());

        Configuracion deleted = configuracionServices.getConfiguracionByPk(horario.getId(), 1, false);
        assertNull(deleted);
    }

    @Test
    public void getAllTiposPersona() {
        List<Configuracion> configuraciones = configuracionServices.getAllConfiguraciones();
        if (configuraciones.isEmpty()){
            LocalTime inicio = LocalTime.of(8, 0, 0);
            LocalTime fin = LocalTime.of(20, 0, 0);
            Horario horario = horarioServices.getHorarioByPk(inicio, fin);
            Configuracion newRecord = new Configuracion(1, false, horario, "Estudiante", "M", 1);
            configuracionServices.insertConfiguracion(newRecord);
            Configuracion insertedRecord1 = configuracionServices.getConfiguracionByPk(horario.getId(), 1, false);

            Configuracion newRecord2 = new Configuracion(1, true, horario, "Trabajador", "F", 1);
            configuracionServices.insertConfiguracion(newRecord2);
            Configuracion insertedRecord2 = configuracionServices.getConfiguracionByPk(horario.getId(), 1, true);

            configuraciones = configuracionServices.getAllConfiguraciones();
            assertNotNull(configuraciones);
            assertFalse(configuraciones.isEmpty());

            horarioServices.deleteHorario(insertedRecord1.getId());
            horarioServices.deleteHorario(insertedRecord2.getId());
        }
    }

    @Test
    public void updateConfiguracion() {
        LocalTime inicio = LocalTime.of(8, 0, 0);
        LocalTime fin = LocalTime.of(20, 0, 0);
        Horario horario = horarioServices.getHorarioByPk(inicio, fin);
        Configuracion configuracion1 = configuracionServices.getConfiguracionByPk(horario.getId(), 1, true);
        if (configuracion1 == null){
            configuracionServices.insertConfiguracion(configuracion1);
        }
        configuracion1 = configuracionServices.getConfiguracionByPk(horario.getId(), 1, true);
        assertNotNull(configuracion1);

        configuracion1.setDiaSemana(2);
        configuracionServices.updateConfiguracion(configuracion1);

        Configuracion configuracion2 = configuracionServices.getConfiguracionByPk(horario.getId(), 2, true);
        assertNotNull(configuracion2);

        configuracion2.setDiaSemana(1);
        configuracionServices.updateConfiguracion(configuracion2);

        Configuracion configuracion3 = configuracionServices.getConfiguracionByPk(horario.getId(), 1, true);
        assertNotNull(configuracion3);
    }

}
