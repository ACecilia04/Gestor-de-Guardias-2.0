package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelOpcionesEstudiante extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Paleta paleta = new Paleta();

    private final JPanel superior;
    private final JPanel panelFiltros;
    private final JPanel panelAddEstudiante;
    private final JPanel panelBotones;
    private final JPanel panelCentral;
    private final JPanel panelVacio;
    private final JPanel panelBaja;

    private final Dimension dimBoton = new Dimension(180, 35);

    private final CustomCheckBox checkDisp;
    //    private final CustomCheckBox checkLicencia;
    private final CustomCheckBox checkBaja;

    private final CustomCheckBox checkMasc;
    private final CustomCheckBox checkFem;
    private final Boton botonEliminar;
    private final Boton botonExport;
    private final Boton botonBaja;
    private final String iconoVolver = "/iconos/FlechaAtras.png";
    private final Buscar buscar;
    private final Boton botonAddEst;
    private final int separacion = 10;
    private final int x = 20;
    private final Font fuente = new Font("Arial", Font.PLAIN, 14);
    //    private final Font fuente2 = new Font("Arial", Font.BOLD, 17);
    private Boton botonBajaMini;
    private JPanel panelCalendar;
    private CustomCalendar calendario;
    private int y = separacion + 10;

    public PanelOpcionesEstudiante(Dimension dim) {
        setPreferredSize(dim);
        setSize(dim);
        setBackground(paleta.getColorFondoTabla());
        setLayout(new BorderLayout());

        //Superior
        superior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        superior.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        superior.setBackground(getBackground());

        //Panel Filtros
        {
            panelFiltros = new JPanel(null);

            Etiqueta filtrar = new Etiqueta("Filtrar");
            filtrar.setBold(true);
            filtrar.setLocation(x, y);

            Etiqueta disponibilidad = new Etiqueta("Disponibilidad");
            disponibilidad.setNuevoSizeLetra(16);

            y += filtrar.getSize().height + separacion;
            disponibilidad.setLocation(x, y);

            Etiqueta sexoEt = new Etiqueta("Sexo");
            sexoEt.setNuevoSizeLetra(16);

            int x2 = x + disponibilidad.getSize().width + separacion * 3;
            sexoEt.setLocation(x2, y);

            y += disponibilidad.getSize().height + separacion;

            checkDisp = new CustomCheckBox("Disponible");
            checkBaja = new CustomCheckBox("Baja");

            checkMasc = new CustomCheckBox("Masculino");
            checkFem = new CustomCheckBox("Femenino");


            checkDisp.setSelected(true);
            checkMasc.setSelected(true);
            checkFem.setSelected(true);

            checkDisp.setNuevaFuente(fuente);
            checkBaja.setNuevaFuente(fuente);
            checkMasc.setNuevaFuente(fuente);
            checkFem.setNuevaFuente(fuente);

            checkDisp.setLocation(x, y);
            checkMasc.setLocation(x2, y);
            y += checkDisp.getPreferredSize().height + separacion;
            checkBaja.setLocation(x, y);
            checkFem.setLocation(x2, y);
            y += checkBaja.getPreferredSize().height + separacion;
            y += separacion;

//        disponibilidad.setNuevoSizeLetra(18);


            panelFiltros.add(filtrar);
            panelFiltros.add(disponibilidad);
            panelFiltros.add(sexoEt);


            panelFiltros.add(checkDisp);
            panelFiltros.add(checkBaja);
            panelFiltros.add(checkMasc);
            panelFiltros.add(checkFem);


            panelFiltros.setPreferredSize(new Dimension(this.getSize().width, y));
        }

        int xBoton = (this.getSize().width - dimBoton.width) / 2;
        int sepBotones = 20;

        //Panel Botones
        {
            panelBotones = new JPanel();
            panelBotones.setLayout(null);

            botonBaja = new Boton("Dar Baja");
            botonExport = new Boton("Exportar PDF");
            botonEliminar = new Boton("Eliminar Estudiante");

            ArrayList<Boton> botones = new ArrayList<>();
            botones.add(botonBaja);
            botones.add(botonExport);
            botones.add(botonEliminar);

            int y2 = sepBotones;

            buscar = new Buscar("Buscar ID", 11);
            buscar.soloNumeros(true);
            buscar.setLocation(x, y2);
            panelBotones.add(buscar);


            y2 += separacion * 4 + buscar.getSize().height;
            for (Boton e : botones) {
                e.setNuevoSize(dimBoton);
                e.setLocation(xBoton, y2);
                e.setColorFondo(paleta.getColorCasillaTabla());
                e.setFuente(fuente);
                e.setColorPresionado(paleta.getColorCaracteristico());
                e.setColorLetraPres(Color.WHITE);
                y2 += dimBoton.height + sepBotones;
                panelBotones.add(e);
            }

            botonBaja.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (calendario != null) {
                        panelCalendar.remove(calendario);
                    }
                    calendario = new CustomCalendar(new Dimension(getWidth() - 30, getWidth() + 20));
                    panelCalendar.add(calendario);
                    calendario.mostrarMesActual();

                    mostrarPanel("panelBaja");
                }
            });


            panelCentral = new JPanel();
            panelCentral.setLayout(new BorderLayout());

            panelBaja = new JPanel();
            panelBaja.setBackground(getBackground());
            iniciarPanelBaja();
        }
        //Panel4 Guardar
        panelAddEstudiante = new JPanel(null);
        panelAddEstudiante.setBackground(getBackground());
        panelAddEstudiante.setPreferredSize(new Dimension(this.getPreferredSize().width, 100));
        botonAddEst = new Boton("Añadir Estudiante");

        botonAddEst.setNuevoSize(new Dimension(140, 40));
        botonAddEst.setBordeado(true);
        botonAddEst.setColorPresionado(paleta.getColorCaracteristico());

        int x = (panelAddEstudiante.getPreferredSize().width - botonAddEst.getSize().width) / 2;
        int y = (panelAddEstudiante.getPreferredSize().height - botonAddEst.getSize().height) / 2;
        botonAddEst.setLocation(x, y);
        panelAddEstudiante.add(botonAddEst);

        JPanel panelComun = new JPanel(new BorderLayout());
        panelComun.add(panelBotones, BorderLayout.CENTER);
        panelComun.add(panelAddEstudiante, BorderLayout.SOUTH);

        panelVacio = new JPanel(new CardLayout());
        panelVacio.add(panelComun, "panelFiltros");
        panelVacio.add(panelBaja, "panelBaja");

        panelVacio.setBackground(getBackground());

        panelBotones.setBackground(getBackground());
        panelFiltros.setBackground(getBackground());

        panelCentral.add(panelFiltros, BorderLayout.NORTH);
        panelCentral.add(panelVacio, BorderLayout.CENTER);


        //add
        add(superior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        //Bordes
        Border border = BorderFactory.createMatteBorder(0, 3, 0, 0, paleta.getColorBorde());
        Border bordeMargen = BorderFactory.createEmptyBorder(10, 15, 0, 0);
        Border margenDoubleBorder = BorderFactory.createCompoundBorder(border, bordeMargen);

        panelCentral.setBorder(border);
        superior.setBorder(margenDoubleBorder);
        setBorder(border);
    }


    public CustomCheckBox getCheckDisp() {
        return checkDisp;
    }

    public CustomCheckBox getCheckBaja() {
        return checkBaja;
    }

    public CustomCheckBox getCheckMasc() {
        return checkMasc;
    }

    public CustomCheckBox getCheckFem() {
        return checkFem;
    }

    public Buscar getBuscar() {
        return buscar;
    }


    public Boton getBotonAddEstud() {
        return botonAddEst;
    }

    public void iniciarPanelBaja() {
        panelBaja.setLayout(new BorderLayout());
        panelBaja.setSize(this.getSize().width, 200);

        //Panel Superior Titulo
        Cuadro panelSup = new Cuadro(new Dimension(this.getWidth(), 30), 0, paleta.getColorCasillaTabla());
        panelSup.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        Boton atras = new Boton();
        atras.addIcono(iconoVolver);
        atras.setColorIcono(Color.BLUE);
        atras.setSelectLetra(true);

        atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel("panelFiltros");
            }
        });

        Etiqueta etiquetaBaja = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Seleccionar Fecha:");

        panelSup.add(atras);
        panelSup.add(etiquetaBaja);

        //Contenido calendario
        panelCalendar = new JPanel(null);
        panelCalendar.setBackground(getBackground());


        //PanelInferior
        JPanel panelBoton = new JPanel(null);
        panelBoton.setBackground(getBackground());
        panelBoton.setPreferredSize(new Dimension(this.getPreferredSize().width, 80));
        botonBajaMini = new Boton("Seleccionar Fecha");

        botonBajaMini.setNuevoSize(new Dimension(140, 40));
        botonBajaMini.setColorFondo(paleta.getColorCasillaTabla());
        botonBajaMini.setColorPresionado(paleta.getColorCaracteristico());
        botonBajaMini.setColorLetra(paleta.getColorLetraMenu());
        botonBajaMini.setColorLetraPres(Color.WHITE);


        int x = (panelBoton.getPreferredSize().width - botonBajaMini.getSize().width) / 2;
        int y = (panelBoton.getPreferredSize().height - botonBajaMini.getSize().height) / 2;
        botonBajaMini.setLocation(x, y);
        panelBoton.add(botonBajaMini);

        //Add paneles
        panelBaja.add(panelSup, BorderLayout.NORTH);
        panelBaja.add(panelCalendar, BorderLayout.CENTER);
        panelBaja.add(panelBoton, BorderLayout.SOUTH);

    }

    public void mostrarPanel(String nombrePanel) {
        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        cardLayout.show(panelVacio, nombrePanel);
        repaint();
        revalidate();
    }


    public Boton getBotonEliminar() {
        return botonEliminar;
    }

    public Boton getBotonBajaMini() {
        return botonBajaMini;
    }

    public CustomCalendar getCalendario() {
        return calendario;
    }

    public Boton getBotonExport() {
        return botonExport;
    }

    public Boton getBotonBaja() {
        return botonBaja;
    }


}
