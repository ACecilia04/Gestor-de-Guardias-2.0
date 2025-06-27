package gui.secciones;

import gui.auxiliares.ConvertidorFecha;
import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.Cuadro;
import gui.componentes.CustomScrollBar;
import gui.componentes.Etiqueta;
import gui.pantallasEmergentes.PantallaAddConfig;
import model.Configuracion;

import services.ServicesLocator;
import utils.dao.SqlServerCustomException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelConfig extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Font fuenteCabecera = new Font("Arial", Font.BOLD, 15);
    private final Font fuenteNormal = new Font("Arial", Font.PLAIN, 14);
    private final Paleta paleta = new Paleta();

    private ArrayList<Configuracion> listaConfiguraciones;
    private JPanel panelTabla;
    private JPanel panelBotones;
    private JScrollPane scrollPane;

    private PantallaAddConfig pantallaConfig;
    // Para selección
    private Configuracion seleccionada;

    public PanelConfig() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        seleccionada = null;
        // Panel superior de botones alineados a la derecha
        panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 16));
        panelBotones.setOpaque(false);

//        Boton btnAgregar = new Boton("Agregar");
//        Boton btnModificar = new Boton("Modificar");
//        Boton btnEliminar = new Boton("Eliminar");
//
//        btnAgregar.setColorFondo(new Color(37, 97, 209));
//        btnAgregar.setColorLetra(Color.WHITE);
//
//        btnModificar.setColorFondo(new Color(37, 97, 209));
//        btnModificar.setColorLetra(Color.WHITE);
//
//        btnEliminar.setColorFondo(new Color(220, 53, 69));
//        btnEliminar.setColorLetra(Color.WHITE);
//
//        btnModificar.setEnabled(false);
//        btnEliminar.setEnabled(false);
//
//        panelBotones.add(btnAgregar);
//        panelBotones.add(btnModificar);
//        panelBotones.add(btnEliminar);

//        add(panelBotones, BorderLayout.NORTH);

        // Panel de tabla
        panelTabla = new JPanel();
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
        panelTabla.setBackground(Color.WHITE);

        // ScrollPane para la tabla
        scrollPane = new JScrollPane(panelTabla);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(scrollPane, BorderLayout.CENTER);

        // Carga datos inicial
        cargarConfiguraciones();
    }

    public void cargarConfiguraciones() {
        // Obtiene la lista desde el servicio (ajusta el método a tu caso)
        listaConfiguraciones = (ArrayList<Configuracion>) ServicesLocator.getInstance()
                .getConfiguracionServices().getAllConfiguraciones();

        construirTabla();
    }

    private void construirTabla() {
        panelTabla.removeAll();

        // Cabecera
        JPanel cabecera = new JPanel(new GridLayout(1, 5));
        cabecera.setBackground(new Color(37, 97, 209));
        cabecera.setBorder(new EmptyBorder(12, 12, 12, 12));

        cabecera.add(crearCabecera("Día"));
        cabecera.add(crearCabecera("Horario"));
        cabecera.add(crearCabecera("Cantidad de personas"));
        cabecera.add(crearCabecera("Sexo"));
        cabecera.add(crearCabecera("Receso"));

        panelTabla.add(cabecera);

        // Agrupa por día
        String diaActual = "";
        JPanel panelDia = null;

        for (Configuracion conf : listaConfiguraciones) {
            String dia = ConvertidorFecha.traducDiaSemana(conf.getDiaSemana());
            if (!dia.equals(diaActual)) {
                // Día nuevo
                if (panelDia != null) {
                    panelTabla.add(panelDia);
                }
                panelDia = crearPanelDia(dia);
                diaActual = dia;
            }
            if (panelDia != null) {
                panelDia.add(crearFilaHorario(conf));
            }
        }
        if (panelDia != null) {
            panelTabla.add(panelDia);
        }

        // Refresh
        panelTabla.revalidate();
        panelTabla.repaint();
    }

    private JLabel crearCabecera(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(fuenteCabecera);
        return lbl;
    }

    private JPanel crearPanelDia(String dia) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(237, 239, 244));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true)
        ));

        JLabel lblDia = new JLabel(dia);
        lblDia.setFont(fuenteNormal.deriveFont(Font.BOLD, 16));
        lblDia.setBorder(new EmptyBorder(4, 10, 4, 0));
        panel.add(lblDia);

        return panel;
    }

    private JPanel crearFilaHorario(Configuracion conf) {
        JPanel fila = new JPanel(new GridLayout(1, 5));
        fila.setBackground(Color.WHITE);
        fila.setBorder(new EmptyBorder(3, 8, 3, 8));
        fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        fila.setToolTipText("Doble clic para editar");

        fila.add(new JLabel("")); // Día vacío (ya se muestra arriba)
        fila.add(new JLabel(conf.getHorario() != null ? conf.getHorario().toString() : ""));
        fila.add(new JLabel(String.valueOf(conf.getCantPersonas())));
        fila.add(new JLabel(conf.getSexo()));
        fila.add(new JLabel(conf.diaEsReceso() ? "Sí" : "No"));

        // Selección visual
        fila.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {// Habilita los botones según selección
                habilitarBotones();
                // Doble click
                if (e.getClickCount() == 2) {
                    modificarConfiguracion();
                }
                // Visual feedback
                for (Component comp : panelTabla.getComponents()) {
                    if (comp instanceof JPanel && comp != fila) {
                        comp.setBackground(Color.WHITE);
                    }
                }
                fila.setBackground(new Color(210, 230, 255));
            }
        });
        return fila;
    }

    private void habilitarBotones() {
        for (Component c : panelBotones.getComponents()) {
            if (c instanceof Boton b) {
                if (b.getEtiqueta().getText().equals("Modificar") || b.getEtiqueta().getText().equals("Eliminar")) {
                    b.setEnabled(true);
                }
            }
        }
    }

    // Métodos de acción (esqueleto)
    public void agregarConfiguracion() {
            // TODO: Implementar diálogo/agregado
            pantallaConfig = new PantallaAddConfig();
            pantallaConfig.revalidate();
            cargarConfiguraciones();
    }

    public void modificarConfiguracion() {
        if(seleccionada != null) {
            pantallaConfig = new PantallaAddConfig(seleccionada);
            pantallaConfig.revalidate();
            cargarConfiguraciones();
        }
    }

    public void eliminarConfiguracion() throws SqlServerCustomException {
        if(seleccionada != null) {
            int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar esta configuración?", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                // Lógica de borrado
                ServicesLocator.getInstance().getConfiguracionServices().deleteConfiguracion(seleccionada.getId());
                JOptionPane.showMessageDialog(this, "Configuración eliminada");
                cargarConfiguraciones();
            }
        }
    }
}