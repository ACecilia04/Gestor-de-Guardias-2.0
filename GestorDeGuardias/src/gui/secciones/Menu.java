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
        Boton boton1 = new Boton("Planificaciones");
        boton1.addIcono("/iconos/Calendar.png");
        boton1.setSelectLetra(true);

        Boton boton2 = new Boton("Estudiantes");
        boton2.addIcono("/iconos/Estudiante.png");
        boton2.setSelectLetra(true);

        Boton boton3 = new Boton("Trabajadores");
        boton3.addIcono("/iconos/Profesor.png");
        boton3.setSelectLetra(true);

        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel1");
            }
        });

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel2");
            }
        });

        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("panel3");
            }
        });

        boton1.setLocation(x, y);
        y += boton1.getSize().height + separacion;
        boton2.setLocation(x, y);
        y += boton2.getSize().height + separacion;
        boton3.setLocation(x, y);
        y += boton3.getSize().height + separacion * 2;

        panel1 = new JPanel(null);
        panel1.setBackground(getBackground());
        panel1.add(boton1);
        panel1.add(boton2);
        panel1.add(boton3);

        panel1.setMinimumSize(new Dimension(this.getPreferredSize().width, y));


        //Panel2
        Boton boton4 = new Boton("A�adir Planificacion");
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
