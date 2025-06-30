package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.secciones.PanelConfig;
import gui.secciones.PantallaUsuarios;
import utils.dao.SqlServerCustomException;

import javax.swing.*;
import java.awt.*;

public class PanelSupOpcionesConfig extends JPanel {
    PanelConfig panelReferencia;
    Boton nuevoBtn;
    Boton editarBtn;
    Boton borrarBtn;
    private boolean algunaConfigSeleccionada;


    public PanelSupOpcionesConfig(int alto) {
        setBackground(new Paleta().getColorFondoTabla());

        nuevoBtn = new Boton();
        nuevoBtn.addIcono("/iconos/Crear.png");
        nuevoBtn.setSelectLetra(true);
        nuevoBtn.cambiarIconTextGap(10);
        nuevoBtn.setToolTipText("Crear Nuevo");
        nuevoBtn.addActionListener(e -> panelReferencia.agregarConfiguracion());


        editarBtn = new Boton();
        editarBtn.addIcono("/iconos/Editar.png");
        editarBtn.setSelectLetra(true);
        editarBtn.cambiarIconTextGap(10);
        editarBtn.setToolTipText("Editar");
        editarBtn.setEnabled(algunaConfigSeleccionada);
        editarBtn.addActionListener(e -> panelReferencia.modificarConfiguracion());

        borrarBtn = new Boton();
        borrarBtn.addIcono("/iconos/Borrar.png");
        borrarBtn.setSelectLetra(true);
        borrarBtn.cambiarIconTextGap(10);
        borrarBtn.setEnabled(algunaConfigSeleccionada);
        borrarBtn.setToolTipText("Borrar");
        borrarBtn.addActionListener(e -> {
            try {
                panelReferencia.eliminarConfiguracion();
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
    public void setAlgunaConfigSeleccionada(boolean algunaConfigSeleccionada) {
        this.algunaConfigSeleccionada = algunaConfigSeleccionada;
        editarBtn.setEnabled(algunaConfigSeleccionada);
        borrarBtn.setEnabled(algunaConfigSeleccionada);

    }


    public void setPanelReferencia(PanelConfig panelReferencia) {
        this.panelReferencia = panelReferencia;
    }
}

