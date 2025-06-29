package gui.internosComp;

import gui.auxiliares.IsTabla;
import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.componentes.CustomScrollBar;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class TablaUsuarios extends Cuadro implements IsTabla {
    private static final long serialVersionUID = 1L;

    private static final int redondez = 0;
    private final Paleta paleta = new Paleta();

    private final Color colorCabecera = new Color(37, 97, 209);
    private final Color colorCabeceraLetra = Color.WHITE;
    private final Color colorDiaFondo = new Color(237, 239, 244);
    private final Font fuenteCabecera = new Font("Arial", Font.BOLD, 15);
    private final Font fuenteNormal = new Font("Arial", Font.PLAIN, 14);
    private final int PAD = 9;
    private final int rowHeight = 40;

    private Usuario usuarioSeleccionado = null;
    private Consumer<Usuario> seleccionListener = null; // Para notificar cambios de selección

    public TablaUsuarios(final Dimension dimension, Color color, ArrayList<Usuario> usuarios) {
        super(dimension, redondez, color);
        this.setLayout(new BorderLayout());
        this.setColorBorde(paleta.getColorCaracteristico());

        JPanel encabezado = imprimirEncabezado();
        JScrollPane cuerpo = imprimirCuerpo(usuarios);

        add(encabezado, BorderLayout.NORTH);
        add(cuerpo, BorderLayout.CENTER);
    }

    // NUEVO: Permitir setear un listener para cambios de selección
    public void setSeleccionListener(Consumer<Usuario> listener) {
        this.seleccionListener = listener;
    }

    // NUEVO: Permitir obtener el usuario seleccionado
    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    private JPanel imprimirEncabezado()  {
        JPanel encabezado = new JPanel(new GridBagLayout());
        encabezado.setPreferredSize(new Dimension(0, 67));
        encabezado.setBorder(new EmptyBorder(PAD, 0, PAD, 0));
        encabezado.setBackground(colorCabecera);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        encabezado.add(celdaEncabezado("Usuarios"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        encabezado.add(celdaEncabezado(""), gbc);

        String[] textos = { "Usuario", "Rol"};
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        for (int i = 0; i < textos.length; i++) {
            gbc.gridx = i;
            gbc.insets = new Insets(0, 50, 0, 0);
            encabezado.add(celdaEncabezado(textos[i]), gbc);
        }

        return encabezado;
    }

    private JLabel celdaEncabezado(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setForeground(colorCabeceraLetra);
        lbl.setFont(fuenteCabecera);
        lbl.setBorder(new EmptyBorder(0, PAD, 0, 0));
        lbl.setPreferredSize(new Dimension(20, 20));
        return lbl;
    }

    private JScrollPane imprimirCuerpo(ArrayList<Usuario> usuarios){
        JPanel cuerpo = new JPanel();
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        final FilaInteractiva[] filaSeleccionada = {null};
        final Usuario[] usuarioSeleccionadoRef = {null};
        AtomicBoolean alt = new AtomicBoolean(false);

        for (Usuario usuario : usuarios) {
            FilaInteractiva fila = construirFila(usuario, alt);

            fila.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Deseleccionar la fila anterior
                    if (filaSeleccionada[0] != null && filaSeleccionada[0] != fila) {
                        filaSeleccionada[0].setSeleccionado(false);
                        filaSeleccionada[0].resetColor(!alt.get());
                    }
                    // Seleccionar la nueva fila
                    fila.setSeleccionado(true);
                    filaSeleccionada[0] = fila;
                    usuarioSeleccionado = usuario;
                    usuarioSeleccionadoRef[0] = usuario;
                    // Notificar si hay listener
                    if (seleccionListener != null) {
                        seleccionListener.accept(usuarioSeleccionado);
                    }
                }
            });

            cuerpo.add(Box.createVerticalStrut(2));
            cuerpo.add(fila);
        }

        JScrollPane scrollPane = new JScrollPane(cuerpo);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());

        return scrollPane;
    }

    private FilaInteractiva construirFila(Usuario usuario, AtomicBoolean alt) {
        FilaInteractiva fila = new FilaInteractiva(alt.get(),
                paleta.getColorCaracteristico(),
                Color.WHITE,
                paleta.getColorLetraMenu(),
                colorDiaFondo,
                Color.WHITE  );
        fila.setMinimumSize(new Dimension(Integer.MAX_VALUE, rowHeight));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, rowHeight));

        Color bgColor = alt.get() ? colorDiaFondo : Color.WHITE;
        fila.setBackground(bgColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;

        String[] textos = {
                usuario.getNombre() != null ? usuario.getNombre() : "",
                usuario.getRol() != null ? usuario.getRol().getNombre() : ""
        };

        for (int j = 0; j < textos.length; j++) {
            gbc.gridx = j + 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 50, 0, 0);
            JLabel label = celdaCuerpo(textos[j], fuenteNormal, bgColor);
            fila.add(label, gbc);
        }

        alt.set(!alt.get());

        return fila;
    }

    private JLabel celdaCuerpo(String text, Font font, Color bgColor) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setFont(font);
        lbl.setBackground(bgColor);
        lbl.setOpaque(true);
        lbl.setBorder(new EmptyBorder(0, PAD, 0, 0));
        lbl.setPreferredSize(new Dimension(20, rowHeight));
        return lbl;
    }

    @Override
    public void actualizar() {}

    @Override
    public void actualizarVistaTabla() {}
}