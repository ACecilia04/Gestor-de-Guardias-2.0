package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.secciones.PantallaCump;
import services.ServicesLocator;

import javax.swing.*;
import java.awt.*;

public class PanelSupOpcionesAsistencia extends JPanel {
    PantallaCump panelAfectado;

    public PanelSupOpcionesAsistencia(int alto, PantallaCump panelAfectado) {
        this.panelAfectado = panelAfectado;
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
        public void setTodosCumplidos(){
            if (panelAfectado != null) {
                panelAfectado.getTabla().setCumplimiento(1);
                panelAfectado.getTabla().actualizarVistaTabla();
            }
        }
        public void setTodosIncumplidos(){
            if (panelAfectado != null) {
                panelAfectado.getTabla().setCumplimiento(2);
                panelAfectado.getTabla().actualizarVistaTabla();
            }
        }
        public void reiniciar(){
            if (panelAfectado != null) {
                panelAfectado.getTabla().setCumplimiento(0);
                panelAfectado.getTabla().actualizarVistaTabla();
            }
        }
        public void guardar(){
            ServicesLocator.getInstance().getTurnoDeGuardiaServices().guardarCumplimientoTurnos(panelAfectado.getTabla().getDias());
        }

}
