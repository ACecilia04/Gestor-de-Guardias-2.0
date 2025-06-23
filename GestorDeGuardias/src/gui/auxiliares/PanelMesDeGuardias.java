package gui.auxiliares;

import gui.componentes.Cuadro;
import gui.componentes.Etiqueta;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import static gui.auxiliares.ConvertidorFecha.traducDiaMes;


public class PanelMesDeGuardias extends Cuadro {
    private static final Paleta paleta = new Paleta();
    private static final Color colorCasual = paleta.getColorFondoTabla();
    private static final Color colorHoy = paleta.getColorCaracteristico();
    private static final Color colorLetraCasual = paleta.getColorLetraMenu();
    private static final Color colorLetraHoy = Color.WHITE;
    private final LocalDate fechaInicio;

    private final Etiqueta titulo;
    private final Etiqueta titulo2;

    private final Color colorFondo;

    private boolean seleccionado = false;


    public PanelMesDeGuardias(LocalDate fechaInicial, Dimension dim) {
        super(dim, Cuadro.redMED, Color.GRAY);
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setLayout(null);
        this.fechaInicio = fechaInicial;

        Color colorLetra;
        if (fechaInicial.getMonth() == LocalDate.now().getMonth() && fechaInicial.getYear() == LocalDate.now().getYear()) {
            colorFondo = colorHoy;
            colorLetra = colorLetraHoy;
        } else {
            colorFondo = colorCasual;
            colorLetra = colorLetraCasual;
        }

        this.setColorFondo(colorFondo);
        this.setBackground(colorFondo);

        String stringMesYAnno = traducDiaMes(fechaInicial) + "  " + fechaInicial.getYear();
        Font fuente = new Font("Arial", Font.BOLD, 14);
        titulo = new Etiqueta(fuente, colorLetra, stringMesYAnno);
        int espacioX = 20;
        int espacioY = 20;
        titulo.setLocation(espacioX, espacioY);


        String stringFechaInicio = "INICIO: " + fechaInicial.getDayOfMonth() + "  " + traducDiaMes(fechaInicial) + "  " + fechaInicial.getYear();
        titulo2 = new Etiqueta(fuente, colorLetra, stringFechaInicio);
        titulo2.setLocation(espacioX, espacioY * 2 + titulo.getSize().height);

        this.add(titulo);
        this.add(titulo2);

        this.setColorBorde(colorFondo.darker());

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el color y el cursor cuando el raton entra en el panel
                setBackground(colorFondo.darker());
                setColorFondo(colorFondo.darker());
                titulo.setColorFondo(colorFondo.darker());
                titulo2.setColorFondo(colorFondo.darker());
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setColorBorde(paleta.getColorCaracteristico().darker().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar el color y el cursor cuando el raton sale del panel
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

