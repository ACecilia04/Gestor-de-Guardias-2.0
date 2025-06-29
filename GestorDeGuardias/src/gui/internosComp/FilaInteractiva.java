package gui.internosComp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class FilaInteractiva extends JPanel {
    private boolean seleccionado = false;
    private final Color colorNormal;
    private final Color colorAlterno;
    private final Color colorSeleccionado;
    private final Color colorTextoNormal;
    private final Color colorTextoSeleccionado;

    public FilaInteractiva(Boolean isAlt, Color colorSeleccionado, Color colorTextoSeleccionado,
                           Color colorTextoNormal, Color colorAlterno, Color colorNormal) {
        super(new GridBagLayout());

        this.colorSeleccionado = colorSeleccionado;
        this.colorTextoSeleccionado = colorTextoSeleccionado;
        this.colorTextoNormal = colorTextoNormal;
        this.colorAlterno = colorAlterno;
        this.colorNormal = colorNormal;

        Color bg = isAlt ? colorAlterno : colorNormal;
        applyRowBackground(bg);
        setOpaque(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!seleccionado) {
                    applyRowBackground(bg.darker());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!seleccionado) {
                    applyRowBackground(bg);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Selection handled externally
            }
        });
    }

    public void setSeleccionado(boolean selected) {
        this.seleccionado = selected;
        Color bg = seleccionado ? colorSeleccionado : getNormalBackground();
        applyRowBackground(bg);
//        for (Component comp : getComponents()) {
//            if (comp instanceof JLabel lbl) {
//                lbl.setForeground(seleccionado ? colorTextoSeleccionado : colorTextoNormal);
//            }
//        }
        repaint();
    }

    public boolean getSeleccionado() {
        return seleccionado;
    }

    public void resetColor(boolean isAlt) {
        if (!seleccionado) {
            Color bg = isAlt ? colorAlterno : colorNormal;
            applyRowBackground(bg);
            for (Component comp : getComponents()) {
                if (comp instanceof JLabel lbl) {
                    lbl.setForeground(colorTextoNormal);
                }
            }
        }
    }

    private Color getNormalBackground() {
        return getBackground().equals(colorAlterno.darker()) ? colorAlterno : colorNormal;
    }

    private void applyRowBackground(Color bg) {
        setBackground(bg);
        for (Component comp : getComponents()) {
            if (comp instanceof JLabel lbl) {
                lbl.setBackground(bg);
                lbl.setOpaque(true);
            }
        }
    }
}

