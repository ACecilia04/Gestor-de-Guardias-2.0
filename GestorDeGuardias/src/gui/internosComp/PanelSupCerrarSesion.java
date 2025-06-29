package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.secciones.Ventana;

import javax.swing.*;
import java.awt.*;

public class PanelSupCerrarSesion extends JPanel{

    public PanelSupCerrarSesion(int alto) {

        setBackground(new Paleta().getColorFondoTabla());

        Boton cerrarSesionBtn = new Boton();
        cerrarSesionBtn.addIcono("/iconos/Exit.png");
        cerrarSesionBtn.setSelectLetra(true);
        cerrarSesionBtn.cambiarIconTextGap(10);
        cerrarSesionBtn.addActionListener(e -> Ventana.getInstance().cerrarSesion());
        cerrarSesionBtn.setToolTipText("Cerrar Sesión");
        add(cerrarSesionBtn);

        FlowLayout miLayout = new FlowLayout(FlowLayout.RIGHT, 5, alto - cerrarSesionBtn.getHeight() - 3);
        setLayout(miLayout);
    }

}
