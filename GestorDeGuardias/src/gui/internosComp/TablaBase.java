package gui.internosComp;

import gui.auxiliares.IsTabla;
import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.componentes.CuadroRectoAbajo;
import gui.componentes.CustomScrollBar;
import model.DiaGuardia;
import model.Horario;
import model.Persona;
import model.TurnoDeGuardia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class TablaBase extends Cuadro implements IsTabla {
    private static final long serialVersionUID = 1L;

    private static final int redondez = 0;
    private final Paleta paleta = new Paleta();

    private final Color colorCabecera = new Color(37, 97, 209);
    private final Color colorCabeceraLetra = Color.WHITE;
    private final Color colorDiaFondo = new Color(237, 239, 244);
    private final Color colorFila1 = new Color(245, 247, 250);
    private final Color colorFila2 = new Color(230, 233, 238);
    private final Font fuenteCabecera = new Font("Arial", Font.BOLD, 15);
    private final Font fuenteNormal = new Font("Arial", Font.PLAIN, 14);
    private final int PAD = 9;

    CuadroRectoAbajo titulo;
    JPanel panelCasillas;
    Cuadro myself;
    ArrayList<DiaGuardia> dias;

    public TablaBase(final Dimension dimension, Color color, ArrayList<DiaGuardia> estosDias) {
        super(dimension, redondez, color);
        this.setLayout(new BorderLayout());
        myself = this;
        this.setColorBorde(paleta.getColorCaracteristico());
        dias = estosDias;

        inicializarCabecera();

        panelCasillas = new JPanel();
        panelCasillas.setBackground(Color.WHITE);
        panelCasillas.setLayout(new BoxLayout(panelCasillas, BoxLayout.Y_AXIS));
        panelCasillas.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        construirFilas();

        JScrollPane scrollPane = new JScrollPane(panelCasillas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        CustomScrollBar sp = new CustomScrollBar();
        sp.setOrientation(JScrollBar.HORIZONTAL);
        scrollPane.setHorizontalScrollBar(sp);
        scrollPane.setBorder(null);

        add(titulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void inicializarCabecera() {
        titulo = new CuadroRectoAbajo(new Dimension(this.getWidth(), 67), redondez, colorCabecera);
        titulo.setLayout(new BorderLayout());
        JPanel panelTitulo = new JPanel(new GridLayout(1, 5));
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(new EmptyBorder(PAD, PAD, PAD, PAD));

        panelTitulo.add(colHeader("Día"));
        panelTitulo.add(colHeader("Horario"));
        panelTitulo.add(colHeader("Carnet"));
        panelTitulo.add(colHeader("Apellidos"));
        panelTitulo.add(colHeader("Nombre"));

        // Etiqueta de mes y año alineada a la izquierda
        String mesAgno = "";
        if (dias != null && !dias.isEmpty()) {
            mesAgno = dias.get(0).getFecha().getMonth().getDisplayName(TextStyle.FULL, new Locale("es"))
                    + " " + dias.get(0).getFecha().getYear();
        }
        JLabel lblMes = new JLabel(mesAgno, SwingConstants.LEFT);
        lblMes.setFont(fuenteCabecera);
        lblMes.setForeground(colorCabeceraLetra);
        lblMes.setBorder(new EmptyBorder(0, PAD, 0, 0));

        JPanel panelMes = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 16));
        panelMes.setOpaque(false);
        panelMes.add(lblMes);

        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);
        north.add(panelMes, BorderLayout.WEST);
        north.add(panelTitulo, BorderLayout.SOUTH);

        titulo.add(north, BorderLayout.CENTER);
    }

    private JLabel colHeader(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setForeground(colorCabeceraLetra);
        lbl.setFont(fuenteCabecera);
        return lbl;
    }

    private void construirFilas() {
        boolean alt = false;
        for (DiaGuardia dia : dias) {
            ArrayList<TurnoDeGuardia> turnos = dia.getTurnos();
            if (turnos == null || turnos.isEmpty()) continue;

            // Panel para el día completo
            JPanel panelDia = new JPanel();
            panelDia.setLayout(new BoxLayout(panelDia, BoxLayout.Y_AXIS));
            panelDia.setBackground(alt ? colorDiaFondo : Color.WHITE);
            panelDia.setBorder(new EmptyBorder(PAD, PAD, PAD, PAD));

            // Nombre del día
            JLabel labDia = new JLabel(String.format("%d %s",
                    dia.getFecha().getDayOfMonth(),
                    dia.getFecha().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"))));
            labDia.setFont(fuenteNormal.deriveFont(Font.BOLD));
            panelDia.add(labDia);

            // Horas y personas del día
            boolean subAlt = false;
            for (TurnoDeGuardia turno : turnos) {
                ArrayList<Persona> personas = turno.getPersonasAsignadas();
                // Si no hay lista de personas asignadas, usar getPersonaAsignada
                if (personas == null || personas.isEmpty()) {
                    Persona persona = turno.getPersonaAsignada();
                    if (persona != null) {
                        JPanel fila = filaTurno(turno.getHorario(), persona, subAlt);
                        panelDia.add(fila);
                        subAlt = !subAlt;
                    }
                } else {
                    for (Persona persona : personas) {
                        JPanel fila = filaTurno(turno.getHorario(), persona, subAlt);
                        panelDia.add(fila);
                        subAlt = !subAlt;
                    }
                }
            }

            panelDia.add(Box.createVerticalStrut(10));
            panelCasillas.add(panelDia);
            alt = !alt;
        }
    }

    private JPanel filaTurno(Horario horario, Persona persona, boolean alt) {
        JPanel fila = new JPanel(new GridLayout(1, 5));
        fila.setBackground(alt ? colorFila2 : colorFila1);
        fila.setOpaque(true);

        String horarioStr = horario != null ? horario.toString() : "";
        String carnet = persona != null && persona.getCarnet() != null ? persona.getCarnet() : "";
        String apellidos = persona != null && persona.getApellido() != null ? persona.getApellido() : "";
        String nombre = persona != null && persona.getNombre() != null ? persona.getNombre() : "";

        fila.add(cell("", fuenteNormal)); // Columna Día vacía, porque el día se muestra arriba
        fila.add(cell(horarioStr, fuenteNormal));
        fila.add(cell(carnet, fuenteNormal));
        fila.add(cell(apellidos, fuenteNormal));
        fila.add(cell(nombre, fuenteNormal));
        fila.setBorder(new EmptyBorder(3, 0, 3, 0));
        return fila;
    }

    private JLabel cell(String text, Font font) {
        JLabel lbl = new JLabel(text != null ? text : "", SwingConstants.LEFT);
        lbl.setFont(font);
        lbl.setBorder(new EmptyBorder(0, 6, 0, 0));
        return lbl;
    }

    @Override
    public void actualizar() {}

    @Override
    public void actualizarVistaTabla() {}
}