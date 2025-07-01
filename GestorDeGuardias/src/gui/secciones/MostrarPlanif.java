package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.CustomScrollBar;
import gui.componentes.Etiqueta;
import gui.componentes.PanelMes;
import gui.internosComp.PanelSupOpcionesPlanifs;
import gui.internosComp.TablaBase;
import gui.pantallasEmergentes.Notificacion;
import model.DiaGuardia;
import services.ServicesLocator;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class MostrarPlanif extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = 230;
    private static final int PANEL_HEIGHT = 150;
    private static final int HORIZONTAL_GAP = 30;
    private static final int VERTICAL_GAP = 20;
    private final Dimension panelDimension = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    private final JPanel panelMes;
    private final ArrayList<PanelMes> panelesMeses;
    private final Font fuente = new Font("Arial", Font.PLAIN, 14);
    private final LayoutManager layout = new FlowLayout(FlowLayout.LEFT, HORIZONTAL_GAP, VERTICAL_GAP);
    private final LayoutManager layout2 = new FlowLayout(FlowLayout.CENTER, 0, 100);
    private PanelMes seleccionado;
    private Object opcionesReferencia;

    public MostrarPlanif() {
        setLayout(new BorderLayout());

        panelesMeses = new ArrayList<>();

        // Crea el panelInterior
        panelMes = new JPanel();
        Paleta paleta = new Paleta();
        panelMes.setBackground(paleta.getColorFondo());

        // Crea un JScrollPane para el panelInterior
        JScrollPane scrollPane = new JScrollPane(panelMes);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setBackground(Color.GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
        actualizarPlanif();
    }

    private void addPlanif(LocalDate fechaIncio) {

        PanelMes nuevoPanel = new PanelMes(fechaIncio, panelDimension);
        nuevoPanel.setParent(this);
        panelMes.add(nuevoPanel);
        panelesMeses.add(nuevoPanel);
        panelMes.revalidate();
        panelMes.repaint();

        // Calcula el tama�o preferido del panelInterior
        calculatePreferredSize();
    }

    public void actualizarPlanif() {
        panelMes.removeAll();
        ArrayList<DiaGuardia> dias = ServicesLocator.getInstance().getPlantillaServices().getPlanDeGuardias();
        panelMes.setLayout(layout);

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
        if (panelesMeses.isEmpty()) {
            panelMes.setLayout(layout2);
            Etiqueta error = new Etiqueta(fuente, Color.GRAY, "No hay planificaciones");
            panelMes.add(error);
        }

    }

    private void calculatePreferredSize() {
        int numPanels = panelMes.getComponentCount();
        // Calcula el n�mero de columnas que caben en el ancho del JScrollPane
        int panelWidthWithGap = PANEL_WIDTH + HORIZONTAL_GAP;
        int numColumns = Math.max(1, getWidth() / panelWidthWithGap); // Evita divisi�n por cero
        int numRows = (numPanels + numColumns - 1) / numColumns; // Calcula el n�mero de filas
        int preferredHeight = (numRows * PANEL_HEIGHT) + ((numRows - 1) * VERTICAL_GAP);
        panelMes.setPreferredSize(new Dimension(600, preferredHeight));
    }


    public PanelMes getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(PanelMes seleccionado) {
        if (seleccionado == null) {
            if (this.seleccionado != null && this.seleccionado.isSeleccionado())
                this.seleccionado.setSeleccionado(false);
        }
        this.seleccionado = seleccionado;
        if (opcionesReferencia != null && opcionesReferencia instanceof PanelSupOpcionesPlanifs)
            ((PanelSupOpcionesPlanifs) opcionesReferencia).setAlgunMesSeleccionado(seleccionado != null);
    }


    public void mostrarTabla() {
        if (seleccionado != null) {
            String string1 = "<html><p>Procesando.....<br><br></p><html>";
            Notificacion notificacion = new Notificacion(new Dimension(300, 200), "Mostrar planificación", string1);

            SwingWorker<String, Void> myWorker = new SwingWorker<>() {
                public String doInBackground() {

                    Ventana ventana = Ventana.getInstance();
                    ventana.getPanelVerPlanif().removeAll();
                    ArrayList<DiaGuardia> diasAux =
                            ServicesLocator.getInstance().getPlantillaServices().getPlanificacionesAPartirDe(seleccionado.getFechaInicio());
                    TablaBase tabla = new TablaBase(ventana.getPanelVacio().getSize(), Color.WHITE, diasAux);
                    tabla.actualizarVistaTabla();
                    ventana.getPanelVerPlanif().add(tabla, BorderLayout.CENTER);
                    ventana.getPanelVerPlanif().revalidate();
                    ventana.getPanelVerPlanif().repaint();
                    ventana.mostrarPanel("panelVerPlanificaciones");

                    notificacion.dispose();
                    return null;
                }

                public void done() {
                }
            };
            myWorker.execute();
            notificacion.setVisible(true);
        }
    }

    public void setOpcionesReferencia(Object opcionesReferencia) {
        this.opcionesReferencia = opcionesReferencia;
    }
}
