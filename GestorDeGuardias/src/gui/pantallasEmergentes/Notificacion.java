package gui.pantallasEmergentes;

import gui.auxiliares.Paleta;
import gui.secciones.Ventana;

import javax.swing.*;
import java.awt.*;

public class Notificacion extends JDialog {
    private static final long serialVersionUID = 1L;

    private final Paleta paleta = new Paleta();
    private final Font fuente = new Font("Arial", Font.PLAIN, 14);
    protected JLabel label;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private GridBagConstraints gbc;

    public Notificacion(Dimension dim, String title, String texto) {
        super(Ventana.getInstance(), title, true);

        inicializar(dim, texto, Ventana.getInstance());

        if (label.getPreferredSize().height > 100) {
            this.setSize(new Dimension(dim.width, label.getPreferredSize().height));
            setMinimumSize(this.getSize());
        }
    }

    private void inicializar(Dimension dim, String texto, final Frame ventana) {
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
}
