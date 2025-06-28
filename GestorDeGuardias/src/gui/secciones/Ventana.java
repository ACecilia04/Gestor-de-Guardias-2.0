package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.internosComp.*;
import gui.pantallasEmergentes.*;
import model.*;
import services.ServicesLocator;
import services.TurnoDeGuardiaServices;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Ventana extends JFrame {

    public static final Dimension SIZE_ADVERTENCIA = new Dimension(530, 300);
    public static final Dimension SIZE_VENTANA = new Dimension(1200, 700);
    private static final long serialVersionUID = 1L;
    private static Ventana ventana;
    final int delayInSeconds = 2;
    private final Paleta paleta;
    private final JPanel contReal;
    private final JPanel zonaInferior;
    private BarraSuperior barraSup;
    private Menu menu;
    private JPanel contentPane;
    private JPanel panelVacio;
    private Cuadro overlayPanel;
    private JPanel panelVerPlanif;
    private AddPlanif panelAddPlanif;
    private PantallaEstudiantes pantallaEstudiantes;
    private PantallaTrabajadores pantallaTrabajadores;
    private MostrarPlanif pantallaPlanif;
    private AsistenciaPlanif pantallaAsistencia;
    private PantallaCump pantallaCump;
    private PantallaFacultad pantallaFacultad;
    private PanelConfig pantallaConfig;
    private Login login;
    private PantallaAddPersona pantallaAddPersona;
    private LocalDate inicio;
    private int distX;
    private int distY;
    private String pantallaActual = "panel1";
    private PantallaSelecPersona nuevaPantalla;
    private ServicesLocator servicesLocator;
    private Usuario usuarioLogueado;

    private Ventana() {
        paleta = new Paleta();
        servicesLocator = ServicesLocator.getInstance();
        inicializarCaractVentana();

        Timer timer = new Timer(delayInSeconds * 500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                setVisible(false);
//                login = new Login(overlayPanel);
//                usuarioLogueado = login.getUsuarioLogueado();
//                if (usuarioLogueado == null) System.exit(0);

                // Instanciar el menú pasando el usuario logueado
                menu = new Menu(zonaInferior, Ventana.this, new Usuario());
                zonaInferior.add(menu, BorderLayout.WEST);

                setVisible(true);
            }
        });
        timer.setRepeats(false);
        timer.start();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionar();
            }
        });

        contReal = new JPanel(new BorderLayout());
        Dimension auxContReal = new Dimension(this.getSize().width, this.getSize().height);
        contReal.setSize(auxContReal);
        contReal.setBackground(getBackground());

        zonaInferior = new JPanel(new BorderLayout());
        zonaInferior.setPreferredSize(new Dimension(contReal.getSize().width, contReal.getSize().height - 85));
        zonaInferior.setSize(new Dimension(contReal.getSize().width, contReal.getSize().height - 85));
        contReal.add(zonaInferior);

        // El menú ahora se instancia después del login

        inicializarPanelCambiante();
        barraSup = new BarraSuperior(contReal, pantallaPlanif, pantallaCump, pantallaConfig);
        contReal.add(barraSup, BorderLayout.NORTH);

        zonaInferior.add(panelVacio, BorderLayout.CENTER);
        contentPane.add(contReal, BorderLayout.SOUTH);
        contentPane.setBorder(BorderFactory.createLineBorder(paleta.getColorBorde(), 2));
    }

    public static Ventana getInstance() {
        if (ventana == null) {
            ventana = new Ventana();
        }
        return ventana;
    }

    public JPanel getPanelVerPlanif() {
        return panelVerPlanif;
    }

    public MostrarPlanif getPanelMostrarPlanif() {
        return pantallaPlanif;
    }

    private void inicializarCaractVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(Ventana.SIZE_VENTANA);
        setLocation(0, 0);
        setBackground(paleta.getColorFondo());
        setMinimumSize(new Dimension(800, 600));
        setTitle("Gestor de Guardias");

        overlayPanel = new Cuadro(this.getSize(), 0, Color.BLACK);
        overlayPanel.setTransparencia(Cuadro.transALTA);
        overlayPanel.setLocation(0, 0);
        getRootPane().setGlassPane(overlayPanel);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(this.getBackground());
        setContentPane(contentPane);
    }

    private void inicializarPanelCambiante() {
        panelVacio = new JPanel(new CardLayout());
        panelVacio.setSize(zonaInferior.getWidth() - 247, zonaInferior.getHeight()); // 247 = ancho menú

        distX = 247;
        distY = 85;
        inicializarEstudiantes();
        inicializarTrabajadores();
        pantallaPlanif = new MostrarPlanif();
        panelAddPlanif = new AddPlanif(panelVacio);
        pantallaAsistencia = new AsistenciaPlanif();
        pantallaCump = new PantallaCump();
        pantallaConfig = new PanelConfig();

        panelVerPlanif = new JPanel();
        panelVerPlanif.setLayout(new BorderLayout());

        panelVacio.add(pantallaPlanif, "panelPlanificaciones");
        panelVacio.add(pantallaEstudiantes, "panelEstudiantes");
        panelVacio.add(pantallaTrabajadores, "panelTrabajadores");
        panelVacio.add(panelAddPlanif, "panelAddPlanif");
        panelVacio.add(pantallaAsistencia, "panelAsistencia");
        panelVacio.add(panelVerPlanif, "panelVerPlanificaciones");
        panelVacio.add(pantallaCump, "panelCumplimiento");
        panelVacio.add(pantallaConfig, "panelConfig");
    }

    private void redimensionar() {
        Dimension frameSize = this.getSize();

        contReal.setPreferredSize(frameSize);
        zonaInferior.setPreferredSize(new Dimension(frameSize.width, frameSize.height - 85));
        zonaInferior.setSize(new Dimension(frameSize.width, frameSize.height - 85));
        panelVacio.setSize(zonaInferior.getWidth() - 247, zonaInferior.getHeight());
        for (Component comp1 : panelVacio.getComponents()) {
            if (comp1 instanceof JPanel) {
                comp1.setSize(panelVacio.getWidth(), panelVacio.getHeight());
            }
        }
        if (pantallaPlanif != null) {
            pantallaPlanif.mostrarTabla();
            if (Objects.equals(pantallaActual, "panelCumplimiento")) {
                ArrayList<DiaGuardia> dias = this.getPantallaCump().getTabla().getDias();
                getPantallaCump().setTabla(dias);
            }
        }

        revalidate();
        repaint();
    }


    public void mostrarPanel(String nombrePanel) {
//        String string1 = "<html><p>Procesando.....<br><br></p><html>";
//        Notificacion notificacion = new Notificacion(new Dimension(300, 200), "Generar automáticamente", string1);
//
//        SwingWorker myWorker = new SwingWorker<String, Void>() {
//            public String doInBackground() {
//o
//
//                notificacion.dispose();
//                return null;
//            }
//
//            public void done() {
//            }
//        };
//        myWorker.execute();
//        notificacion.setVisible(true);

        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        boolean cambiar = true;

        if (Objects.equals(pantallaActual, "panelAddPlanif") && !Objects.equals(nombrePanel, "panelAddPlanif")) {
            String string = "<html><p>Estás seguro de que quieres cambiar? <br> <br><br>Perderás la planifcacion no guardada</p><html>";
            Advertencia advertencia = new Advertencia(new Dimension(530, 300), "Advertencia", string, "Cancelar", "Aceptar");

            if (advertencia.getEleccion()) {
                cambiar = false;
            }
        }

        if (cambiar) {
            if (Objects.equals(nombrePanel, "panelPlanificaciones") && !Objects.equals(pantallaActual, "panelPlanificaciones")) {

                pantallaPlanif.actualizarPlanif();

            } else if (Objects.equals(nombrePanel, "panelTrab") && !Objects.equals(pantallaActual, "panelTrab")) {

                pantallaTrabajadores.revalidarTabla();

            } else if (Objects.equals(nombrePanel, "panelAsistencia") && !Objects.equals(pantallaActual, "panelAsistencia")) {

                pantallaAsistencia.actualizarPlanif();

            } else if (Objects.equals(nombrePanel, "panelAddPlanif")) {

                ArrayList<DiaGuardia> dias;
                boolean empezarHoy = false;

                TurnoDeGuardiaServices tgServ = servicesLocator.getTurnoDeGuardiaServices();
                ArrayList<TurnoDeGuardia> plan = tgServ.getAllTurnosDeGuardia();


                if (plan.isEmpty() || (tgServ.getTurnosAPartirDe(LocalDate.now()).isEmpty() && !plan.getLast().getFecha().isAfter(LocalDate.now()))) {
                    String string = "<html><p>No se encuentran registradas planificaciones <br> para el mes actual <br><br>Desea generar las planificaciones del resto del mes o del próximo?</p></html>";
                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Advertencia", string, "Mes Actual", "Próximo Mes");
                    empezarHoy = advertencia.getEleccion();
                }
                dias = servicesLocator.getPlantillaServices().crearPLantilla(empezarHoy);

                Dimension tablaDim = panelAddPlanif.getTablaDim(panelVacio.getWidth(), panelVacio.getHeight());
                Tabla tabla = new Tabla(tablaDim, paleta.getColorFondoTabla(), dias, panelAddPlanif.getTablaOpciones(),distX, distY, panelAddPlanif);
                panelAddPlanif.getTablaOpciones().setTabla(tabla);

                panelAddPlanif.addTabla(tabla);
            } else if (Objects.equals(nombrePanel, "panelConfig") && !Objects.equals(pantallaActual, "panelConfig")){
                pantallaConfig.cargarConfiguraciones();
            }

            switch (nombrePanel) {
                case "panelPlanificaciones" -> barraSup.mostrarPanel("panelOpcionesPlanifs");
                case "panelCumplimiento" -> barraSup.mostrarPanel("panelOpcionesAsistencia");
                case "panelConfig" -> barraSup.mostrarPanel("panelOpcionesConfig");
                case null, default -> barraSup.mostrarPanel("panelEd1");
            }

            if (!Objects.equals(pantallaActual, nombrePanel)) {
                cardLayout.show(panelVacio, nombrePanel);
                pantallaActual = nombrePanel;
            }
        }
    }

    private void inicializarEstudiantes() {
        pantallaEstudiantes = new PantallaEstudiantes();
        TablaEstudiantes customTabla = new TablaEstudiantes();
        customTabla.revalidarTabla(pantallaEstudiantes.checkFiltros((ArrayList<Persona>) servicesLocator.getPersonaServices().getPersonasByTipo(new TipoPersona("Estudiante"))));
        pantallaEstudiantes.addTabla(customTabla);
    }

    private void inicializarTrabajadores() {
        pantallaTrabajadores = new PantallaTrabajadores();
        TablaTrabajadores customTabla = new TablaTrabajadores();
        customTabla.revalidarTabla(pantallaTrabajadores.checkFiltros((ArrayList<Persona>) servicesLocator.getPersonaServices().getPersonasByTipo(new TipoPersona("Trabajador"))));
        pantallaTrabajadores.addTabla(customTabla);
    }

    public void addPantallaSelecPerona(PanelTurno turno, ArrayList<DiaGuardia> diasEnPantalla) throws EntradaInvalidaException {
        final TablaBuscarPersona tablaP = new TablaBuscarPersona();
        try {
            DiaGuardia fechaAux = turno.getFecha();
            Horario horarioAux = turno.getTurno().getHorario();

            nuevaPantalla = new PantallaSelecPersona(tablaP, (ArrayList<Persona>) servicesLocator.getPlantillaServices().getPersonasDisponibles(fechaAux.getFecha(), horarioAux, diasEnPantalla), turno);
        } catch (MultiplesErroresException e) {
            e.printStackTrace();
        }
    }

    public void addPantallaAddTrabajador() {
        pantallaAddPersona = new PantallaAddTrabajador();
        pantallaTrabajadores.revalidarTabla();
    }

    public void addPantallaAddEstudiante() {
        pantallaAddPersona = new PantallaAddEstudiante();
        pantallaEstudiantes.revalidarTabla();
    }

    public JPanel getPanelVacio() {
        return panelVacio;
    }

    public void mostrarFacultad() {
        pantallaFacultad = new PantallaFacultad();
    }

    public void editarPlanif(ArrayList<DiaGuardia> diasAux) {
        Dimension tablaDim = panelAddPlanif.getTablaDim(panelVacio.getWidth(), panelVacio.getHeight());
        Tabla tabla = new Tabla(tablaDim, paleta.getColorFondoTabla(), diasAux, panelAddPlanif.getTablaOpciones(), distX, distY, panelAddPlanif);
        barraSup.mostrarPanel("panelEd1");
        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        cardLayout.show(panelVacio, "panelAddPlanif");
        pantallaActual = "panelAddPlanif";
        panelAddPlanif.addTabla(tabla);
    }

    public PantallaCump getPantallaCump() {
        return pantallaCump;
    }

    public void cerrarSesion() {
        usuarioLogueado = null;
        getContentPane().removeAll();
        login = new Login(overlayPanel);
        getContentPane().add(login, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}