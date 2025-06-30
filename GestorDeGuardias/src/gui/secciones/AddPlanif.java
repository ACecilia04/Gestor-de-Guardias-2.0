package gui.secciones;

import gui.auxiliares.Paleta;
import gui.internosComp.PanelOpcionesPlanif;
import gui.internosComp.Tabla;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;


public class AddPlanif extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private Dimension tablaDim ;
    private final JPanel backdropPane;
    private PanelOpcionesPlanif tablaOpciones;
    private Tabla tabla;


    public AddPlanif(JPanel contenedor) {
        setLayout(new BorderLayout());

        Dimension dimTablaOpciones = new Dimension(300, contenedor.getSize().height);

        backdropPane = new JPanel();
        backdropPane.setLayout(null);

        Paleta paleta = new Paleta();
        backdropPane.setBackground(paleta.getColorFondo());

        tablaOpciones = new PanelOpcionesPlanif(dimTablaOpciones);

        add(tablaOpciones, BorderLayout.EAST);
        add(backdropPane, BorderLayout.CENTER);
    }

    public void addTabla(Tabla tabla) {
        if (this.tabla != null) {
            backdropPane.remove(this.tabla);
        }
        tabla.setSize(tablaDim);
        this.tabla = tabla;
        tabla.setLocation(SwingConstants.CENTER, 20);
        backdropPane.add(tabla);//Hacer singleton
        repaint();
        revalidate();
    }

    public Dimension getTablaDim(Integer contenedorWidth, Integer contenedorHeight) {
        tablaDim = new Dimension(contenedorWidth - tablaOpciones.getWidth(), contenedorHeight);
        return tablaDim;
    }

    public void setTablaDim(Dimension tablaDim) {
        this.tablaDim = tablaDim;
    }

    public PanelOpcionesPlanif getTablaOpciones() {
        return tablaOpciones;
    }

    public void setTablaOpciones(PanelOpcionesPlanif tablaOpciones) {
        this.tablaOpciones = tablaOpciones;
    }

}
