package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.CustomSplitPane;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Menu extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Paleta paleta;
    private final JPanel superior;
    private final CustomSplitPane splitPanel;
    private final JPanel panel1;
    private final JPanel panel2;

    // Botones del menú
    private final Boton btnMinimizar = new Boton();
    private final Boton btnPlanifs = new Boton();
    private final Boton btnActualizarAsist = new Boton();
    private final Boton btnEstudiantes = new Boton();
    private final Boton btnTrabajadores = new Boton();
    private final Boton btnFacultad = new Boton();
    private final Boton btnConfig = new Boton();
    private final Boton btnUsuarios = new Boton();

    // Asociación de botones a sus textos para maximizar
    private final Map<Boton, String> textosBotones = new HashMap<>();

    // Configuración de botones por rol
    private static final Map<String, List<BotonRef>> botonesPanel1PorRol = new HashMap<>();
    private static final Map<String, List<BotonRef>> botonesPanel2PorRol = new HashMap<>();
    static {
        // Panel1: Planificaciones, Asistencias
        botonesPanel1PorRol.put("administrador", Arrays.asList(
                BotonRef.PLANIFS, BotonRef.ACTUALIZAR_ASIST
        ));
        botonesPanel1PorRol.put("planificador", Arrays.asList(
                BotonRef.PLANIFS, BotonRef.ACTUALIZAR_ASIST
        ));
        botonesPanel1PorRol.put("controlador", Arrays.asList(
                BotonRef.ACTUALIZAR_ASIST
        ));
        botonesPanel1PorRol.put("usuario", Collections.emptyList());

        // Panel2: Estudiantes, Trabajadores, Facultad, Config, Usuarios
        botonesPanel2PorRol.put("administrador", Arrays.asList(
                BotonRef.ESTUDIANTES, BotonRef.TRABAJADORES, BotonRef.FACULTAD, BotonRef.CONFIG, BotonRef.USUARIOS
        ));
        botonesPanel2PorRol.put("planificador", Collections.emptyList());
        botonesPanel2PorRol.put("controlador", Collections.emptyList());
        botonesPanel2PorRol.put("usuario", Collections.emptyList());
    }
    // Referencias para mapear las listas anteriores a instancias reales
    private enum BotonRef {
        PLANIFS, ACTUALIZAR_ASIST, ESTUDIANTES, TRABAJADORES, FACULTAD, CONFIG, USUARIOS
    }
    private Boton getBotonFromRef(BotonRef ref) {
        switch (ref) {
            case PLANIFS: return btnPlanifs;
            case ACTUALIZAR_ASIST: return btnActualizarAsist;
            case ESTUDIANTES: return btnEstudiantes;
            case TRABAJADORES: return btnTrabajadores;
            case FACULTAD: return btnFacultad;
            case CONFIG: return btnConfig;
            case USUARIOS: return btnUsuarios;
        }
        return null;
    }

    public Menu(JPanel contenedor, final Ventana ventana, Usuario usuario) {
        paleta = new Paleta();
        setBackground(paleta.getColorFondoTabla());

        setPreferredSize(new Dimension(247, contenedor.getSize().height));
        setSize(new Dimension(247, contenedor.getSize().height));
        setLayout(new BorderLayout());

        // Superior
        {
            superior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            superior.setSize(new Dimension(this.getPreferredSize().width, 50));
            superior.setBackground(getBackground());
            add(superior, BorderLayout.NORTH);

            btnMinimizar.addIcono("/iconos/MenuRayas.png");
            btnMinimizar.setSelectLetra(true);
            btnMinimizar.addActionListener(new ActionListener() {
                private boolean isMinimized = false;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isMinimized) {
                        maximizarMenu();
                    } else {
                        minimizarMenu();
                    }
                    isMinimized = !isMinimized;
                    revalidate();
                    repaint();
                }
            });

            superior.add(btnMinimizar);
        }

        // Panel1 y Panel2 con BoxLayout
        panel1 = new JPanel();
        panel1.setBackground(getBackground());
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel2 = new JPanel();
        panel2.setBackground(getBackground());
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        // Configurar botones: icono, texto y acciones
        btnPlanifs.addIcono("/iconos/Calendar.png");
        btnPlanifs.setSelectLetra(true);
        btnPlanifs.addActionListener(e -> ventana.mostrarPanel("panelPlanificaciones"));
        textosBotones.put(btnPlanifs, "Planificaciones");

        btnActualizarAsist.addIcono("/iconos/Documento.png");
        btnActualizarAsist.setSelectLetra(true);
        btnActualizarAsist.addActionListener(e -> ventana.mostrarPanel("panelAsistencia"));
        textosBotones.put(btnActualizarAsist, "Asistencias");

        btnEstudiantes.addIcono("/iconos/Estudiante.png");
        btnEstudiantes.setSelectLetra(true);
        btnEstudiantes.addActionListener(e -> ventana.mostrarPanel("panelEstudiantes"));
        textosBotones.put(btnEstudiantes, "Estudiantes");

        btnTrabajadores.addIcono("/iconos/Profesor.png");
        btnTrabajadores.setSelectLetra(true);
        btnTrabajadores.addActionListener(e -> ventana.mostrarPanel("panelTrabajadores"));
        textosBotones.put(btnTrabajadores, "Trabajadores");

        btnFacultad.addIcono("/iconos/Casa.png");
        btnFacultad.setSelectLetra(true);
        btnFacultad.addActionListener(e -> Ventana.getInstance().mostrarFacultad());
        textosBotones.put(btnFacultad, "Facultad");

        btnConfig.addIcono("/iconos/Config.png");
        btnConfig.setSelectLetra(true);
        btnConfig.addActionListener(e -> ventana.mostrarPanel("panelConfig"));
        textosBotones.put(btnConfig, "Configuración");

        btnUsuarios.addIcono("/iconos/Community.png");
        btnUsuarios.setSelectLetra(true);
        btnUsuarios.addActionListener(e -> ventana.mostrarPanel("panelUsuarios"));
        textosBotones.put(btnUsuarios, "Usuarios");

        splitPanel = new CustomSplitPane(panel1, panel2, JSplitPane.VERTICAL_SPLIT);
        add(splitPanel, BorderLayout.CENTER);

        Border bordeMargen = BorderFactory.createEmptyBorder(10, 0, 0, 15);
        Border border = BorderFactory.createMatteBorder(0, 0, 0, 3, paleta.getColorBorde());
        Border margenDoubleBorder = BorderFactory.createCompoundBorder(border, bordeMargen);

        panel1.setBorder(border);
        panel2.setBorder(border);
        superior.setBorder(margenDoubleBorder);
        setBorder(border);

        // Mostrar solo los botones permitidos según el rol
        reconstruirSegunRol(usuario.getRol().getNombre().toLowerCase());

        minimizarMenu();
    }

    private void reconstruirSegunRol(String rol) {
        // Limpiar paneles
        panel1.removeAll();
        panel2.removeAll();

        // Panel1
        List<BotonRef> refsPanel1 = botonesPanel1PorRol.getOrDefault(rol, Collections.emptyList());
        for (BotonRef ref : refsPanel1) {
            Boton btn = getBotonFromRef(ref);
            if (btn != null) {
                btn.setText(textosBotones.get(btn));
                btn.setVisible(true);
                panel1.add(btn);
                panel1.add(Box.createVerticalStrut(20));
            }
        }
        // Panel2
        List<BotonRef> refsPanel2 = botonesPanel2PorRol.getOrDefault(rol, Collections.emptyList());
        for (BotonRef ref : refsPanel2) {
            Boton btn = getBotonFromRef(ref);
            if (btn != null) {
                btn.setText(textosBotones.get(btn));
                btn.setVisible(true);
                panel2.add(btn);
                panel2.add(Box.createVerticalStrut(20));
            }
        }

        // Refrescar
        panel1.revalidate();
        panel1.repaint();
        panel2.revalidate();
        panel2.repaint();
    }

    private void minimizarMenu() {
        setPreferredSize(new Dimension(70, getHeight()));
        // Panel1
        for (Component c : panel1.getComponents()) {
            if (c instanceof Boton) {
                ((Boton) c).setText("");
            }
        }
        // Panel2
        for (Component c : panel2.getComponents()) {
            if (c instanceof Boton) {
                ((Boton) c).setText("");
            }
        }
    }

    private void maximizarMenu() {
        setPreferredSize(new Dimension(247, getHeight()));
        // Panel1
        for (Component c : panel1.getComponents()) {
            if (c instanceof Boton) {
                Boton btn = (Boton) c;
                btn.setText(textosBotones.getOrDefault(btn, ""));
            }
        }
        // Panel2
        for (Component c : panel2.getComponents()) {
            if (c instanceof Boton) {
                Boton btn = (Boton) c;
                btn.setText(textosBotones.getOrDefault(btn, ""));
            }
        }
    }
}