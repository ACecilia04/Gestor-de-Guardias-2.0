package gui.secciones;

import gui.componentes.CustomScrollBar;
import gui.internosComp.PanelSupOpcionesConfig;
import gui.internosComp.TablaConfig;
import gui.pantallasEmergentes.PantallaAddConfig;
import model.Configuracion;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

public class PanelConfig extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private final JScrollPane scrollPane;
    private ArrayList<Configuracion> listaConfiguraciones;
    private Configuracion seleccionada;
    private TablaConfig tablaConfig;
    private PantallaAddConfig pantallaConfig;
    private Object opcionesReferencia;

    public PanelConfig() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        seleccionada = null;

        // ScrollPane para la tabla
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(scrollPane, BorderLayout.CENTER);

        // Carga datos inicial
        cargarConfiguraciones();
    }

    public void cargarConfiguraciones() {
        // Obtiene la lista desde el servicio
        listaConfiguraciones = (ArrayList<Configuracion>) ServicesLocator.getInstance()
                .getConfiguracionServices().getAllConfiguraciones();
        if (opcionesReferencia instanceof PanelSupOpcionesConfig panelOpciones) {
            panelOpciones.setAlgunaConfigSeleccionada(false);
        }        construirTabla();
    }

    private void construirTabla() {
        if (tablaConfig != null) {
            scrollPane.setViewportView(null);
        }
        // Instancia la tabla y pasa el consumidor para la selección
        tablaConfig = new TablaConfig(
                new Dimension(900, 540),
                Color.WHITE,
                listaConfiguraciones,
                conf -> seleccionada = conf
        );
        scrollPane.setViewportView(tablaConfig);
        tablaConfig.setOpcionesReferencia(opcionesReferencia);
        revalidate();
        repaint();
    }

    public void agregarConfiguracion() {
        pantallaConfig = new PantallaAddConfig();
        pantallaConfig.revalidate();
        cargarConfiguraciones();
    }

    public void modificarConfiguracion() {
        if (seleccionada != null) {
            pantallaConfig = new PantallaAddConfig(seleccionada);
            pantallaConfig.revalidate();
            cargarConfiguraciones();
        }
    }

    public void eliminarConfiguracion() throws SqlServerCustomException {
        if (seleccionada != null) {
            int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar esta configuración?", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                // Lógica de borrado
                ServicesLocator.getInstance().getConfiguracionServices().deleteConfiguracion(seleccionada.getId());
                JOptionPane.showMessageDialog(this, "Configuración eliminada");
                cargarConfiguraciones();
            }
        }
    }

    public void setOpcionesReferencia(Object opcionesReferencia) {
        this.opcionesReferencia = opcionesReferencia;
    }
}