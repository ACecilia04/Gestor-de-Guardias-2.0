package Tests;

import model.Persona;
import model.TipoPersona;
import org.junit.BeforeClass;
import org.junit.Test;
import services.PersonaServices;
import services.ServicesLocator;
import services.TipoPersonaServices;
import utils.abstracts.MainBaseDao;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class PersonaTest {

    private static final String TEST_CI = "01010112345";
    private static PersonaServices personaServices;
    private static TipoPersonaServices tipoPersonaServices;

    @BeforeClass
    public static void setUp() {
        // Asegúrate de usar una base de datos de PRUEBAS
        MainBaseDao baseDao = new MainBaseDao();
        personaServices = ServicesLocator.getServicesLocatorInstance().getPersonaServices();
        tipoPersonaServices = ServicesLocator.getServicesLocatorInstance().getTipoPersonaServices();
    }

    @Test
    public void testInsertPersona() {
//  tipoPersonaServices.insertTipoPersona("Estudiante");
//        personaServices.insertPersona("Ana", "García", 'f', TEST_CI, "Estudiante");

        Persona persona = personaServices.getPersonaByCi(TEST_CI);
        assertNotNull(persona);
        assertEquals("Ana", persona.getNombre());
        assertEquals("García", persona.getApellido());
        assertEquals('f', persona.getSexo().toLowerCase().charAt(0));
        assertEquals(TEST_CI, persona.getCarnet());
        assertEquals("Estudiante", persona.getTipo().toString());
        assertTrue((boolean)persona.isActivo());
    }

    @Test
    public void testUpdatePersona() {
        Persona p = personaServices.getPersonaByCi(TEST_CI);
        assertNotNull(p);

        personaServices.updatePersona(
                p.getId(),
                "Ana María", "García", 'f', TEST_CI,
                LocalDate.now(), 2, null, null,
                "Estudiante", true
        );
//  personaServices.updatePersona(
//          p.getId(),
//          "Ana María", null, null, null,
//          LocalDate.now(), 2, null, null,
//          null, true
//  );

        Persona updated = personaServices.getPersonaByCi(TEST_CI);
        assertEquals("Ana María", updated.getNombre());
        assertEquals(2, updated.getGuardiasDeRecuperacion());
    }

    @Test
    public void testGetAllPersonas() {
        List<Persona> personas = personaServices.getAllPersonas();
        assertNotNull(personas);
        assertFalse(personas.isEmpty());
    }

    @Test
    public void testGetPersonaByTipo() {
        List<Persona> estudiantes = personaServices.getPersonaByTipo(new TipoPersona("Estudiante")
//  {
//
//   public String obtenerTipo() {
//    return "Estudiante";
//   }
//  }
        ); // Se usa clase anónima en lugar de lambda

        assertNotNull(estudiantes);
        assertTrue(estudiantes.stream().anyMatch(p -> p.getCarnet().equals(TEST_CI)));
        estudiantes.forEach(System.out::println);
    }

    @Test
    public void testDeletePersona() {
        personaServices.deletePersonaByCi(TEST_CI);
        Persona deleted = personaServices.getPersonaByCi(TEST_CI);
        assertNull(deleted);
    }
}
