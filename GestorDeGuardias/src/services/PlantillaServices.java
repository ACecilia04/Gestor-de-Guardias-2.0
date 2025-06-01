package services;

import model.DiaGuardia;
import model.Horario;
import model.TurnoDeGuardia;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class PlantillaServices {
    ServicesLocator sl;
    private HorarioServices horarioServices;
    private TurnoDeGuardiaServices turnoDeGuardiaServices;

    ConfiguracionServices configuracionServices;
    private PersonaServices personaServices;

    public PlantillaServices(ServicesLocator sl) {
        this.sl = sl;
        horarioServices = sl.getHorarioServices();
        turnoDeGuardiaServices = sl.getTurnoDeGuardiaServices();
        configuracionServices = sl.getConfiguracionServices();
        personaServices = sl.getPersonaServices();
    }

    public ArrayList<DiaGuardia> crearPLantilla(boolean empezarHoy) {
        LocalDate inicio;
        ArrayList<DiaGuardia> dias = new ArrayList<>();
        ArrayList<TurnoDeGuardia> turnosActuales = turnoDeGuardiaServices.getAllTurnosDeGuardia();

        if (empezarHoy)
            inicio = LocalDate.now();

        else {
            inicio = (turnosActuales.isEmpty())
                    ? LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth())
                    : turnosActuales.getLast().getFecha().plusDays(1);
        }

        int numDias = inicio.lengthOfMonth() - inicio.getDayOfMonth();

        for (int i = 0; i <= numDias; i++) {
            LocalDate fecha = inicio.plusDays(i);
            List<Horario> horarios = horarioServices.getHorariosDeFecha(fecha);
            ArrayList<TurnoDeGuardia> turnos = new ArrayList<>();
            for (Horario horario : horarios) {
                turnos.add(new TurnoDeGuardia(horario));
            }
            dias.add(new DiaGuardia(fecha, turnos));
        }
        return dias;
    }

}
