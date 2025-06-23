package gui.secciones;

import gui.auxiliares.Paleta;
import gui.auxiliares.PanelMesDeGuardias;
import gui.componentes.CustomScrollBar;
import gui.componentes.Etiqueta;
import model.DiaGuardia;
import services.ServicesLocator;
import utils.exceptions.EntradaInvalidaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class AsistenciaPlanif extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 200;
    private static final int HORIZONTAL_GAP = 30;
    private static final int VERTICAL_GAP = 20;
    private final Dimension panelDimension = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    private final JPanel panelInterior;
    private final ArrayList<PanelMesDeGuardias> paneles;
    private final Font fuente = new Font("Arial", Font.PLAIN, 14);
    private final LayoutManager layout = new FlowLayout(FlowLayout.LEFT, HORIZONTAL_GAP, VERTICAL_GAP);
    private final LayoutManager layout2 = new FlowLayout(FlowLayout.CENTER, 0, 100);
    private PanelMesDeGuardias panelSelec;


    public AsistenciaPlanif() {

        setLayout(new BorderLayout());
        setBackground(Color.GREEN);

        paneles = new ArrayList<>();

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
    }

    public PanelMesDeGuardias addPlanif(LocalDate fechaIncio) {
        PanelMesDeGuardias nuevoPanel = new PanelMesDeGuardias(fechaIncio, panelDimension);

        panelInterior.add(nuevoPanel);
        panelInterior.revalidate();
        panelInterior.repaint();

        // Calcula el tamaño preferido del panelInterior
        calculatePreferredSize();
        return nuevoPanel;
    }

    public void actualizarPlanif() {
        panelInterior.removeAll();
        ArrayList<DiaGuardia> dias;
        try {
            dias = ServicesLocator.getInstance().getPlantillaServices().getDiasPorActualizarCumplimiento();
            panelInterior.setLayout(layout);

            HashSet<String> mesesArchivables = new HashSet<>();

            for (DiaGuardia dia : dias) {
                LocalDate fechaAux = dia.getFecha();
                int mesPlanif = fechaAux.getMonthValue();
                int annoPlanif = fechaAux.getYear();
                String claveMesAnno = annoPlanif + "-" + mesPlanif;

                if (mesesArchivables.add(claveMesAnno)) {
                    paneles.add(addPlanif(fechaAux));
                }
            }
            if (paneles.isEmpty()) {
                panelInterior.setLayout(layout2);
                Etiqueta error = new Etiqueta(fuente, Color.GRAY, "No hay planificación cuya asistencia pueda ser actualizada.");
                panelInterior.add(error);
            }
        } catch (EntradaInvalidaException e1) {
            panelInterior.setLayout(layout2);
            Etiqueta error = new Etiqueta(fuente, Color.GRAY, e1.getMessage());
            panelInterior.add(error);
        }


        for (PanelMesDeGuardias e : paneles) {
            final PanelMesDeGuardias aux = e;
            aux.addMouseListener(new MouseAdapter() {
                private static final long DOUBLE_CLICK_DELAY = 500;
                private long lastClickTime = 0;

                @Override
                public void mousePressed(MouseEvent e) {
                    long currentClickTime = System.currentTimeMillis();
                    if (currentClickTime - lastClickTime <= DOUBLE_CLICK_DELAY) {
                        Ventana.getInstance().mostrarPanel("panelCumplimiento");
                        panelSelec = aux;
                        mostrarTabla();

                    }
                    lastClickTime = currentClickTime;
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


    public void mostrarTabla() {
        if (panelSelec != null) {
            ArrayList<DiaGuardia> diasAux = new ArrayList<>();

            ArrayList<DiaGuardia> dias = new ArrayList<>();
            try {
                dias = ServicesLocator.getInstance().getPlantillaServices().getDiasPorActualizarCumplimiento();
            } catch (EntradaInvalidaException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            int valorMes = panelSelec.getFechaInicio().getMonthValue();
            int valorAgno = panelSelec.getFechaInicio().getYear();

            LocalDate fechaInicio = LocalDate.of(valorAgno, valorMes, 1);
            LocalDate fechaFin = LocalDate.of(valorAgno, valorMes, 1).plusMonths(1);
            boolean continuar = true;
            for (int i = 0; i < dias.size() && continuar; i++) {
                if (dias.get(i).getFecha().isAfter(fechaInicio) || dias.get(i).getFecha().isEqual(fechaInicio)) {
                    if (dias.get(i).getFecha().isAfter(fechaFin) || dias.get(i).getFecha().isEqual(fechaFin)) {
                        continuar = false;
                    } else {
                        diasAux.add(dias.get(i));
                    }
                }
            }
            Ventana.getInstance().getPantallaCump().setTabla(diasAux);
        }
    }

}
