package gui.pantallasEmergentes;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.Etiqueta;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class PantallaAddHorario extends JDialog {
    protected static final long serialVersionUID = 1L;

    protected JPanel contentPane;
    protected JPanel panelTitulo, panelCampos, panelBotones;
    protected Paleta paleta = new Paleta();

    protected Dimension dim = new Dimension(360, 300); // Cambiado para ser más grande
    protected Dimension dimBoton = new Dimension(100, 36);
    protected Font fuente = new Font("Arial", Font.PLAIN, 14);

    protected JTextField txtHoraInicio;
    protected JTextField txtHoraFin;

    private boolean aceptado = false;

    public PantallaAddHorario(Window parent) {
        super(parent, "Añadir Horario", ModalityType.APPLICATION_MODAL);
        setResizable(false); // Cambiado para permitir redimensionar
        setSize(dim);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setBackground(paleta.getColorFondoTabla());
        contentPane.setLayout(new BorderLayout());

        int x = (parent.getWidth() - this.getWidth()) / 2;
        int y = (parent.getHeight() - this.getHeight()) / 2;
        setLocation(x, y);

        inicializarTitulo();
        inicializarPanelCampos();
        inicializarPanelBotones();

        contentPane.add(panelTitulo, BorderLayout.NORTH);
        contentPane.add(panelCampos, BorderLayout.CENTER);
        contentPane.add(panelBotones, BorderLayout.SOUTH);

        Border border = BorderFactory.createMatteBorder(2, 2, 2, 2, paleta.getColorBorde());
        contentPane.setBorder(border);

        setVisible(true);
    }

    private void inicializarTitulo() {
        panelTitulo = new JPanel();
        panelTitulo.setBackground(contentPane.getBackground());
        panelTitulo.setPreferredSize(new Dimension(this.getSize().width, 40));
        panelTitulo.setLayout(new BorderLayout());

        Etiqueta titulo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Añadir Horario");
        titulo.setBold(true);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitulo.add(titulo);
    }

    private void inicializarPanelCampos() {
        panelCampos = new JPanel();
        panelCampos.setBackground(contentPane.getBackground());
        panelCampos.setLayout(null);

        int margenIzq = 24;
        int y = 18;

        Etiqueta lblInicio = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Hora inicio (HH:mm):");
        lblInicio.setBounds(margenIzq, y, 130, 26);
        panelCampos.add(lblInicio);

        txtHoraInicio = new JTextField();
        txtHoraInicio.setBounds(margenIzq + 150, y, 100, 26);
        panelCampos.add(txtHoraInicio);

        y += 44;

        Etiqueta lblFin = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Hora fin (HH:mm):");
        lblFin.setBounds(margenIzq, y, 130, 26);
        panelCampos.add(lblFin);

        txtHoraFin = new JTextField();
        txtHoraFin.setBounds(margenIzq + 150, y, 100, 26);
        panelCampos.add(txtHoraFin);

        panelCampos.setPreferredSize(new Dimension(this.getWidth(), y + 60));
    }

    private void inicializarPanelBotones() {
        panelBotones = new JPanel();
        panelBotones.setBackground(contentPane.getBackground());
        panelBotones.setPreferredSize(new Dimension(this.getWidth(), 60));
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));

        Boton btnCancelar = new Boton("Cancelar");
        btnCancelar.setNuevoSize(dimBoton);
        btnCancelar.setColorLetra(paleta.getColorLetraMenu());
        btnCancelar.setColorFondo(paleta.getColorCasillaTabla());
        btnCancelar.addActionListener(e -> {
            aceptado = false;
            dispose();
        });

        Boton btnAceptar = new Boton("Aceptar");
        btnAceptar.setNuevoSize(dimBoton);
        btnAceptar.setColorLetra(Color.WHITE);
        btnAceptar.setColorFondo(paleta.getColorCaracteristico());

        btnAceptar.addActionListener(e -> {
            String horaInicio = txtHoraInicio.getText().trim();
            String horaFin = txtHoraFin.getText().trim();
            // Validación simple de campos y formato HH:mm
            if (horaInicio.isEmpty() || horaFin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe rellenar ambos campos de hora.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!horaInicio.matches("\\d{2}:\\d{2}") || !horaFin.matches("\\d{2}:\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Formato de hora inválido. Use HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            aceptado = true;
            dispose();
        });

        panelBotones.add(btnCancelar);
        panelBotones.add(btnAceptar);
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public String getHoraInicio() {
        return txtHoraInicio.getText().trim();
    }
    public String getHoraFin() {
        return txtHoraFin.getText().trim();
    }
}