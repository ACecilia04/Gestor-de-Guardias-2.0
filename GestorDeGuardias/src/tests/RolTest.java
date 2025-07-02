package tests;

import model.Rol;
import model.Usuario;
import org.junit.BeforeClass;
import org.junit.Test;
import services.RolServices;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;
import utils.exceptions.MultiplesErroresException;

import java.util.List;

import static org.junit.Assert.*;

public class RolTest {

    private static RolServices rolServices;

    @BeforeClass
    public static void setup() {
        rolServices = ServicesLocator.getInstance().getRolServices();
        Usuario usuarioLogueado = ServicesLocator.getInstance().getUsuarioServices().getUsuarioById(1L);
        ServicesLocator.getInstance().setUsuarioActual(usuarioLogueado);
    }


    @Test
    public void insertNameEmpty_throwException() {
        String rol = "";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.insertRol(rol);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Nombre no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertNameNull_throwException() {
        String rol = null;
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.insertRol(rol);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Nombre no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertNameExisting_throwException() {
        String rol = "Administrador";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.insertRol(rol);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Rol existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void updateNameEmpty_throwException() {
        String rol = "";
        String nuevoRol = "Administrator2";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.updateRol(rol, nuevoRol);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Rol inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNameNull_throwException() {
        String rol = null;
        String nuevoRol = "Administrator2";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.updateRol(rol, nuevoRol);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Rol inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNameNonExisting_throwException() {
        String rol = "Administrator";
        String nuevoRol = "Administrator2";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.updateRol(rol, nuevoRol);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Rol inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNameExisting_throwException() {
        String rol = "Usuario";
        String nuevoRol = "Administrador";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.updateRol(rol, nuevoRol);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Rol existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void deleteNameEmpty_throwException() {
        String rol = "";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.deleteRol(rol);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Rol inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void deleteNameNull_throwException() {
        String rol = "";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.deleteRol(rol);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Rol inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void deleteNameNonExisting_throwException() {
        String rol = "Administrator";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.deleteRol(rol);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Rol inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    //    @Test
    public void deleteRecordInUse_throwException() {
        String rol = "Usuario";
        boolean validaError = false;
        boolean performed = false;
        try {
            rolServices.deleteRol(rol);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("El rol no se puede eliminar porque está siendo utilizado");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void getNonExisting_returnNull() {
        String rol = "Administrator";
        Rol record = rolServices.getRol(rol);
        assertNull(record);
    }

    @Test
    public void getExistingName_success() {
        String nombre = "Usuario";
        Rol record = rolServices.getRol(nombre);
        assertNotNull(record);
    }


    @Test
    public void insert_get_update_delete_success() {
        String rol = "Administrator";
        try {
            rolServices.insertRol(rol);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        Rol record = rolServices.getRol(rol);
        assertNotNull(record);

        String nuevoRol = "Administrator2";
        try {
            rolServices.updateRol(rol, nuevoRol);
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        record = rolServices.getRol(nuevoRol);
        assertEquals(nuevoRol.toLowerCase(), record.getNombre().toLowerCase());

        try {
            rolServices.deleteRol(nuevoRol);
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        record = rolServices.getRol(nuevoRol);
        assertNull(record);
    }

    @Test
    public void getAll_success() {
        List<Rol> records = rolServices.getAllRoles();
        assertNotNull(records);
        assertFalse(records.isEmpty());
    }
}
