package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PanelOpcionesArchivar extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Paleta paleta = new Paleta();

    private final JPanel panel3;

    private final Dimension dimBoton = new Dimension(140, 40);

    private final Boton botonCump;
    private final Boton botonCump2;
    private final Boton reiniciar;
    private final Boton botonGuardar; // <--- NUEVO

    public PanelOpcionesArchivar(Dimension dim) {
        setPreferredSize(dim);
        setSize(dim);
        setBackground(paleta.getColorFondoTabla());
        setLayout(new BorderLayout());

        panel3 = new JPanel(null);
        panel3.setBackground(getBackground());
        panel3.setPreferredSize(new Dimension(this.getPreferredSize().width, 500));
        botonCump = new Boton("Todos cumplidos");

        botonCump.setNuevoSize(dimBoton);
        botonCump.setColorFondo(paleta.getColorCasillaTabla());
        botonCump.setColorPresionado(paleta.getColorCaracteristico());
        botonCump.setColorLetraPres(Color.WHITE);

        botonCump2 = new Boton("Todos incumplidos");

        botonCump2.setNuevoSize(dimBoton);
        botonCump2.setColorFondo(paleta.getColorCasillaTabla());
        botonCump2.setColorPresionado(paleta.getColorCaracteristico());
        botonCump2.setColorLetraPres(Color.WHITE);

        reiniciar = new Boton("Reiniciar");

        reiniciar.setNuevoSize(dimBoton);
        reiniciar.setColorFondo(paleta.getColorCasillaTabla());
        reiniciar.setColorPresionado(paleta.getColorCaracteristico());
        reiniciar.setColorLetraPres(Color.WHITE);

        botonGuardar = new Boton("Guardar");  // <--- NUEVO

        botonGuardar.setNuevoSize(dimBoton);
        botonGuardar.setColorFondo(paleta.getColorCasillaTabla());
        botonGuardar.setColorPresionado(paleta.getColorCaracteristico());
        botonGuardar.setColorLetraPres(Color.WHITE);

        int x = (panel3.getPreferredSize().width - dimBoton.getSize().width) / 2;
        int y = (panel3.getPreferredSize().height - dimBoton.getSize().height) / 2;

        y += 50;
        reiniciar.setLocation(x, y);
        y += botonCump.getHeight() + 20;
        botonCump.setLocation(x, y);
        y += botonCump.getHeight() + 20;
        botonCump2.setLocation(x, y);
        y += botonCump2.getHeight() + 20;
        botonGuardar.setLocation(x, y); // <--- NUEVO

        panel3.add(botonCump);
        panel3.add(botonCump2);
        panel3.add(reiniciar);
        panel3.add(botonGuardar); // <--- NUEVO

        this.add(panel3, BorderLayout.CENTER);
        //Bordes
        Border border = BorderFactory.createMatteBorder(0, 3, 0, 0, paleta.getColorBorde());

        setBorder(border);
    }

    public Boton getBotonCump() {
        return botonCump;
    }

    public Boton getBotonCump2() {
        return botonCump2;
    }

    public Boton getReiniciar() {
        return reiniciar;
    }

    public Boton getBotonGuardar() {
        return botonGuardar;
    }
}