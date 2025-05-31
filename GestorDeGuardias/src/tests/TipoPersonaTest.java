package tests;

import model.TipoPersona;
import org.junit.BeforeClass;
import org.junit.Test;
import services.ServicesLocator;
import services.TipoPersonaServices;
import utils.dao.SqlServerCustomException;
import utils.exceptions.MultiplesErroresException;

import java.util.List;

import static org.junit.Assert.*;

public class TipoPersonaTest {

    private static TipoPersonaServices tipoPersonaServices;

    @BeforeClass
    public static void setup() {
        tipoPersonaServices = ServicesLocator.getInstance().getTipoPersonaServices();
    }

    @Test
    public void insertTipoPersona() {
        String tipo = "Estudiante";
        if (tipoPersonaServices.getTipoPersona(tipo) == null) {
            try {
                tipoPersonaServices.insertTipoPersona(tipo);
            } catch (MultiplesErroresException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getErrores());
            } catch (SqlServerCustomException e) {
                System.out.println(e.getMessage());
            }
        }
        TipoPersona tipoInsertado = tipoPersonaServices.getTipoPersona(tipo);
        assertNotNull(tipoInsertado);
    }


    @Test
    public void getTipoPersona() {
        String tipo = "Estudiante";
        TipoPersona tipoPersona = tipoPersonaServices.getTipoPersona(tipo);
        assertNotNull(tipoPersona);
        assertEquals("Estudiante", tipoPersona.getNombre());
    }

    @Test
    public void updateTipoPersona() {
        String tipo = "Estudiante";
        TipoPersona tp1 = tipoPersonaServices.getTipoPersona(tipo);
        assertNotNull(tp1);

        try {
            tipoPersonaServices.updateTipoPersona(tp1.getNombre(), "Estudiantil");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }

        TipoPersona tp2 = tipoPersonaServices.getTipoPersona("Estudiantil");
        assertNotNull(tp2);

        try {
            tipoPersonaServices.updateTipoPersona(tp2.getNombre(), tp1.getNombre());
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }

        TipoPersona tp3 = tipoPersonaServices.getTipoPersona(tipo);
        assertNotNull(tp3);
    }

    @Test
    public void getAllTiposPersona() {
        List<TipoPersona> tiposPersona = tipoPersonaServices.getAllTiposPersona();
        assertNotNull(tiposPersona);
        assertFalse(tiposPersona.isEmpty());

        if (tiposPersona.size() < 2){
            String tipo = "Trabajador";
            if (tipoPersonaServices.getTipoPersona(tipo) == null) {
                try {
                    tipoPersonaServices.insertTipoPersona(tipo);
                } catch (MultiplesErroresException e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getErrores());
                } catch (SqlServerCustomException e) {
                    System.out.println(e.getMessage());
                }
            }
            TipoPersona tipoInsertado = tipoPersonaServices.getTipoPersona(tipo);
            assertNotNull(tipoInsertado);

            tiposPersona = tipoPersonaServices.getAllTiposPersona();
            assertNotNull(tiposPersona);
            assertFalse(tiposPersona.isEmpty());
        }
    }

    @Test
    public void deleteTipoPersona() {
        String tipo = "Trabajador";
        try {
            tipoPersonaServices.deleteTipoPersona(tipo);
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        TipoPersona deleted = tipoPersonaServices.getTipoPersona(tipo);
        assertNull(deleted);

        try {
            tipoPersonaServices.insertTipoPersona(tipo);
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrores());
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
    }
}
