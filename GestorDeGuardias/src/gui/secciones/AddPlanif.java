package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.internosComp.PanelTurno;
import gui.internosComp.Tabla;
import gui.internosComp.TablaOpcionesPlanif;
import logica.excepciones.EntradaInvalidaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;


public class AddPlanif extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private static Dimension tablaDim = new Dimension(1200, 745);
    int x;
    private final JPanel contentPane;
    private TablaOpcionesPlanif tablaOpciones;
    private Tabla tabla;


    public AddPlanif(JPanel contenedor) {
        setLayout(new BorderLayout());


        Dimension dimTablaOpciones = new Dimension(310, contenedor.getSize().height);


        contentPane = new JPanel();
        contentPane.setLayout(null);

        Paleta paleta = new Paleta();
        contentPane.setBackground(paleta.getColorFondo());


        tablaOpciones = new TablaOpcionesPlanif(dimTablaOpciones);

        final Boton aux1 = tablaOpciones.getBotonAddPersona();
        aux1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (aux1.isSeleccionable()) {
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

        x = (contenedor.getWidth() - dimTablaOpciones.width - tablaDim.width) / 2;


        add(tablaOpciones, BorderLayout.EAST);
        add(contentPane, BorderLayout.CENTER);


    }

    public static Dimension getTablaDim() {
        return tablaDim;
    }

//    public static void setTablaDim(Dimension tablaDim) {
//        AddPlanif.tablaDim = tablaDim;
//    }

    public void addTabla(Tabla tabla) {
        if (this.tabla != null) {
            contentPane.remove(this.tabla);
        }
        tabla.setSize(tablaDim);
        this.tabla = tabla;
        tabla.setLocation(x, 20);
        contentPane.add(tabla);//Hacer singleton
        repaint();
        revalidate();
    }

    public TablaOpcionesPlanif getTablaOpciones() {
        return tablaOpciones;
    }

//    public void setTablaOpciones(TablaOpcionesPlanif tablaOpciones) {
//        this.tablaOpciones = tablaOpciones;
//    }
}
