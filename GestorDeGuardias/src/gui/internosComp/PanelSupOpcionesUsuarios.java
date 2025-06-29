package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.secciones.MostrarPlanif;
import gui.secciones.PantallaUsuarios;

import javax.swing.*;
import java.awt.*;

public class PanelSupOpcionesUsuarios extends JPanel {
    PantallaUsuarios panelReferencia;
    Boton nuevoBtn;
    Boton editarBtn;
    Boton borrarBtn;
    private boolean algunUsuarioSeleccionado;

    public PanelSupOpcionesUsuarios(int alto) {
        setBackground(new Paleta().getColorFondoTabla());

        nuevoBtn = new Boton();
        nuevoBtn.addIcono("/iconos/Crear.png");
        nuevoBtn.setSelectLetra(true);
        nuevoBtn.cambiarIconTextGap(10);
        nuevoBtn.setToolTipText("Crear Nuevo Usuario");
        nuevoBtn.addActionListener(e -> panelReferencia.agregarUsuario());

        editarBtn = new Boton();
        editarBtn.addIcono("/iconos/Editar.png");
        editarBtn.setSelectLetra(true);
        editarBtn.cambiarIconTextGap(10);
        editarBtn.setToolTipText("Editar Usuario");
        editarBtn.addActionListener(e -> panelReferencia.modificarUsuario());

        borrarBtn = new Boton();
        borrarBtn.addIcono("/iconos/Borrar.png");
        borrarBtn.setSelectLetra(true);
        borrarBtn.cambiarIconTextGap(10);
        borrarBtn.setToolTipText("Borrar Usuario");
        borrarBtn.addActionListener(e -> panelReferencia.eliminarUsuario());

        add(nuevoBtn);
        add(editarBtn);
        add(borrarBtn);

        FlowLayout miLayout = new FlowLayout(FlowLayout.RIGHT, 5, alto - nuevoBtn.getHeight() - 8);
        setLayout(miLayout);
        setEdicionHabilitada(true);
    }


    public void setAlgunUsuarioSeleccionado(boolean algunUsuarioSeleccionado) {
        this.algunUsuarioSeleccionado = algunUsuarioSeleccionado;
        editarBtn.setEnabled(algunUsuarioSeleccionado);
        borrarBtn.setEnabled(algunUsuarioSeleccionado);

    }


    public void setPanelReferencia(PantallaUsuarios panelReferencia) {
        this.panelReferencia = panelReferencia;
    }


    public void setEdicionHabilitada(boolean enabled) {
        editarBtn.setEnabled(enabled);
        borrarBtn.setEnabled(enabled);
    }
}