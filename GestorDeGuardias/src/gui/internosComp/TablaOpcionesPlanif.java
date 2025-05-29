package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.CustomSplitPane;
import gui.componentes.CustomTabla;
import model.DiaGuardia;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class TablaOpcionesPlanif extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JPanel superior;
    private final CustomSplitPane splitPane;
    //    private final int separacion = 10;
    //    private CustomSplitPane splitPaneInterno;
//    private JPanel panel1;
    private final JPanel panel2;
    private final JPanel panel3;
    private final JPanel panel4;
    private final CustomTabla tablaSelec;
    //    private final int x = 40;
//    private final int y = separacion + 10;
    private final Boton botonAddPersona;
    private final Boton guardar;
    private final Boton botonAut;
    Paleta paleta = new Paleta();


    //   private final Font fuente = new Font("Arial", Font.PLAIN, 17);

    public TablaOpcionesPlanif(Dimension dimension) {
        setPreferredSize(dimension);
        setBackground(paleta.getColorFondoTabla());
        setLayout(new BorderLayout());

        //Superior
        superior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        superior.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        superior.setBackground(getBackground());

        add(superior, BorderLayout.NORTH);


        //Panel2
        panel2 = new JPanel(null);
        panel2.setBackground(getBackground());
        botonAut = new Boton("Generar Aut");
        botonAut.addIcono("/iconos/Espiral.png");
        botonAut.setNuevoSize(new Dimension(botonAut.getSize().width + 10, botonAut.getSize().height + 10));
        botonAut.cambiarIconTextGap(10);
        botonAut.setColorLetra(Color.WHITE);
        botonAut.setColorLetraPres(Color.WHITE);
        botonAut.setColorFondo(paleta.getColorCaracteristico());
        Dimension aux = new Dimension(botonAut.getSize().width + 30, botonAut.getSize().height);
        botonAut.setNuevoSize(aux);

        tablaSelec = new CustomTabla("Seleccionados");
        botonAddPersona = new Boton("Añadir Persona");
        botonAddPersona.setNuevoSize(new Dimension(botonAut.getSize().width + 10, botonAut.getSize().height + 10));

        aux = new Dimension(botonAut.getSize().width, botonAut.getSize().height);
        botonAddPersona.setNuevoSize(aux);

        botonAddPersona.setLocation((this.getPreferredSize().width - botonAut.getWidth()) / 2, 20);
        int yAux = botonAddPersona.getLocation().y + botonAddPersona.getPreferredSize().height + 25;
        botonAut.setLocation((this.getPreferredSize().width - botonAut.getWidth()) / 2, yAux);

        actualizarColorBoton();


        panel2.add(botonAut);
        panel2.add(botonAddPersona);
        panel2.setMinimumSize(new Dimension(getPreferredSize().width, 150));

        //Panel3
        panel3 = new JPanel(new BorderLayout());
        panel3.setBackground(getBackground());


        panel3.add(tablaSelec, BorderLayout.CENTER);

        panel3.setMinimumSize(new Dimension(this.getPreferredSize().width, 200));

        splitPane = new CustomSplitPane(panel2, panel3, JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);

        //Panel4 Guardar
        panel4 = new JPanel(null);
        panel4.setBackground(getBackground());
        panel4.setPreferredSize(new Dimension(this.getPreferredSize().width, 100));
        guardar = new Boton("Guardar");

        guardar.setNuevoSize(new Dimension(140, 40));
        guardar.setBordeado(true);
        guardar.setColorPresionado(paleta.getColorCaracteristico());

        int x = (panel4.getPreferredSize().width - guardar.getSize().width) / 2;
        int y = (panel4.getPreferredSize().height - guardar.getSize().height) / 2;
        guardar.setLocation(x, y);
        panel4.add(guardar);
        add(panel4, BorderLayout.SOUTH);

        //Bordes
        Border border = BorderFactory.createMatteBorder(0, 3, 0, 0, paleta.getColorBorde());
        Border bordeMargen = BorderFactory.createEmptyBorder(10, 15, 0, 0);
        Border margenDoubleBorder = BorderFactory.createCompoundBorder(border, bordeMargen);

        splitPane.setBorder(border);
        panel4.setBorder(border);

        superior.setBorder(margenDoubleBorder);
        setBorder(border);

    }

    public void actualizarTablaSelec(ArrayList<PanelDiaBase> panelesCasillas) {
        tablaSelec.limpiarTabla();
        ArrayList<Component> panelesSelec = new ArrayList<>();

        for (PanelDiaBase e : panelesCasillas) {
            String inicio = "   " + e.getFechaNumero() + "   " + e.getFechaSemana() + "   :   ";
            ArrayList<PanelTurno> panelesTurno = e.getPanelesTurno();

            if (e.isSeleccionado()) {
                for (PanelTurno a : panelesTurno) {
                    tablaSelec.agregarFila(inicio + a.getHorario().getText());
                    panelesSelec.add(a);
                }

            } else {

                for (PanelTurno a : panelesTurno) {
                    if (a.isSeleccionado()) {
                        tablaSelec.agregarFila(inicio + a.getHorario().getText());
                        panelesSelec.add(a);
                    }
                }
            }
        }
        tablaSelec.setComponentes(panelesSelec);
        actualizarColorBoton();
    }

    public void actualizarColorBoton() {
        if (tablaSelec.getCantFilas() == 1) {
            botonAddPersona.setColorLetra(Color.WHITE);
            botonAddPersona.setColorLetraPres(Color.WHITE);
            botonAddPersona.setColorFondo(paleta.getColorCaracteristico());
            botonAddPersona.setSeleccionable(true);

        } else {
            botonAddPersona.setColorLetra(Color.GRAY.darker());
            botonAddPersona.setColorFondo(Color.LIGHT_GRAY);
            botonAddPersona.setSeleccionable(false);

        }
    }

    public Boton getGuardar() {
        return guardar;
    }

    public Boton getBotonAut() {
        return botonAut;
    }

    public Boton getBotonAddPersona() {
        return botonAddPersona;
    }

    public CustomTabla getTablaSelec() {
        return tablaSelec;
    }

    public ArrayList<DiaGuardia> getDiasSeleccionados() {
        ArrayList<DiaGuardia> diasAux = new ArrayList<>();
        ArrayList<Component> comp = tablaSelec.getComponentes();
        for (Component a : comp) {
            if (a instanceof PanelTurno) {
                diasAux.add(((PanelTurno) a).getFecha());
            }

        }

        return diasAux;

    }


}
