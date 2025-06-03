package gui.internosComp;

import gui.auxiliares.Actualizable;
import gui.auxiliares.IsTabla;
import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.componentes.CustomPopupMenu;
import gui.componentes.Etiqueta;
import model.DiaGuardia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;

import static gui.auxiliares.ConvertidorFecha.traducDiaSemana;

public abstract class PanelDiaBase extends Cuadro implements Actualizable {
    protected static final long serialVersionUID = 1L;
    //Casillas
    protected static int casillaLargo = 50;
    protected static int largoComun = 54;
//    protected static int largoDoble = 80;
//    protected static int largoTriple = 110;
    protected Paleta paleta = new Paleta();
    protected DiaGuardia dia;
    protected Cuadro myself;
    //Seleccionar
    protected boolean seleccionado = false;
    protected boolean seleccionadoParcial = false;
    protected Color colorFondo;
    protected Color colorSelec;
    protected Color colorPasada;
    protected Color colorLetra;
    protected boolean entrado = false;
    protected Etiqueta fecha;
    protected int anchoFecha = 128;
    protected int margen = 10;
    protected IsTabla tabla;

    protected ArrayList<PanelTurno> panelesTurno;

    //Turnos
    JPanel panelTurnos;
    JPanel panelFecha;

    String fechaNumero;
    String fechaSemana;

    public PanelDiaBase(DiaGuardia dia, Color color, IsTabla tabla) {
        super(new Dimension(casillaLargo, largoComun), Cuadro.redBAJA, color);
        this.setColorBorde(paleta.getColorCaracteristico());
        this.tabla = tabla;
        this.dia = dia;
        this.colorFondo = color;
        myself = this;

        inicializarTabla();
        inicializarTurnos();
        addMouseListener(popupTurno());


        this.setPreferredSize(this.getSize());
        this.setMaximumSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setLayout(null);
        myself.setAgrandarCuandoBorde(true);

        panelFecha.setSize(new Dimension(anchoFecha - margen, this.getHeight() - 10));
        panelFecha.setLocation(margen, 5);
        panelFecha.setLayout(null);


        LocalDate auxFecha = dia.getFecha();
        fechaNumero = Integer.toString(dia.getFecha().getDayOfMonth());
        fechaSemana = traducDiaSemana(auxFecha);

        String textoHtml = "<html><p><b><font face='Arial' size='5'>"
                + fechaNumero + "</font></b> <font face='Arial' size='4'>"
                + fechaSemana + "</font></p></html>";

        fecha = new Etiqueta(paleta.getColorLetraMenu(), textoHtml);


        fecha.setLocation(10, (panelFecha.getSize().height - fecha.getSize().height) / 2);
        panelFecha.add(fecha);

        panelTurnos.setSize(new Dimension(this.getWidth() - anchoFecha - margen, this.getHeight() - 10));
        panelTurnos.setLocation(anchoFecha, 5);
        panelTurnos.setBackground(getBackground());

        add(panelFecha);
        add(panelTurnos);

    }

    public static void setCasillaLargo(int casillaLargo) {
        PanelDia.casillaLargo = casillaLargo;
    }

    public void inicializarTabla() {
        colorPasada = colorFondo.darker();
        colorSelec = paleta.getColorCaracteristico();
        colorLetra = paleta.getColorLetraMenu();
        panelesTurno = new ArrayList<PanelTurno>();

        seleccionado = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Cambiar el borde al hacer clic
                if (!seleccionado) {
                    myself.setColorFondo(colorSelec);
                    setSeleccionado(true);


                } else {
                    if (entrado) {
                        myself.setColorFondo(colorPasada);
                    } else {
                        myself.setColorFondo(colorFondo);
                    }
                    setSeleccionado(false);
                }
                actualizar();
                revalidate();
                repaint();
            }

