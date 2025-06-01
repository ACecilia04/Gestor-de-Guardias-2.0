package tests;

import model.Persona;
import model.TipoPersona;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import services.PersonaServices;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import java.util.List;

import static org.junit.Assert.*;

public class PersonaTest {

    private static PersonaServices personaServices;

    @BeforeClass
    public static void setup() {
        personaServices = ServicesLocator.getInstance().getPersonaServices();

        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("01010112355", "Ana", "Garcia", "f", tipo);
        try {
            personaServices.insertPersona(nuevoRecord);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            throw new RuntimeException(e);
        }

        Persona nuevoRecord2 = new Persona("10101012345", "Mario", "Menocal", "m", tipo);
        try {
            personaServices.insertPersona(nuevoRecord2);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void done() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        try {
            personaServices.deletePersonaById(record.getId());
        } catch (SqlServerCustomException e) {
            throw new RuntimeException(e);
        }

        Persona record2 = personaServices.getPersonaByCi("10101012345");
        try {
            personaServices.deletePersonaById(record2.getId());
        } catch (SqlServerCustomException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void insertCiEmpty_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("", "Tomás", "Palma", "m", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertCiNull_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona(null, "Tomás", "Palma", "m", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertCiIllegal_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("1234567890", "Tomás", "Palma", "m", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no válido: Longitud incorrecta.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertCiIllegal2_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("1234567890G", "Tomás", "Palma", "m", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no válido: Caracteres no numéricos.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertCiIllegal3_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("12345678901", "Tomás", "Palma", "m", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no válido: Mes incorrecto.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertCiIllegal4_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("12125678901", "Tomás", "Palma", "m", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no válido: Dia incorrecto.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertNombreEmpty_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("04121278901", "", "Palma", "m", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
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
    public void insertApellidoEmpty_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("04121278901", "Tomás", null, "m", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Apellido no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertSexoEmpty_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("04121278901", "Tomás", "Palma", null, tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Sexo no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertSexoUnknown_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("04121278901", "Tomás", "Palma", "r", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Sexo no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertTipoPersonaEmpty_throwException() {
        Persona nuevoRecord = new Persona("04121278901", "Tomás", "Palma", "m", null);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Tipo de persona no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertTipoPersonaUnknown_throwException() {
        TipoPersona tipo = new TipoPersona("Perico");
        Persona nuevoRecord = new Persona("04121278901", "Tomás", "Palma", "m", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Tipo de persona desconocido.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertExistingCi_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("01010112355", "Ana2", "Garcia", "f", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Persona existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void insertExistingNombre_throwException() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("01010112356", "Ana", "Garcia", "f", tipo);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.insertPersona(nuevoRecord);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Persona existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void updateCiEmpty_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setCarnet("");
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateCiNull_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setCarnet(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateCiIllegal_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setCarnet("1234567890");
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no válido: Longitud incorrecta.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateCiIllegal2_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setCarnet("1234567890G");
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no válido: Caracteres no numéricos.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateCiIllegal3_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setCarnet("12345678901");
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no válido: Mes incorrecto.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateCiIllegal4_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setCarnet("12125678901");
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no válido: Dia incorrecto.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNombreEmpty_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setNombre(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
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
    public void updateApellidoEmpty_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setApellido("");
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Apellido no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateSexoEmpty_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setSexo("");
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Sexo no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateSexoUnknown_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setSexo("g");
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Sexo no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateTipoPersonaEmpty_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setTipo(null);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Tipo de persona no especificado.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateTipoPersonaUnknown_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setTipo(new TipoPersona("Latón"));
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Tipo de persona desconocido.");
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNonExisting_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setId(99L);
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Persona inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNameExisting_throwException() {
        Persona record = personaServices.getPersonaByCi("01010112355");
        record.setCarnet("10101012345");
        record.setSexo("m");
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.updatePersona(record);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Persona existente");
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
            personaServices.deletePersonaById(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Persona inexistente");
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
            personaServices.deletePersonaById(id);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Persona inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void deleteCarnetEmpty_throwException() {
        String ci = null;
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.deletePersonaByCi(ci);
            performed = true;
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        } catch (EntradaInvalidaException e) {
            validaError = e.getMessage().equals("Carnet de identidad no especificado.");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void deleteCarnetNonExisting_throwException() {
        String ci = "72100313482";
        boolean validaError = false;
        boolean performed = false;
        try {
            personaServices.deletePersonaByCi(ci);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Persona inexistente");
        } catch (EntradaInvalidaException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void getNonExistingId_returnNull() {
        Long id = 1000L;
        Persona record = personaServices.getPersonaById(id);
        assertNull(record);
    }

    @Test
    public void getNonExistingCI_returnNull() {
        String ci = "72100313482";
        Persona record = personaServices.getPersonaByCi(ci);
        assertNull(record);
    }

    @Test
    public void getExistingCi_success() {
        String ci = "01010112355";
        Persona record = personaServices.getPersonaByCi(ci);
        assertNotNull(record);
        Persona record2 = personaServices.getPersonaById(record.getId());
        assertNotNull(record2);
    }


    @Test
    public void getAll_success() {
        List<Persona> records = personaServices.getAllPersonas();
        assertNotNull(records);
        assertTrue(records.size() > 0);
    }


    @Test
    public void insert_get_update_delete_success() {
        TipoPersona tipo = new TipoPersona("Estudiante");
        Persona nuevoRecord = new Persona("04121278901", "Tomás", "Palma", "m", tipo);
        try {
            personaServices.insertPersona(nuevoRecord);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        Persona record = personaServices.getPersonaByCi("04121278901");
        assertNotNull(record);

        String nuevoApellido = "04121278901";
        record.setApellido(nuevoApellido);
        try {
            personaServices.updatePersona(record);
        } catch (SqlServerCustomException | MultiplesErroresException e) {
            System.out.println(e.getMessage());
        }
        record = personaServices.getPersonaById(record.getId());
        assertEquals(nuevoApellido.toLowerCase(), record.getApellido().toLowerCase());

        try {
            personaServices.deletePersonaById(record.getId());
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        record = personaServices.getPersonaById(record.getId());
        assertNull(record);
    }

}
