package gui.internosComp;

import gui.auxiliares.IsTabla;
import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.componentes.CuadroRectoAbajo;
import gui.componentes.CustomScrollBar;
import gui.componentes.Etiqueta;
import model.DiaGuardia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class TablaBase extends Cuadro implements IsTabla {
    private static final long serialVersionUID = 1L;

    private static final int redondez = 0;
    private final Paleta paleta = new Paleta();
    //Tamanos
    private final int tituloLargo = 67;
    private final ArrayList<PanelDiaBase> panelesCasillas = new ArrayList<>();
    private final int sepIzquierda = 46;
    private final Color colorLetraTitulo = Color.WHITE;
    private final Font fuente = new Font("Arial", Font.BOLD, 14);
    //Secciones
    CuadroRectoAbajo titulo;
    JPanel panelCasillas;
    Cuadro myself;
    ArrayList<DiaGuardia> dias;

    public TablaBase(final Dimension dimension, Color color, ArrayList<DiaGuardia> estosDias) {
        super(dimension, redondez, color);
        PanelDia.setCasillaLargo(dimension.width - sepIzquierda * 2);
        this.setLayout(null);
        myself = this;
        this.setColorBorde(paleta.getColorCaracteristico());

        dias = estosDias;

        // Agregar MouseListener y MouseMotionListener al panel t�tulo
        inicializarTitulo();

        panelCasillas = new JPanel();
        panelCasillas.setBackground(Color.WHITE);
        panelCasillas.setLayout(new BoxLayout(panelCasillas, BoxLayout.Y_AXIS));
        panelCasillas.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

        panelCasillas.add(Box.createRigidArea(new Dimension(10, 0)));
        for (int i = 0; i < dias.size(); i++) {
            DiaGuardia dia = dias.get(i);
            Component caja = Box.createRigidArea(new Dimension(0, 5));
            Color colorAux = (i % 2 == 0 ? paleta.getColorCasillaTabla() : paleta.getColorCasillaTablaVacia()); // Alternar colores
            PanelDiaBase nuevoDia = new PanelDia(dia, colorAux, this);

            panelesCasillas.add(nuevoDia);
            panelCasillas.add(nuevoDia);
            panelCasillas.add(caja);

        }

        JScrollPane scrollPane = new JScrollPane(panelCasillas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        CustomScrollBar sp = new CustomScrollBar();
        sp.setOrientation(JScrollBar.HORIZONTAL);
        scrollPane.setHorizontalScrollBar(sp);

        scrollPane.setBounds(30, tituloLargo + 20, getWidth() - 50, this.getHeight() - tituloLargo - 35);

        add(titulo, BorderLayout.NORTH);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.SOUTH);
    }


    //Metodos para dividir el Constructor
    private void inicializarTitulo() {
        titulo = new CuadroRectoAbajo(new Dimension(this.getWidth(), tituloLargo), redondez, paleta.getColorCaracteristico());
        titulo.setLayout(null);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(titulo.getBackground());
        panelTitulo.setLayout(null);
        panelTitulo.setSize(titulo.getWidth(), 20);
        panelTitulo.setLocation(80, titulo.getHeight() - panelTitulo.getHeight() - 10);

        titulo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }
        });

        titulo.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
        });


        Etiqueta ID = new Etiqueta(fuente, colorLetraTitulo, "Carnet");
        int auxY = (panelTitulo.getSize().height - ID.getSize().height) / 2;

        Etiqueta diaT = new Etiqueta(fuente, colorLetraTitulo, "Dia");
        diaT.setPreferredSize(diaT.getSize());

        Etiqueta nombre = new Etiqueta(fuente, colorLetraTitulo, "Nombre");
        nombre.setPreferredSize(nombre.getSize());

        Etiqueta apellidos = new Etiqueta(fuente, colorLetraTitulo, "Apellidos");
        apellidos.setPreferredSize(apellidos.getSize());

        Etiqueta horario = new Etiqueta(fuente, colorLetraTitulo, "Horario");
        horario.setPreferredSize(horario.getSize());

        int sepHorario = 120, sepID = 90, sepApellidos = 130, sepNombre = 170;

        diaT.setLocation(0, auxY);
        horario.setLocation(diaT.getWidth() + sepHorario, auxY);
        ID.setLocation(horario.getLocation().x + horario.getWidth() + sepID, auxY);
        apellidos.setLocation(ID.getLocation().x + ID.getWidth() + sepApellidos, auxY);
        nombre.setLocation(apellidos.getLocation().x + apellidos.getWidth() + sepNombre, auxY);


        panelTitulo.add(diaT);
        panelTitulo.add(horario);
        panelTitulo.add(ID);
        panelTitulo.add(apellidos);
        panelTitulo.add(nombre);

        int auxAgno = dias.getFirst().getFecha().getYear();
        Etiqueta planificacion = new Etiqueta(fuente, colorLetraTitulo, dias.get(0).getFecha().getMonth().name() + "  " + auxAgno);
        planificacion.setLocation(15, 10);


        titulo.add(panelTitulo);
        titulo.add(planificacion);

    }


    @Override
    public void actualizar() {

    }

    @Override
    public void actualizarVistaTabla() {
        for (PanelDiaBase e : panelesCasillas) {
            e.actualizarTabla();
        }
    }

}
