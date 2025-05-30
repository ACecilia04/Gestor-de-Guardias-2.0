package tests;

import model.Configuracion;
import model.Horario;
import org.junit.BeforeClass;
import org.junit.Test;
import services.ConfiguracionServices;
import services.ServicesLocator;

import java.time.LocalTime;

import static org.junit.Assert.assertNotNull;

public class ConfigurationTest {

    private static ConfiguracionServices configuracionServices;

    @BeforeClass
    public static void setup() {
        configuracionServices = ServicesLocator.getInstance().getConfiguracionServices();
    }

    @Test
    public void insertConfiguracion() {
        Horario horario = new Horario(LocalTime.of(8, 0, 0), LocalTime.of(20, 0, 0));
        Configuracion record = new Configuracion(1, false, horario, "Estudiante", "M", 1);
        if (configuracionServices.getConfiguracionByPk(record.getHorario().getId(), record.getDiaSemana(), record.isDiaEsReceso()) == null) {
            configuracionServices.insertConfiguracion(record);
        }
        Configuracion recordInsertado = configuracionServices.getConfiguracionByPk(record.getHorario().getId(), record.getDiaSemana(), record.isDiaEsReceso());
        assertNotNull(recordInsertado);
    }


//    @Test
//    public void getConfiguracion() {
//        Horario horario = new Horario(LocalTime.of(8, 0, 0), LocalTime.of(20, 0, 0));
//        Configuracion configuracion = configuracionServices.getConfiguracionByPk(horario, record.getDiaSemana(), record.isDiaEsReceso());
//        assertNotNull(configuracion);
//        assertEquals("Estudiante", configuracion.getNombre());
//    }
//
//    @Test
//    public void updateConfiguracion() {
//        String tipo = "Estudiante";
//        Configuracion tp1 = configuracionServices.getConfiguracion(tipo);
//        assertNotNull(tp1);
//
//        configuracionServices.updateConfiguracion(tp1.getNombre(), "Estudiantil");
//
//        Configuracion tp2 = configuracionServices.getConfiguracion("Estudiantil");
//        assertNotNull(tp2);
//
//        configuracionServices.updateConfiguracion(tp2.getNombre(), tp1.getNombre());
//
//        Configuracion tp3 = configuracionServices.getConfiguracion(tipo);
//        assertNotNull(tp3);
//    }
//
//    @Test
//    public void getAllTiposPersona() {
//        List<Configuracion> tiposPersona = configuracionServices.getAllTiposPersona();
//        assertNotNull(tiposPersona);
//        assertFalse(tiposPersona.isEmpty());
//
//        if (tiposPersona.size() < 2){
//            String tipo = "Trabajador";
//            if (configuracionServices.getConfiguracion(tipo) == null) {
//                configuracionServices.insertConfiguracion(tipo);
//            }
//            Configuracion tipoInsertado = configuracionServices.getConfiguracion(tipo);
//            assertNotNull(tipoInsertado);
//
//            tiposPersona = configuracionServices.getAllTiposPersona();
//            assertNotNull(tiposPersona);
//            assertFalse(tiposPersona.isEmpty());
//        }
//    }
//
//    @Test
//    public void deleteConfiguracion() {
//        String tipo = "Trabajador";
//        configuracionServices.deleteConfiguracion(tipo);
//        Configuracion deleted = configuracionServices.getConfiguracion(tipo);
//        assertNull(deleted);
//
//        configuracionServices.insertConfiguracion(tipo);
//    }
}
