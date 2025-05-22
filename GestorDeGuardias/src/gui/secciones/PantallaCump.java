package gui.secciones;

import gui.internosComp.PanelOpcionesArchivar;
import gui.internosComp.TablaArchivar;
import model.DiaGuardia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PantallaCump extends JPanel {
    //    private JPanel contentPane;
    private final PanelOpcionesArchivar tablaOpciones;

    private final int opcionesAncho = 200;

//    private final int margen = 25;

    private TablaArchivar tabla;

    public PantallaCump() {
        this.setLayout(new BorderLayout());
        this.tablaOpciones = new PanelOpcionesArchivar(new Dimension(opcionesAncho, 100));

        add(tablaOpciones, BorderLayout.EAST);

        tablaOpciones.getReiniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabla != null) {
                    tabla.setCumplimiento(0);
                }
            }
        });


        tablaOpciones.getBotonCump().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabla != null) {
                    tabla.setCumplimiento(1);
                }
            }
        });

        tablaOpciones.getBotonCump2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabla != null) {
                    tabla.setCumplimiento(2);
                }
            }
        });

    }

    public TablaArchivar getTabla() {
        return tabla;
    }

    public void setTabla(ArrayList<DiaGuardia> diasAux) {
        if (tabla != null) {
            this.remove(tabla);
        }

        Ventana venAux = Ventana.getInstance();
        Dimension auxDim = new Dimension(venAux.getPanelVacio().getSize().width - opcionesAncho, venAux.getPanelVacio().getSize().height);
        this.tabla = new TablaArchivar(auxDim, Color.WHITE, diasAux);

        this.add(tabla, BorderLayout.CENTER);

        revalidate();
        repaint();
    }


}
