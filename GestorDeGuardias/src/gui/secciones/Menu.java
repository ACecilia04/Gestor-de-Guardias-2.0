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

        //Panel1
        Boton botonPlanifs = new Boton("Planificaciones");
        botonPlanifs.addIcono("/iconos/Calendar.png");
        botonPlanifs.setSelectLetra(true);

        Boton botonEstudiantes = new Boton("Estudiantes");
        botonEstudiantes.addIcono("/iconos/Estudiante.png");
        botonEstudiantes.setSelectLetra(true);

        Boton botonTrabajadores = new Boton("Trabajadores");
        botonTrabajadores.addIcono("/iconos/Profesor.png");
        botonTrabajadores.setSelectLetra(true);

        botonPlanifs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel1");
            }
        });

        botonEstudiantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel2");
            }
        });

        botonTrabajadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel3");
            }
        });

        botonPlanifs.setLocation(x, y);
        y += botonPlanifs.getSize().height + separacion;
        botonEstudiantes.setLocation(x, y);
        y += botonEstudiantes.getSize().height + separacion;
        botonTrabajadores.setLocation(x, y);
        y += botonTrabajadores.getSize().height + separacion * 2;

        panel1 = new JPanel(null);
        panel1.setBackground(getBackground());
        panel1.add(botonPlanifs);
        panel1.add(botonEstudiantes);
        panel1.add(botonTrabajadores);

        panel1.setMinimumSize(new Dimension(this.getPreferredSize().width, y));


        //Panel2
        Boton boton4 = new Boton("Añadir Planificación");
        boton4.addIcono("/iconos/Estrella.png");
        boton4.setSelectLetra(true);

        Boton boton5 = new Boton("Actualizar Asistencias");
        boton5.addIcono("/iconos/Documento.png");
        boton5.setSelectLetra(true);

        Boton boton6 = new Boton("Facultad");
        boton6.addIcono("/iconos/Casa.png");
        boton6.setSelectLetra(true);

        boton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ventana.mostrarPanel("panel4");
            }
        });

        boton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel5");
            }
        });

        boton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ventana.getInstance().mostrarFacultad();
            }
        });

        y = separacion;
        boton4.setLocation(x, y);
        y += boton4.getSize().height + separacion;
        boton5.setLocation(x, y);
        y += boton5.getSize().height + separacion;
        boton6.setLocation(x, y);

        panel2 = new JPanel(null);
        panel2.setBackground(getBackground());
        panel2.add(boton4);
        panel2.add(boton5);
        panel2.add(boton6);

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
