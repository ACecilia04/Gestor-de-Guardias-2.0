package gui.pantallasEmergentes;

import gui.auxiliares.Actualizable;
import gui.auxiliares.CustomTablaComplex;
import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.Buscar;
import gui.internosComp.PanelTurno;
import gui.secciones.Ventana;
import model.Persona;
import services.Gestor;
import services.ServicesLocator;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PantallaSelecPersona extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JPanel contentPane;
    private final Paleta paleta = new Paleta();

    private final JPanel panel1;
    private final JPanel panel2;
    private final JPanel panel3;
    private final JPanel panel4;

    private final Dimension dim = new Dimension(1100, 700);
    private final Dimension dimBoton = new Dimension(120, 40);
    private final Dimension dimOpc = new Dimension(280, 100);

    private final CustomTablaComplex tabla;
    private final Font fuente = new Font("Arial", Font.PLAIN, 15);
    private final ArrayList<Persona> personas;
    private final Boton boton1;
    private final ArrayList<Actualizable> actualizables;
    private Buscar buscar;
    private String IDselec;

    public PantallaSelecPersona(final CustomTablaComplex tabla, final ArrayList<Persona> personas, final PanelTurno turno) {
        super(Ventana.getInstance(), "JDialog", true);

        this.personas = personas;

        this.tabla = tabla;

        actualizables = new ArrayList<>();
        Ventana ventana = Ventana.getInstance();

        setSize(dim);

        int x = (ventana.getSize().width - this.getWidth()) / 2;
        int y = (ventana.getSize().height - this.getHeight()) / 2;
        setLocation(x, y);

        contentPane = new JPanel();
        this.setContentPane(contentPane);

        contentPane.setBackground(paleta.getColorFondoTabla());
        contentPane.setLayout(new BorderLayout(0, 0));

        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        panel1.setBackground(Color.WHITE);

        boton1 = new Boton("Seleccionar") {
            private static final long serialVersionUID = 1L;

            public void actualizar() {
                if (tabla.getColumnaSelec() != -1) {

                    boton1.setColorLetra(Color.WHITE);
                    boton1.setColorLetraPres(Color.WHITE);
                    boton1.setColorFondo(paleta.getColorCaracteristico());
                    boton1.setSeleccionable(true);

                } else {

                    boton1.setColorLetra(Color.GRAY.darker());
                    boton1.setColorFondo(Color.LIGHT_GRAY);
                    boton1.setColorPresionado(Color.LIGHT_GRAY);
                    boton1.setSeleccionable(false);


                }
            }
        };
        boton1.setSeleccionable(false);
        actualizables.add(boton1);
        boton1.setColorLetra(Color.GRAY.darker());
        boton1.setColorFondo(Color.LIGHT_GRAY);
        boton1.setColorPresionado(Color.LIGHT_GRAY);

        boton1.setNuevoSize(dimBoton);


        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (boton1.isSeleccionable()) {
                    if (tabla.getColumnaSelec() != -1) {
                        IDselec = (String) tabla.getTabla().getValueAt(tabla.getColumnaSelec(), 0);

                        try {
                            //Gestor.getInstance().asignarPersona(dia,, persona);********************************************************************************************************************
                            Persona personaAux = ServicesLocator.getInstance().getPersonaServices().getPersonaByCi(IDselec);
                            Gestor.getInstance().asignarPersona(turno.getFecha(), turno.getTurno().getHorario(), personaAux);
                            turno.actualizar();
                        } catch (EntradaInvalidaException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (MultiplesErroresException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        dispose();
                    }
                }
            }
        });


        Boton boton2 = new Boton("Cancelar");
        boton2.setBordeado(true);
        boton2.setNuevoSize(dimBoton);
        boton2.setColorPresionado(paleta.getColorCaracteristico().darker());

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel1.add(boton2);
        panel1.add(boton1);

        //Panel2
        panel2 = new JPanel(null);
        panel2.setPreferredSize(dimOpc);
        panel2.setBackground(contentPane.getBackground());

        inicializarPanel2();


        panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());

        panel3.add(tabla, BorderLayout.CENTER);

        panel4 = new JPanel();
        panel4.setLayout(new BorderLayout());

        panel4.add(panel1, BorderLayout.SOUTH);
        panel4.add(panel2, BorderLayout.CENTER);
        contentPane.add(panel4, BorderLayout.EAST);
        contentPane.add(panel3, BorderLayout.CENTER);


        final Buscar buscarAux = buscar;
        buscarAux.getTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Check if the pressed key is Enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Persona persona = null;
                    try {
                        persona = buscarAux.buscar();
                    } catch (EntradaInvalidaException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    if (persona != null && persona.getTipo().equals("estudiante")) {
                        revalidarTabla(persona);
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {

            }
        });

        buscarAux.getBoton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Persona persona = null;
                try {
                    persona = buscarAux.buscar();
                } catch (EntradaInvalidaException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                ArrayList<Persona> personaUnica = new ArrayList<>();
                if (persona != null && persona.getTipo().equals("estudiante")) {
                    personaUnica.add(persona);
                }
                revalidarTabla();
            }
        });

        tabla.setActualizables(actualizables);

        //Bordes
        Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, paleta.getColorBorde());
        Border border3 = BorderFactory.createMatteBorder(0, 5, 0, 0, paleta.getColorBorde());
        Border border2 = BorderFactory.createMatteBorder(15, 10, 85, 10, Color.WHITE);
        //		Border border4 = BorderFactory.createMatteBorder(5, 0, 0, 0,paleta.getColorBorde());
        //		Border bordeMargen = BorderFactory.createEmptyBorder(0, 0, 5, 10);
        //		Border margenDoubleBorder = BorderFactory.createCompoundBorder( border4, bordeMargen);


        revalidarTabla();
        panel3.setBorder(border2);
        panel4.setBorder(border3);
        contentPane.setBorder(border);


        setVisible(true);

    }


    private void inicializarPanel2() {
        buscar = new Buscar("Buscar ID", 11);
        buscar.soloNumeros(true);
        buscar.setLocation(12, 50);

        panel2.add(buscar);
    }

    public void revalidarTabla() {
        tabla.revalidarTabla(getPersonas());

    }

    public void revalidarTabla(Persona persona) {
        ArrayList<Persona> aux = new ArrayList<>();
        aux.add(persona);
        tabla.revalidarTabla(aux);
    }


    public ArrayList<Persona> getPersonas() {
        return personas;
    }
}
