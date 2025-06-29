package gui.componentes;

import gui.auxiliares.Paleta;
import gui.secciones.MostrarPlanif;
import gui.secciones.Ventana;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import static gui.auxiliares.ConvertidorFecha.traducDiaMes;


public class PanelMes extends Cuadro {
    private static final Paleta paleta = new Paleta();
    private static final Color colorCasual = paleta.getColorFondoTabla();
    private static final Color colorHoy = paleta.getColorCaracteristico();
    private static final Color colorLetraCasual = paleta.getColorLetraMenu();
    private static final Color colorLetraHoy = Color.WHITE;
    private final LocalDate fechaInicio;

    private final Etiqueta titulo;
    private final Etiqueta titulo2;

    private final Color colorFondo;

    private boolean seleccionado;
    private Object parent;

    public PanelMes(LocalDate fechaInicial, Dimension dim) {
        super(dim, Cuadro.redMED, Color.GRAY);
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setLayout(null);
        setToolTipText("Doble clic para ver más");

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

        this.setSeleccionado(false);

        PanelMes aux = this;
        this.addMouseListener(new MouseAdapter() {
            private static final long DOUBLE_CLICK_DELAY = 500;
            private long lastClickTime = 0;

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

            @Override
            public void mousePressed(MouseEvent e) {
                long currentClickTime = System.currentTimeMillis();

                if (e.getClickCount() == 2) {
                    if (parent != null && parent instanceof MostrarPlanif)
                        ((MostrarPlanif) parent).mostrarTabla();

                    Ventana.getInstance().mostrarPanel("panelVerPlanificaciones");
                    setSeleccionado(false);
                } else {

                    if (parent != null && parent instanceof MostrarPlanif && ((MostrarPlanif) parent).getSeleccionado() != null && !((MostrarPlanif) parent).getSeleccionado().equals(aux))
                        ((MostrarPlanif) parent).setSeleccionado(null);

                    setSeleccionado(!seleccionado);
                }

                lastClickTime = currentClickTime;
            }

        });
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
        this.setBorde(seleccionado);
        revalidate();
        repaint();
        if (parent != null && parent instanceof MostrarPlanif)
            if (seleccionado)
                ((MostrarPlanif) parent).setSeleccionado(this);
            else
                ((MostrarPlanif) parent).setSeleccionado(null);
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }
}

