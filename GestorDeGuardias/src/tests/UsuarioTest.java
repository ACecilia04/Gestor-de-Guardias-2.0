package tests;

import model.Rol;
import model.Usuario;
import org.junit.BeforeClass;
import org.junit.Test;
import services.ServicesLocator;
import services.UsuarioServices;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.util.List;

import static org.junit.Assert.*;

public class UsuarioTest {

    private static UsuarioServices usuarioServices;

    @BeforeClass
    public static void setup() {
        // Asegúrate de usar una base de datos de PRUEBAS
        usuarioServices = ServicesLocator.getInstance().getUsuarioServices();
    }

    @Test
    public void insertUsuario() {
        Usuario nuevoRecord = new Usuario("Usuario", "usuario", new Rol("Usuario"));
        try {
            usuarioServices.insertUsuario(nuevoRecord);
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrores());
        } catch (EntradaInvalidaException e) {
            System.out.println(e.getMessage());
        }
        Usuario insertado = usuarioServices.getUsuarioByNombre("Usuario");

        assertNotNull(insertado);
    }


    @Test
    public void getUsuario() {
        Usuario usuario = usuarioServices.getUsuarioByNombre("Usuario");
        assertNotNull(usuario);
        assertEquals("usuario", usuario.getContrasenna());
    }

   @Test
    public void updateUsuario() {
        String nombre = "Usuario";
        Usuario p1 = usuarioServices.getUsuarioByNombre(nombre);
        assertNotNull(p1);

        Usuario p2 = usuarioServices.getUsuarioByNombre(nombre);
        p2.setNombre("Ana María");
        p2.setContrasenna("password");

        usuarioServices.updateUsuario(p2);

        Usuario updated2 = usuarioServices.getUsuarioByNombre("Ana María");
        assertEquals("Ana María", updated2.getNombre());
        assertEquals("password", updated2.getContrasenna());

        usuarioServices.updateUsuario(p1);

        Usuario updated1 = usuarioServices.getUsuarioByNombre(nombre);
        assertEquals("Usuario", updated1.getNombre());
        assertEquals("usuario", updated1.getContrasenna());
    }

    @Test
    public void getAllUsuarios() {
        List<Usuario> usuarios = usuarioServices.getAllUsuarios();
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());

        if (usuarios.size() < 2){
            Usuario nuevoRecord = new Usuario("Usuario2", "usuario2", new Rol("Administrador"));
            try {
                usuarioServices.insertUsuario(nuevoRecord);
            } catch (MultiplesErroresException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getErrores());
            } catch (EntradaInvalidaException e) {
                System.out.println(e.getMessage());
            }

            usuarios = usuarioServices.getAllUsuarios();
            assertNotNull(usuarios);
            assertFalse(usuarios.isEmpty());
        }
    }

    @Test
    public void deleteUsuario() {
        Usuario record = usuarioServices.getUsuarioByNombre("Usuario");
        try {
            usuarioServices.deleteUsuario(record.getId());
        } catch (EntradaInvalidaException e) {
            System.out.println(e.getMessage());
        }
        Usuario deleted = usuarioServices.getUsuarioById(record.getId());
        assertNull(deleted);

        Usuario nuevoRecord = new Usuario("Usuario", "usuario", new Rol("Usuario"));
        try {
            usuarioServices.insertUsuario(nuevoRecord);
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrores());
        } catch (EntradaInvalidaException e) {
            System.out.println(e.getMessage());
        }
        Usuario insertado = usuarioServices.getUsuarioByNombre("Usuario");
        assertNotNull(insertado);
    }
}
