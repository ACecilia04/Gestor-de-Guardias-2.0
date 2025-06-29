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
    private final Color colorCabecera = new Color(37, 97, 209); // igual que TablaBase
    private final Color colorLetraCabecera = Color.WHITE;
    private final Color colorDiaFondo = new Color(237, 239, 244); // igual que TablaBase
    private final Font fuenteCabecera = new Font("Arial", Font.BOLD, 15);
    private final Font fuenteNormal = new Font("Arial", Font.PLAIN, 14);
    private final int PAD = 9;
    private final int rowHeight = 40;
    private final int alturaCabecera = 67; // igual que TablaBase

    private ArrayList<Configuracion> configuraciones;
    private Consumer<Configuracion> onDoubleClick;

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

        // Fila 2: Nombres de columnas
        String[] textos = { "Día", "Horario", "Cantidad", "Sexo", "Receso" };
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

        // Agrupar configuraciones por día de la semana
        LinkedHashMap<String, ArrayList<Configuracion>> mapaDias = new LinkedHashMap<>();
        for (Configuracion conf : configuraciones) {
            String dia = traducDiaSemana(conf.getDiaSemana());
            mapaDias.computeIfAbsent(dia, k -> new ArrayList<>()).add(conf);
        }

        boolean alt = false;
        for (String dia : mapaDias.keySet()) {
            ArrayList<Configuracion> confsDia = mapaDias.get(dia);

            int cantFilas = confsDia.size();
            JPanel fila = new JPanel(new GridBagLayout());
            int maxHeight = rowHeight * cantFilas;
            fila.setMinimumSize(new Dimension(Integer.MAX_VALUE, maxHeight));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));
            Color bgColor = alt ? colorDiaFondo : Color.WHITE;
            fila.setBackground(bgColor);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weighty = 1.0;
            gbc.weightx = 1.0;

            // Celda unida verticalmente para el día
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = cantFilas;
            fila.add(celdaCuerpo(dia, fuenteNormal.deriveFont(Font.BOLD), bgColor), gbc);

            gbc.gridheight = 1; // reset

            for (int i = 0; i < confsDia.size(); i++) {
                Configuracion conf = confsDia.get(i);
                Color rowBgColor = (alt ? colorDiaFondo : Color.WHITE);
                String[] textos = {
                        conf.getHorario() != null ? conf.getHorario().toString() : "",
                        String.valueOf(conf.getCantPersonas()),
                        conf.getSexo() != null ? conf.getSexo().toString() : "",
                        conf.diaEsReceso() != null ? (conf.diaEsReceso() ? "Sí" : "No") : ""
                };
                for (int j = 0; j < textos.length; j++) {
                    gbc.gridx = j + 1; // empieza en 1 porque la columna 0 ya tiene el dia
                    gbc.gridy = i;
                    JLabel celda = celdaCuerpo(textos[j], fuenteNormal, rowBgColor);
                    // Doble clic
                    if (onDoubleClick != null) {
                        final Configuracion confFinal = conf;
                        celda.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                if (evt.getClickCount() == 2) {
                                    onDoubleClick.accept(confFinal);
                                }
                            }
                        });
                    }
                    fila.add(celda, gbc);
                }
                alt = !alt;
            }
            cuerpo.add(fila);
        }

        JScrollPane scrollPane = new JScrollPane(cuerpo);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());

        return scrollPane;
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

}