            public void mouseEntered(MouseEvent e) {
                entrado = true;
                cambiarColor(colorPasada, colorLetra);
                cambiarColorMini(paleta.getColorCaracteristico(), Color.WHITE, colorPasada, colorLetra);
                revalidate();
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                entrado = false;
                cambiarColor(colorFondo, colorLetra);
                cambiarColorMini(paleta.getColorCaracteristico(), Color.WHITE, colorFondo, colorLetra);

                revalidate();
                repaint();
            }
        });


        panelFecha = new JPanel();
        panelFecha.setBackground(getBackground());
        panelTurnos = new JPanel();
        panelTurnos.setLayout(new BoxLayout(panelTurnos, BoxLayout.Y_AXIS));
    }

    public void inicializarTurnos() {
        int cantTurnos = dia.getTurnos().size();
        iniciarCasilla();
        if (cantTurnos == 1) {
            Component nuevo = panelTurnos.getComponent(0);
            MouseListener[] listeners = this.getMouseListeners();

            for (int i = 0; i < nuevo.getMouseListeners().length; i++) {
                nuevo.removeMouseListener(nuevo.getMouseListeners()[i]);
            }

            for (MouseListener listener : listeners) {
                nuevo.addMouseListener(listener);
            }
            nuevo.setLocation(0, (this.getHeight() - 10 - nuevo.getHeight()) / 2);

        }

    }

    protected abstract void iniciarCasilla();

    protected MouseAdapter popupTurno() {
        MouseAdapter nuevo = new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    mostrarPopup(evt);
                }
            }

            public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    mostrarPopup(evt);
                }
            }
        };

        return nuevo;
    }

    protected int getAncho() {
        int ancho = largoComun;
      for (int i = 0; i < dia.getTurnos().size(); i++)
            ancho = (int) (ancho * 1.5);

        return ancho;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        if (panelesTurno.size() > 1) {
            for (PanelTurno e : panelesTurno) {
                e.setSeleccionado(seleccionado);
            }
            cambiarColorMini(paleta.getColorCaracteristico(), Color.WHITE, colorPasada, colorLetra);

        }

        this.seleccionado = seleccionado;
    }

    private void mostrarPopup(MouseEvent evt) {
        CustomPopupMenu popup = new CustomPopupMenu();

        JMenuItem itemA = new JMenuItem("D");
        JMenuItem itemB = new JMenuItem("E");
        JMenuItem itemC = new JMenuItem("F");

        popup.addItem(itemA);
        popup.addItem(itemB);
        popup.addItem(itemC);

        popup.show(evt.getComponent(), evt.getX(), evt.getY());
    }

    public void cambiarColor(Color color, Color colorLetra) {

        panelTurnos.setBackground(color);
        panelFecha.setBackground(color);
        fecha.setBackground(color);
        fecha.setForeground(colorLetra);

        if (seleccionado) {
            this.setColorFondo(colorSelec);

        } else {
            this.setColorFondo(color);
        }
    }

    public void cambiarColorMini(Color colorSelec, Color colorLetraSelec, Color color, Color colorLetra) {
        for (PanelTurno e : panelesTurno) {
            if (e instanceof PanelTurno) {
                if (e.isSeleccionado()) {
                    e.cambiar(colorSelec, colorLetraSelec);
                } else {
                    e.cambiar(color, colorLetra);
                }

            }
        }
    }

    public void actualizar() {
        boolean act = false;
        for (PanelTurno e : panelesTurno) {
            if (e.isSeleccionado()) {
                act = true;
                break;
            }
        }
        this.seleccionadoParcial = act;
        tabla.actualizar();
    }

    public String getFechaNumero() {
        return fechaNumero;
    }

    public void setFechaNumero(String fechaNumero) {
        this.fechaNumero = fechaNumero;
    }

    public String getFechaSemana() {
        return fechaSemana;
    }

    public void setFechaSemana(String fechaSemana) {
        this.fechaSemana = fechaSemana;
    }

    public ArrayList<PanelTurno> getPanelesTurno() {
        return panelesTurno;
    }

    public void setPanelesTurno(ArrayList<PanelTurno> panelesTurno) {
        this.panelesTurno = panelesTurno;
    }

    public boolean isSeleccionadoParcial() {
        return seleccionadoParcial;
    }

    public void setSeleccionadoParcial(boolean seleccionadoParcial) {
        this.seleccionadoParcial = seleccionadoParcial;
    }

    public DiaGuardia getDia() {
        return dia;
    }

    public void actualizarTabla() {
        for (PanelTurno e : panelesTurno) {
            e.actualizar();
        }
    }

}