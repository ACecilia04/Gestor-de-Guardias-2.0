package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.internosComp.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serial;

public class BarraSuperior extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private JPanel panelUsuario;
    private final JPanel panelDerecha;
    private final JPanel panelIzquierda;

    private final JPanel panelEdicion1;
    private final PanelEdicion2 panelEd2;
    private PanelSupOpcionesPlanifs opcionesPlanif;
    private PanelSupOpcionesAsistencia opcionesAsistencia;
    private PanelSupOpcionesConfig opcionesConfig;
    private PanelSupOpcionesUsuarios opcionesUsuarios; // <-- NUEVO
    private PanelSupCerrarSesion cerrarSesionPnl;

    public BarraSuperior(
            JPanel contenedor,
            MostrarPlanif panelMostrarPlanifs,
            PantallaCump panelCumplimiento,
            PanelConfig panelConfig,
            PantallaUsuarios panelUsuarios // <-- NUEVO
    ) {
        Dimension dim = new Dimension(contenedor.getWidth(), 85);
        setPreferredSize(new Dimension(dim));
        Paleta paleta = new Paleta();
        setBackground(paleta.getColorFondoTabla());
        setLayout(new BorderLayout());

        opcionesPlanif = new PanelSupOpcionesPlanifs(this.getPreferredSize().height, panelMostrarPlanifs);
        opcionesAsistencia = new PanelSupOpcionesAsistencia(this.getPreferredSize().height, panelCumplimiento);
        opcionesConfig = new PanelSupOpcionesConfig(this.getPreferredSize().height, panelConfig);
        opcionesUsuarios = new PanelSupOpcionesUsuarios(this.getPreferredSize().height, panelUsuarios); // <-- NUEVO

        panelDerecha = new JPanel(new CardLayout());
        panelEdicion1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEdicion1.setBackground(Color.WHITE);

        panelEd2 = new PanelEdicion2(this.getPreferredSize().height);

        cerrarSesionPnl  = new PanelSupCerrarSesion(this.getPreferredSize().height);
        panelIzquierda = new JPanel(new CardLayout());
        panelIzquierda.add(cerrarSesionPnl, "panelCerrarSesion");
        add(panelIzquierda, BorderLayout.WEST);

        panelDerecha.add(panelEdicion1, "panelEd1");
        panelDerecha.add(panelEd2, "panelEd2");
        panelDerecha.add(opcionesPlanif,"panelOpcionesPlanifs");
        panelDerecha.add(opcionesAsistencia,"panelOpcionesAsistencia");
        panelDerecha.add(opcionesConfig, "panelOpcionesConfig");
        panelDerecha.add(opcionesUsuarios, "panelOpcionesUsuarios"); // <-- NUEVO
        add(panelDerecha, BorderLayout.EAST);

        //Bordes
        Border border = BorderFactory.createMatteBorder(0, 0, 4, 0, contenedor.getBackground());
        setBorder(border);

        CardLayout cardLayout = (CardLayout) panelDerecha.getLayout();
        cardLayout.show(panelDerecha, "panelOpcionesPlanifs");
        cardLayout = (CardLayout) panelIzquierda.getLayout();
        cardLayout.show(panelIzquierda,"panelCerrarSesion" );
    }

    public void mostrarPanel(String nombrePanel) {
        CardLayout cardLayout = (CardLayout) panelDerecha.getLayout();
        cardLayout.show(panelDerecha, nombrePanel);
        Ventana.getInstance().revalidate();
        Ventana.getInstance().repaint();
    }
}