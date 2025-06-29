package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.CustomScrollBar;
import gui.componentes.PanelMes;
import gui.internosComp.PanelSupOpcionesPlanifs;
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
    private ArrayList<Configuracion> listaConfiguraciones;
    private final JScrollPane scrollPane;
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

        construirTabla();
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
                conf -> {
                    seleccionada = conf; // Guarda la configuración seleccionada
                    // Si quieres, puedes llamar aquí directamente a modificarConfiguracion(conf);
                }
        );
        scrollPane.setViewportView(tablaConfig);

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
//    public Configuracion getSeleccionada() {
//        return seleccionada;
//    }
//    public void setSeleccionada(PanelMes seleccionado) {
//        if (seleccionado == null){
//            if (this.seleccionada != null && this.seleccionada.isSeleccionado())
//                this.seleccionada.setSeleccionado(false);
//        }
//        this.seleccionado = seleccionado;
//        if (opcionesReferencia != null && opcionesReferencia instanceof PanelSupOpcionesPlanifs)
//            ((PanelSupOpcionesPlanifs)opcionesReferencia).setAlgunMesSeleccionado(seleccionado != null);
//    }
}