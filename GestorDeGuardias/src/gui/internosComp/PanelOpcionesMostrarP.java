package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.Buscar;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;


public class PanelOpcionesMostrarP extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Paleta paleta = new Paleta();

    private final JPanel superior;
    private final JPanel panel1;
    private final JPanel panel4;

    private final Boton botonVerPlanif;
    private final Boton botonBorrarPlanif;
    private final Boton botonEditarPlanif;
    private final Boton botonCrearPlanif;
    private final Boton botonExport;
    private final int separacion = 10;
    private final int x = 20;
    private final int y = separacion + 10;
    private final Font fuente = new Font("Arial", Font.PLAIN, 14);
    private Buscar buscar;
    private Dimension dimBoton = new Dimension(200, 35);

    public PanelOpcionesMostrarP(Dimension dim) {
        setPreferredSize(dim);
        setSize(dim);
        setBackground(paleta.getColorFondoTabla());
        setLayout(new BorderLayout());

        //Superior
        superior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        superior.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        superior.setBackground(getBackground());

        //panel1
        panel1 = new JPanel(null);


        panel1.setBackground(getBackground());
        panel1.setMinimumSize(new Dimension(this.getPreferredSize().width, y));


        //Panel4 Guardar
        panel4 = new JPanel(null);
        panel4.setBackground(getBackground());
        panel4.setPreferredSize(new Dimension(this.getPreferredSize().width, 500));

        ArrayList<Boton> botones = new ArrayList<>();
        int botonSep = 20;
        int diferencia = 20;
        int x = (panel4.getPreferredSize().width - dimBoton.getSize().width) / 2;
        int y = botonSep;

        botonVerPlanif = new Boton("Ver");

//        botoaddEstud.setBordeado(true);
        botonVerPlanif.setColorPresionado(paleta.getColorCaracteristico());
        botonVerPlanif.setNuevoSize(dimBoton);
        botonVerPlanif.setLocation(x, y);
        botonVerPlanif.setColorFondo(paleta.getColorCaracteristico());
        botonVerPlanif.setColorLetra(Color.WHITE);
        botonVerPlanif.setColorLetraPres(Color.WHITE);


        botonBorrarPlanif = new Boton("Borrar");
        botonEditarPlanif = new Boton("Editar");
        botonCrearPlanif = new Boton("Nuevo");
        botonExport = new Boton("Exportar PDF");

        botonEditarPlanif.setNuevoSize(dimBoton);
        botonCrearPlanif.setNuevoSize(dimBoton);
        botonBorrarPlanif.setNuevoSize(dimBoton);
        botonExport.setNuevoSize(dimBoton);

        botones.add(botonBorrarPlanif);
        botones.add(botonEditarPlanif);
        botones.add(botonCrearPlanif);
        botones.add(botonExport);

        panel4.add(botonVerPlanif);
        y += dimBoton.height + y;
        dimBoton = new Dimension(dimBoton.width - diferencia, dimBoton.height);
        x = (panel4.getPreferredSize().width - dimBoton.getSize().width) / 2;

        for (Boton e : botones) {
            e.setBordeado(true);
            e.setColorPresionado(paleta.getColorCaracteristico());
            e.setNuevoSize(new Dimension(dimBoton.width, dimBoton.height));
            e.setLocation(x, y);
            panel4.add(e);
            y += dimBoton.height + botonSep;
        }

        //add
        add(superior, BorderLayout.NORTH);
        add(panel4, BorderLayout.CENTER);


        //Bordes
        Border border = BorderFactory.createMatteBorder(0, 3, 0, 0, paleta.getColorBorde());
        Border bordeMargen = BorderFactory.createEmptyBorder(10, 15, 0, 0);
        Border margenDoubleBorder = BorderFactory.createCompoundBorder(border, bordeMargen);

        panel1.setBorder(border);
        panel4.setBorder(border);

        superior.setBorder(margenDoubleBorder);
        setBorder(border);
    }


    public Buscar getBuscar() {
        return buscar;
    }

    public Boton getBotonVerPlanif() {
        return botonVerPlanif;
    }

    public Boton getBotonBorrarPlanif() {
        return botonBorrarPlanif;
    }

    public Boton getBotonEditarPlanif() {
        return botonEditarPlanif;
    }

    public Boton getBotonCrearPlanif() {
        return botonCrearPlanif;
    }
    public Boton getBotonExport() {
        return botonExport;
    }

}
