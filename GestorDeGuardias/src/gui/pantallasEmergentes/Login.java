package gui.pantallasEmergentes;

import gui.auxiliares.Paleta;
import gui.componentes.*;
import gui.secciones.Ventana;
import model.Usuario;
import services.ServicesLocator;
import services.UsuarioServices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Login extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JPanel panel1;
    private final JPanel panel2;
    private final int margen = 48;
    private final int espacioInicial = 72;

    private final Paleta paleta = new Paleta();
    private Usuario usuarioLogueado; // Nuevo: Para almacenar el usuario autenticado

    public Login(JPanel overlayPane) {
        super(Ventana.getInstance(), "JDialog", true);
        overlayPane.setVisible(true);
        setSize(500, 695);

        final Login myself = this;

        int x = (Ventana.getInstance().getSize().width - this.getWidth()) / 2;
        int y = (Ventana.getInstance().getSize().height - this.getHeight()) / 2;
        setLocation(x, y);

        setUndecorated(true);
        contentPane = new JPanel();
        this.setContentPane(contentPane);

        contentPane.setBackground(paleta.getColorFondoTabla());
        contentPane.setLayout(new BorderLayout());

        // ===== NUEVO: Panel de encabezado branding centrado =====
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(contentPane.getBackground());
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(36, 0, 20, 0));

        // Logo centrado
        JPanel logoRow = new JPanel();
        logoRow.setBackground(contentPane.getBackground());
        logoRow.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        Cuadro logo = new Cuadro(new Dimension(60, 60), Cuadro.redBAJA, paleta.getColorCaracteristico());
        logoRow.add(logo);

        // Textos branding centrados
        JLabel appName = new JLabel("Gestor de Guardias");
        appName.setFont(new Font("Arial", Font.BOLD, 22));
        appName.setForeground(paleta.getColorCaracteristico());
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appSub = new JLabel("Proyecto Final");
        appSub.setFont(new Font("Arial", Font.PLAIN, 16));
        appSub.setForeground(paleta.getColorLetraMenu());
        appSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelHeader.add(logoRow);
        panelHeader.add(Box.createVerticalStrut(10));
        panelHeader.add(appName);
        panelHeader.add(appSub);

        contentPane.add(panelHeader, BorderLayout.NORTH);

        //PanelBotones panel2
        panel2 = new JPanel(null);
        panel2.setBackground(contentPane.getBackground());
        panel2.setPreferredSize(new Dimension(this.getWidth(), 200));

        Boton iniciarSesionBtn = new Boton("Iniciar sesion");
        iniciarSesionBtn.setBordeado(true);
        iniciarSesionBtn.setNuevoSize(new Dimension(330, 54));
        iniciarSesionBtn.setColorPresionado(paleta.getColorCaracteristico());
        iniciarSesionBtn.setColorLetra(paleta.getColorCaracteristico());

        x = (panel2.getPreferredSize().width - iniciarSesionBtn.getWidth()) / 2;
        y = 0;
        iniciarSesionBtn.setLocation(x, 30);

        Boton cerrarBtn = new Boton("Cerrar Programa");
        cerrarBtn.setColorFondo(paleta.getColorCaracteristico());
        cerrarBtn.setColorLetraPres(Color.WHITE);
        cerrarBtn.setNuevoSize(new Dimension(330, 54));

        x = (panel2.getPreferredSize().width - cerrarBtn.getWidth()) / 2;
        y += iniciarSesionBtn.getHeight() * 2;

        cerrarBtn.setLocation(x, y);
        cerrarBtn.setColorLetra(Color.WHITE);

        cerrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ventana.getInstance().dispose(); // Cerrar el JDialog
            }
        });

        panel2.add(iniciarSesionBtn);
        panel2.add(cerrarBtn);

        add(panel2, BorderLayout.SOUTH);

        //panel1 Todo lo demas
        panel1 = new JPanel(null);
        panel1.setBackground(contentPane.getBackground());

        //Incia sesion
        Etiqueta iniciarSesionLbl = new Etiqueta("Iniciar Sesión");

        iniciarSesionLbl.setLocation(margen, 20);
        iniciarSesionLbl.setNuevoSizeLetra((float) 20);
        iniciarSesionLbl.setNuevoSizeLetra(24f);
        iniciarSesionLbl.setSize(300, iniciarSesionLbl.getPreferredSize().height);
        iniciarSesionLbl.setForeground(paleta.getColorLetraMenu());
        panel1.add(iniciarSesionLbl);

        //Ingresar Textos
        final CustomTextField inicio = new CustomTextField(new Dimension(370, 47), "Ingresa tu Nombre", 40, Color.LIGHT_GRAY);
        int sepTextField = 50;
        inicio.setLocation(margen, iniciarSesionLbl.getLocation().y + iniciarSesionLbl.getSize().height + sepTextField);
        panel1.add(inicio);

        final CustomPasswordField contrasena = new CustomPasswordField(new Dimension(370, 47), "Contraseña", Cuadro.redBAJA, 40, Color.LIGHT_GRAY);
        contrasena.setLocation(margen, inicio.getLocation().y + contrasena.getSize().height + 30);
        panel1.add(contrasena);

        //Etiquetas de error
        int sepError = 5;
        final Etiqueta inicioIncorrecto = new Etiqueta("Usuario Inexistente");
        inicioIncorrecto.setForeground(Color.RED);
        inicioIncorrecto.setNuevoSizeLetra(16);
        inicioIncorrecto.setLocation(margen, inicio.getLocation().y - inicioIncorrecto.getSize().height - sepError);
        inicioIncorrecto.setVisible(false);

        final Etiqueta contrasenaIncorrecta = new Etiqueta("Contraseña Incorrecta");
        contrasenaIncorrecta.setForeground(Color.RED);
        contrasenaIncorrecta.setNuevoSizeLetra(16);
        contrasenaIncorrecta.setLocation(margen, contrasena.getLocation().y - contrasenaIncorrecta.getSize().height - sepError);
        contrasenaIncorrecta.setVisible(false);

        panel1.add(inicioIncorrecto);
        panel1.add(contrasenaIncorrecta);

        iniciarSesionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean correcto = true;
                String nombreUsuario = inicio.getText();
                String contrasenaIngresada = contrasena.getText();

                // Ocultar errores previos
                inicioIncorrecto.setVisible(false);
                contrasenaIncorrecta.setVisible(false);

                if (nombreUsuario.isEmpty() || nombreUsuario.equals(inicio.getTextoPorDefecto())) {
                    correcto = false;
                    inicioIncorrecto.setTexto("Campo Obligatorio");
                    inicioIncorrecto.setVisible(true);
                } else {
                    UsuarioServices usuarioServices = ServicesLocator.getInstance().getUsuarioServices();
                    Usuario usuario = usuarioServices.getUsuarioByNombre(nombreUsuario);

                    if (usuario == null) {
                        correcto = false;
                        inicioIncorrecto.setTexto("Usuario Inexistente");
                        inicioIncorrecto.setVisible(true);
                    } else if (contrasenaIngresada.isEmpty() || contrasenaIngresada.equals(contrasena.getTextoPorDefecto())) {
                        correcto = false;
                        contrasenaIncorrecta.setTexto("Campo Obligatorio");
                        contrasenaIncorrecta.setVisible(true);
                    } else if (!usuario.getContrasenna().equals(contrasenaIngresada)) {
                        correcto = false;
                        contrasenaIncorrecta.setTexto("Contraseña Incorrecta");
                        contrasenaIncorrecta.setVisible(true);
                    } else {
                        // Login correcto
                        usuarioLogueado = usuario;
                        myself.dispose();
                    }
                }
            }
        });

//        inicioIncorrecto.requestFocusInWindow();

        contentPane.add(panel1, BorderLayout.CENTER);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Cuadro.redMED, Cuadro.redMED));
        setVisible(true);
        overlayPane.setVisible(false);
    }

    private static String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
}