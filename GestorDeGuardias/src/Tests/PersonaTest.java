package Tests;

import model.TipoPersona;
import org.junit.*;
import services.PersonaServices;
import utils.abstracts.MainBaseDao;
import model.Persona;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class PersonaTest {

 private static PersonaServices personaServices;
 private static final String TEST_CI = "01010112345";

 @BeforeClass
 public static void setUp() {
  // Asegúrate de usar una base de datos de PRUEBAS
  MainBaseDao baseDao = new MainBaseDao();
  personaServices = new PersonaServices(baseDao);
 }

 @Test
 public void testInsertPersona() {
  personaServices.insertPersona("Ana", "García", 'f', TEST_CI, "Estudiante");

  Persona persona = personaServices.getPersonaByCi(TEST_CI);
  assertNotNull(persona);
  assertEquals("Ana", persona.getNombre());
  assertEquals("García", persona.getApellido());
  assertEquals('f', persona.getSexo());
  assertEquals(TEST_CI, persona.getCarnet());
  assertEquals("Estudiante", persona.getTipo());
  assertTrue(persona.isActivo());
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

  Persona updated = personaServices.getPersonaByCi(TEST_CI);
  assertEquals("Ana María", updated.getNombre());
  assertEquals(2, updated.getGuardiasDeRecuperacion());
 }

 @Test
 public void testGetAllPersonas() {
  List<Persona> personas = personaServices.getAllPersonas();
  assertNotNull(personas);
  assertTrue(personas.size() > 0);
 }

 @Test
 public void testGetPersonaByTipo() {
  List<Persona> estudiantes = personaServices.getPersonaByTipo(new TipoPersona("Estudiante") {

   public String obtenerTipo() {
    return "Estudiante";
   }
  }); // Se usa clase anónima en lugar de lambda

  assertNotNull(estudiantes);
  assertTrue(estudiantes.stream().anyMatch(p -> p.getCarnet().equals(TEST_CI)));
 }

 @Test
 public void testDeletePersona() {
  personaServices.deletePersonaByCi(TEST_CI);
  Persona deleted = personaServices.getPersonaByCi(TEST_CI);
  assertNull(deleted);
 }
}
