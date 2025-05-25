package services;

import utils.abstracts.MainBaseDao;

public class ServicesLocator {
    static ServicesLocator me = null;
    private ConfiguracionServices configuracionServices;
    private EsquemaServices esquemaServices;
    private HorarioServices horarioServices;
    private PeriodoNoPlanificableServices periodoNoPlanificableServices;
    private PersonaServices personaServices;
    private TipoPersonaServices tipoPersonaServices;
    private TurnoDeGuardiaServices turnoDeGuardiaServices;
    private UsuarioService usuarioService;
    private MainBaseDao mbDAO;

    public static ServicesLocator getServicesLocatorInstance(){
        if(me == null){
            return new ServicesLocator();
        }
        return me;
    }

    private ServicesLocator(){
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
