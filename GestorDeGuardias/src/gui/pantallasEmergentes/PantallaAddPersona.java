package gui.pantallasEmergentes;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.CustomRadioButton;
import gui.componentes.CustomTextField;
import gui.componentes.Etiqueta;
import gui.secciones.Ventana;
import utils.exceptions.MultiplesErroresException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public abstract class PantallaAddPersona extends JDialog {
    protected static final long serialVersionUID = 1L;

    protected JPanel contentPane;
    protected JPanel panelTitulo, panelInf, panelBotones;
    protected Paleta paleta = new Paleta();

    protected Dimension dim = new Dimension(500, 700);
    protected Dimension dimTextField = new Dimension(300, 40);
    protected Dimension dimBoton = new Dimension(120, 40);

    protected Font fuente = new Font("Arial", Font.PLAIN, 14);
    protected Font fuenteRadio = new Font("Arial", Font.BOLD, 14);

    protected CustomTextField boxNombre;
    protected int maxCharNombre = 30;
    protected CustomTextField boxApellidos;
    protected int maxCharApellido = 30;
    protected CustomTextField boxID;
    protected int maxCharID = 11;

    protected String sexo;

    protected CustomRadioButton radioFemenino;
    protected CustomRadioButton radioMasculino;

    protected int margenIzquierdo = 40;

    public PantallaAddPersona() {
        super(Ventana.getInstance(), "Añadir Persona", true);
        setResizable(false);
        setSize(dim);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setBackground(paleta.getColorFondoTabla());
        contentPane.setLayout(new BorderLayout());


        int x = (Ventana.getInstance().getSize().width - this.getWidth()) / 2;
        int y = (Ventana.getInstance().getSize().height - this.getHeight()) / 2;
        setLocation(x, y);

        //Iniciar Titulo
        inicializarTitulo();
        inicializarPanelInf();
        inicializarPanelBotones();

        contentPane.add(panelTitulo, BorderLayout.NORTH);
        contentPane.add(panelInf, BorderLayout.CENTER);
        contentPane.add(panelBotones, BorderLayout.SOUTH);

        Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, paleta.getColorBorde());
        Border border2 = BorderFactory.createMatteBorder(3, 0, 3, 0, paleta.getColorBorde());
        contentPane.setBorder(border);
        panelInf.setBorder(border2);
        contentPane.requestFocus();

        setVisible(true);
    }


    private void inicializarPanelBotones() {
        panelBotones = new JPanel();
        panelBotones.setBackground(contentPane.getBackground());
        panelBotones.setPreferredSize(new Dimension(this.getSize().width, 100));
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, margenIzquierdo, 28));

        Boton botonCancelar = new Boton("Cancelar");
        botonCancelar.setNuevoSize(dimBoton);
        botonCancelar.setColorLetra(paleta.getColorLetraMenu());
        botonCancelar.setColorFondo(paleta.getColorCasillaTabla());

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        Boton botonAceptar = new Boton("Aceptar");
        botonAceptar.setNuevoSize(dimBoton);
        botonAceptar.setColorLetra(Color.WHITE);
        botonAceptar.setColorFondo(paleta.getColorCaracteristico());

        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioFemenino.isSelected()) {
                    sexo = "f";
                }
                if (radioMasculino.isSelected()) {
                    sexo = "m";
                }

                realizarAccion();


            }


        });

        panelBotones.add(botonCancelar);
        panelBotones.add(botonAceptar);
    }

    private void inicializarPanelInf() {

        panelInf = new JPanel(null);
        panelInf.setBackground(contentPane.getBackground());

        boxNombre = new CustomTextField(dimTextField, "Nombre", maxCharNombre, paleta.getColorCasillaTabla());
        boxApellidos = new CustomTextField(dimTextField, "Apellidos", maxCharApellido, paleta.getColorCasillaTabla());
        boxID = new CustomTextField(dimTextField, "ID", maxCharID, paleta.getColorCasillaTabla());

        int separacion = 40;
        int y3 = separacion + 15;
        boxNombre.setLocation(margenIzquierdo, y3);
        y3 += dimTextField.height + separacion;
        boxApellidos.setLocation(margenIzquierdo, y3);
        y3 += dimTextField.height + separacion;
        boxID.setLocation(margenIzquierdo, y3);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                contentPane.requestFocus();
            }
        });

        panelInf.add(boxNombre);
        boxNombre.pasaFoco(true);
        panelInf.add(boxApellidos);
        boxApellidos.pasaFoco(true);
        panelInf.add(boxID);

        boxID.getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    contentPane.requestFocus();
                }
            }
        });
        boxNombre.soloLetras(true);
        boxApellidos.soloLetras(true);
        boxID.soloNumeros(true);

        int y2 = boxID.getLocation().y + dimTextField.height;
        y2 += separacion * 2;

        Etiqueta etiquetaSexo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Seleccionar Sexo");
        etiquetaSexo.setLocation(margenIzquierdo, y2);
        etiquetaSexo.setColorFondo(paleta.getColorCasillaTabla());
        etiquetaSexo.setSize(new Dimension(this.getSize().width - margenIzquierdo * 2, 30));
        etiquetaSexo.setHorizontalAlignment(JLabel.CENTER);

        radioFemenino = new CustomRadioButton("Femenino");
        radioFemenino.setNuevaFuente(fuenteRadio);
        y2 += etiquetaSexo.getSize().height + separacion;
        radioFemenino.setLocation(margenIzquierdo * 3, y2);


        radioMasculino = new CustomRadioButton("Masculino");
        radioMasculino.setNuevaFuente(fuenteRadio);
        radioMasculino.setLocation(etiquetaSexo.getWidth() - margenIzquierdo - radioMasculino.getSize().width, y2);

        ButtonGroup radioGrupo = new ButtonGroup();
        radioGrupo.add(radioFemenino);
        radioGrupo.add(radioMasculino);

        panelInf.add(radioFemenino);
        panelInf.add(radioMasculino);
        panelInf.add(etiquetaSexo);

    }

    private void inicializarTitulo() {
        panelTitulo = new JPanel();
        panelTitulo.setBackground(contentPane.getBackground());
        panelTitulo.setSize(this.getSize().width, 80);
        panelTitulo.setPreferredSize(panelTitulo.getSize());
        panelTitulo.setLayout(null);

        Etiqueta titulo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Rellenar Información");


        titulo.setNuevoSizeLetra(20);
        titulo.setBold(true);
        titulo.setLocation((this.getSize().width - titulo.getSize().width) / 2, 30);
        panelTitulo.add(titulo);
    }


    protected abstract void realizarAccion();
}
