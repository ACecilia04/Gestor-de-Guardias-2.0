package gui.componentes;

import javax.swing.*;
import java.awt.*;

class CustomJMenu extends JMenu {
    private Color colorFondo = Color.WHITE;

    public CustomJMenu(String nombre) {
        super(nombre);
    }

    public void addItem(JMenuItem item) {
        item.setBackground(colorFondo);
        this.add(item);
    }

    public Color getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

}