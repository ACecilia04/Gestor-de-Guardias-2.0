package gui.secciones;

import gui.internosComp.TablaArchivar;
import model.DiaGuardia;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PantallaCump extends JPanel {
    private TablaArchivar tabla;

    public PantallaCump() {
        this.setLayout(new BorderLayout());
    }

    public TablaArchivar getTabla() {
        return tabla;
    }

    public void setTabla(ArrayList<DiaGuardia> diasAux) {
        if (tabla != null) {
            this.remove(tabla);
        }

        Ventana venAux = Ventana.getInstance();
        Dimension auxDim = new Dimension(venAux.getPanelVacio().getSize().width, venAux.getPanelVacio().getSize().height);
        this.tabla = new TablaArchivar(auxDim, Color.WHITE, diasAux);

        this.add(tabla, BorderLayout.CENTER);
        this.tabla.actualizarVistaTabla();

        revalidate();
        repaint();
    }


}
