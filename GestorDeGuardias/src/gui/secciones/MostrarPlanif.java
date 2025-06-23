package gui.secciones;

import gui.auxiliares.Paleta;
import gui.auxiliares.PanelInterno;
import gui.componentes.CustomScrollBar;
import gui.componentes.Etiqueta;
import gui.internosComp.PanelOpcionesMostrarP;
import gui.internosComp.TablaBase;
import gui.pantallasEmergentes.Advertencia;
import model.DiaGuardia;
import services.ReporteServices;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;
import utils.exceptions.EntradaInvalidaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class MostrarPlanif extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 200;
    private static final int HORIZONTAL_GAP = 30;
    private static final int VERTICAL_GAP = 20;
    private final Dimension panelDimension = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    private final JPanel panelInterior;
    private final ArrayList<PanelInterno> paneles;

    private final PanelOpcionesMostrarP panelOpciones;

    private final Font fuente = new Font("Arial", Font.PLAIN, 14);

    private final LayoutManager layout = new FlowLayout(FlowLayout.LEFT, HORIZONTAL_GAP, VERTICAL_GAP);
    private final LayoutManager layout2 = new FlowLayout(FlowLayout.CENTER, 0, 100);

    public MostrarPlanif() {
        setLayout(new BorderLayout());
//        setBackground(Color.GREEN);

        paneles = new ArrayList<>();
        int opcionesAncho = 300;
        panelOpciones = new PanelOpcionesMostrarP(new Dimension(opcionesAncho, 100));

        panelOpciones.getBotonVerPlanif().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ventana.getInstance().mostrarPanel("panel6");
                mostrarTabla();
            }

        });

        panelOpciones.getBotonExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 1. Obtener los trabajadores filtrados

