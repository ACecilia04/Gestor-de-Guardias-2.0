package gui.componentes;


import gui.auxiliares.ModernScrollBarUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

@SuppressWarnings("serial")
public class CustomScrollBar extends JScrollBar {

    public CustomScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(10, 8));
        setForeground(new Color(48, 144, 216));
        setBackground(Color.WHITE);
        setOpaque(true);
        addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setUnitIncrement(50); // Ajusta este valor seg�n sea necesario
            }
        });

        setFocusable(false);

    }
}
