package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;

import javax.swing.*;
import java.awt.*;

public class PanelEdicion2 extends JPanel {
    private static final long serialVersionUID = 1L;


    private final Paleta paleta = new Paleta();

    public PanelEdicion2(int alto) {
        setBackground(paleta.getColorFondoTabla());

        Boton boton1 = new Boton("Intercambiar Dias");
        boton1.addIcono("/iconos/Estrella.png");
        boton1.setSelectLetra(true);
        boton1.cambiarIconTextGap(10);

        Boton boton2 = new Boton("Eliminar");
        boton2.addIcono("/iconos/Espiral.png");
        boton2.setSelectLetra(true);
        boton2.cambiarIconTextGap(10);

        Boton boton3 = new Boton("Cambiar Horarios");
        boton3.addIcono("/iconos/X.png");
        boton3.setSelectLetra(true);
        boton3.cambiarIconTextGap(10);

        add(boton1);
        add(boton2);
        add(boton3);

        FlowLayout miLayout = new FlowLayout(FlowLayout.LEFT, 5, alto - boton1.getHeight() - 8);
        setLayout(miLayout);

    }

}
