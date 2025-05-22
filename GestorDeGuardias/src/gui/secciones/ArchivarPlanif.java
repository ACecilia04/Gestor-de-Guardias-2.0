package gui.secciones;

import gui.auxiliares.Paleta;
import gui.auxiliares.PanelInterno;
import gui.componentes.CustomScrollBar;
import gui.componentes.Etiqueta;
import logica.excepciones.EntradaInvalidaException;
import model.DiaGuardia;
import services.Gestor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class ArchivarPlanif extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 200;
    private static final int HORIZONTAL_GAP = 30;
    private static final int VERTICAL_GAP = 20;
    private final Dimension panelDimension = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    private final JPanel panelInterior;
    private final ArrayList<PanelInterno> paneles;
    private final Font fuente = new Font("Arial", Font.PLAIN, 22);
    private final LayoutManager layout = new FlowLayout(FlowLayout.LEFT, HORIZONTAL_GAP, VERTICAL_GAP);
    private final LayoutManager layout2 = new FlowLayout(FlowLayout.CENTER, 0, 100);
    private PanelInterno panelSelec;


    public ArchivarPlanif() {

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
        ArrayList<DiaGuardia> dias;
        try {
            dias = Gestor.getInstance().getDiasPorActualizarCumplimiento();
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
                Etiqueta error = new Etiqueta(fuente, Color.GRAY, "No hay planificaciones para Archivar");
                panelInterior.add(error);
            }
        } catch (EntradaInvalidaException e1) {
            panelInterior.setLayout(layout2);
            Etiqueta error = new Etiqueta(fuente, Color.GRAY, e1.getMessage());
            panelInterior.add(error);
        }


        for (PanelInterno e : paneles) {
            final PanelInterno aux = e;
            aux.addMouseListener(new MouseAdapter() {
                private static final long DOUBLE_CLICK_DELAY = 500;
                private long lastClickTime = 0;

                @Override
                public void mousePressed(MouseEvent e) {
                    long currentClickTime = System.currentTimeMillis();
                    if (currentClickTime - lastClickTime <= DOUBLE_CLICK_DELAY) {
                        Ventana.getInstance().mostrarPanel("panel7");
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
                dias = Gestor.getInstance().getDiasPorActualizarCumplimiento();
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
