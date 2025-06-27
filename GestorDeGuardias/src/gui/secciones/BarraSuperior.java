package gui.secciones;

import gui.auxiliares.Paleta;
import gui.internosComp.PanelEdicion2;
import gui.internosComp.PanelSupOpcionesAsistencia;
import gui.internosComp.PanelSupOpcionesPlanifs;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serial;

public class BarraSuperior extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private JPanel panelUsuario;
    private final JPanel panelOpciones;
    private final JPanel panelVacio;
    private final JPanel panelEdicion1;
    private final PanelEdicion2 panelEd2;
    private PanelSupOpcionesPlanifs opcionesPlanif;
    private PanelSupOpcionesAsistencia opcionesAsistencia;

//    private final Font fuente = new Font("Arial", Font.PLAIN, 18);

    public BarraSuperior(JPanel contenedor, MostrarPlanif panelMostrarPlanifs, PantallaCump panelCumplimiento) {
        Dimension dim = new Dimension(contenedor.getSize().width, 85);
        setPreferredSize(new Dimension(dim));
        Paleta paleta = new Paleta();
        setBackground(paleta.getColorFondoTabla());
        setLayout(new BorderLayout());

        //panelOpciones
//      TODO: login and exit options
        panelOpciones = new JPanel(null);
        panelOpciones.setBackground(this.getBackground());

        Border bordeMargen = BorderFactory.createEmptyBorder(0, 10, 0, 0);
        panelOpciones.setPreferredSize(new Dimension(247, this.getPreferredSize().height));

        opcionesPlanif = new PanelSupOpcionesPlanifs(this.getPreferredSize().height,panelMostrarPlanifs);
        opcionesAsistencia = new PanelSupOpcionesAsistencia(this.getPreferredSize().height,panelCumplimiento);
        //panelEdicion
        //Crear PanelCambiante
        panelVacio = new JPanel(new CardLayout());
        //Crear paneles que cambiar [BORRAR MIENTRAS SE CREEN LOS REALES]
        panelEdicion1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEdicion1.setBackground(Color.WHITE);

        panelEd2 = new PanelEdicion2(this.getPreferredSize().height);


        panelVacio.add(panelEdicion1, "panelEd1");
        panelVacio.add(panelEd2, "panelEd2");
        panelVacio.add(opcionesPlanif,"panelOpcionesPlanifs");
        panelVacio.add(opcionesAsistencia,"panelOpcionesAsistencia");
        add(panelVacio, BorderLayout.EAST);

        //Bordes
        Border border = BorderFactory.createMatteBorder(0, 0, 4, 0, contenedor.getBackground());
        panelOpciones.setBorder(bordeMargen);
        setBorder(border);

        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        cardLayout.show(panelVacio, "panelOpcionesPlanifs");
    }

    public void mostrarPanel(String nombrePanel) {
        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        cardLayout.show(panelVacio, nombrePanel);
        Ventana.getInstance().revalidate();
        Ventana.getInstance().repaint();
    }

}
