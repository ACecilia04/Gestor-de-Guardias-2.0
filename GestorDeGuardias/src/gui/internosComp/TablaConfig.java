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
    private final Color colorCabecera = new Color(37, 97, 209);
    private final Color colorLetraCabecera = Color.WHITE;
    private final Color colorDiaFondo = new Color(237, 239, 244);
    private final Color colorSeleccion = new Color(187, 222, 251); // azul claro para selección
    private final Font fuenteCabecera = new Font("Arial", Font.BOLD, 15);
    private final Font fuenteNormal = new Font("Arial", Font.PLAIN, 14);
    private final int PAD = 9;
    private final int rowHeight = 40;
    private final int alturaCabecera = 67;

    private ArrayList<Configuracion> configuraciones;
    private Consumer<Configuracion> onDoubleClick;

    // Para mantener el seleccionado
    private Configuracion seleccionada;
    //    private JPanel panelSeleccionado;
    private Object opcionesReferencia;

    private java.util.List<JPanel> panelesFilas = new ArrayList<>();

    public TablaConfig(Dimension dimension, Color color, ArrayList<Configuracion> configuraciones, Consumer<Configuracion> onDoubleClick) {
        super(dimension, REDONDEZ, color);
        setLayout(new BorderLayout());
        setColorBorde(paleta.getColorCaracteristico());
        this.configuraciones = configuraciones;
        this.onDoubleClick = onDoubleClick;

        JPanel encabezado = crearEncabezado();
        JScrollPane cuerpo = crearCuerpo();

        add(encabezado, BorderLayout.NORTH);
        add(cuerpo, BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new GridBagLayout());
        encabezado.setPreferredSize(new Dimension(0, alturaCabecera));
        encabezado.setBorder(new EmptyBorder(PAD, 0, PAD, 0));
        encabezado.setBackground(colorCabecera);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;

        String[] textos = {"Día", "Horario", "Personas", "Sexo", "Receso"};
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        for (int i = 0; i < textos.length; i++) {
            gbc.gridx = i;
            encabezado.add(celdaEncabezado(textos[i]), gbc);
        }

        return encabezado;
    }

    private JLabel celdaEncabezado(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setForeground(colorLetraCabecera);
        lbl.setFont(fuenteCabecera);
        lbl.setBorder(new EmptyBorder(0, PAD, 0, 0));
        lbl.setPreferredSize(new Dimension(20, 20));
        return lbl;
    }

    private JScrollPane crearCuerpo() {
        JPanel cuerpo = new JPanel();
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        panelesFilas.clear();
        // Agrupar configuraciones por día de la semana
        LinkedHashMap<String, ArrayList<Configuracion>> mapaDias = new LinkedHashMap<>();
        for (Configuracion conf : configuraciones) {
            String dia = traducDiaSemana(conf.getDiaSemana());
            mapaDias.computeIfAbsent(dia, k -> new ArrayList<>()).add(conf);
        }

        // Crear mapa de color alternado por día
        LinkedHashMap<String, Color> colorPorDia = new LinkedHashMap<>();
        boolean altColor = false;
        for (String dia : mapaDias.keySet()) {
            colorPorDia.put(dia, altColor ? colorDiaFondo : Color.WHITE);
            altColor = !altColor;
        }

        for (String dia : mapaDias.keySet()) {
            ArrayList<Configuracion> confsDia = mapaDias.get(dia);
            Color colorDia = colorPorDia.get(dia);

            for (int i = 0; i < confsDia.size(); i++) {
                Configuracion conf = confsDia.get(i);
                boolean esSeleccionada = conf.equals(seleccionada);
                Color rowBgColor = esSeleccionada ? colorSeleccion : colorDia;

                JPanel panelFila = new JPanel(new GridLayout(1, 5));
                panelFila.setOpaque(true);

                // Solo la primera fila del día muestra el texto del día, las demás quedan vacías
                String diaCol = (i == 0) ? dia : "";

                String[] textos = {
                        diaCol,
                        conf.getHorario() != null ? conf.getHorario().toString() : "",
                        String.valueOf(conf.getCantPersonas()),
                        sexoToString(conf.getSexo()),
                        conf.diaEsReceso() != null ? (conf.diaEsReceso() ? "Sí" : "No") : ""
                };

                for (int j = 0; j < textos.length; j++) {
                    Color celdaColor;
                    if (j == 0) { // columna "Día" SIEMPRE usa colorDia
                        celdaColor = colorDia;
                    } else {
                        celdaColor = rowBgColor;
                    }
                    JLabel celda = celdaCuerpo(
                            textos[j],
                            (j == 0 && i == 0 && !diaCol.isEmpty()) ? fuenteNormal.deriveFont(Font.BOLD) : fuenteNormal,
                            celdaColor
                    );
                    panelFila.add(celda);
                }

                // Click para seleccionar
                panelFila.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        setSeleccionado(null);
                        if (evt.getClickCount() == 1) {
                            setSeleccionado(conf);
                            actualizarSeleccion();
                        }
                        if (evt.getClickCount() == 2 && onDoubleClick != null) {
                            onDoubleClick.accept(conf);
                        }
                    }
                });
                panelesFilas.add(panelFila);

                cuerpo.add(panelFila);
            }
        }

        JScrollPane scrollPane = new JScrollPane(cuerpo);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());

        return scrollPane;
    }

    // Actualiza el color de la fila seleccionada
    private void actualizarSeleccion() {
        // Agrupar configuraciones por día de la semana
        LinkedHashMap<String, ArrayList<Configuracion>> mapaDias = new LinkedHashMap<>();
        for (Configuracion conf : configuraciones) {
            String dia = traducDiaSemana(conf.getDiaSemana());
            mapaDias.computeIfAbsent(dia, k -> new ArrayList<>()).add(conf);
        }

        // Crear mapa de color alternado por día
        LinkedHashMap<String, Color> colorPorDia = new LinkedHashMap<>();
        boolean altColor = false;
        for (String dia : mapaDias.keySet()) {
            colorPorDia.put(dia, altColor ? colorDiaFondo : Color.WHITE);
            altColor = !altColor;
        }

        int idx = 0;
        for (String dia : mapaDias.keySet()) {
            ArrayList<Configuracion> confsDia = mapaDias.get(dia);
            Color colorDia = colorPorDia.get(dia);
            for (Configuracion conf : confsDia) {
                JPanel panelFila = panelesFilas.get(idx++);
                boolean esSeleccionada = conf.equals(seleccionada);
                Color bgColor = esSeleccionada ? colorSeleccion : colorDia;
                Component[] celdas = panelFila.getComponents();
                for (int j = 0; j < celdas.length; j++) {
                    if (j == 0) { // columna "Día" SIEMPRE usa colorDia
                        celdas[j].setBackground(colorDia);
                    } else {
                        celdas[j].setBackground(bgColor);
                    }
                }
                panelFila.setBackground(bgColor); // Por compatibilidad visual
            }
        }
        repaint();
    }

    private JLabel celdaCuerpo(String text, Font font, Color bgColor) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setFont(font);
        lbl.setBackground(bgColor);
        lbl.setOpaque(true);
        lbl.setBorder(new EmptyBorder(0, PAD, 0, 0));
        lbl.setPreferredSize(new Dimension(20, rowHeight));
        return lbl;
    }

    // Utilidad para mostrar "femenino", "masculino" o "ambos"
    private String sexoToString(Object sexo) {
        if (sexo == null) return "Ambos";
        String s = sexo.toString();
        if (s.equalsIgnoreCase("F")) return "Femenino";
        if (s.equalsIgnoreCase("M")) return "Masculino";
        return s;
    }

    public void setOpcionesReferencia(Object opcionesReferencia) {
        this.opcionesReferencia = opcionesReferencia;
    }

    public Configuracion getSeleccionado() {
        return seleccionada;
    }

    public void setSeleccionado(Configuracion seleccionada) {
//        if (seleccionada == null){
//                this.panelSeleccionado.setSeleccionado(false);
//        }
        this.seleccionada = seleccionada;
        if (opcionesReferencia != null && opcionesReferencia instanceof PanelSupOpcionesConfig)
            ((PanelSupOpcionesConfig) opcionesReferencia).setAlgunaConfigSeleccionada(seleccionada != null);
    }

}