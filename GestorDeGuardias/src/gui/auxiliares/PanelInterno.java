package gui.auxiliares;

import gui.componentes.Cuadro;
import gui.componentes.Etiqueta;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;


public class PanelInterno extends Cuadro {
    private static final Paleta paleta = new Paleta();
    private static final Color colorCasual = paleta.getColorFondoTabla();
    private static final Color colorHoy = paleta.getColorCaracteristico();
    private static final Color colorLetraCasual = paleta.getColorLetraMenu();
    private static final Color colorLetraHoy = Color.WHITE;
    private final LocalDate fechaInicio;
    private final int espacioX = 20;
    private final int espacioY = 20;

    private final Etiqueta titulo;
    private final Etiqueta titulo2;

    private final Color colorLetra;
    private final Color colorFondo;
    private final Font fuente = new Font("Arial", Font.BOLD, 16);

    private boolean seleccionado = false;


    public PanelInterno(LocalDate fechaInicial, Dimension dim) {
        super(dim, Cuadro.redMED, Color.GRAY);
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setLayout(null);
        this.fechaInicio = fechaInicial;

        if (fechaInicial.getMonth() == LocalDate.now().getMonth() && fechaInicial.getYear() == LocalDate.now().getYear()) {
            colorFondo = colorHoy;
            colorLetra = colorLetraHoy;
        } else {
            colorFondo = colorCasual;
            colorLetra = colorLetraCasual;
        }

        this.setColorFondo(colorFondo);
        this.setBackground(colorFondo);

        String string = fechaInicial.getMonth().name() + "  " + fechaInicial.getYear();
        titulo = new Etiqueta(fuente, colorLetra, string);
        titulo.setLocation(espacioX, espacioY);


        String string2 = "INICIO: " + fechaInicial.getDayOfMonth() + "  " + fechaInicial.getMonth().name() + "  " + fechaInicial.getYear();
        titulo2 = new Etiqueta(fuente, colorLetra, string2);
        titulo2.setLocation(espacioX, espacioY * 2 + titulo.getSize().height);

        this.add(titulo);
        this.add(titulo2);

        this.setColorBorde(colorFondo.darker());

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el color y el cursor cuando el rat�n entra en el panel
                setBackground(colorFondo.darker());
                setColorFondo(colorFondo.darker());
                titulo.setColorFondo(colorFondo.darker());
                titulo2.setColorFondo(colorFondo.darker());
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setColorBorde(paleta.getColorCaracteristico().darker().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar el color y el cursor cuando el rat�n sale del panel
                setBackground(colorFondo);
                setColorFondo(colorFondo);
                titulo.setColorFondo(colorFondo);
                titulo2.setColorFondo(colorFondo);
                setCursor(Cursor.getDefaultCursor());
                setColorBorde(paleta.getColorCaracteristico().darker());
            }
        });

    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setSeleccionado(boolean x) {
        seleccionado = x;
        this.setBorde(x);
        revalidate();
        repaint();
    }

    public void setSeleccionado() {
        seleccionado = !seleccionado;
        this.setBorde(seleccionado);
        revalidate();
        repaint();
    }

}

