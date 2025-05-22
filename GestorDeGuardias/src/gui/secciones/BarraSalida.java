package gui.secciones;

import gui.componentes.Boton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class BarraSalida extends JPanel {
    private static final long serialVersionUID = 1L;

    int mouseX, mouseY;
    private boolean isMaximized = false;

    public BarraSalida(final Ventana ventana) {

        setBackground(ventana.getBackground());
        this.setSize(ventana.getSize().width, 50);
        this.setPreferredSize(new Dimension(1159, 50));
        setLayout(new BorderLayout());

        JPanel apoyoDerecha = new JPanel();
        apoyoDerecha.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        apoyoDerecha.setBackground(getBackground());


        Boton botonSalir2 = new Boton();
        botonSalir2.setColorFondo(getBackground());
        Boton maximizar = new Boton();
        maximizar.setColorFondo(getBackground());
        maximizar.setSelectLetra(true);
        maximizar.setColorIcono(Color.GRAY);
        maximizar.setColorIconoPres(Color.BLACK);
        Boton minimizar = new Boton();
        minimizar.setSelectLetra(true);
        minimizar.setColorIcono(Color.GRAY);
        minimizar.setColorIconoPres(Color.BLACK);

        minimizar.setColorFondo(getBackground());

        botonSalir2.addIcono("/iconos/XRoja.png");
        maximizar.addIcono("/iconos/CuadradoGris.png");
        minimizar.addIcono("/iconos/LineaGris.png");

        apoyoDerecha.add(minimizar);
        apoyoDerecha.add(maximizar);
        apoyoDerecha.add(botonSalir2);

        add(apoyoDerecha);

        botonSalir2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        minimizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.setState(JFrame.ICONIFIED); // Minimizar la ventana
            }
        });

        maximizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isMaximized) {
                    // Restaurar tama�o
                    Ventana.getInstance().setExtendedState(JFrame.NORMAL);
                    Ventana.getInstance().setSize(Ventana.SIZE_VENTANA);
                    Ventana.getInstance().setShape(new RoundRectangle2D.Double(0, 0, Ventana.getInstance().getWidth(), Ventana.getInstance().getHeight(), 20, 20));
                } else {
                    // Maximizar sin cubrir la barra de tareas
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int taskbarHeight = getToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;
                    Ventana.getInstance().setSize(screenSize.width, screenSize.height - taskbarHeight);
                    Ventana.getInstance().setLocation(0, 0);
                    Ventana.getInstance().setShape(null);
                }
                isMaximized = !isMaximized;
            }
        });


        Border border = BorderFactory.createMatteBorder(0, 0, 3, 0, getBackground().darker());
        setBorder(border);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!isMaximized) {
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();
//                    if (ventana != null) {
                        ventana.setLocation(x - mouseX, y - mouseY);
//                    }
                }


            }
        });

    }

    public boolean isMaximized() {
        return isMaximized;
    }
}
