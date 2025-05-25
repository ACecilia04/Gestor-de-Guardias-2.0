package services;

import utils.abstracts.MainBaseDao;

public class ServicesLocator {
    ServicesLocator me = null;
    ConfiguracionServices configuracionServices;
    EsquemaServices esquemaServices;
    HorarioServices horarioServices;
    PeriodoNoPlanificableServices periodoNoPlanificableServices;
    PersonaServices personaServices;
    TipoPersonaServices tipoPersonaServices;
    TurnoDeGuardiaServices turnoDeGuardiaServices;
    UsuarioService usuarioService;
    MainBaseDao mbDAO;

    public ServicesLocator getServicesLocatorInstance(){
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
