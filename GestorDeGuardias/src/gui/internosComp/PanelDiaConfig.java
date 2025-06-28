package gui.internosComp;

import gui.componentes.Etiqueta;
import model.Configuracion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PanelDiaConfig extends JPanel {
    private final Font fuente;
    private final Color colorFondo;
    private final Consumer<Configuracion> onSeleccion;

    public PanelDiaConfig(String dia, ArrayList<Configuracion> configuraciones, Color colorFondo, Font fuente, Consumer<Configuracion> onSeleccion) {
        super();
        this.fuente = fuente.deriveFont(16f);
        this.colorFondo = colorFondo;
        this.onSeleccion = onSeleccion;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(colorFondo);
        setBorder(new EmptyBorder(8, 16, 8, 8));

        Etiqueta lblDia = new Etiqueta(fuente.deriveFont(Font.BOLD, 18f), Color.DARK_GRAY, dia);
        lblDia.setBorder(new EmptyBorder(0, 10, 6, 0));
        add(lblDia);

        for (Configuracion conf : configuraciones) {
            add(crearFila(conf));
            add(Box.createRigidArea(new Dimension(0, 2)));
        }
    }

    private JPanel crearFila(Configuracion conf) {
        JPanel fila = new JPanel(new GridLayout(1, 5));
        fila.setBackground(Color.WHITE);
        fila.setOpaque(true);
        fila.setBorder(new EmptyBorder(5, 8, 5, 8));
        fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        fila.add(new JLabel(""));
        fila.add(labelCell(conf.getHorario() != null ? conf.getHorario().toString() : ""));
        fila.add(labelCell(String.valueOf(conf.getCantPersonas())));
        fila.add(labelCell(conf.getSexo() != null ? conf.getSexo() : ""));
        fila.add(labelCell(conf.diaEsReceso() ? "Sí" : "No"));

        fila.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fila.setBackground(new Color(210, 230, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                fila.setBackground(Color.WHITE);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onSeleccion != null) onSeleccion.accept(conf);
            }
        });

        return fila;
    }

    private JLabel labelCell(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.LEFT);
        lbl.setFont(fuente);
        lbl.setBorder(new EmptyBorder(0, 6, 0, 0));
        return lbl;
    }
}