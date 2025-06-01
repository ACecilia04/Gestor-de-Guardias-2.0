package services;

import utils.dao.MainBaseDao;

public class ServicesLocator {
    static ServicesLocator me = null;
    private final ConfiguracionServices configuracionServices;
    private final HorarioServices horarioServices;
    private final PeriodoNoPlanificableServices periodoNoPlanificableServices;
    private final PersonaServices personaServices;
    private final TipoPersonaServices tipoPersonaServices;
    private final TurnoDeGuardiaServices turnoDeGuardiaServices;
    private final UsuarioServices usuarioServices;
    private final RolServices rolServices;
    private final PlantillaServices plantillaServices;

    private ServicesLocator() {
        MainBaseDao mbDAO = new MainBaseDao();
        configuracionServices = new ConfiguracionServices(mbDAO);
        horarioServices = new HorarioServices(mbDAO);
        periodoNoPlanificableServices = new PeriodoNoPlanificableServices(mbDAO);
        personaServices = new PersonaServices(mbDAO);
        tipoPersonaServices = new TipoPersonaServices(mbDAO);
        turnoDeGuardiaServices = new TurnoDeGuardiaServices(mbDAO);
        usuarioServices = new UsuarioServices(mbDAO);
        rolServices = new RolServices(mbDAO);
        plantillaServices = new PlantillaServices(this);
    }

    public static ServicesLocator getInstance() {
        if (me == null) {
            return new ServicesLocator();
        }
        return me;
    }

    public ConfiguracionServices getConfiguracionServices() {
        return configuracionServices;
    }

    public HorarioServices getHorarioServices() {
        return horarioServices;
    }

    public PeriodoNoPlanificableServices getPeriodoNoPlanificableServices() {
        return periodoNoPlanificableServices;
    }

    public PersonaServices getPersonaServices() {
        return personaServices;
    }

    public TipoPersonaServices getTipoPersonaServices() {
        return tipoPersonaServices;
    }

    public TurnoDeGuardiaServices getTurnoDeGuardiaServices() {
        return turnoDeGuardiaServices;
    }

    public UsuarioServices getUsuarioServices() {
        return usuarioServices;
    }

    public RolServices getRolServices() {
        return rolServices;
    }

    public PlantillaServices getPlantillaServices(){ return plantillaServices;}
}
