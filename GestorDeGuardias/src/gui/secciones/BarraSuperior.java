package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.Etiqueta;
import gui.internosComp.PanelEdicion2;

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

//    private final Font fuente = new Font("Arial", Font.PLAIN, 18);

    public BarraSuperior(JPanel contenedor) {
        Dimension dim = new Dimension(contenedor.getSize().width, 85);
        setPreferredSize(new Dimension(dim));
        Paleta paleta = new Paleta();
        setBackground(paleta.getColorFondoTabla());
        setLayout(new BorderLayout());

        //panelOpciones
// TODO: login and exit options
        panelOpciones = new JPanel(null);
        panelOpciones.setBackground(this.getBackground());
        Border bordeMargen = BorderFactory.createEmptyBorder(0, 10, 0, 0);
        panelOpciones.setPreferredSize(new Dimension(247, this.getPreferredSize().height));

//        Etiqueta nombre = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Gestor de Guardias");
//        nombre.setLocation(20, 15);
//        panelOpciones.add(nombre);
//        add(panelOpciones, BorderLayout.WEST);


        //panelEdicion
        //Crear PanelCambiante
        panelVacio = new JPanel(new CardLayout());

        //Crear paneles que cambiar [BORRAR MIENTRAS SE CREEN LOS REALES]
        panelEdicion1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEdicion1.setBackground(Color.WHITE);
        new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelEd2 = new PanelEdicion2(this.getPreferredSize().height);


        panelVacio.add(panelEdicion1, "panelEd1");
        panelVacio.add(panelEd2, "panelEd2");
        add(panelVacio, BorderLayout.CENTER);

        //Bordes
        Border border = BorderFactory.createMatteBorder(0, 0, 4, 0, contenedor.getBackground());
        panelOpciones.setBorder(bordeMargen);
        setBorder(border);
    }

    public void mostrarPanel(String nombrePanel) {

        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        cardLayout.show(panelVacio, nombrePanel);
        Ventana.getInstance().revalidate();
        Ventana.getInstance().repaint();
    }

}
