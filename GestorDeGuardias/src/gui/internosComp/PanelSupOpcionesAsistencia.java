package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.secciones.PantallaCump;
import services.ServicesLocator;

import javax.swing.*;
import java.awt.*;

public class PanelSupOpcionesAsistencia extends JPanel {
    PantallaCump panelReferencia;

    public PanelSupOpcionesAsistencia(int alto) {
        setBackground(new Paleta().getColorFondoTabla());

        Boton todosCumpBtn = new Boton();
        todosCumpBtn.addIcono("/iconos/Check.png");
        todosCumpBtn.setSelectLetra(true);
        todosCumpBtn.cambiarIconTextGap(10);
        todosCumpBtn.setToolTipText("Todos Cumplidos");
        todosCumpBtn.addActionListener(e -> setTodosCumplidos());

        Boton todosIncumpBtn = new Boton();
        todosIncumpBtn.addIcono("/iconos/XInSquare.png");
        todosIncumpBtn.setSelectLetra(true);
        todosIncumpBtn.cambiarIconTextGap(10);
        todosIncumpBtn.setToolTipText("Todos Incumplidos");
        todosIncumpBtn.addActionListener(e -> setTodosIncumplidos());

        Boton reiniciarBtn = new Boton();
        reiniciarBtn.addIcono("/iconos/Restart.png");
        reiniciarBtn.setSelectLetra(true);
        reiniciarBtn.cambiarIconTextGap(10);
        reiniciarBtn.setToolTipText("Reiniciar");
        reiniciarBtn.addActionListener(e -> reiniciar());

        Boton guardarBtn = new Boton();
        guardarBtn.addIcono("/iconos/Guardar.png");
        guardarBtn.setSelectLetra(true);
        guardarBtn.cambiarIconTextGap(10);
        guardarBtn.setToolTipText("Guardar");
        guardarBtn.addActionListener(e -> guardar());

        add(todosCumpBtn);
        add(todosIncumpBtn);
        add(reiniciarBtn);
        add(guardarBtn);

        FlowLayout miLayout = new FlowLayout(FlowLayout.RIGHT, 5, alto - todosCumpBtn.getHeight() - 8);
        setLayout(miLayout);
    }

    public void setTodosCumplidos() {
        if (panelReferencia != null) {
            panelReferencia.getTabla().setCumplimiento(1);
            panelReferencia.getTabla().actualizarVistaTabla();
        }
    }

    public void setTodosIncumplidos() {
        if (panelReferencia != null) {
            panelReferencia.getTabla().setCumplimiento(2);
            panelReferencia.getTabla().actualizarVistaTabla();
        }
    }

    public void reiniciar() {
        if (panelReferencia != null) {
            panelReferencia.getTabla().setCumplimiento(0);
            panelReferencia.getTabla().actualizarVistaTabla();
        }
    }

    public void guardar() {
        ServicesLocator.getInstance().getTurnoDeGuardiaServices().guardarCumplimientoTurnos(panelReferencia.getTabla().getDias());
    }

    public void setPanelReferencia(PantallaCump panelReferencia) {
        this.panelReferencia = panelReferencia;
    }
}
