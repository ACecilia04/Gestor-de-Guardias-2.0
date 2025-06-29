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

public class Menu extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Paleta paleta;
    private final JPanel superior;
    private final CustomSplitPane splitPanel;
    private final JPanel panel1;
    private final JPanel panel2;
    private final int separacion = 30;
    private final int x = 30;
    private int y = separacion - 10;

    Boton btnMinimizar = new Boton();
    Boton btnPlanifs = new Boton();
    Boton btnEstudiantes = new Boton();
    Boton btnTrabajadores = new Boton();

    Boton btnConfig = new Boton();
    Boton btnUsuarios = new Boton();
    Boton btnActualizarAsist = new Boton();
    Boton btnFacultad = new Boton();

    public Menu (JPanel contenedor, final Ventana ventana, Usuario usuario) {
        paleta = new Paleta();
        setBackground(paleta.getColorFondoTabla());

        setPreferredSize(new Dimension(247, contenedor.getSize().height));
        setSize(new Dimension(247, contenedor.getSize().height));
        setLayout(new BorderLayout());

        //Superior
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

        //Panel1
        {
            panel1 = new JPanel(null);
            panel1.setBackground(getBackground());

            btnPlanifs.addIcono("/iconos/Calendar.png");
            btnPlanifs.setSelectLetra(true);
            btnPlanifs.setLocation(x, y);
//            btnPlanifs.setToolTipText("Planificaciones");
            y += btnPlanifs.getSize().height + separacion;
            btnPlanifs.addActionListener(e -> ventana.mostrarPanel("panelPlanificaciones"));

            btnActualizarAsist.addIcono("/iconos/Documento.png");
            btnActualizarAsist.setSelectLetra(true);
            btnActualizarAsist.setLocation(x, y);
//            btnActualizarAsist.setToolTipText("Asistencias");
            y += btnActualizarAsist.getSize().height + separacion * 2;
            btnActualizarAsist.addActionListener(e -> ventana.mostrarPanel("panelAsistencia"));

            panel1.add(btnPlanifs);
            panel1.add(btnActualizarAsist);
            panel1.setMinimumSize(new Dimension(this.getPreferredSize().width, y));
        }

        y = separacion;

        //Panel2
        {
            panel2 = new JPanel(null);
            panel2.setBackground(getBackground());

            btnEstudiantes.addIcono("/iconos/Estudiante.png");
            btnEstudiantes.setSelectLetra(true);
            btnEstudiantes.setLocation(x, y);
//            btnEstudiantes.setToolTipText("Estudiantes");
            y += btnEstudiantes.getSize().height + separacion;
            btnEstudiantes.addActionListener(e -> ventana.mostrarPanel("panelEstudiantes"));

            btnTrabajadores.addIcono("/iconos/Profesor.png");
            btnTrabajadores.setSelectLetra(true);
            btnTrabajadores.setLocation(x, y);
//            btnTrabajadores.setToolTipText("Trabajadores");
            y += btnTrabajadores.getSize().height + separacion;
            btnTrabajadores.addActionListener(e -> ventana.mostrarPanel("panelTrabajadores"));

            btnFacultad.addIcono("/iconos/Casa.png");
            btnFacultad.setSelectLetra(true);
            btnFacultad.setLocation(x, y);
//            btnFacultad.setToolTipText("Facultad");
            y += btnFacultad.getSize().height + separacion;
            btnFacultad.addActionListener(e -> Ventana.getInstance().mostrarFacultad());

            btnConfig.addIcono("/iconos/Config.png");
            btnConfig.setSelectLetra(true);
            btnConfig.setLocation(x, y);
//            btnConfig.setToolTipText("Configuración");
            y += btnConfig.getSize().height + separacion;
            btnConfig.addActionListener(e -> ventana.mostrarPanel("panelConfig"));

            btnUsuarios.addIcono("/iconos/Community.png");
            btnUsuarios.setSelectLetra(true);
            btnUsuarios.setLocation(x, y);
//            btnUsuarios.setToolTipText("Usuarios");
            y += btnUsuarios.getSize().height + separacion;
            btnUsuarios.addActionListener(e -> ventana.mostrarPanel("panelUsuarios"));

            panel2.add(btnConfig);
            panel2.add(btnEstudiantes);
            panel2.add(btnTrabajadores);
            panel2.add(btnFacultad);

            panel2.setMinimumSize(new Dimension(this.getPreferredSize().width, y));
        }

        splitPanel = new CustomSplitPane(panel1, panel2, JSplitPane.VERTICAL_SPLIT);
        add(splitPanel, BorderLayout.CENTER);

        Border bordeMargen = BorderFactory.createEmptyBorder(10, 0, 0, 15);
        Border border = BorderFactory.createMatteBorder(0, 0, 0, 3, paleta.getColorBorde());
        Border margenDoubleBorder = BorderFactory.createCompoundBorder(border, bordeMargen);

        panel1.setBorder(border);
        panel2.setBorder(border);
        superior.setBorder(margenDoubleBorder);
        setBorder(border);
        deshabilitarOpcionesPorRol(usuario.getRol().getNombre());
        minimizarMenu();

        // Aquí: deshabilitar botones según el rol

    }

    private void deshabilitarOpcionesPorRol(String rolNombre) {
        rolNombre = rolNombre.toLowerCase();

        switch (rolNombre) {
            case "administrador":
                // No deshabilitar nada TODO: cambiar acceso de admin y ver si setVisible funciona como pienso
                break;
            case "planificador":
                // Solo deja planificación y añadir planificación
                btnEstudiantes.setVisible(false);
                btnTrabajadores.setVisible(false);
                btnActualizarAsist.setVisible(false);
                btnFacultad.setVisible(false);
                break;
            case "controlador":
                // Solo deja asistencia
                btnPlanifs.setVisible(false);
                btnEstudiantes.setVisible(false);
                btnTrabajadores.setVisible(false);
                btnConfig.setVisible(false);
                btnFacultad.setVisible(false);
                break;
            default:
                // Si el rol no se reconoce, deshabilita todo
                btnPlanifs.setVisible(false);
                btnEstudiantes.setVisible(false);
                btnTrabajadores.setVisible(false);
                btnConfig.setVisible(false);
                btnActualizarAsist.setVisible(false);
                btnFacultad.setVisible(false);
        }
    }

    private void minimizarMenu(){
        setPreferredSize(new Dimension(70, getHeight()));
        btnPlanifs.setText("");
        btnPlanifs.setLocation(10, btnPlanifs.getY());

        btnActualizarAsist.setText("");
        btnActualizarAsist.setLocation(10, btnActualizarAsist.getY());

        btnEstudiantes.setText("");
        btnEstudiantes.setLocation(10, btnEstudiantes.getY());

        btnTrabajadores.setText("");
        btnTrabajadores.setLocation(10, btnTrabajadores.getY());

        btnConfig.setText("");
        btnConfig.setLocation(10, btnConfig.getY());

        btnFacultad.setText("");
        btnFacultad.setLocation(10, btnFacultad.getY());
    }

    private void maximizarMenu(){
        setPreferredSize(new Dimension(247, getHeight()));
        btnPlanifs.setText("Planificaciones");
        btnPlanifs.setLocation(x, btnPlanifs.getY());

        btnEstudiantes.setText("Estudiantes");
        btnEstudiantes.setLocation(x, btnEstudiantes.getY());

        btnTrabajadores.setText("Trabajadores");
        btnTrabajadores.setLocation(x, btnTrabajadores.getY());

        btnConfig.setText("Configuración");
        btnConfig.setLocation(x, btnConfig.getY());

        btnActualizarAsist.setText("Asistencias");
        btnActualizarAsist.setLocation(x, btnActualizarAsist.getY());

        btnFacultad.setText("Facultad");
        btnFacultad.setLocation(x, btnFacultad.getY());
    }
}
