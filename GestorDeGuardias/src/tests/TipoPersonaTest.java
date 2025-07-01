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
    public void insertNameEmpty_throwException() {
        String tipo = "";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.insertTipoPersona(tipo);
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
        String tipo = null;
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.insertTipoPersona(tipo);
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
        String tipo = "Estudiante";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.insertTipoPersona(tipo);
            performed = true;
        } catch (MultiplesErroresException e) {
            System.out.println(e.getMessage());
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Tipo de persona existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void updateNameEmpty_throwException() {
        String tipo = "";
        String nuevoTipo = "Estudiante2";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.updateTipoPersona(tipo, nuevoTipo);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Tipo de persona inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNameNull_throwException() {
        String tipo = null;
        String nuevoTipo = "Estudiante2";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.updateTipoPersona(tipo, nuevoTipo);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Tipo de persona inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNameNonExisting_throwException() {
        String tipo = "Estudiant";
        String nuevoTipo = "Estudiante2";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.updateTipoPersona(tipo, nuevoTipo);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Tipo de persona inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void updateNameExisting_throwException() {
        String tipo = "Estudiante";
        String nuevoTipo = "Trabajador";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.updateTipoPersona(tipo, nuevoTipo);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Tipo de persona existente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void deleteNameEmpty_throwException() {
        String tipo = "";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.deleteTipoPersona(tipo);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Tipo de persona inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void deleteNameNull_throwException() {
        String tipo = "";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.deleteTipoPersona(tipo);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Tipo de persona inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    @Test
    public void deleteNameNonExisting_throwException() {
        String tipo = "Estudiant";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.deleteTipoPersona(tipo);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("Tipo de persona inexistente");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }

    //    @Test
    public void deleteRecordInUse_throwException() {
        String tipo = "Estudiante";
        boolean validaError = false;
        boolean performed = false;
        try {
            tipoPersonaServices.deleteTipoPersona(tipo);
            performed = true;
        } catch (SqlServerCustomException e) {
            validaError = e.getMessage().equals("El tipo de persona no se puede eliminar porque está siendo utilizado");
        }
        assertTrue(validaError);
        assertFalse(performed);
    }


    @Test
    public void getNonExisting_returnNull() {
        String tipo = "Estudiant";
        TipoPersona record = tipoPersonaServices.getTipoPersona(tipo);
        assertNull(record);
    }

    @Test
    public void getExistingName_success() {
        String nombre = "Estudiante";
        TipoPersona record = tipoPersonaServices.getTipoPersona(nombre);
        assertNotNull(record);
    }


    @Test
    public void insert_get_delete_success() {
        String tipo = "Estudiante2";
        try {
            tipoPersonaServices.insertTipoPersona(tipo);
        } catch (MultiplesErroresException | SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        TipoPersona record = tipoPersonaServices.getTipoPersona(tipo);
        assertNotNull(record);

        String nuevoTipo = "Estudiante3";
        try {
            tipoPersonaServices.updateTipoPersona(tipo, nuevoTipo);
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        record = tipoPersonaServices.getTipoPersona(nuevoTipo);
        assertEquals(nuevoTipo.toLowerCase(), record.getNombre().toLowerCase());

        try {
            tipoPersonaServices.deleteTipoPersona(nuevoTipo);
        } catch (SqlServerCustomException e) {
            System.out.println(e.getMessage());
        }
        record = tipoPersonaServices.getTipoPersona(nuevoTipo);
        assertNull(record);
    }

    @Test
    public void getAll_success() {
        List<TipoPersona> records = tipoPersonaServices.getAllTiposPersona();
        assertNotNull(records);
        assertFalse(records.isEmpty());
    }
}
