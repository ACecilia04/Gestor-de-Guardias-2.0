package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.secciones.PantallaUsuarios;

import javax.swing.*;
import java.awt.*;

public class PanelSupOpcionesUsuarios extends JPanel {
    PantallaUsuarios panelAfectado;

    public PanelSupOpcionesUsuarios(int alto, PantallaUsuarios panelAfectado) {
        this.panelAfectado = panelAfectado;
        setBackground(new Paleta().getColorFondoTabla());

        Boton nuevoBtn = new Boton();
        nuevoBtn.addIcono("/iconos/Crear.png");
        nuevoBtn.setSelectLetra(true);
        nuevoBtn.cambiarIconTextGap(10);
        nuevoBtn.setToolTipText("Crear Nuevo Usuario");
        nuevoBtn.addActionListener(e -> panelAfectado.agregarUsuario());


        Boton editarBtn = new Boton();
        editarBtn.addIcono("/iconos/Editar.png");
        editarBtn.setSelectLetra(true);
        editarBtn.cambiarIconTextGap(10);
        editarBtn.setToolTipText("Editar Usuario");
        editarBtn.addActionListener(e -> panelAfectado.modificarUsuario());

        Boton borrarBtn = new Boton();
        borrarBtn.addIcono("/iconos/Borrar.png");
        borrarBtn.setSelectLetra(true);
        borrarBtn.cambiarIconTextGap(10);
        borrarBtn.setToolTipText("Borrar Usuario");
        borrarBtn.addActionListener(e -> panelAfectado.eliminarUsuario());


        add(nuevoBtn);
        add(editarBtn);
        add(borrarBtn);

        FlowLayout miLayout = new FlowLayout(FlowLayout.RIGHT, 5, alto - nuevoBtn.getHeight() - 8);
        setLayout(miLayout);
    }
}

