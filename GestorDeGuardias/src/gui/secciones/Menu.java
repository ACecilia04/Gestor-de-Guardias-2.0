package gui.secciones;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.CustomSplitPane;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Arrays;

public class Menu extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Paleta paleta;
    private final JPanel superior;
    private final CustomSplitPane splitPanel;
    private final JPanel panel1;
    private final JPanel panel2;
    private final int separacion = 30;
    private final int x = 30;
    private int y = separacion - 10;

    public Menu(JPanel contenedor, final Ventana ventana) {
        paleta = new Paleta();
        setBackground(paleta.getColorFondoTabla());

        setPreferredSize(new Dimension(247, contenedor.getSize().height));
        setSize(new Dimension(247, contenedor.getSize().height));
        setLayout(new BorderLayout());

        //Superior
        superior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Border bordeMargen = BorderFactory.createEmptyBorder(10, 0, 0, 15);
        superior.setSize(new Dimension(this.getPreferredSize().width, 50));
        superior.setBackground(getBackground());

        add(superior, BorderLayout.NORTH);

        //PanelSplit
        Boton btnMinimizar = new Boton();
        btnMinimizar.addIcono("/iconos/MenuRayas.png");
        btnMinimizar.setSelectLetra(true);
        superior.add(btnMinimizar);
        add(superior, BorderLayout.NORTH);
        //Panel1
        Boton btnPlanifs = new Boton("Planificaciones");
        btnPlanifs.addIcono("/iconos/Calendar.png");
        btnPlanifs.setSelectLetra(true);

        Boton btnEstudiantes = new Boton("Estudiantes");
        btnEstudiantes.addIcono("/iconos/Estudiante.png");
        btnEstudiantes.setSelectLetra(true);

        Boton btnTrabajadores = new Boton("Trabajadores");
        btnTrabajadores.addIcono("/iconos/Profesor.png");
        btnTrabajadores.setSelectLetra(true);

        btnMinimizar.addActionListener(new ActionListener() {
            private boolean isMinimized = false;
            List botones = (List) Arrays.asList(btnPlanifs, btnEstudiantes, btnTrabajadores);
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMinimized) {
                    setPreferredSize(new Dimension(247, getHeight()));  // Expand to full size
                } else {
                    setPreferredSize(new Dimension(70, getHeight()));  // Minimized width
                }

                isMinimized = !isMinimized;
                revalidate();
                repaint();
            }
        });

        btnPlanifs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel1");
            }
        });

        btnEstudiantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel2");
            }
        });

        btnTrabajadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel3");
            }
        });

        btnPlanifs.setLocation(x, y);
        y += btnPlanifs.getSize().height + separacion;
        btnEstudiantes.setLocation(x, y);
        y += btnEstudiantes.getSize().height + separacion;
        btnTrabajadores.setLocation(x, y);
        y += btnTrabajadores.getSize().height + separacion * 2;

        panel1 = new JPanel(null);
        panel1.setBackground(getBackground());
        panel1.add(btnPlanifs);
        panel1.add(btnEstudiantes);
        panel1.add(btnTrabajadores);

        panel1.setMinimumSize(new Dimension(this.getPreferredSize().width, y));


        //Panel2
        Boton btnAddPlanif = new Boton("Añadir Planificación");
        btnAddPlanif.addIcono("/iconos/Estrella.png");
        btnAddPlanif.setSelectLetra(true);

        Boton btnActualizarAsist = new Boton("Actualizar Asistencias");
        btnActualizarAsist.addIcono("/iconos/Documento.png");
        btnActualizarAsist.setSelectLetra(true);

        Boton btnFacultad = new Boton("Facultad");
        btnFacultad.addIcono("/iconos/Casa.png");
        btnFacultad.setSelectLetra(true);

        btnAddPlanif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ventana.mostrarPanel("panel4");
            }
        });

        btnActualizarAsist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel5");
            }
        });

        btnFacultad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ventana.getInstance().mostrarFacultad();
            }
        });

        y = separacion;
        btnAddPlanif.setLocation(x, y);
        y += btnAddPlanif.getSize().height + separacion;
        btnActualizarAsist.setLocation(x, y);
        y += btnActualizarAsist.getSize().height + separacion;
        btnFacultad.setLocation(x, y);

        panel2 = new JPanel(null);
        panel2.setBackground(getBackground());
        panel2.add(btnAddPlanif);
        panel2.add(btnActualizarAsist);
        panel2.add(btnFacultad);

        panel2.setMinimumSize(new Dimension(this.getPreferredSize().width, y));

        splitPanel = new CustomSplitPane(panel1, panel2, JSplitPane.VERTICAL_SPLIT);
        add(splitPanel, BorderLayout.CENTER);


        //Bordes
        Border border = BorderFactory.createMatteBorder(0, 0, 0, 3, paleta.getColorBorde());
        Border margenDoubleBorder = BorderFactory.createCompoundBorder(border, bordeMargen);

        panel1.setBorder(border);
        panel2.setBorder(border);
        superior.setBorder(margenDoubleBorder);
        setBorder(border);
    }

}
