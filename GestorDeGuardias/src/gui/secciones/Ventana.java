package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.internosComp.*;
import gui.pantallasEmergentes.*;
import logica.principal.DiaGuardia;
import model.Horario;
import model.TipoPersona;
import services.Gestor;
import services.ServicesLocator;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.util.ArrayList;

public class Ventana extends JFrame {

    public static final Dimension SIZE_ADVERTENCIA = new Dimension(530, 300);
    public static final Dimension SIZE_VENTANA = new Dimension(1792, 932);
    private static final long serialVersionUID = 1L;
    private static Ventana ventana;
    //Demora en aparecer el panel de inicio
    final int delayInSeconds = 2;
    private final Paleta paleta;
    //Paneles para layouts
    private final JPanel contReal;
    private final JPanel zonaInferior; //Inferior
    //Pedazos de informacion
    private final BarraSalida barraSalida;
    private final BarraSuperior barraSup;
    private final Menu menu;
    //Auxiliar
    private JPanel contentPane;
    private JPanel panelVacio;
    //Fondo negro
    private Cuadro overlayPanel;
    //Paneles de cambio
    private JPanel panel1, panel5;
    private JPanel panel2;
    private AddPlanif panelAddPlanif;
    private PantallaEstudiantes pantallaEstudiantes;
    private PantallaTrabajadores pantallaTrabajadores;
    private MostrarPlanif pantallaPlanif;
    private ArchivarPlanif pantallaArchivar;
    private PantallaCump pantallaCump;

    private PantallaFacultad pantallaFacultad;
    @SuppressWarnings("unused")
    private Login login;
    private PantallaAddPersona pantallaAddPersona;
    //Fechas para iniciar la tabla
    private LocalDate inicio;
    private int distX;
    private int distY;
    private String pantallaActual = "panel1";
    @SuppressWarnings("unused")
    private PantallaSelecPersona nuevaPantalla;

    private Ventana() {
        paleta = new Paleta();

        //Caracteristicas de la ventana
        inicializarCaractVentana();

        // Crear un temporizador para mostrar el JDialog despu�s de un retraso
        Timer timer = new Timer(delayInSeconds * 500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //******************************************************************************************************************
                //login = new Login(overlayPanel);
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


        //BarraSalida
        barraSalida = new BarraSalida(this);
        contentPane.add(barraSalida, BorderLayout.NORTH);

        //ContentPane real
        contReal = new JPanel(new BorderLayout());

        Dimension auxContReal = new Dimension(this.getSize().width, this.getSize().height - barraSalida.getSize().height);
        contReal.setSize(auxContReal);
        contReal.setBackground(getBackground());

        //Crear&Add Barra Superior
        barraSup = new BarraSuperior(contReal);
        contReal.add(barraSup, BorderLayout.NORTH);

        //Contenedor de la zona Inferior
        zonaInferior = new JPanel(new BorderLayout());
        zonaInferior.setPreferredSize(new Dimension(contReal.getSize().width, contReal.getSize().height - barraSup.getPreferredSize().height));
        zonaInferior.setSize(new Dimension(contReal.getSize().width, contReal.getSize().height - barraSup.getPreferredSize().height));
        contReal.add(zonaInferior);


        //Crear&Add Menu
        menu = new Menu(zonaInferior, this);
        zonaInferior.add(menu, BorderLayout.WEST);

        //Crear PanelCambiante
        inicializarPanelCambiante();

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

    public JPanel getPanel1() {
        return panel1;
    }

    private void inicializarCaractVentana() {
        //Caracteristicas de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        setSize(Ventana.SIZE_VENTANA);
        setLocation(0, 0);
        setBackground(paleta.getColorFondo());
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Cuadro.redMED, Cuadro.redMED));

        // Crear el panel de superposici�n
        overlayPanel = new Cuadro(this.getSize(), 0, Color.BLACK);
        overlayPanel.setTransparencia(Cuadro.transALTA);
        overlayPanel.setLocation(0, 0);
        getRootPane().setGlassPane(overlayPanel);

        //ContentPane
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(this.getBackground());
        setContentPane(contentPane);
    }

    private void inicializarPanelCambiante() {
        panelVacio = new JPanel(new CardLayout());
        panelVacio.setSize(zonaInferior.getWidth() - menu.getWidth(), zonaInferior.getHeight());

        distX = menu.getSize().width;
        distY = barraSup.getPreferredSize().height + barraSalida.getSize().height;

        //Crear paneles que cambiar [BORRAR MIENTRAS SE CREEN LOS REALES]
        inicializarEstudiantes();
        inicializarTrabajadores();
        pantallaPlanif = new MostrarPlanif();
        panelAddPlanif = new AddPlanif(panelVacio);
        pantallaArchivar = new ArchivarPlanif();
        pantallaCump = new PantallaCump();

        //Paneles Auxiliares
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel5 = new JPanel();
        panel5.setBackground(Color.MAGENTA);

        panelVacio.add(pantallaPlanif, "panel1");
        panelVacio.add(pantallaEstudiantes, "panel2");
        panelVacio.add(pantallaTrabajadores, "panel3");
        panelVacio.add(panelAddPlanif, "panel4");
        panelVacio.add(pantallaArchivar, "panel5");
        panelVacio.add(panel1, "panel6");
        panelVacio.add(pantallaCump, "panel7");
    }