//            if (filtrados.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "No hay trabajadores para exportar con los filtros actuales.", "Sin datos", JOptionPane.WARNING_MESSAGE);
//                return;
//            }

                // 2. Dialogo para elegir donde guardar
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Guardar reporte PDF");
                chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF", "pdf"));
                int seleccion = chooser.showSaveDialog(MostrarPlanif.this);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    if (!path.toLowerCase().endsWith(".pdf")) path += ".pdf";

                    // Obtiene todos los turnos a partir de la fecha de inicio seleccionada
                    ArrayList<DiaGuardia> diasGuardia = ServicesLocator.getInstance()
                            .getPlantillaServices()
                            .getPlanificacionesAPartirDe(getSeleccionado().getFechaInicio());
                    // 3. Llamar al servicio de reporte

                     new ReporteServices().generarReportePlantilla( diasGuardia, path);

                    JOptionPane.showMessageDialog(MostrarPlanif.this, "PDF generado exitosamente:\n" + path, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        panelOpciones.getBotonCrearPlanif().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ventana.getInstance().mostrarPanel("panel4");
            }
        });


        panelOpciones.getBotonEditarPlanif().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getSeleccionado() != null) {
                    ArrayList<DiaGuardia> diasAux = ServicesLocator.getInstance().getPlantillaServices().agruparPorDia(ServicesLocator.getInstance().getTurnoDeGuardiaServices().getTurnosAPartirDe(getSeleccionado().getFechaInicio()));
                    Ventana.getInstance().editarPlanif(diasAux);
                }

            }
        });

        panelOpciones.getBotonBorrarPlanif().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = "<html><p>Si borras una planificación las posteriores <br>tambien se perderan. Esta accion no se puede retroceder<br><br>Presione aceptar para continuar</p></html>";
                Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Advertencia", string, "Cancelar", "Aceptar");
                if (!advertencia.getEleccion()) {
                    try {
                        ServicesLocator.getInstance().getTurnoDeGuardiaServices().deleteTurnosDeGuardiaAPartirDe(getSeleccionado().getFechaInicio());
                    } catch (SqlServerCustomException | EntradaInvalidaException ex) {
                        String errorMsg = "<html><p>Ocurrió un error:<br>" + ex.getMessage() + "</p></html>";
                        new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", errorMsg, "Aceptar", true);
//                        throw new RuntimeException(ex);

                    }
                    actualizarPlanif();
                    getSeleccionado().setSeleccionado(false);

                    panelOpciones.getBotonBorrarPlanif().setSeleccionable(false);
                    panelOpciones.getBotonEditarPlanif().setSeleccionable(false);
                    panelOpciones.getBotonCrearPlanif().setSeleccionable(false);
                    panelOpciones.getBotonVerPlanif().setSeleccionable(false);
                    panelOpciones.getBotonExport().setSeleccionable(false);

                    revalidate();
                    repaint();
                }
            }
        });


        panelOpciones.getBotonBorrarPlanif().setSeleccionable(false);
        panelOpciones.getBotonEditarPlanif().setSeleccionable(false);
        panelOpciones.getBotonCrearPlanif().setSeleccionable(true);
        panelOpciones.getBotonVerPlanif().setSeleccionable(false);
        panelOpciones.getBotonExport().setSeleccionable(false);

        // Crea el panelInterior
        panelInterior = new JPanel();
        //    private static final Dimension tablaDim = new Dimension(1200, 745);
        Paleta paleta = new Paleta();
        panelInterior.setBackground(paleta.getColorFondo());

        // Crea un JScrollPane para el panelInterior
        JScrollPane scrollPane = new JScrollPane(panelInterior);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setBackground(Color.GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
        add(panelOpciones, BorderLayout.EAST);
        actualizarPlanif();
    }

    public void addPlanif(LocalDate fechaIncio) {
        PanelInterno nuevoPanel = new PanelInterno(fechaIncio, panelDimension);

        panelInterior.add(nuevoPanel);
        paneles.add(nuevoPanel);
        panelInterior.revalidate();
        panelInterior.repaint();

        // Calcula el tama�o preferido del panelInterior
        calculatePreferredSize();
    }

    public void actualizarPlanif() {
        panelInterior.removeAll();
//        HashSet<String> mesesPlanificados = new HashSet<>();

        ArrayList<DiaGuardia> dias = ServicesLocator.getInstance().getPlantillaServices().getPlanDeGuardias();
        panelInterior.setLayout(layout);

        HashSet<String> mesesArchivables = new HashSet<>();

        for (DiaGuardia dia : dias) {
            LocalDate fechaAux = dia.getFecha();
            int mesPlanif = fechaAux.getMonthValue();
            int annoPlanif = fechaAux.getYear();
            String claveMesAnno = annoPlanif + "-" + mesPlanif;

            if (mesesArchivables.add(claveMesAnno)) {
                addPlanif(fechaAux);
            }
        }
        if (paneles.isEmpty()) {
            panelInterior.setLayout(layout2);
            Etiqueta error = new Etiqueta(fuente, Color.GRAY, "No hay planificaciones");
            panelInterior.add(error);
        }


        for (PanelInterno e : paneles) {
            final PanelInterno aux = e;
            aux.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // Llamar al método setSeleccionado() cuando se hace clic en el panel
                    aux.setSeleccionado();
                    calcularSeleccionados(aux);

                    if (getSeleccionado() != null) {
                        panelOpciones.getBotonBorrarPlanif().setSeleccionable(true);
                        panelOpciones.getBotonEditarPlanif().setSeleccionable(true);
                        panelOpciones.getBotonCrearPlanif().setSeleccionable(true);
                        panelOpciones.getBotonVerPlanif().setSeleccionable(true);
                        panelOpciones.getBotonExport().setSeleccionable(true);
                    } else {
                        panelOpciones.getBotonBorrarPlanif().setSeleccionable(false);
                        panelOpciones.getBotonEditarPlanif().setSeleccionable(false);
                        panelOpciones.getBotonCrearPlanif().setSeleccionable(false);
                        panelOpciones.getBotonVerPlanif().setSeleccionable(false);
                        panelOpciones.getBotonExport().setSeleccionable(false);
                    }
                    repaint();
                    revalidate();
                }
            });
        }


    }

    private void calculatePreferredSize() {
        int numPanels = panelInterior.getComponentCount();
        // Calcula el n�mero de columnas que caben en el ancho del JScrollPane
        int panelWidthWithGap = PANEL_WIDTH + HORIZONTAL_GAP;
        int numColumns = Math.max(1, getWidth() / panelWidthWithGap); // Evita divisi�n por cero
        int numRows = (numPanels + numColumns - 1) / numColumns; // Calcula el n�mero de filas
        int preferredHeight = (numRows * PANEL_HEIGHT) + ((numRows - 1) * VERTICAL_GAP);
        panelInterior.setPreferredSize(new Dimension(600, preferredHeight));
    }

    private void calcularSeleccionados(PanelInterno aux) {
        for (PanelInterno e : paneles) {
            if (e != aux) {
                e.setSeleccionado(false);
            }
        }
    }

    public PanelInterno getSeleccionado() {
        PanelInterno aux = null;
        for (int i = 0; i < paneles.size() && aux == null; i++) {
            if (paneles.get(i).isSeleccionado()) {
                aux = paneles.get(i);
            }
        }
        return aux;
    }

    public void mostrarTabla() {
        if (getSeleccionado() != null) {
            Ventana.getInstance().getPanel1().removeAll();
            ArrayList<DiaGuardia> diasAux = ServicesLocator.getInstance().getPlantillaServices().getPlanificacionesAPartirDe(getSeleccionado().getFechaInicio());
            TablaBase tabla = new TablaBase(Ventana.getInstance().getPanelVacio().getSize(), Color.WHITE, diasAux);
            Ventana.getInstance().getPanel1().add(tabla, BorderLayout.CENTER);
        }
    }

}
