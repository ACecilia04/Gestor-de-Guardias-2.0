package gui.componentes;

import javax.swing.*;
import java.awt.*;

public class CustomCheckBox extends JCheckBox {

    private final int border = 4;
    private final Color colorSelec = new Color(69, 124, 235);
    private final Color colorBorde = Color.GRAY;

    public CustomCheckBox(String texto) {
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
        int ly = (getHeight() - 16) / 2;
        if (isSelected()) {
            if (isEnabled()) {
                g2.setColor(colorSelec);
            } else {
                g2.setColor(Color.GRAY);
            }
            g2.fillRoundRect(1, ly, 16, 16, border, border);
            // Draw Check Icon
            int[] px = {4, 8, 14, 12, 8, 6};
            int[] py = {ly + 8, ly + 14, ly + 5, ly + 3, ly + 10, ly + 6};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(px, py, px.length);
        } else {
            g2.setColor(colorBorde);
            g2.fillRoundRect(1, ly, 16, 16, border, border);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(2, ly + 1, 14, 14, border, border);
        }
        g2.dispose();
    }

    public void setNuevaFuente(Font x) {
        setFont(x);
        Dimension x2 = getPreferredSize();
        setSize(x2);
    }

//    public Color getColorSelec() {
//        return colorSelec;
//    }
//
//    public void setColorSelec(Color colorSelec) {
//        this.colorSelec = colorSelec;
//    }
//
//    public Color getColorBorde() {
//        return colorBorde;
//    }
//
//    public void setColorBorde(Color colorBorde) {
//        this.colorBorde = colorBorde;
//    }


}