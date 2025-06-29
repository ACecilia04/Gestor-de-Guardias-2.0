package gui.internosComp;

import gui.auxiliares.IsTabla;
import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.componentes.CustomScrollBar;
import model.DiaGuardia;
import model.Persona;
import model.TurnoDeGuardia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class TablaBase extends Cuadro implements IsTabla {
    private static final long serialVersionUID = 1L;

    private static final int redondez = 0;
    private final Paleta paleta = new Paleta();

    private final Color colorCabecera = new Color(37, 97, 209);
    private final Color colorCabeceraLetra = Color.WHITE;
    private final Color colorDiaFondo = new Color(237, 239, 244);
    private final Font fuenteCabecera = new Font("Arial", Font.BOLD, 15);
    private final Font fuenteNormal = new Font("Arial", Font.PLAIN, 14);
    private final int PAD = 9;


    public TablaBase(final Dimension dimension, Color color, ArrayList<DiaGuardia> estosDias) {
        super(dimension, redondez, color);
        this.setLayout(new BorderLayout());
        this.setColorBorde(paleta.getColorCaracteristico());

        String mesAgno = "";
        if (estosDias != null && !estosDias.isEmpty()) {
            mesAgno = estosDias.getFirst().getFecha().getMonth().getDisplayName(TextStyle.FULL, new Locale("es"))
                    + " " + estosDias.getFirst().getFecha().getYear();
        }

        JPanel encabezado = imprimirEncabezado(mesAgno);
        JScrollPane cuerpo = imprimirCuerpo(estosDias);

        add(encabezado, BorderLayout.NORTH);
        add(cuerpo, BorderLayout.CENTER);
    }

    private JPanel imprimirEncabezado(String mesAgno)  {
        JPanel encabezado = new JPanel(new GridBagLayout());
        encabezado.setPreferredSize(new Dimension(0, 67));
        encabezado.setBorder(new EmptyBorder(PAD, 0, PAD, 0));
        encabezado.setBackground(colorCabecera);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;

        // Fila 1, Columna 0: "A"
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        encabezado.add(celdaEncabezado(mesAgno), gbc);

        // Fila 1, Columnas 1–4: celdas unidas
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        encabezado.add(celdaEncabezado(""), gbc);

        // Textos de la fila 2
        String[] textos = { "Día", "Horario", "Carnet", "Apellidos", "Nombre" };
        gbc.gridy = 1;
        gbc.gridwidth = 1; // De nuevo una celda por columna

        for (int i = 0; i < textos.length; i++) {
            gbc.gridx = i;
            encabezado.add(celdaEncabezado(textos[i]), gbc);
        }

        return encabezado;
    }

    private JLabel celdaEncabezado(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setForeground(colorCabeceraLetra);
        lbl.setFont(fuenteCabecera);
        lbl.setBorder(new EmptyBorder(0, PAD, 0, 0));
        lbl.setPreferredSize(new Dimension(20, 20)); // esto es para forzar que funcione gbc.weightx y gbc.weighty = 1.0
        return lbl;
    }

    private JScrollPane imprimirCuerpo(ArrayList<DiaGuardia> estosDias){
        JPanel cuerpo = new JPanel();
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));

        AtomicBoolean alt = new AtomicBoolean(false); // AtomicBoolean en lugar de Boolean para poder pasar la variable por referencia
        for (DiaGuardia dia : estosDias) {
            ArrayList<TurnoDeGuardia> turnos = dia.getTurnos();
            if (turnos != null || !turnos.isEmpty()) {
                String nombreDia = String.format("%d %s",
                        dia.getFecha().getDayOfMonth(),
                        dia.getFecha().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es")));

                JPanel fila = construirFila(turnos, nombreDia, alt);
                cuerpo.add(fila);
                cuerpo.setPreferredSize(null);
            }
        }

        JScrollPane scrollPane = new JScrollPane(cuerpo);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());

        scrollPane.setBorder(null);

        return scrollPane;
    }

    private JPanel construirFila(ArrayList<TurnoDeGuardia> turnos, String nombreDia, AtomicBoolean alt){
        // Es necesario saber cuantas personas hay en el dia para determinar la cantidad de filas del dia
        int cantPersonas = 0;
        for (TurnoDeGuardia turno : turnos){
            ArrayList<Persona> personas = turno.getPersonasAsignadas();
            if (personas == null || personas.isEmpty()) {
                Persona persona = turno.getPersonaAsignada();
                if (persona != null) {
                    cantPersonas++;
                }
            } else {
                cantPersonas += personas.size();
            }
        }
        int cantFilas = cantPersonas + (cantPersonas % 2 == 1 ? 0 : 1); // forzar a que las filas sean siempre un numero impar para que se vea cambio de color de fondo de una fecha a otra

        JPanel fila = new JPanel(new GridBagLayout());

        int maxHeight = 40 * cantFilas;
//        fila.setPreferredSize(new Dimension(Integer.MAX_VALUE, maxHeight));
        fila.setMinimumSize(new Dimension(Integer.MAX_VALUE, maxHeight));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

        Color bgColor = alt.get() ? colorDiaFondo : Color.WHITE;
        fila.setBackground(bgColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0; // todas las celdas deben tener el mismo ancho y alto => pesoX y pesoY = 1

        // Celda unida verticalmente en la primera columna
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = cantFilas;
        fila.add(celdaCuerpo(nombreDia, fuenteNormal.deriveFont(Font.BOLD), bgColor), gbc);

        // Celdas restantes: columnas 1–4, filas 0–4
        gbc.gridheight = 1; // Importante: resetear altura unificada

        int i = 0;
        for (TurnoDeGuardia turno : turnos) {
            String horarioStr = turno.getHorario() != null ? turno.getHorario().toString() : "";
            ArrayList<Persona> personas = turno.getPersonasAsignadas();
            if (personas == null || personas.isEmpty()){
                Persona persona = turno.getPersonaAsignada();
                if (persona != null) {
                    bgColor = alt.get() ? colorDiaFondo : Color.WHITE;
                    String[] textos = {
                            horarioStr,
                            persona != null && persona.getCarnet() != null ? persona.getCarnet() : "",
                            persona != null && persona.getApellido() != null ? persona.getApellido() : "",
                            persona != null && persona.getNombre() != null ? persona.getNombre() : ""
                    };
                    for (int j = 0; j < textos.length; j++) {
                        gbc.gridx = j + 1; // empieza en 1 porque la columna 0 ya tiene el dia
                        gbc.gridy = i;
                        fila.add(celdaCuerpo(textos[j], fuenteNormal, bgColor), gbc);
                    }
                    i++;
                    alt.set(!alt.get());
                }
            } else {
                for (Persona persona : personas) {
                    bgColor = alt.get() ? colorDiaFondo : Color.WHITE;
                    String[] textos = {
                            horarioStr,
                            persona != null && persona.getCarnet() != null ? persona.getCarnet() : "",
                            persona != null && persona.getApellido() != null ? persona.getApellido() : "",
                            persona != null && persona.getNombre() != null ? persona.getNombre() : ""
                    };
                    for (int j = 0; j < textos.length; j++) {
                        gbc.gridx = j + 1; // empieza en 1 porque la columna 0 ya tiene el dia
                        gbc.gridy = i;
                        fila.add(celdaCuerpo(textos[j], fuenteNormal, bgColor), gbc);
                    }
                    i++;
                    alt.set(!alt.get());
                }
            }
        }

        if (i < cantFilas){ // hay menos personas que filas, añadir una fila vacia para garantizar el cambio de color de fondo a la siguiente fecha
            bgColor = alt.get() ? colorDiaFondo : Color.WHITE;
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.gridwidth = 4;
            fila.add(celdaCuerpo("", fuenteNormal, bgColor), gbc);
            alt.set(!alt.get());
        }

        fila.setPreferredSize(null);

        System.out.println(fila.getHeight());

        return fila;
    }

    private JLabel celdaCuerpo(String text, Font font, Color bgColor) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setFont(font);
        lbl.setBackground(bgColor);
        lbl.setOpaque(true);
        lbl.setBorder(new EmptyBorder(0, PAD, 0, 0));
        lbl.setPreferredSize(new Dimension(20, 20)); // esto es para forzar que funcione gbc.weightx y gbc.weighty = 1.0
        return lbl;
    }


    @Override
    public void actualizar() {}

    @Override
    public void actualizarVistaTabla() {}
}