package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.CustomSplitPane;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

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

    Boton btnAddPlanif = new Boton();
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

            btnMinimizar.addActionListener(new java.awt.event.ActionListener() {
                private boolean isMinimized = false;
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
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
            y += btnPlanifs.getSize().height + separacion;
            btnPlanifs.addActionListener(e -> ventana.mostrarPanel("panel1"));

            btnActualizarAsist.addIcono("/iconos/Documento.png");
            btnActualizarAsist.setSelectLetra(true);
            btnActualizarAsist.setLocation(x, y);
            y += btnActualizarAsist.getSize().height + separacion * 2;
            btnActualizarAsist.addActionListener(e -> ventana.mostrarPanel("panel5"));

            panel1.add(btnPlanifs);
            panel1.add(btnActualizarAsist);
            panel1.setMinimumSize(new Dimension(this.getPreferredSize().width, y));
        }

        y = separacion;

        //Panel2
        {
            panel2 = new JPanel(null);
            panel2.setBackground(getBackground());

//            btnAddPlanif.addIcono("/iconos/Estrella.png");
//            btnAddPlanif.setSelectLetra(true);
//            btnAddPlanif.setLocation(x, y);
//            y += btnAddPlanif.getSize().height + separacion;
//            btnAddPlanif.addActionListener(e -> ventana.mostrarPanel("panel4"));

            btnEstudiantes.addIcono("/iconos/Estudiante.png");
            btnEstudiantes.setSelectLetra(true);
            btnEstudiantes.setLocation(x, y);
            y += btnEstudiantes.getSize().height + separacion;
            btnEstudiantes.addActionListener(e -> ventana.mostrarPanel("panel2"));

            btnTrabajadores.addIcono("/iconos/Profesor.png");
            btnTrabajadores.setSelectLetra(true);
            btnTrabajadores.setLocation(x, y);
            y += btnTrabajadores.getSize().height + separacion;
            btnTrabajadores.addActionListener(e -> ventana.mostrarPanel("panel3"));

            btnFacultad.addIcono("/iconos/Casa.png");
            btnFacultad.setSelectLetra(true);
            btnFacultad.setLocation(x, y);
            btnFacultad.addActionListener(e -> Ventana.getInstance().mostrarFacultad());

//            panel2.add(btnAddPlanif);
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
        maximizarMenu();

        // Aquí: deshabilitar botones según el rol

    }

    private void deshabilitarOpcionesPorRol(String rolNombre) {
        rolNombre = rolNombre.toLowerCase();

        switch (rolNombre) {
            case "administrador":
                // No deshabilitar nada TODO: cambiar acceso de admin
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
//                btnAddPlanif.setVisible(false);
                btnFacultad.setVisible(false);
                break;
            default:
                // Si el rol no se reconoce, deshabilita todo
                btnPlanifs.setVisible(false);
                btnEstudiantes.setVisible(false);
                btnTrabajadores.setVisible(false);
//                btnAddPlanif.setVisible(false);
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

//        btnAddPlanif.setText("");
//        btnAddPlanif.setLocation(10, btnAddPlanif.getY());

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

        btnAddPlanif.setText("Añadir Planificación");
        btnAddPlanif.setLocation(x, btnAddPlanif.getY());

        btnActualizarAsist.setText("Actualizar Asistencias");
        btnActualizarAsist.setLocation(x, btnActualizarAsist.getY());

        btnFacultad.setText("Facultad");
        btnFacultad.setLocation(x, btnFacultad.getY());
    }
}