package tests;

import model.Horario;
import org.junit.BeforeClass;
import org.junit.Test;
import services.HorarioServices;
import services.ServicesLocator;

import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class HorarioTest {

    private static HorarioServices horarioServices;

    @BeforeClass
    public static void setup() {
        horarioServices = ServicesLocator.getInstance().getHorarioServices();
    }

    @Test
    public void insertHorario() {
        LocalTime inicio = LocalTime.of(8, 0, 0);
        LocalTime fin = LocalTime.of(20, 0, 0);
        Horario newRecord = new Horario(inicio, fin);
        if (horarioServices.getHorarioByPk(inicio, fin) == null) {
            horarioServices.insertHorario(newRecord);
        }
        Horario recordInsertado = horarioServices.getHorarioByPk(inicio, fin);
        assertNotNull(recordInsertado);
        horarioServices.deleteHorario(recordInsertado.getId());
    }

    @Test
    public void updateHorario() {
        LocalTime inicio = LocalTime.of(8, 0, 0);
        LocalTime fin = LocalTime.of(20, 0, 0);
        Horario newRecord = new Horario(inicio, fin);
        if (horarioServices.getHorarioByPk(inicio, fin) == null) {
            horarioServices.insertHorario(newRecord);
        }
        Horario record = horarioServices.getHorarioByPk(inicio, fin);
        if ( record != null) {
            record.setInicio(LocalTime.of(12, 0, 0));
            horarioServices.updateHorario(record);

            Horario record2 = horarioServices.getHorarioById(record.getId());
            assertEquals(LocalTime.of(12, 0, 0), record2.getInicio());

            record.setInicio(inicio);
            horarioServices.updateHorario(record);

            horarioServices.deleteHorario(record.getId());
        }
    }

    @Test
    public void getAllHorarios() {
        List<Horario> horarios = horarioServices.getAllHorarios();
        if (horarios.isEmpty()){
            LocalTime inicio = LocalTime.of(20, 0, 0);
            LocalTime fin = LocalTime.of(8, 0, 0);
            Horario newRecord = new Horario(inicio, fin);
            horarioServices.insertHorario(newRecord);
            Horario insertedRecord1 = horarioServices.getHorarioByPk(inicio, fin);

            inicio = LocalTime.of(8, 0, 0);
            fin = LocalTime.of(20, 0, 0);
            Horario newRecord2 = new Horario(inicio, fin);
            horarioServices.insertHorario(newRecord2);
            Horario insertedRecord2 = horarioServices.getHorarioByPk(inicio, fin);

            horarios = horarioServices.getAllHorarios();
            assertNotNull(horarios);
            assertFalse(horarios.isEmpty());

            horarioServices.deleteHorario(insertedRecord1.getId());
            horarioServices.deleteHorario(insertedRecord2.getId());
        }
    }

    @Test
    public void getHorario() {
        LocalTime inicio = LocalTime.of(8, 0, 0);
        LocalTime fin = LocalTime.of(20, 0, 0);
        Horario record = horarioServices.getHorarioByPk(inicio, fin);
        if (record == null){
            Horario newRecord2 = new Horario(inicio, fin);
            horarioServices.insertHorario(newRecord2);
            Horario insertedRecord2 = horarioServices.getHorarioByPk(inicio, fin);
            assertNotNull(insertedRecord2);
            assertEquals(LocalTime.of(20, 0, 0), insertedRecord2.getFin());

            horarioServices.deleteHorario(insertedRecord2.getId());
        } else {
            assertEquals(LocalTime.of(20, 0, 0), record.getFin());
        }
    }

    @Test
    public void deleteHorario() {
        LocalTime inicio = LocalTime.of(8, 0, 0);
        LocalTime fin = LocalTime.of(20, 0, 0);
        Horario newRecord = new Horario(inicio, fin);
        if (horarioServices.getHorarioByPk(inicio, fin) == null) {
            horarioServices.insertHorario(newRecord);
        }
        Horario record = horarioServices.getHorarioByPk(inicio, fin);
        assertNotNull(record);

        horarioServices.deleteHorario(record.getId());
        Horario record2 = horarioServices.getHorarioById(record.getId());
        assertNull(record2);
    }
}
