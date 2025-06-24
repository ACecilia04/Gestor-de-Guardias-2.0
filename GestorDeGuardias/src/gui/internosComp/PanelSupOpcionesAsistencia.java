package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.secciones.AsistenciaPlanif;
import gui.secciones.MostrarPlanif;
import gui.secciones.Ventana;

import javax.swing.*;
import java.awt.*;

public class PanelSupOpcionesAsistencia extends JPanel {
    AsistenciaPlanif panelAfectado;
    public PanelSupOpcionesAsistencia(int alto, AsistenciaPlanif panelAfectado) {
        this.panelAfectado = panelAfectado;
        setBackground(new Paleta().getColorFondoTabla());

        Boton nuevoBtn = new Boton();
        nuevoBtn.addIcono("/iconos/Crear.png");
        nuevoBtn.setSelectLetra(true);
        nuevoBtn.cambiarIconTextGap(10);
        nuevoBtn.addActionListener(e -> Ventana.getInstance().mostrarPanel("panelAddPlanif"));


        Boton editarBtn = new Boton();
        editarBtn.addIcono("/iconos/Editar.png");
        editarBtn.setSelectLetra(true);
        editarBtn.cambiarIconTextGap(10);
//        editarBtn.addActionListener(e -> editar());

        Boton borrarBtn = new Boton();
        borrarBtn.addIcono("/iconos/Borrar.png");
        borrarBtn.setSelectLetra(true);
        borrarBtn.cambiarIconTextGap(10);
//        borrarBtn.addActionListener(e -> borrar());

        Boton pdfBtn = new Boton();
        pdfBtn.addIcono("/iconos/PDF.png");
        pdfBtn.setSelectLetra(true);
        pdfBtn.cambiarIconTextGap(10);
//        pdfBtn.addActionListener(e -> exportar());

        add(nuevoBtn);
        add(editarBtn);
        add(borrarBtn);
        add(pdfBtn);

        FlowLayout miLayout = new FlowLayout(FlowLayout.LEFT, 5, alto - nuevoBtn.getHeight() - 8);
        setLayout(miLayout);
        /*
        borrarBtn.setSeleccionable(false);
        editarBtn.setSeleccionable(false);
        nuevoBtn.setSeleccionable(true);
        pdfBtn.setSeleccionable(false);
         */
    }

}
