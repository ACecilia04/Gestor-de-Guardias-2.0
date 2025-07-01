package gui.pantallasEmergentes;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.secciones.Ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Advertencia extends JDialog {
    private static final long serialVersionUID = 1L;
    private final Dimension dimBoton = new Dimension(120, 40);
    private final Paleta paleta = new Paleta();
    private final Font fuente = new Font("Arial", Font.PLAIN, 14);
    protected Boton boton1;
    protected Boton boton2;
    protected JDialog myself;
    protected JLabel label;
    private JPanel contentPanel;
    private String opcion1 = "Aceptar";
    private String opcion2 = "Cancelar";
    private boolean eleccion; //True op1
    private Dimension dim = new Dimension(450, 250);
    private JPanel buttonPanel;
    private GridBagConstraints gbc;

    public Advertencia(Dimension dim, String title, String texto, String op1, String op2) {
        super(Ventana.getInstance(), title, true);
        this.myself = this;
        this.dim = dim;
        this.opcion1 = op1;
        this.opcion2 = op2;

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        inicializar(texto, Ventana.getInstance());
        addBotonSi();
        addBotonNo();

        //SetSize
        if (label.getPreferredSize().height > 100) {
            this.setSize(new Dimension(dim.width, label.getPreferredSize().height + 200));
            setMinimumSize(this.getSize());
        }
        setVisible(true);
    }

    public Advertencia(Dimension dim, String title, String texto, String op2, boolean modal) {
        super(Ventana.getInstance(), title, modal);
        this.myself = this;
        this.dim = dim;
        this.opcion2 = op2;

        inicializar(texto, Ventana.getInstance());
        addBotonNo();

        if (label.getPreferredSize().height > 100) {
            this.setSize(new Dimension(dim.width, label.getPreferredSize().height + 150));
            setMinimumSize(this.getSize());
        }

        setVisible(true);
    }

    public Advertencia(Dimension dim, String title, String texto, String op2) {
        super(Ventana.getInstance(), title, true);
        this.myself = this;
        this.dim = dim;
        this.opcion2 = op2;

        inicializar(texto, Ventana.getInstance());
        addBotonNo();

        if (label.getPreferredSize().height > 100) {
            this.setSize(new Dimension(dim.width, label.getPreferredSize().height + 150));
            setMinimumSize(this.getSize());
        }

        setVisible(true);
    }

    public void inicializar(String texto, final Frame ventana) {
        setLocationRelativeTo(ventana);
        setMinimumSize(dim);

        // Centrar el JDialog
        setLocation(ventana.getX() + (ventana.getWidth() - getWidth()) / 2,
                ventana.getY() + (ventana.getHeight() - getHeight()) / 2);

        // Crear el panel de contenido
        contentPanel = new JPanel();
        contentPanel.setBackground(paleta.getColorFondoTabla());
        this.setContentPane(contentPanel);
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Crear un panel para el label y los botones
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(paleta.getColorFondoTabla());
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Crear el JLabel en la parte superior del panel central
        label = new JLabel(texto);
        label.setFont(fuente);
        label.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(label, BorderLayout.NORTH);


        // Crear un panel para los botones
        buttonPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 40, 0, 50); // Espacio horizontal de 50 p�xeles entre botones
        gbc.anchor = GridBagConstraints.CENTER;

        // Agregar el panel de botones al panel central
        buttonPanel.setBackground(paleta.getColorFondoTabla());
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Ajustar el espacio entre el label y la parte superior del panel central
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 0, 0)); // 50 p�xeles arriba y abajo
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    }

    public void addBotonSi() {
        boton1 = new Boton(opcion1);
        boton1.setNuevoSize(dimBoton);
        boton1.setPreferredSize(dimBoton);
        boton1.setBordeado(true);
        boton1.setColorPresionado(paleta.getColorCaracteristico());


        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eleccion = true;
                dispose();
            }
        });

        // A�adir el bot�n "SI"
        gbc.gridx = 0; // Primera columna
        buttonPanel.add(boton1, gbc);
    }

    private void addBotonNo() {
        boton2 = new Boton(opcion2);
        boton2.setNuevoSize(dimBoton);
        boton2.setPreferredSize(dimBoton);
        boton2.setColorFondo(paleta.getColorCaracteristico());
        boton2.setColorLetra(Color.WHITE);
        boton2.setColorLetraPres(Color.WHITE);

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eleccion = false;
                dispose();
            }
        });

        // A�adir el bot�n "NO"
        gbc.gridx = 1; // Segunda columna


        buttonPanel.add(boton2, gbc);
    }

    public Boton getBoton1() {
        return boton1;
    }

    public void setBoton1(Boton boton1) {
        this.boton1 = boton1;
    }

    public Boton getBoton2() {
        return boton2;
    }

    public void setBoton2(Boton boton2) {
        this.boton2 = boton2;
    }

    public boolean getEleccion() {
        return eleccion;
    }
}