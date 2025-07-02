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
    Boton btnMinimizar = new Boton();
    Boton btnPlanifs = new Boton();
    Boton btnEstudiantes = new Boton();
    Boton btnTrabajadores = new Boton();
    Boton btnConfig = new Boton();
    Boton btnUsuarios = new Boton();
    Boton btnAuditoria = new Boton();
    Boton btnActualizarAsist = new Boton();
    Boton btnFacultad = new Boton();
    String usuarioRol;
    private int y = separacion - 10;

    public Menu(JPanel contenedor, final Ventana ventana, Usuario usuario) {
        paleta = new Paleta();
        setBackground(paleta.getColorFondoTabla());

        setPreferredSize(new Dimension(247, contenedor.getSize().height));
        setSize(new Dimension(247, contenedor.getSize().height));
        setLayout(new BorderLayout());
        usuarioRol = usuario.getRol().getNombre().toLowerCase();
        //Superior
        {
            superior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            superior.setSize(new Dimension(this.getPreferredSize().width, 50));
            superior.setBackground(getBackground());
            add(superior, BorderLayout.NORTH);

            btnMinimizar = new Boton();
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

            if (puedeVer(usuarioRol, "planifs")) {
                btnPlanifs = new Boton();
                btnPlanifs.addIcono("/iconos/Calendar.png");
                btnPlanifs.setSelectLetra(true);
                btnPlanifs.setLocation(x, y);
                btnPlanifs.addActionListener(e -> ventana.mostrarPanel("panelPlanificaciones"));
                panel1.add(btnPlanifs);
                y += btnPlanifs.getSize().height + separacion;
            }

            if (puedeVer(usuarioRol, "asist")) {
                btnActualizarAsist = new Boton();
                btnActualizarAsist.addIcono("/iconos/Documento.png");
                btnActualizarAsist.setSelectLetra(true);
                btnActualizarAsist.setLocation(x, y);
                btnActualizarAsist.addActionListener(e -> ventana.mostrarPanel("panelAsistencia"));
                panel1.add(btnActualizarAsist);
                y += btnActualizarAsist.getSize().height + separacion * 2;
            }

            panel1.setMinimumSize(new Dimension(this.getPreferredSize().width, y));
        }

        y = panel1.getY() + separacion;

        //Panel2
        {
            panel2 = new JPanel(null);
            panel2.setBackground(getBackground());

            if (puedeVer(usuarioRol, "estudiantes")) {
                btnEstudiantes = new Boton();
                btnEstudiantes.addIcono("/iconos/Estudiante.png");
                btnEstudiantes.setSelectLetra(true);
                btnEstudiantes.setLocation(x, y);
                btnEstudiantes.addActionListener(e -> ventana.mostrarPanel("panelEstudiantes"));
                panel2.add(btnEstudiantes);
                y += btnEstudiantes.getSize().height + separacion;
            }

            if (puedeVer(usuarioRol, "trabajadores")) {
                btnTrabajadores = new Boton();
                btnTrabajadores.addIcono("/iconos/Profesor.png");
                btnTrabajadores.setSelectLetra(true);
                btnTrabajadores.setLocation(x, y);
                btnTrabajadores.addActionListener(e -> ventana.mostrarPanel("panelTrabajadores"));
                panel2.add(btnTrabajadores);
                y += btnTrabajadores.getSize().height + separacion;
            }

            if (puedeVer(usuarioRol, "facultad")) {
                btnFacultad = new Boton();
                btnFacultad.addIcono("/iconos/Casa.png");
                btnFacultad.setSelectLetra(true);
                btnFacultad.setLocation(x, y);
                btnFacultad.addActionListener(e -> Ventana.getInstance().mostrarFacultad());
                panel2.add(btnFacultad);
                y += btnFacultad.getSize().height + separacion;
            }

            if (puedeVer(usuarioRol, "config")) {
                btnConfig = new Boton();
                btnConfig.addIcono("/iconos/Config.png");
                btnConfig.setSelectLetra(true);
                btnConfig.setLocation(x, y);
                btnConfig.addActionListener(e -> ventana.mostrarPanel("panelConfig"));
                panel2.add(btnConfig);
                y += btnConfig.getSize().height + separacion;
            }

            if (puedeVer(usuarioRol, "usuarios")) {
                btnUsuarios = new Boton();
                btnUsuarios.addIcono("/iconos/Community.png");
                btnUsuarios.setSelectLetra(true);
                btnUsuarios.setLocation(x, y);
                btnUsuarios.addActionListener(e -> ventana.mostrarPanel("panelUsuarios"));
                panel2.add(btnUsuarios);
                y += btnUsuarios.getSize().height + separacion;
            }

            if (puedeVer(usuarioRol, "auditoria")) {
                btnAuditoria = new Boton();
                btnAuditoria.addIcono("/iconos/Community.png");
                btnAuditoria.setSelectLetra(true);
                btnAuditoria.setLocation(x, y);
                btnAuditoria.addActionListener(e -> ventana.mostrarPanel("panelAuditoria"));
                panel2.add(btnAuditoria);
                y += btnAuditoria.getSize().height + separacion;
            }

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
        minimizarMenu();
    }

    private boolean puedeVer(String rol, String clave) {
        rol = rol.toLowerCase();

        // El desarrollador puede ver todo
        if (rol.equals("desarrollador")) {
            return true;
        }
        if (rol.equals("administrador")) {
            return switch (clave.toLowerCase()) {
                case "trabajadores", "facultad", "estudiantes", "usuarios", "config" -> true;
                default -> false;
            };
        }
        if (rol.equals("planificador"))
            return clave.equalsIgnoreCase("planifs");

        if (rol.equals("controlador"))
            return clave.equalsIgnoreCase("asist");

        return false;
    }

    private void minimizarMenu() {
        setPreferredSize(new Dimension(70, getHeight()));

        if (puedeVer(usuarioRol, "planifs")) {
            btnPlanifs.setText("");
            btnPlanifs.setLocation(10, btnPlanifs.getY());
        }

        if (puedeVer(usuarioRol, "asist")) {
            btnActualizarAsist.setText("");
            btnActualizarAsist.setLocation(10, btnActualizarAsist.getY());
        }

        if (puedeVer(usuarioRol, "estudiantes")) {
            btnEstudiantes.setText("");
            btnEstudiantes.setLocation(10, btnEstudiantes.getY());
        }

        if (puedeVer(usuarioRol, "trabajadores")) {
            btnTrabajadores.setText("");
            btnTrabajadores.setLocation(10, btnTrabajadores.getY());
        }

        if (puedeVer(usuarioRol, "config")) {
            btnConfig.setText("");
            btnConfig.setLocation(10, btnConfig.getY());
        }

        if (puedeVer(usuarioRol, "facultad")) {
            btnFacultad.setText("");
            btnFacultad.setLocation(10, btnFacultad.getY());
        }

        if (puedeVer(usuarioRol, "usuarios")) {
            btnUsuarios.setText("");
            btnUsuarios.setLocation(10, btnUsuarios.getY());
        }

        if (puedeVer(usuarioRol, "auditoria")) {
            btnAuditoria.setText("");
            btnAuditoria.setLocation(10, btnAuditoria.getY());
        }
    }

    private void maximizarMenu() {
        setPreferredSize(new Dimension(247, getHeight()));

        if (puedeVer(usuarioRol, "planifs")) {
            btnPlanifs.setText("Planificaciones");
            btnPlanifs.setLocation(x, btnPlanifs.getY());
        }

        if (puedeVer(usuarioRol, "asist")) {
            btnActualizarAsist.setText("Asistencias");
            btnActualizarAsist.setLocation(x, btnActualizarAsist.getY());
        }

        if (puedeVer(usuarioRol, "estudiantes")) {
            btnEstudiantes.setText("Estudiantes");
            btnEstudiantes.setLocation(x, btnEstudiantes.getY());
        }

        if (puedeVer(usuarioRol, "trabajadores")) {
            btnTrabajadores.setText("Trabajadores");
            btnTrabajadores.setLocation(x, btnTrabajadores.getY());
        }

        if (puedeVer(usuarioRol, "config")) {
            btnConfig.setText("Configuración");
            btnConfig.setLocation(x, btnConfig.getY());
        }

        if (puedeVer(usuarioRol, "facultad")) {
            btnFacultad.setText("Facultad");
            btnFacultad.setLocation(x, btnFacultad.getY());
        }

        if (puedeVer(usuarioRol, "usuarios")) {
            btnUsuarios.setText("Usuarios");
            btnUsuarios.setLocation(x, btnUsuarios.getY());
        }

        if (puedeVer(usuarioRol, "auditoria")) {
            btnAuditoria.setText("");
            btnAuditoria.setLocation(10, btnAuditoria.getY());
        }
    }
}
