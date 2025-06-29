package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.secciones.PanelConfig;
import gui.secciones.PantallaCump;
import gui.secciones.Ventana;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;

import javax.swing.*;
import java.awt.*;

public class PanelSupOpcionesConfig extends JPanel {
    PanelConfig panelAfectado;

    public PanelSupOpcionesConfig(int alto, PanelConfig panelAfectado) {
        this.panelAfectado = panelAfectado;
        setBackground(new Paleta().getColorFondoTabla());

        Boton nuevoBtn = new Boton();
        nuevoBtn.addIcono("/iconos/Crear.png");
        nuevoBtn.setSelectLetra(true);
        nuevoBtn.cambiarIconTextGap(10);
        nuevoBtn.setToolTipText("Crear Nuevo");
        nuevoBtn.addActionListener(e -> panelAfectado.agregarConfiguracion());


        Boton editarBtn = new Boton();
        editarBtn.addIcono("/iconos/Editar.png");
        editarBtn.setSelectLetra(true);
        editarBtn.cambiarIconTextGap(10);
        editarBtn.setToolTipText("Editar");
        editarBtn.addActionListener(e -> panelAfectado.modificarConfiguracion());

        Boton borrarBtn = new Boton();
        borrarBtn.addIcono("/iconos/Borrar.png");
        borrarBtn.setSelectLetra(true);
        borrarBtn.cambiarIconTextGap(10);
        borrarBtn.setToolTipText("Borrar");
        borrarBtn.addActionListener(e -> {
            try {
                panelAfectado.eliminarConfiguracion();
            } catch (SqlServerCustomException ex) {
                throw new RuntimeException(ex);
            }
        });

        add(nuevoBtn);
        add(editarBtn);
        add(borrarBtn);

        FlowLayout miLayout = new FlowLayout(FlowLayout.RIGHT, 5, alto - nuevoBtn.getHeight() - 8);
        setLayout(miLayout);
    }

}

