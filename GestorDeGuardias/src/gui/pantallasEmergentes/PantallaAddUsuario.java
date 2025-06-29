package gui.pantallasEmergentes;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.Cuadro;
import gui.componentes.Etiqueta;
import gui.componentes.CustomComboBoxV2;
import gui.componentes.CustomTextField;
import gui.componentes.CustomPasswordField;
import gui.secciones.Ventana;
import model.Rol;
import model.Usuario;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;
import utils.exceptions.MultiplesErroresException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class PantallaAddUsuario extends JDialog {
    protected static final long serialVersionUID = 1L;

    protected JPanel contentPane;
    protected JPanel panelTitulo, panelInf, panelBotones;
    protected Paleta paleta = new Paleta();

    protected Dimension dim = new Dimension(480, 420);
    protected Dimension dimBoton = new Dimension(130, 44);
    protected Font fuente = new Font("Arial", Font.PLAIN, 16);

    protected CustomComboBoxV2 comboRoles;
    protected CustomTextField fieldUsuario;
    protected CustomPasswordField fieldPassword;

    protected ArrayList<Rol> listaRoles;

    public PantallaAddUsuario(ArrayList<Rol> rolesDisponibles) {
        super(Ventana.getInstance(), "Crear Usuario", true);
        this.listaRoles = rolesDisponibles;
        setResizable(false);
        setSize(dim);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setBackground(paleta.getColorFondoTabla());
        contentPane.setLayout(new BorderLayout());

        int x = (Ventana.getInstance().getSize().width - this.getWidth()) / 2;
        int y = (Ventana.getInstance().getSize().height - this.getHeight()) / 2;
        setLocation(x, y);

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

    private void inicializarTitulo() {
        panelTitulo = new JPanel();
        panelTitulo.setBackground(contentPane.getBackground());
        panelTitulo.setSize(this.getSize().width, 50);
        panelTitulo.setPreferredSize(panelTitulo.getSize());
        panelTitulo.setLayout(new BorderLayout());

        Etiqueta titulo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Crear Usuario");
        titulo.setBold(true);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitulo.add(titulo);
    }

    private void inicializarPanelInf() {
        panelInf = new JPanel(null);
        panelInf.setBackground(contentPane.getBackground());

        int y = 38;
        int margenIzquierdo = 40;
        Dimension dimCampo = new Dimension(260, 32);

        // Etiqueta Usuario
        Etiqueta etiquetaUsuario = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Usuario:");
        etiquetaUsuario.setLocation(margenIzquierdo, y);
        etiquetaUsuario.setSize(new Dimension(110, 32));
        panelInf.add(etiquetaUsuario);

        fieldUsuario = new CustomTextField(dimCampo, "Ingrese usuario", 30, paleta.getColorCasillaTabla());
        fieldUsuario.setFont(fuente);
        fieldUsuario.setLocation(margenIzquierdo + 120, y);
        fieldUsuario.setSize(dimCampo);
        panelInf.add(fieldUsuario);
        y += 59;

        // Etiqueta Contraseña
        Etiqueta etiquetaPass = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Contraseña:");
        etiquetaPass.setLocation(margenIzquierdo, y);
        etiquetaPass.setSize(new Dimension(110, 32));
        panelInf.add(etiquetaPass);


        fieldPassword = new CustomPasswordField(dimCampo, "Ingrese contraseña", Cuadro.redBAJA, 30, paleta.getColorCasillaTabla());
        fieldPassword.setFont(fuente);
        fieldPassword.setLocation(margenIzquierdo + 120, y);
        fieldPassword.setSize(dimCampo);
        panelInf.add(fieldPassword);

        y += 54;

        // ComboBox de Roles
        Etiqueta etiquetaRol = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Rol:");
        etiquetaRol.setLocation(margenIzquierdo, y);
        etiquetaRol.setSize(new Dimension(110, 32));
        panelInf.add(etiquetaRol);

        String[] nombresRoles = listaRoles.stream().map(Rol::getNombre).toArray(String[]::new);
        comboRoles = new CustomComboBoxV2(nombresRoles, dimCampo, Cuadro.redBAJA, paleta.getColorCasillaTabla());
        comboRoles.setFont(fuente);
        comboRoles.setBounds(margenIzquierdo + 120, y, dimCampo.width, dimCampo.height);
        panelInf.add(comboRoles);

        // Altura dinámica del panel
        panelInf.setPreferredSize(new Dimension(this.getSize().width, y + 74));
    }

    private void inicializarPanelBotones() {
        panelBotones = new JPanel();
        panelBotones.setBackground(contentPane.getBackground());
        panelBotones.setPreferredSize(new Dimension(this.getSize().width, 98));
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 28));

        Boton botonCancelar = new Boton("Cancelar");
        botonCancelar.setNuevoSize(dimBoton);
        botonCancelar.setColorLetra(paleta.getColorLetraMenu());
        botonCancelar.setColorFondo(paleta.getColorCasillaTabla());
        botonCancelar.addActionListener(e -> dispose());

        Boton botonAceptar = new Boton("Aceptar");
        botonAceptar.setNuevoSize(dimBoton);
        botonAceptar.setColorLetra(Color.WHITE);
        botonAceptar.setColorFondo(paleta.getColorCaracteristico());

        botonAceptar.addActionListener(this::actionPerformed);

        panelBotones.add(botonCancelar);
        panelBotones.add(botonAceptar);
    }

    private void actionPerformed(ActionEvent e) {
        String usuario = fieldUsuario.getText().trim();
        String pass = fieldPassword.getText();
        int rolIdx = comboRoles.getSelectedIndex();

        if (usuario.isEmpty() || pass.isEmpty() || rolIdx < 0) {
            new Advertencia(new Dimension(400, 150), "Campos requeridos", "Debe ingresar usuario, contraseña y rol.", "Aceptar");
            return;
        }

        Rol rolSeleccionado = listaRoles.get(rolIdx);

        Usuario newUser = new Usuario();
        newUser.setNombre(usuario);
        newUser.setContrasenna(pass);
        newUser.setRol(rolSeleccionado);

        try {
            ServicesLocator.getInstance().getUsuarioServices().insertUsuario(newUser);
            new Advertencia(new Dimension(400, 150), "Éxito", "Usuario creado correctamente.", "Aceptar");
            dispose();
        } catch (MultiplesErroresException | SqlServerCustomException ex) {
            new Advertencia(new Dimension(400, 150), "Error", "Error al crear usuario: " + ex.getMessage(), "Aceptar");
        }
    }
}