package gui.componentes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

@SuppressWarnings("serial")
public class CustomPopupMenu extends JPopupMenu {
    private final int redondez = 20;
    private final Color colorFondo = Color.WHITE;
    private int ancho = 100;

    public CustomPopupMenu() {
        setOpaque(false);
        setBorder(new CustomRedBorder(Color.GRAY, 5, redondez));
        this.setPreferredSize(new Dimension(ancho, 10));
    }

    public CustomPopupMenu(int ancho) {
        this.ancho = ancho;
        setOpaque(false);
        setBorder(new CustomRedBorder(Color.GRAY, 5, redondez));
        this.setPreferredSize(new Dimension(ancho, 10));
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(colorFondo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), redondez, redondez);

    }

    public void addItem(JMenuItem item) {
        item.setBackground(colorFondo);
        if (item.getPreferredSize().width > ancho) {
            ancho = item.getPreferredSize().width + 10;
        }

        this.setPreferredSize(new Dimension(ancho, this.getPreferredSize().height + item.getPreferredSize().height));
        this.add(item);
    }

    public class CustomRedBorder extends LineBorder {
        private final int arcSize;

        public CustomRedBorder(Color color, int thickness, int arcSize) {
            super(color, thickness);
            this.arcSize = arcSize;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getLineColor());
            g2.drawRoundRect(x, y, width - 1, height - 1, arcSize, arcSize);


        }


    }
}