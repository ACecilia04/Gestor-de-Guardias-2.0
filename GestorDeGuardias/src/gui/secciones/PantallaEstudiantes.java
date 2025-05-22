package gui.secciones;

import gui.auxiliares.CustomTablaComplex;
import gui.componentes.Buscar;
import gui.componentes.CustomCheckBox;
import gui.internosComp.PanelOpcionesEstudiante;
import gui.pantallasEmergentes.Advertencia;
import gui.pantallasEmergentes.PantallaLicenciasEstudiante;
import logica.Gestor;
import logica.excepciones.EntradaInvalidaException;
import rdb.entity.Disponibilidad;
import rdb.entity.Persona;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class PantallaEstudiantes extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JPanel contentPane;
    private CustomTablaComplex tabla;

    private final PanelOpcionesEstudiante tablaOpciones;

    private final int opcionesAncho = 300;

    private final int margen = 25;

    public PantallaEstudiantes() {
        this.setLayout(new BorderLayout());
        this.tablaOpciones = new PanelOpcionesEstudiante(new Dimension(opcionesAncho, 100));

        ArrayList<CustomCheckBox> checks = new ArrayList<>();
        checks.add(tablaOpciones.getCheckBaja());
        checks.add(tablaOpciones.getCheckLicencia());
        checks.add(tablaOpciones.getCheckDisp());
        checks.add(tablaOpciones.getCheckFem());
        checks.add(tablaOpciones.getCheckMasc());


        for (CustomCheckBox e : checks) {
            final CustomCheckBox aux = e;
            aux.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (aux.isSelected()) {
                        ArrayList<Persona> personaAux = checkFiltros(Gestor.getInstance().getFacultad().getEstudiantes());
                        revalidarTabla(personaAux);
                    } else {
                        ArrayList<Persona> personaAux = checkFiltros(Gestor.getInstance().getFacultad().getEstudiantes());
                        revalidarTabla(personaAux);
                    }
                }
            });
        }

        tablaOpciones.getBotonAddEstud().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ventana.getInstance().addPantallaAddEstudiante();
            }
        });

        final Buscar buscarAux = tablaOpciones.getBuscar();
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
                    ArrayList<Persona> personaUnica = new ArrayList<>();
                    if (persona != null && persona.getTipo().equalsIgnoreCase("estudiante")) {
                        personaUnica.add(persona);
                    }
                    revalidarTabla(personaUnica);
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
                if (persona != null && persona.getTipo().equalsIgnoreCase("estudiante")) {
                    personaUnica.add(persona);
                }
                revalidarTabla(personaUnica);
            }
        });

        tablaOpciones.getBotonEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = tabla.getCarnet();
                if (ID != null) {
                    try {
                        String string = "<html><p>Estas a punto de eliminar a un Estudiante del <br>registro. Esta accion no se puede retroceder<br><br>Presione aceptar para continuar</p></html>";
                        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Advertencia", string, "Cancelar", "Aceptar");
                        if (!advertencia.getEleccion()) {
                            Gestor.getInstance().getFacultad().eliminarPersona(ID);
                            revalidarTabla();
                        }


                    } catch (EntradaInvalidaException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });


        tablaOpciones.getBotonBajaMini().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = tabla.getCarnet();
                repaint();
                revalidate();
                LocalDate fechaAux = tablaOpciones.getCalendario().getFechaSelec();
                if (ID != null) {
                    String string = "<html><p>Estas Seguro? <br>Esta accion no se puede retroceder<br><br>Presione aceptar para continuar</p></html>";
                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Advertencia", string, "Cancelar", "Aceptar");
                    if (!advertencia.getEleccion()) {
                        try {
                            System.out.println(fechaAux);
                            Gestor.getInstance().getFacultad().buscarPersona(ID).darBaja(fechaAux);
                            Advertencia advertencia2 = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Baja Exitosa", "Baja Exitosa", "Aceptar");

                        } catch (EntradaInvalidaException e1) {
                            String string2 = "<html><p style='text-align: center;'> ERROR <br><br><br>" + e1.getMessage() + "</p></html>";
                            Advertencia advertencia3 = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", string, "Aceptar");
                        }
                        revalidarTabla();
                    }
                }
                tablaOpciones.mostrarPanel("panel1");
            }
        });

        tablaOpciones.getBotonLicencia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = tabla.getCarnet();
                try {
                    PantallaLicenciasEstudiante pantallaLic = new PantallaLicenciasEstudiante(Gestor.getInstance().getFacultad().buscarPersona(ID));
                    revalidarTabla();
                } catch (EntradaInvalidaException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });


        tablaOpciones.getBotonEliminar().setSeleccionable(false);
        tablaOpciones.getBotonBaja().setSeleccionable(false);
        tablaOpciones.getBotonLicencia().setSeleccionable(false);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        add(this.tablaOpciones, BorderLayout.EAST);
        add(contentPane, BorderLayout.CENTER);
    }

    public void addTabla(final CustomTablaComplex tabla) {
        this.tabla = tabla;
        Border border = BorderFactory.createMatteBorder(margen, margen, margen, margen, Color.WHITE);
        tabla.setBorder(border);
        contentPane.add(tabla, BorderLayout.CENTER);

        tabla.getTabla().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Check if the event is not adjusting (to avoid duplicate calls)
                if (!e.getValueIsAdjusting()) {
                    boolean hasSelection = tabla.getTabla().getSelectedRowCount() > 0;
                    tablaOpciones.getBotonEliminar().setSeleccionable(hasSelection);
                    tablaOpciones.getBotonBaja().setSeleccionable(hasSelection);
                    tablaOpciones.getBotonLicencia().setSeleccionable(hasSelection);
                    revalidate();
                    repaint();
                }
            }
        });

        contentPane.revalidate();
        contentPane.repaint();
    }


    public void revalidarTabla(ArrayList<Persona> personas) {
        this.tabla.revalidarTabla(personas);

        revalidate();
        repaint();
    }

    public void revalidarTabla() {
        this.tabla.revalidarTabla(checkFiltros(Gestor.getInstance().getFacultad().getEstudiantes()));

        revalidate();
        repaint();
    }


    public ArrayList<Persona> checkFiltros(ArrayList<Persona> personas) {
        ArrayList<Persona> aux = new ArrayList<>();

        for (Persona e : personas) {
            boolean selec = true;
            if (!tablaOpciones.getCheckDisp().isSelected() && e.getDisponibilidadParaFecha(LocalDate.now()) == Disponibilidad.DISPONIBLE && selec) {
                selec = false;
            }
            if (!tablaOpciones.getCheckBaja().isSelected() && e.getDisponibilidadParaFecha(LocalDate.now()) == Disponibilidad.BAJA && selec) {
                selec = false;
            }
            if (!tablaOpciones.getCheckMasc().isSelected() && e.getSexo().equalsIgnoreCase("masculino")&& selec) {
                selec = false;
            }
            if (!tablaOpciones.getCheckFem().isSelected() && e.getSexo().equalsIgnoreCase("femenino")  && selec) {
                selec = false;
            }


            if (selec) {
                aux.add(e);
            }
        }

        return aux;
    }


}