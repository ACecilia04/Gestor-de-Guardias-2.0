package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.internosComp.PanelOpcionesPlanif;
import gui.internosComp.PanelTurno;
import gui.internosComp.Tabla;
import utils.exceptions.EntradaInvalidaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;


public class AddPlanif extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private Dimension tablaDim ;
//            = new Dimension(1200, 745);
    private final JPanel backdropPane;
    //int x;
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

        final Boton btnAddPersona = tablaOpciones.getBotonAddPersona();
        btnAddPersona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnAddPersona.isSeleccionable()) {
                    try {

                        if (tablaOpciones.getTablaSelec().getComponentes().size() == 1) {
                            PanelTurno aux = (PanelTurno) tablaOpciones.getTablaSelec().getComponentes().getFirst();
                            Ventana.getInstance().addPantallaSelecPerona(aux, tabla.getDias());
                        }

                    } catch (EntradaInvalidaException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        // Esto no se usa, lo quitamos
        //x = (contenedor.getWidth() - dimTablaOpciones.width - tablaDim.width) / 2;

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
