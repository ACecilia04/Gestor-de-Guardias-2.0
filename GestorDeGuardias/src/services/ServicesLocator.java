package services;

import utils.abstracts.MainBaseDao;

public class ServicesLocator {
    static ServicesLocator me = null;
    private final ConfiguracionServices configuracionServices;
    private final EsquemaServices esquemaServices;
    private final HorarioServices horarioServices;
    private final PeriodoNoPlanificableServices periodoNoPlanificableServices;
    private final PersonaServices personaServices;
    private final TipoPersonaServices tipoPersonaServices;
    private final TurnoDeGuardiaServices turnoDeGuardiaServices;
    private final UsuarioService usuarioService;
    private final MainBaseDao mbDAO;

    private ServicesLocator() {
        mbDAO = new MainBaseDao();
        configuracionServices = new ConfiguracionServices(mbDAO);
        esquemaServices = new EsquemaServices(mbDAO);
        horarioServices = new HorarioServices(mbDAO);
        periodoNoPlanificableServices = new PeriodoNoPlanificableServices(mbDAO);
        personaServices = new PersonaServices(mbDAO);
        tipoPersonaServices = new TipoPersonaServices(mbDAO);
        turnoDeGuardiaServices = new TurnoDeGuardiaServices(mbDAO);
        usuarioService = new UsuarioService(mbDAO);
    }

    public static ServicesLocator getServicesLocatorInstance() {
        if (me == null) {
            return new ServicesLocator();
        }
        return me;
    }

    public ConfiguracionServices getConfiguracionServices() {
        return configuracionServices;
    }

    public EsquemaServices getEsquemaServices() {
        return esquemaServices;
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

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

}
