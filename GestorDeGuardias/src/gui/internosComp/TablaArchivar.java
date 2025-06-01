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
import java.util.ArrayList;


public class TablaArchivar extends Cuadro implements IsTabla {
    private static final long serialVersionUID = 1L;

    private static final int redondez = 0;
    private final Paleta paleta = new Paleta();
    private final JPanel panelCasillas;
    //Tamanos
    private final int tituloLargo = 67;
    private final ArrayList<PanelDiaBase> panelesCasillas = new ArrayList<>();
    private final ArrayList<DiaGuardia> dias;
    private final int sepIzquierda = 46;
    private final Color colorLetraTitulo = Color.WHITE;
    private final Font fuente = new Font("Arial", Font.BOLD, 14);
    //Secciones
    private CuadroRectoAbajo titulo;

    public TablaArchivar(final Dimension dimension, Color color, ArrayList<DiaGuardia> estosDias) {
        super(dimension, redondez, color);
        PanelDia.setCasillaLargo(dimension.width - sepIzquierda * 2);
        this.setLayout(null);
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
            PanelDiaArch nuevoDia = new PanelDiaArch(dia, colorAux, this);

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
    public void inicializarTitulo() {
        titulo = new CuadroRectoAbajo(new Dimension(this.getWidth(), tituloLargo), redondez, paleta.getColorCaracteristico());
        titulo.setLayout(null);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(titulo.getBackground());
        panelTitulo.setLayout(null);
        panelTitulo.setSize(titulo.getWidth(), 20);
        panelTitulo.setLocation(0, titulo.getHeight() - panelTitulo.getHeight() - 10);

        Etiqueta ID = new Etiqueta(fuente, colorLetraTitulo, "ID");

        Etiqueta diaT = new Etiqueta(fuente, colorLetraTitulo, "Dia");
        diaT.setPreferredSize(diaT.getSize());

        Etiqueta nombre = new Etiqueta(fuente, colorLetraTitulo, "Nombre");
        nombre.setPreferredSize(nombre.getSize());

        Etiqueta apellidos = new Etiqueta(fuente, colorLetraTitulo, "Apellidos");
        apellidos.setPreferredSize(apellidos.getSize());

        Etiqueta horario = new Etiqueta(fuente, colorLetraTitulo, "Horario");
        horario.setPreferredSize(horario.getSize());

        Etiqueta cumplimiento = new Etiqueta(fuente, colorLetraTitulo, "Cumplimiento");
        cumplimiento.setPreferredSize(cumplimiento.getSize());

        int auxY = (panelTitulo.getSize().height - ID.getSize().height) / 2;

        if (this.getWidth() - 1000 < 400) {
            int sepHorario = 180;
            int sepID = 80;
            int sepApellidos = 100;
            int sepNombre = 170;
            int sepCump = 210;

            diaT.setLocation(100, auxY);
            horario.setLocation(diaT.getWidth() + sepHorario, auxY);
            ID.setLocation(horario.getLocation().x + horario.getWidth() + sepID, auxY);
            apellidos.setLocation(ID.getLocation().x + ID.getWidth() + sepApellidos, auxY);
            nombre.setLocation(apellidos.getLocation().x + apellidos.getWidth() + sepNombre, auxY);
            cumplimiento.setLocation(nombre.getLocation().x + nombre.getWidth() + sepCump, auxY);

        } else {
            int sepHorario = 200;
            int sepID = 100;
            int sepApellidos = 120;
            int sepNombre = 190;
            int sepCump = 230;

            diaT.setLocation(100, auxY);
            horario.setLocation(diaT.getWidth() + sepHorario, auxY);
            ID.setLocation(horario.getLocation().x + horario.getWidth() + sepID, auxY);
            apellidos.setLocation(ID.getLocation().x + ID.getWidth() + sepApellidos, auxY);
            nombre.setLocation(apellidos.getLocation().x + apellidos.getWidth() + sepNombre, auxY);
            cumplimiento.setLocation(nombre.getLocation().x + nombre.getWidth() + sepCump, auxY);

        }

        panelTitulo.add(diaT);
        panelTitulo.add(horario);
        panelTitulo.add(ID);
        panelTitulo.add(apellidos);
        panelTitulo.add(nombre);
        panelTitulo.add(cumplimiento);

        int auxAgno = dias.get(0).getFecha().getYear();
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


    public void setCumplimiento(int b) {
        for (PanelDiaBase e1 : panelesCasillas) {
            if (e1 instanceof PanelDiaArch e) {
                ArrayList<PanelTurno> paneles = e.getPanelesTurno();
                for (PanelTurno a : paneles) {
                    if (a instanceof PanelTurnoArch a1) {
                        switch (b) {
                            case 0:
                                a1.getRadioGrupo().clearSelection();
                                a1.getTurno().actualizarCumplimiento(null);
                                break;
                            case 1:

                                if (!a1.getCumplido().isSelected() && !a1.getNoCumplido().isSelected()) {
                                    a1.getCumplido().setSelected(true);
                                    a1.getTurno().actualizarCumplimiento(true);
                                }
                                break;
                            case 2:
                                if (!a1.getCumplido().isSelected() && !a1.getNoCumplido().isSelected()) {
                                    a1.getNoCumplido().setSelected(true);
                                    a1.getTurno().actualizarCumplimiento(false);
                                }
                                break;
                        }
                    }
                }
            }
            revalidate();
            repaint();
        }

    }


    public ArrayList<DiaGuardia> getDias() {
        return dias;
    }
}