    private void redimensionar() {
        Dimension frameSize = this.getSize();

        contReal.setSize(frameSize.width, frameSize.height - barraSalida.getSize().height);
        zonaInferior.setPreferredSize(new Dimension(frameSize.width, frameSize.height - barraSup.getPreferredSize().height - barraSalida.getSize().height));
        zonaInferior.setSize(new Dimension(frameSize.width, frameSize.height - barraSup.getPreferredSize().height - barraSalida.getSize().height));
        panelVacio.setSize(zonaInferior.getWidth() - menu.getWidth(), zonaInferior.getHeight());
        for (Component comp1 : panelVacio.getComponents()) {
            if (comp1 instanceof JPanel) {
                comp1.setSize(panelVacio.getWidth(), panelVacio.getHeight());
            }
        }

        pantallaPlanif.mostrarTabla();
        if (pantallaActual == "panel7") {
            ArrayList<DiaGuardia> dias = this.getPantallaCump().getTabla().getDias();
            getPantallaCump().setTabla(dias);
        }


        revalidate();
        repaint();
    }


    public void mostrarPanel(String nombrePanel) {
        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        boolean cambiar = true;
        if (pantallaActual == "panel4" && nombrePanel != "panel4") {
            String string = "<html><p>Estas seguro de que quieres cambiar? <br> <br><br>Perderas la planifcacion no guardada</p><html>";
            Advertencia advertencia = new Advertencia(new Dimension(530, 300), "Advertencia", string, "Cancelar", "Aceptar");

            if (advertencia.getEleccion()) {
                cambiar = false;
            }
        }

        if (cambiar) {
            if (nombrePanel == "panel1" && pantallaActual != "panel1") {
                pantallaPlanif.actualizarPlanif();
            }


            if (nombrePanel == "panel3" && pantallaActual != "panel3") {
                pantallaTrabajadores.revalidarTabla();
            }

            if (nombrePanel == "panel5" && pantallaActual != "panel5") {

                pantallaArchivar.actualizarPlanif();

            }


            if (nombrePanel == "panel4") {
                //barraSup.mostrarPanel("panelEd2");
                ArrayList<DiaGuardia> dias = new ArrayList<>();
                boolean opcion = false;

                Gestor gestorAux = Gestor.getInstance();
                ArrayList<DiaGuardia> plan = gestorAux.getPlanDeGuardias();
                if (plan.isEmpty() || (gestorAux.getPlanificaciones(LocalDate.now()).isEmpty() && !plan.get(plan.size() - 1).getFecha().isAfter(LocalDate.now()))) {
                    String string = "<html><p>No se encuentran registradas planificaciones <br> para el mes actual <br><br>Desea generar las planificaciones del resto del mes o del pr�ximo?</p></html>";
                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Advertencia", string, "Mes Actual", "Pr�ximo Mes");
                    opcion = advertencia.getEleccion();
                }

                try {
                    dias = Gestor.getInstance().crearPlantilla(opcion);
                } catch (EntradaInvalidaException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Tabla tabla = new Tabla(AddPlanif.getTablaDim(), paleta.getColorFondoTabla(), dias, panelAddPlanif.getTablaOpciones(), distX, distY, panelAddPlanif);

                panelAddPlanif.addTabla(tabla);

            } else {
                barraSup.mostrarPanel("panelEd1");
            }

            if (pantallaActual != nombrePanel) {
                cardLayout.show(panelVacio, nombrePanel);
                pantallaActual = nombrePanel;
            }


        }
    }

    private void inicializarEstudiantes() {


        pantallaEstudiantes = new PantallaEstudiantes();

        TablaEstudiantes customTabla = new TablaEstudiantes();
        customTabla.revalidarTabla(pantallaEstudiantes.checkFiltros(ServicesLocator.getInstance().getPersonaServices().getPersonaByTipo(new TipoPersona("Estudiante"))));
        pantallaEstudiantes.addTabla(customTabla);
    }

    private void inicializarTrabajadores() {

        pantallaTrabajadores = new PantallaTrabajadores();

        TablaTrabajadores customTabla = new TablaTrabajadores();
        customTabla.revalidarTabla(pantallaTrabajadores.checkFiltros(ServicesLocator.getInstance().getPersonaServices().getPersonaByTipo(new TipoPersona("Trabajador"))));
        pantallaTrabajadores.addTabla(customTabla);
    }

    public void addPantallaSelecPerona(PanelTurno turno, ArrayList<DiaGuardia> diasEnPantalla) throws EntradaInvalidaException {
        final TablaBuscarPersona tablaP = new TablaBuscarPersona();
        try {
            DiaGuardia fechaAux = turno.getFecha();
            Horario horarioAux = turno.getTurno().getHorario();

            nuevaPantalla = new PantallaSelecPersona(tablaP, Gestor.getInstance().getPersonasDisponibles(fechaAux.getFecha(), horarioAux, diasEnPantalla), turno);
        } catch (MultiplesErroresException e) {
            // TODO Auto-generated catch block
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
        Tabla tabla = new Tabla(AddPlanif.getTablaDim(), paleta.getColorFondoTabla(), diasAux, panelAddPlanif.getTablaOpciones(), distX, distY, panelAddPlanif);
        barraSup.mostrarPanel("panelEd1");
        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        cardLayout.show(panelVacio, "panel4");
        pantallaActual = "panel4";
        panelAddPlanif.addTabla(tabla);
    }

    public JPanel getPanel2() {
        return panel2;
    }

    public PantallaCump getPantallaCump() {
        return pantallaCump;
    }

    public boolean isMaximized() {
        return barraSalida.isMaximized();
    }


}


