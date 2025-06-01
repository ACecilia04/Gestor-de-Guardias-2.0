package gui.componentes;

import gui.auxiliares.Paleta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@SuppressWarnings("serial")
public class Etiqueta extends JLabel {
    protected boolean isBold = false;
    Paleta paleta = new Paleta();
    private Font fuente = new Font("Arial", Font.PLAIN, 18);
    private Color colorLetra = paleta.getLetraOscura();
    private Color ColorFondo;

    //Constructors*****************************************

    public Etiqueta(String texto) {
        super(texto);
        this.inicializar();
    }

    public Etiqueta(Color color, String texto) {
        super(texto);
        colorLetra = color;
        this.inicializar();
    }

    public Etiqueta(Font fuente, Color color, String texto) {
        super(texto);
        this.fuente = fuente;
        colorLetra = color;
        this.inicializar();
    }

    public void inicializar() {
        setFocusable(false);
        setOpaque(true);
        setForeground(colorLetra);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setFont(fuente);

        Dimension x = getPreferredSize();
        setSize(x);
    }

    public void setNuevoSizeLetra(float x) {
        Font fuenteActual = fuente.deriveFont(x);
        fuente = fuenteActual;
        setFont(fuenteActual);
        Dimension x2 = getPreferredSize();
        setSize(x2);

    }

    public void setBold(boolean x) {
        if (x && !isBold) {
            Font fuenteActual = this.getFont();
            Font fuente = fuenteActual.deriveFont(Font.BOLD);
            setFont(fuente);
            isBold = true;
        } else if (!x && isBold) {
            Font fuenteActual = this.getFont();
            fuente = fuenteActual.deriveFont(Font.PLAIN);
            setFont(fuente);
            isBold = false;
        }
        Dimension x2 = getPreferredSize();
        setSize(x2);
    }

    private void setFondoContenedor() {
        Container cont = SwingUtilities.getAncestorOfClass(Container.class, this);
        if (cont != null && ColorFondo == null) {
            setBackground(cont.getBackground());
        }
        if (ColorFondo != null) {
            setBackground(ColorFondo);

        }
    }

    public void setTexto(String text) {
        this.setText(text);
        this.setSize(getPreferredSize());

    }

    //Metodos Sobreescrittos*****************************************
    @Override //Reconoce cuando el componente se vuelve visible
    public void addNotify() {
        super.addNotify();
        setFondoContenedor();
    }


    public Color getColorFondo() {
        return ColorFondo;
    }

    public void setColorFondo(Color colorFondo) {
        ColorFondo = colorFondo;
        setBackground(ColorFondo);
    }

}
