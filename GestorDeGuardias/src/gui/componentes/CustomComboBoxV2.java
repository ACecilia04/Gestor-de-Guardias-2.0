package gui.componentes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.geom.Area;

public class CustomComboBoxV2 extends JComboBox<String> {
    private final Color backgroundColor;
    private final Color borderColor;
    private final int cornerRadius;

    public CustomComboBoxV2(String[] items, Dimension dimension, int cornerRadius, Color backgroundColor) {
        super(items);
        this.cornerRadius = cornerRadius;
        this.backgroundColor = backgroundColor;
        this.borderColor = Color.GRAY;

        setSelectedIndex(-1);
        setPreferredSize(dimension);
        setOpaque(false);
        setBackground(backgroundColor);
        setRenderer(new CustomRenderer());
        setUI(new CustomComboBoxUI());
        ((JComponent) getUI().getAccessibleChild(this, 0)).setBackground(backgroundColor);
        setBorder(new RoundedBorder(cornerRadius, borderColor, backgroundColor));
    }

    static class RoundedBorder implements Border {
        private final int radius;
        private final Color borderColor;
        private final Color fillColor;

        public RoundedBorder(int radius, Color borderColor, Color fillColor) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.fillColor = fillColor;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create a clipping path to draw only around the edges (border thickness ~3px)
            Area clip = new java.awt.geom.Area(
                    new java.awt.geom.RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius)
            );
            Shape inner = new java.awt.geom.RoundRectangle2D.Float(x + 3, y + 3, width - 7, height - 7, radius - 3, radius - 3);
            clip.subtract(new java.awt.geom.Area(inner));

            g2.setClip(clip);
            g2.setColor(fillColor);
            g2.fillRect(x, y, width, height); // Paint only the clipped frame

            g2.setClip(null);
            g2.setColor(borderColor);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);

            g2.dispose();
        }
    }

    private class CustomComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            ImageIcon arrowIcon = new ImageIcon(getClass().getResource("/iconos/FlechaAbajo.png"));
            JButton button = new JButton(arrowIcon);
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
            return button;
        }

    }

    private class CustomRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setOpaque(true);
            label.setForeground(Color.DARK_GRAY);

            if (value != null) {
                label.setBackground(backgroundColor);
                label.setText(value.toString());

            } else {
                label.setBackground(isSelected ? new Color(220, 220, 220) : backgroundColor);
            }

            return label;
        }
    }
}
