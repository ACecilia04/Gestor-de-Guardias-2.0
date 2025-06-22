package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.CustomSplitPane;
import gui.componentes.CustomTabla;
import gui.pantallasEmergentes.Advertencia;
import gui.secciones.Ventana;
import model.DiaGuardia;
import services.ServicesLocator;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelOpcionesPlanif extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JPanel superior;
    private final CustomSplitPane splitPane;
        private final int separacion = 10;
//        private CustomSplitPane splitPaneInterno;
//    private JPanel panel1;
    private final JPanel panelControl;
    private final JPanel panelTablaSeleccionados;
    private final JPanel panelGuardar;
    private final CustomTabla tablaSelec;
//        private final int x = 40;
//    private final int y = separacion + 10;
    private final Boton botonAddPersona;
    private final Boton btnGuardar;
    private final Boton botonAut;
    Paleta paleta = new Paleta();

    private Tabla tabla;


    public PanelOpcionesPlanif(Dimension dimension) {
        setPreferredSize(dimension);
        setBackground(paleta.getColorFondoTabla());
        setLayout(new BorderLayout());

        //Superior
        superior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        superior.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        superior.setBackground(getBackground());

        add(superior, BorderLayout.NORTH);


        //Panel2
        panelControl = new JPanel(null);
        panelControl.setBackground(getBackground());
        botonAut = new Boton("Auto Generación");
        botonAut.addIcono("/iconos/Espiral.png");
        botonAut.setNuevoSize(new Dimension(botonAut.getSize().width + 10, botonAut.getSize().height + 10));
        botonAut.cambiarIconTextGap(10);
        botonAut.setColorLetra(Color.WHITE);
        botonAut.setColorLetraPres(Color.WHITE);
        botonAut.setColorFondo(paleta.getColorCaracteristico());
        Dimension aux = new Dimension(botonAut.getSize().width + 30, botonAut.getSize().height);
        botonAut.setNuevoSize(aux);

        botonAut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                generarAutomaticamente();
            }
        });

        tablaSelec = new CustomTabla("Seleccionados");
        botonAddPersona = new Boton("Añadir Persona");
        botonAddPersona.setNuevoSize(new Dimension(botonAut.getSize().width + 10, botonAut.getSize().height + 10));

        aux = new Dimension(botonAut.getSize().width, botonAut.getSize().height);
        botonAddPersona.setNuevoSize(aux);

        botonAddPersona.setLocation((this.getPreferredSize().width - botonAut.getWidth()) / 2, 20);
        int yAux = botonAddPersona.getLocation().y + botonAddPersona.getPreferredSize().height + 25;
        botonAut.setLocation((this.getPreferredSize().width - botonAut.getWidth()) / 2, yAux);

        actualizarColorBoton();


        panelControl.add(botonAut);
        panelControl.add(botonAddPersona);
        panelControl.setMinimumSize(new Dimension(getPreferredSize().width, 150));

        //Panel3
        panelTablaSeleccionados = new JPanel(new BorderLayout());
        panelTablaSeleccionados.setBackground(getBackground());


        panelTablaSeleccionados.add(tablaSelec, BorderLayout.CENTER);

        panelTablaSeleccionados.setMinimumSize(new Dimension(this.getPreferredSize().width, 200));

        splitPane = new CustomSplitPane(panelControl, panelTablaSeleccionados, JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);

        //Panel4 Guardar
        panelGuardar = new JPanel(null);
        panelGuardar.setBackground(getBackground());
        panelGuardar.setPreferredSize(new Dimension(this.getPreferredSize().width, 100));
        btnGuardar = new Boton("Guardar");

        btnGuardar.setNuevoSize(new Dimension(140, 40));
        btnGuardar.setBordeado(true);
        btnGuardar.setColorPresionado(paleta.getColorCaracteristico());

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        int x = (panelGuardar.getPreferredSize().width - btnGuardar.getSize().width) / 2;
        int y = (panelGuardar.getPreferredSize().height - btnGuardar.getSize().height) / 2;
        btnGuardar.setLocation(x, y);
        panelGuardar.add(btnGuardar);
        add(panelGuardar, BorderLayout.SOUTH);

        //Bordes
        Border border = BorderFactory.createMatteBorder(0, 3, 0, 0, paleta.getColorBorde());
        Border bordeMargen = BorderFactory.createEmptyBorder(10, 15, 0, 0);
        Border margenDoubleBorder = BorderFactory.createCompoundBorder(border, bordeMargen);

        splitPane.setBorder(border);
        panelGuardar.setBorder(border);

        superior.setBorder(margenDoubleBorder);
        setBorder(border);

    }

    public void actualizarTablaSelec(ArrayList<PanelDiaBase> panelesCasillas) {
        tablaSelec.limpiarTabla();
        ArrayList<Component> panelesSelec = new ArrayList<>();

        for (PanelDiaBase e : panelesCasillas) {
            String inicio = "   " + e.getFechaNumero() + "   " + e.getFechaSemana() + "   :   ";
            ArrayList<PanelTurno> panelesTurno = e.getPanelesTurno();

            if (e.isSeleccionado()) {
                for (PanelTurno a : panelesTurno) {
                    tablaSelec.agregarFila(inicio + a.getHorario().getText());
                    panelesSelec.add(a);
                }

            } else {

                for (PanelTurno a : panelesTurno) {
                    if (a.isSeleccionado()) {
                        tablaSelec.agregarFila(inicio + a.getHorario().getText());
                        panelesSelec.add(a);
                    }
                }
            }
        }
        tablaSelec.setComponentes(panelesSelec);
        actualizarColorBoton();
    }

    public void actualizarColorBoton() {
        if (tablaSelec.getCantFilas() == 1) {
            botonAddPersona.setColorLetra(Color.WHITE);
            botonAddPersona.setColorLetraPres(Color.WHITE);
            botonAddPersona.setColorFondo(paleta.getColorCaracteristico());
            botonAddPersona.setSeleccionable(true);

        } else {
            botonAddPersona.setColorLetra(Color.GRAY.darker());
            botonAddPersona.setColorFondo(Color.LIGHT_GRAY);
            botonAddPersona.setSeleccionable(false);

        }
    }

    public Boton getBtnGuardar() {
        return btnGuardar;
    }

    public Boton getBotonAut() {
        return botonAut;
    }

    public Boton getBotonAddPersona() {
        return botonAddPersona;
    }

    public CustomTabla getTablaSelec() {
        return tablaSelec;
    }

    public ArrayList<DiaGuardia> getDiasSeleccionados() {
        ArrayList<DiaGuardia> diasAux = new ArrayList<>();
        ArrayList<Component> comp = tablaSelec.getComponentes();
        for (Component a : comp) {
            if (a instanceof PanelTurno) {
                diasAux.add(((PanelTurno) a).getFecha());
            }

        }

        return diasAux;

    }

    public void generarAutomaticamente(){

        final ArrayList<DiaGuardia> diasAPlanificar = !getDiasSeleccionados().isEmpty()
                ? getDiasSeleccionados()
                : tabla.getDias();

        Advertencia procesandoDialog = new Advertencia( new Dimension(530, 300),
                "Procesando",
                "<html><p style='text-align: center;'>Generando planificación, por favor espere...</p></html>",
                "Cerrar", false
        );
        procesandoDialog.setModalityType(Dialog.ModalityType.MODELESS);
        procesandoDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        procesandoDialog.setVisible(true);

        new Thread(() -> {
            try {
                ServicesLocator.getInstance()
                        .getPlantillaServices()
                        .crearPlanificacionAutomaticamente(diasAPlanificar);

            } catch (MultiplesErroresException e1) {
                StringBuilder stringAux = new StringBuilder();
                for (String error : e1.getErrores()) {
                    stringAux.append(error).append("<br>");
                }
                String msg = "<html><p style='text-align: center;'> ERROR <br><br>" + stringAux + "</p></html>";
                SwingUtilities.invokeLater(() -> new Advertencia(
                        Ventana.SIZE_ADVERTENCIA, "Errores", msg, "Aceptar", true));

            } catch (EntradaInvalidaException e1) {
                String msg = "<html><p style='text-align: center;'> ERROR <br><br><br>" + e1.getMessage() + "</p></html>";
                SwingUtilities.invokeLater(() -> new Advertencia(
                        Ventana.SIZE_ADVERTENCIA, "Error", msg, "Aceptar", true));

            } finally {
                SwingUtilities.invokeLater(() -> {
                    procesandoDialog.dispose();
                    tabla.actualizarVistaTabla();
                });
            }
        }).start();
    }

    private void guardar() {
        try {
            ServicesLocator.getInstance().getTurnoDeGuardiaServices().guardarTurnos(tabla.getDias());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(tabla, "Ocurrió un error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        String string = "<html><p>Guardado Exitoso<br><br></p><html>";
        Advertencia advertencia = new Advertencia(new Dimension(530, 300), "Guardado Exitoso", string, "Aceptar", true);
        advertencia.dispose();
    }
    public void setTabla(Tabla tabla) {
        this.tabla = tabla;
    }
}
