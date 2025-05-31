package tests;

import model.Rol;
import org.junit.BeforeClass;
import org.junit.Test;
import services.RolServices;
import services.ServicesLocator;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.util.List;

import static org.junit.Assert.*;

public class RolTest {

    private static RolServices rolServices;

    @BeforeClass
    public static void setup() {
        rolServices = ServicesLocator.getInstance().getRolServices();
    }

    @Test
    public void insertRol() {
        String tipo = "Administrador1";
        if (rolServices.getRol(tipo) == null) {
            try {
                rolServices.insertRol(tipo);
            } catch (MultiplesErroresException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getErrores());
            } catch (EntradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        }
        Rol tipoInsertado = rolServices.getRol(tipo);
        assertNotNull(tipoInsertado);
    }


    @Test
    public void getRol() {
        String tipo = "Administrador1";
        Rol rol = rolServices.getRol(tipo);
        assertNotNull(rol);
        assertEquals("Administrador1", rol.getNombre());
    }

    @Test
    public void updateRol() {
        String tipo = "Administrador1";
        Rol tp1 = rolServices.getRol(tipo);
        assertNotNull(tp1);

        rolServices.updateRol(tp1.getNombre(), "Administrador2");

        Rol tp2 = rolServices.getRol("Administrador2");
        assertNotNull(tp2);

        rolServices.updateRol(tp2.getNombre(), tp1.getNombre());

        Rol tp3 = rolServices.getRol(tipo);
        assertNotNull(tp3);
    }

    @Test
    public void getAllRoles() {
        List<Rol> tiposPersona = rolServices.getAllRoles();
        assertNotNull(tiposPersona);
        assertFalse(tiposPersona.isEmpty());

        if (tiposPersona.size() < 2){
            String tipo = "Usuario1";
            if (rolServices.getRol(tipo) == null) {
                try {
                    rolServices.insertRol(tipo);
                } catch (MultiplesErroresException e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getErrores());
                } catch (EntradaInvalidaException e) {
                    System.out.println(e.getMessage());
                }
            }
            Rol tipoInsertado = rolServices.getRol(tipo);
            assertNotNull(tipoInsertado);

            tiposPersona = rolServices.getAllRoles();
            assertNotNull(tiposPersona);
            assertFalse(tiposPersona.isEmpty());
        }
    }

    @Test
    public void deleteRol() {
        String tipo = "Usuario1";
        try {
            rolServices.deleteRol(tipo);
            Rol deleted = rolServices.getRol(tipo);
            assertNull(deleted);

            try {
                rolServices.insertRol(tipo);
            } catch (MultiplesErroresException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getErrores());
            } catch (EntradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } catch (EntradaInvalidaException e) {
            e.printStackTrace();

        }
    }
}
