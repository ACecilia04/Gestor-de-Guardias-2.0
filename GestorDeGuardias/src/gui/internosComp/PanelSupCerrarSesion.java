package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.Etiqueta;
import gui.secciones.Ventana;

import javax.swing.*;
import java.awt.*;

public class PanelSupCerrarSesion extends JPanel {
    Etiqueta nombreSeccionLbl;
    public PanelSupCerrarSesion(int alto) {

        setBackground(new Paleta().getColorFondoTabla());

        Boton cerrarSesionBtn = new Boton();
        cerrarSesionBtn.addIcono("/iconos/Exit.png");
        cerrarSesionBtn.setSelectLetra(true);
        cerrarSesionBtn.cambiarIconTextGap(10);
        cerrarSesionBtn.addActionListener(e -> Ventana.getInstance().cerrarSesion());
        cerrarSesionBtn.setToolTipText("Cerrar Sesión");
        add(cerrarSesionBtn);

        nombreSeccionLbl = new Etiqueta("");
        nombreSeccionLbl.setNuevoSizeLetra(16);
        nombreSeccionLbl.setForeground(Color.DARK_GRAY);
        add(nombreSeccionLbl);

        FlowLayout miLayout = new FlowLayout(FlowLayout.RIGHT, 5, alto - cerrarSesionBtn.getHeight() - 3);
        setLayout(miLayout);
    }

    public void setNombreSeccion(String nombreSeccion){
        nombreSeccionLbl.setTexto(nombreSeccion);
    }

}
