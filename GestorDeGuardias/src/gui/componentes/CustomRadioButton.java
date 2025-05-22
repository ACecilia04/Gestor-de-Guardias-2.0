package gui.componentes;

import javax.swing.*;
import java.awt.*;

public class CustomRadioButton extends JRadioButton {

    private final int border = 4;
    private Color colorSelec = new Color(69, 124, 235);
    private Color colorBorde = Color.GRAY;

    public CustomRadioButton(String texto) {
        super("  " + texto);

        setFocusable(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setBackground(new Color(69, 124, 235));

        setSize(getPreferredSize().width + 10, getPreferredSize().height + 20);
    }

    public void paint(Graphics grphcs) {
        super.paint(grphcs);

        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int ly = (getHeight() - 16) / 2; // Posici�n vertical del c�rculo
        int diameter = 16; // Di�metro del c�rculo
        int smallCircleDiameter = 10; // Di�metro del peque�o c�rculo


        if (isSelected()) {
            g2.setColor(colorSelec);
            g2.fillOval(1, ly, diameter, diameter);
            g2.setColor(Color.WHITE);
            g2.fillOval(2, ly + 1, diameter - 2, diameter - 2);

            int smallCircleX = 4;
            int smallCircleY = ly + 3;
            g2.setColor(colorSelec);
            g2.fillOval(smallCircleX, smallCircleY, smallCircleDiameter, smallCircleDiameter);
        } else {
            g2.setColor(colorBorde);
            g2.fillOval(1, ly, diameter, diameter);
            g2.setColor(Color.WHITE);
            g2.fillOval(2, ly + 1, diameter - 2, diameter - 2);
        }


        g2.dispose();
    }

    public void setNuevaFuente(Font x) {
        setFont(x);
        Dimension x2 = getPreferredSize();
        setSize(x2);
    }

    public Color getColorSelec() {
        return colorSelec;
    }

    public void setColorSelec(Color colorSelec) {
        this.colorSelec = colorSelec;
    }

    public Color getColorBorde() {
        return colorBorde;
    }

    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
    }
}
