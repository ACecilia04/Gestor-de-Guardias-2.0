package gui.componentes;


import javax.swing.*;
import java.awt.*;

public class Cuadro extends JPanel {
    //Valores predefinidos
    public static final int redBAJA = 15;
    public static final int redMED = 40;
    public static final int redALTA = 50;
    public static final int transBAJA = 10;
    public static final int transMED = 20;
    public static final int transALTA = 50;
    private final int bordeGrosor = 5;
    protected int redondez;
    protected int transparencia = 0;
    private Color colorFondo;
    private Color colorBorde = Color.GRAY;
    private boolean isBorde = false;
    private boolean agrandarCuandoBorde = false;

    public Cuadro(Dimension dimension, int redondez, Color color) {

        colorFondo = color;
        setBackground(colorFondo);
        setPreferredSize(dimension);
        setSize(dimension);

        this.redondez = redondez;

        setOpaque(false);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Transparencia
        int aux = (int) ((100 - transparencia) * 2.55);

        //Redondez
        if (isBorde) {
            g2.setColor(new Color(colorBorde.getRed(), colorBorde.getGreen(), colorBorde.getBlue(), aux));

            if (redondez > 0) {
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), redondez, redondez);
                g2.setColor(new Color(colorFondo.getRed(), colorFondo.getGreen(), colorFondo.getBlue(), aux));
                g2.fillRoundRect(bordeGrosor, bordeGrosor, getWidth() - bordeGrosor * 2, getHeight() - bordeGrosor * 2, redondez, redondez);
            } else {
                g2.fillRect(0, 0, getWidth() + bordeGrosor, getHeight() + bordeGrosor);
                g2.setColor(new Color(colorFondo.getRed(), colorFondo.getGreen(), colorFondo.getBlue(), aux));
                g2.fillRect(bordeGrosor, bordeGrosor, getWidth() - bordeGrosor * 2, getHeight() - bordeGrosor * 2);
            }

        } else {
            g2.setColor(new Color(colorFondo.getRed(), colorFondo.getGreen(), colorFondo.getBlue(), aux));
            if (redondez > 0) {
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), redondez, redondez);
            } else {
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        }

    }

    public void setTransparencia(int transparencia) {
        this.transparencia = transparencia;
    }


    public void setColorFondo(Color color) {
        colorFondo = color;
        repaint();
    }


    public Color getColorBorde() {
        return colorBorde;
    }

    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
    }

    public boolean isBorde() {
        return isBorde;
    }

    public void setBorde(boolean isBorde) {
        if (this.agrandarCuandoBorde) {
            if (isBorde) {
                setSize(getWidth() + bordeGrosor * 2, getHeight() + bordeGrosor * 2);
                repaint();
                revalidate();
            } else {
                setSize(getWidth() - bordeGrosor * 2, getHeight() - bordeGrosor * 2);
                repaint();
                revalidate();
            }
        }
        setPreferredSize(getSize());
        this.isBorde = isBorde;
    }

    public boolean isAgrandarCuandoBorde() {
        return agrandarCuandoBorde;
    }

    public void setAgrandarCuandoBorde(boolean x) {

        this.agrandarCuandoBorde = x;
    }

    public boolean isSeleccionado() {
        // TODO Auto-generated method stub
        return isBorde;
    }

}
