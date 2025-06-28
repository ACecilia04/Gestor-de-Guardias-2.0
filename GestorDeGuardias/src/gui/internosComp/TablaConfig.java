package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.componentes.CustomScrollBar;
import model.Configuracion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

import static gui.auxiliares.ConvertidorFecha.traducDiaSemana;

public class TablaConfig extends Cuadro {
    private static final long serialVersionUID = 1L;

    private static final int REDONDEZ = Cuadro.redMED;
    private final Paleta paleta = new Paleta();
    private final Color colorCabecera = new Color(52, 121, 255);
    private final Color colorLetraCabecera = Color.WHITE;
    private final Font fuenteCabecera = new Font("Arial", Font.BOLD, 17);
    private final int alturaCabecera = 44;

    private JPanel panelCasillas;
    private ArrayList<Configuracion> configuraciones;
    private Consumer<Configuracion> onDoubleClick;

    public TablaConfig(Dimension dimension, Color color, ArrayList<Configuracion> configuraciones, Consumer<Configuracion> onDoubleClick) {
        super(dimension, REDONDEZ, color);
        setLayout(new BorderLayout());
        setColorBorde(paleta.getColorCaracteristico());
        this.configuraciones = configuraciones;
        this.onDoubleClick = onDoubleClick;

        panelCasillas = new JPanel();
        panelCasillas.setBackground(Color.WHITE);
        panelCasillas.setLayout(new BoxLayout(panelCasillas, BoxLayout.Y_AXIS));
        panelCasillas.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Cabecera alineada
        JPanel panelCabecera = new JPanel(new GridLayout(1, 5));
        panelCabecera.setBackground(colorCabecera);
        panelCabecera.setBorder(new EmptyBorder(0, 28, 0, 0));
        panelCabecera.setPreferredSize(new Dimension(0, alturaCabecera));
        panelCabecera.add(colHeader("Horario"));
        panelCabecera.add(colHeader("Cantidad"));
        panelCabecera.add(colHeader("Sexo"));
        panelCabecera.add(colHeader("Receso"));
        panelCasillas.add(panelCabecera);

        // Agrupar configuraciones por día de la semana (usa tu propio método de agrupación si es necesario)
        LinkedHashMap<String, ArrayList<Configuracion>> mapaDias = new LinkedHashMap<>();
        for (Configuracion conf : configuraciones) {
            String dia = traducDiaSemana(conf.getDiaSemana());
            mapaDias.computeIfAbsent(dia, k -> new ArrayList<>()).add(conf);
        }

        int i = 0;
        for (String dia : mapaDias.keySet()) {
            ArrayList<Configuracion> confsDia = mapaDias.get(dia);
            Color colorFondo = new Color(245, 246, 248); // Gris suave tipo tarjeta
            PanelDiaConfig panelDia = new PanelDiaConfig(dia, confsDia, colorFondo, fuenteCabecera, onDoubleClick);
            panelCasillas.add(Box.createRigidArea(new Dimension(0, 10)));
            panelCasillas.add(panelDia);
            i++;
        }

        JScrollPane scrollPane = new JScrollPane(panelCasillas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        CustomScrollBar sp = new CustomScrollBar();
        sp.setOrientation(JScrollBar.HORIZONTAL);
        scrollPane.setHorizontalScrollBar(sp);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JLabel colHeader(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setForeground(colorLetraCabecera);
        lbl.setFont(fuenteCabecera);
        return lbl;
    }
}