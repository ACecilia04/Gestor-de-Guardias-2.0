package gui.pantallasEmergentes;

import gui.auxiliares.Paleta;
import gui.componentes.*;
import gui.secciones.Ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Login extends JDialog {
    private static final long serialVersionUID = 1L;
    private final String CLAVE = "1234";
    private final JPanel contentPane;
    private final JPanel panel1;
    private final JPanel panel2;
    private final int margen = 48;
    private final int espacioInicial = 72;

    private final Paleta paleta = new Paleta();

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

        //PanelBotones panel2
        panel2 = new JPanel(null);
        panel2.setBackground(contentPane.getBackground());
        panel2.setPreferredSize(new Dimension(this.getWidth(), 200));

        Boton boton1 = new Boton("Iniciar sesion");
        boton1.setBordeado(true);
        boton1.setNuevoSize(new Dimension(330, 54));
        boton1.setColorPresionado(paleta.getColorCaracteristico());
        boton1.setColorLetra(paleta.getColorCaracteristico());

        x = (panel2.getPreferredSize().width - boton1.getWidth()) / 2;
        y = 0;
        boton1.setLocation(x, 30);

        Boton boton2 = new Boton("Cerrar Programa");
        boton2.setColorFondo(paleta.getColorCaracteristico());
        boton2.setColorLetraPres(Color.WHITE);
        boton2.setNuevoSize(new Dimension(330, 54));

        x = (panel2.getPreferredSize().width - boton2.getWidth()) / 2;
        y += boton1.getHeight() * 2;

        boton2.setLocation(x, y);
        boton2.setColorLetra(Color.WHITE);

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ventana.getInstance().dispose(); // Cerrar el JDialog
            }
        });

        panel2.add(boton1);
        panel2.add(boton2);

        add(panel2, BorderLayout.SOUTH);

        //Panel1 Todo lo demas
        panel1 = new JPanel(null);
        panel1.setBackground(contentPane.getBackground());
        Cuadro logo = new Cuadro(new Dimension(60, 60), Cuadro.redBAJA, paleta.getColorCaracteristico());

        logo.setLocation(margen, espacioInicial);
        panel1.add(logo);

        //Pequenos textos junto al logo
        //Mira esto que cul
        Etiqueta texto1 = new Etiqueta("<html>" +
                "<span style='font-size:17px; color:" + toHex(paleta.getColorCaracteristico()) + ";'>Gestor de Guardias</span><br>" + // Tama�o de fuente para "Gestor de Guardias"
                "<span style='font-size:16px; color:" + toHex(paleta.getColorLetraMenu()) + ";'>Proyecto Final</span>" + // Tama�o y color para "Proyecto Final"
                "</html>");
        y = logo.getLocation().y + (logo.getSize().height - texto1.getSize().height) / 2;
        texto1.setLocation(logo.getLocation().x + logo.getWidth() + 10, y);
        panel1.add(texto1);

        //Incia sesion
        Etiqueta etiqueta = new Etiqueta("<html>Inicia sesion con tu Correo o Nombre <br>de Usuario</html>");
        etiqueta.setLocation(margen, 190);
        etiqueta.setNuevoSizeLetra((float) 23);
        etiqueta.setForeground(paleta.getColorLetraMenu());
        panel1.add(etiqueta);


        //Ingresar Textos
        final CustomTextField inicio = new CustomTextField(new Dimension(370, 47), "Ingresa tu Correo/Nombre (Clave = 1234)", 40, Color.LIGHT_GRAY);
        int sepTextField = 50;
        inicio.setLocation(margen, etiqueta.getLocation().y + etiqueta.getSize().height + sepTextField);
        panel1.add(inicio);

        final CustomPasswordField contrasena = new CustomPasswordField(new Dimension(370, 47), "Contrase�a (Clave = 1234)", Cuadro.redBAJA, 40, Color.LIGHT_GRAY);
        contrasena.setLocation(margen, inicio.getLocation().y + contrasena.getSize().height + 30);
        panel1.add(contrasena);

        //Etiquetas de error

        //Error 1
        int sepError = 5;
        final Etiqueta inicioIncorrecto = new Etiqueta("Usuario Inexistente");
        inicioIncorrecto.setForeground(Color.RED);
        inicioIncorrecto.setNuevoSizeLetra(16);
        inicioIncorrecto.setLocation(margen, inicio.getLocation().y - inicioIncorrecto.getSize().height - sepError);
        inicioIncorrecto.setVisible(false);

        final Etiqueta contrasenaIncorrecta = new Etiqueta("Contrasena Incorrecta");
        contrasenaIncorrecta.setForeground(Color.RED);
        contrasenaIncorrecta.setNuevoSizeLetra(16);
        contrasenaIncorrecta.setLocation(margen, contrasena.getLocation().y - contrasenaIncorrecta.getSize().height - sepError);
        contrasenaIncorrecta.setVisible(false);

        panel1.add(inicioIncorrecto);
        panel1.add(contrasenaIncorrecta);


        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean correcto = true;

                if (!myself.contrasenaCorrecta(contrasena.getText(), CLAVE)) {
                    correcto = false;
                    if (contrasena.getText().isEmpty() || contrasena.getText().equals(contrasena.getTextoPorDefecto())) {
                        contrasenaIncorrecta.setTexto("Campo Obligatorio");
                    } else {
                        contrasenaIncorrecta.setTexto("Contrasena Incorrecta");
                    }
                    if (!contrasenaIncorrecta.isVisible()) {
                        contrasenaIncorrecta.setVisible(true);
                    }
                } else if (contrasenaIncorrecta.isVisible()) {
                    contrasenaIncorrecta.setVisible(false);
                }

                if (!myself.usuarioCorrecto(inicio.getText(), CLAVE)) {
                    correcto = false;
                    if (inicio.getText().isEmpty() || inicio.getText().equals(inicio.getTextoPorDefecto())) {
                        inicioIncorrecto.setTexto("Campo Obligatorio");
                    } else {
                        inicioIncorrecto.setTexto("Usuario Inexistente");
                    }
                    if (!inicioIncorrecto.isVisible()) {
                        inicioIncorrecto.setVisible(true);
                    }
                } else if (inicioIncorrecto.isVisible()) {
                    inicioIncorrecto.setVisible(false);
                }

                if (correcto) {
                    myself.dispose();
                }
            }
        });

        inicioIncorrecto.requestFocusInWindow();

        contentPane.add(panel1, BorderLayout.CENTER);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Cuadro.redMED, Cuadro.redMED));
        setVisible(true);
        overlayPane.setVisible(false);
    }

    private static String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public boolean usuarioCorrecto(String usuario, String clave) {
        boolean correcto = usuario.equals(clave);
        return correcto;
    }

    public boolean contrasenaCorrecta(String contrasena, String clave) {
        boolean correcto = contrasena.equals(clave);
        return correcto;
    }
}

