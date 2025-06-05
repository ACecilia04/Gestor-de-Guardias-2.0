package tests;

import model.Rol;
import model.Usuario;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import services.ServicesLocator;
import services.UsuarioServices;
import utils.dao.SqlServerCustomException;
import utils.exceptions.MultiplesErroresException;

import java.util.List;

import static org.junit.Assert.*;

public class UsuarioTest {

    private static UsuarioServices usuarioServices;

    @BeforeClass
    public static void setup() {
        usuarioServices = ServicesLocator.getInstance().getUsuarioServices();

        Usuario nuevoRecord = new Usuario("Usuario1", "usuario", new Rol("Usuario"));
        try {
            usuarioServices.insertUsuario(nuevoRecord);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            throw new RuntimeException(e);
        }

        Usuario nuevoRecord2 = new Usuario("Usuario11", "usuario11", new Rol("Usuario"));
        try {
            usuarioServices.insertUsuario(nuevoRecord2);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void done() {
        Usuario record = usuarioServices.getUsuarioByNombre("Usuario1");
        try {
            usuarioServices.deleteUsuario(record.getId());
        } catch (SqlServerCustomException e) {
            throw new RuntimeException(e);
        }

        Usuario record2 = usuarioServices.getUsuarioByNombre("Usuario11");
        try {
            usuarioServices.deleteUsuario(record2.getId());
        } catch (SqlServerCustomException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void insertNameEmpty_throwException() {
        Usuario nuevoRecord = new Usuario("", "usuario", new Rol("Usuario"));
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.insertUsuario(nuevoRecord);
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
        Usuario nuevoRecord = new Usuario(null, "usuario", new Rol("Usuario"));
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.insertUsuario(nuevoRecord);
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
    public void insertContrasenaEmpty_throwException() {
        Usuario nuevoRecord = new Usuario("Usuario", "", new Rol("Usuario"));
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.insertUsuario(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Contraseña no especificada.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertContrasenaNull_throwException() {
        Usuario nuevoRecord = new Usuario("Usuario", null, new Rol("Usuario"));
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.insertUsuario(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Contraseña no especificada.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertRolNull_throwException() {
        Usuario nuevoRecord = new Usuario("Usuario", "usuario", null);
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.insertUsuario(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Rol no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertExisting_throwException() {
        Usuario nuevoRecord = new Usuario("Usuario1", "usuario", new Rol("Usuario"));
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.insertUsuario(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Usuario existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void updateNameEmpty_throwException() {
        Usuario record = usuarioServices.getUsuarioByNombre("Usuario1");
        record.setNombre("");
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.updateUsuario(record);
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
    public void updateNameNull_throwException() {
        Usuario record = usuarioServices.getUsuarioByNombre("Usuario1");
        record.setNombre(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.updateUsuario(record);
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
    public void updateContrasenaEmpty_throwException() {
        Usuario record = usuarioServices.getUsuarioByNombre("Usuario1");
        record.setContrasenna("");
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.updateUsuario(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Contraseña no especificada.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateContrasenaNull_throwException() {
        Usuario record = usuarioServices.getUsuarioByNombre("Usuario1");
        record.setContrasenna(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.updateUsuario(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Contraseña no especificada.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateRolNull_throwException() {
        Usuario record = usuarioServices.getUsuarioByNombre("Usuario1");
        record.setRol(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.updateUsuario(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Rol no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNonExisting_throwException() {
        Usuario record = new Usuario("Usuario99", "usuario99", new Rol("Usuario"));
        record.setId(99L);
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.updateUsuario(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Usuario inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNameExisting_throwException() {
        Usuario record = usuarioServices.getUsuarioByNombre("Usuario1");
        record.setNombre("Usuario11");
        boolean validaError = false;
        boolean performed = false;
        try {
            usuarioServices.updateUsuario(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Usuario existente");
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
            usuarioServices.deleteUsuario(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Usuario inexistente");
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
            usuarioServices.deleteUsuario(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Usuario inexistente");
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
            usuarioServices.deleteUsuario(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Usuario inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void getNonExistingId_returnNull() {
        Long id = 1000L;
        Usuario record = usuarioServices.getUsuarioById(id);
        assertNull(record);
    }

    @Test
    public void getNonExistingName_returnNull() {
        String nombre = "User1";
        Usuario record = usuarioServices.getUsuarioByNombre(nombre);
        assertNull(record);
    }

    @Test
    public void getExistingName_success() {
        String nombre = "Usuario1";
        Usuario record = usuarioServices.getUsuarioByNombre(nombre);
        assertNotNull(record);
        Usuario record2 = usuarioServices.getUsuarioById(record.getId());
        assertNotNull(record2);
    }


    @Test
    public void insert_get_update_delete_success() {
        Usuario nuevoRecord = new Usuario("Usuario2", "usuario", new Rol("Usuario"));
        try {
            usuarioServices.insertUsuario(nuevoRecord);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        Usuario record = usuarioServices.getUsuarioByNombre("Usuario2");
        assertNotNull(record);

        String nuevoNombre = "Usuario21";
        record.setNombre(nuevoNombre);
        try {
            usuarioServices.updateUsuario(record);
        } catch (SqlServerCustomException | MultiplesErroresException e) {
            System.out.println(e.getMessage());
        }
        record = usuarioServices.getUsuarioById(record.getId());
        assertEquals(nuevoNombre.toLowerCase(), record.getNombre().toLowerCase());

        try {
            usuarioServices.deleteUsuario(record.getId());
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        record = usuarioServices.getUsuarioById(record.getId());
        assertNull(record);
    }

    @Test
    public void getAll_success() {
        List<Usuario> records = usuarioServices.getAllUsuarios();
        assertNotNull(records);
        assertTrue(records.size() > 0);
    }
}
