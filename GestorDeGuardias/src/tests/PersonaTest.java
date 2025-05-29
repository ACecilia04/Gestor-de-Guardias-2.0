package tests;

import model.Persona;
import model.TipoPersona;
import org.junit.BeforeClass;
import org.junit.Test;
import services.PersonaServices;
import services.ServicesLocator;
import utils.exceptions.MultiplesErroresException;

import java.util.List;

import static org.junit.Assert.*;

public class PersonaTest {

    private static final String TEST_CI = "01010112355";
    private static PersonaServices personaServices;

    @BeforeClass
    public static void setup() {
        // Asegúrate de usar una base de datos de PRUEBAS
        personaServices = ServicesLocator.getInstance().getPersonaServices();
    }

    @Test
    public void insertPersona() {
        Persona persona = personaServices.getPersonaByCi(TEST_CI);
        TipoPersona tipo = new TipoPersona("Estudiante");
        if(persona == null){
            Persona nuevaPersona = new Persona(TEST_CI, "Ana", "Garcia", "f", tipo);
            try {
                personaServices.insertPersona(nuevaPersona);
            } catch (MultiplesErroresException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getErrores());
            }
            persona = personaServices.getPersonaByCi(TEST_CI);
        }
        assertNotNull(persona);
        Persona nuevaPersona = new Persona(TEST_CI, "Ana2", "Garcia2", "f", tipo);
        try {
            personaServices.insertPersona(nuevaPersona);
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrores());
        }
    }


    @Test
    public void getPersona() {
        String tipo = "Estudiante";
        Persona persona = personaServices.getPersonaByCi(TEST_CI);
        assertNotNull(persona);
        assertEquals("Ana", persona.getNombre());
        assertEquals("Garcia", persona.getApellido());
        assertEquals("f", persona.getSexo().toLowerCase().substring(0, 1));
        assertEquals(TEST_CI, persona.getCarnet());
        assertEquals(tipo, persona.getTipo().getNombre());
        assertFalse(persona.isBorrado());
    }

    @Test
    public void updatePersona() {
        Persona p1 = personaServices.getPersonaByCi(TEST_CI);
        assertNotNull(p1);

        Persona p2 = personaServices.getPersonaByCi(TEST_CI);
        p2.setNombre("Ana María");
        p2.setGuardiasDeRecuperacion(2);

        personaServices.updatePersona(p2);

        Persona updated2 = personaServices.getPersonaByCi(TEST_CI);
        assertEquals("Ana María", updated2.getNombre());
        assertEquals(2, updated2.getGuardiasDeRecuperacion());

        personaServices.updatePersona(p1);

        Persona updated1 = personaServices.getPersonaByCi(TEST_CI);
        assertEquals("Ana", updated1.getNombre());
        assertEquals(0, updated1.getGuardiasDeRecuperacion());
    }

    @Test
    public void getAllPersonas() {
        List<Persona> personas = personaServices.getAllPersonas();
        assertNotNull(personas);
        assertFalse(personas.isEmpty());

        if (personas.size() < 2){
            TipoPersona tipo = new TipoPersona("Estudiante");
            Persona nuevaPersona = new Persona("02010112355", "Ana", "Garcia", "f", tipo);
            try {
                personaServices.insertPersona(nuevaPersona);
            } catch (MultiplesErroresException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getErrores());
            }

            personas = personaServices.getAllPersonas();
            assertNotNull(personas);
            assertFalse(personas.isEmpty());
        }
    }

    @Test
    public void getEstudiantes() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        List<Persona> personas = personaServices.getPersonasByTipo(tipo);
        assertNotNull(personas);
        assertTrue(personas.size() == 2);
        assertTrue(personas.stream().anyMatch(p -> p.getCarnet().equals(TEST_CI)));
    }

    @Test
    public void getTrabajadores() {
        TipoPersona tipo = new TipoPersona("Trabajador");
        List<Persona> personas = personaServices.getPersonasByTipo(tipo);
        assertNotNull(personas);
        assertTrue(personas.size() == 0);
        assertFalse(personas.stream().anyMatch(p -> p.getCarnet().equals(TEST_CI)));
    }

    @Test
    public void deletePersona() {
        personaServices.deletePersonaByCi(TEST_CI);
        Persona deleted = personaServices.getPersonaByCi(TEST_CI);
        assertNull(deleted);

        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevaPersona = new Persona(TEST_CI, "Ana", "Garcia", "f", tipo);
        try {
            personaServices.insertPersona(nuevaPersona);
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrores());
        }
    }
}
