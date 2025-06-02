package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelOpcionesTrabajador extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Paleta paleta = new Paleta();

    private final JPanel superior;
    private final JPanel panelFiltros;
    private final JPanel panelAddTrabajador;
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
//    private final Boton botonVolunt;
    private final Boton botonEliminar;
    private final Boton botonExport;
    private final Boton botonBaja;
    private final String iconoVolver = "/iconos/FlechaAtras.png";
    private final Buscar buscar;
    private final Boton botonAddTrab;
    private final int separacion = 10;
    private final int x = 20;
    private final Font fuente = new Font("Arial", Font.PLAIN, 14);
//    private final Font fuente2 = new Font("Arial", Font.BOLD, 17);
    private CustomCalendar calendario;
    private Boton botonBajaMini;
    private int y = separacion + 10;

    public PanelOpcionesTrabajador(Dimension dim) {
        setPreferredSize(dim);
        setSize(dim);
        setBackground(paleta.getColorFondoTabla());
        setLayout(new BorderLayout());

        //Superior
        superior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        superior.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        superior.setBackground(getBackground());

        //panel1
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

        checkDisp = new CustomCheckBox("Disponible    ");
        checkBaja = new CustomCheckBox("Baja     ");

        checkMasc = new CustomCheckBox("Masculino     ");
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


//        disponibilidad.setNuevoSizeLetra(18);

        panelFiltros.add(filtrar);
        panelFiltros.add(disponibilidad);
        panelFiltros.add(sexoEt);

        panelFiltros.add(checkDisp);
        panelFiltros.add(checkBaja);
        panelFiltros.add(checkMasc);
        panelFiltros.add(checkFem);

        panelFiltros.setPreferredSize(new Dimension(this.getSize().width, y));

        int xBoton = (this.getSize().width - dimBoton.width) / 2;
        int sepBotones = 20;

        //Nuevos botones
        panelBotones = new JPanel();
        panelBotones.setLayout(null);

        botonBaja = new Boton("Dar Baja     ");
        botonExport = new Boton("Exportar PDF    ");
        botonEliminar = new Boton("Eliminar Trabajador    ");

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
                calendario.mostrarMesActual();
                mostrarPanel("panel2");
            }
        });


        panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());

        panelBaja = new JPanel();
        panelBaja.setBackground(getBackground());
        inciarPanelBaja();


        //Panel4 Guardar
        panelAddTrabajador = new JPanel(null);
        panelAddTrabajador.setBackground(getBackground());
        panelAddTrabajador.setPreferredSize(new Dimension(this.getPreferredSize().width, 100));
        botonAddTrab = new Boton("Añadir Trabajador");

        botonAddTrab.setNuevoSize(new Dimension(140, 40));
        botonAddTrab.setBordeado(true);
        botonAddTrab.setColorPresionado(paleta.getColorCaracteristico());

        int x = (panelAddTrabajador.getPreferredSize().width - botonAddTrab.getSize().width) / 2;
        int y = (panelAddTrabajador.getPreferredSize().height - botonAddTrab.getSize().height) / 2;
        botonAddTrab.setLocation(x, y);
        panelAddTrabajador.add(botonAddTrab);

        JPanel panelComun = new JPanel(new BorderLayout());
        panelComun.add(panelBotones, BorderLayout.CENTER);
        panelComun.add(panelAddTrabajador, BorderLayout.SOUTH);

        panelVacio = new JPanel(new CardLayout());
        panelVacio.add(panelComun, "panelFiltros");
        panelVacio.add(panelBaja, "panel2");

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

    public Boton getBotonAddTrab() {
        return botonAddTrab;
    }

    public void inciarPanelBaja() {
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
        JPanel panelCalendar = new JPanel(null);
        panelCalendar.setBackground(getBackground());

        calendario = new CustomCalendar(new Dimension(this.getWidth() - 30, this.getWidth() + 20));
        calendario.setLocation(10, 10);
        panelCalendar.add(calendario);


        //PanelInferior
        JPanel panelBoton = new JPanel(null);
        panelBoton.setBackground(getBackground());
        panelBoton.setPreferredSize(new Dimension(this.getPreferredSize().width, 80));
        botonBajaMini = new Boton("Selec Fecha");

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

//    public Boton getBotonVolunt() {
//        return botonVolunt;
//    }
}
