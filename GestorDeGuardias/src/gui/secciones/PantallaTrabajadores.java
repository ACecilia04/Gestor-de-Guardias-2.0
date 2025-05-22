package gui.secciones;

import gui.auxiliares.CustomTablaComplex;
import gui.componentes.Buscar;
import gui.componentes.CustomCheckBox;
import gui.internosComp.PanelOpcionesTrabajador;
import gui.pantallasEmergentes.Advertencia;
import gui.pantallasEmergentes.PantallaAddVolunt;
import gui.pantallasEmergentes.PantallaLicenciasTrabajador;
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
import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;

public class PantallaTrabajadores extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JPanel contentPane;
    private CustomTablaComplex tabla;

    private final PanelOpcionesTrabajador tablaOpciones;
    private final int opcionesAncho = 300;

    private final int margen = 25;

    public PantallaTrabajadores() {
        this.setLayout(new BorderLayout());
        this.tablaOpciones = new PanelOpcionesTrabajador(new Dimension(opcionesAncho, 100));

        //Hacer que funcionen los checks
        ArrayList<CustomCheckBox> checks = new ArrayList<>();
        checks.add(tablaOpciones.getCheckBaja());
        checks.add(tablaOpciones.getCheckLicencia());
        checks.add(tablaOpciones.getCheckDisp());
        checks.add(tablaOpciones.getCheckFem());
        checks.add(tablaOpciones.getCheckMasc());

        for (CustomCheckBox e : checks) {
            e.addActionListener(e12 -> {
                ArrayList<Persona> personaAux = checkFiltros(Gestor.getInstance().getFacultad().getTrabajadores());
                revalidarTabla(personaAux);
            });
        }

        tablaOpciones.getBotonAddTrab().addActionListener(e -> Ventana.getInstance().addPantallaAddTrabajador());

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
                    if (persona != null && persona instanceof Estudiante) {
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
        buscarAux.getBoton().addActionListener(e -> {
            Persona persona = null;
            try {
                persona = buscarAux.buscar();
            } catch (EntradaInvalidaException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            ArrayList<Persona> personaUnica = new ArrayList<>();
            if (persona != null && persona instanceof Estudiante) {
                personaUnica.add(persona);
            }
            revalidarTabla(personaUnica);
        });


        tablaOpciones.getBotonBajaMini().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = tabla.getCarnet();
                LocalDate fechaAux = tablaOpciones.getCalendario().getFechaSelec();
                if (ID != null) {
                    String string = "<html><p>Estas Seguro? <br>Esta accion no se puede retroceder<br><br>Presione aceptar para continuar</p></html>";
                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Advertencia", string, "Cancelar", "Aceptar");
                    if (!advertencia.getEleccion()) {
                        try {
                            Gestor.getInstance().getFacultad().buscarPersona(ID).darBaja(fechaAux);
                        } catch (EntradaInvalidaException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        revalidarTabla();
                    }
                }
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
                tablaOpciones.mostrarPanel("panel1");
            }
        });

        tablaOpciones.getBotonLicencia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = tabla.getCarnet();
                LocalDate fechaAux = tablaOpciones.getCalendario().getFechaSelec();
                try {
                    PantallaLicenciasTrabajador pantallaLic = new PantallaLicenciasTrabajador(Gestor.getInstance().getFacultad().buscarPersona(ID));
                    revalidarTabla();
                } catch (EntradaInvalidaException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });

        tablaOpciones.getBotonVolunt().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = tabla.getCarnet();
                LocalDate fechaAux = tablaOpciones.getCalendario().getFechaSelec();
                try {
                    PantallaAddVolunt pantallaVolunt = new PantallaAddVolunt(Gestor.getInstance().getFacultad().buscarPersona(ID));
                    revalidarTabla();
                } catch (EntradaInvalidaException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });


        tablaOpciones.getBotonEliminar().setSeleccionable(false);
        tablaOpciones.getBotonBaja().setSeleccionable(false);
        tablaOpciones.getBotonVolunt().setSeleccionable(false);
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
                    tablaOpciones.getBotonVolunt().setSeleccionable(hasSelection);
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
        this.tabla.revalidarTabla(checkFiltros(Gestor.getInstance().getFacultad().getTrabajadores()));

        revalidate();
        repaint();
    }

    public ArrayList<Persona> checkFiltros(ArrayList<Persona> personas) {
        ArrayList<Persona> aux = new ArrayList<>();

        for (Persona e : personas) {
            boolean selec = tablaOpciones.getCheckDisp().isSelected() || e.getDisponibilidadParaFecha(LocalDate.now()) != Disponibilidad.DISPONIBLE;
            if (!tablaOpciones.getCheckBaja().isSelected() && e.getDisponibilidadParaFecha(LocalDate.now()) == Disponibilidad.BAJA && selec) {
                selec = false;
            }
            if (!tablaOpciones.getCheckMasc().isSelected() &&e.getSexo().equalsIgnoreCase("masculino")  && selec) {
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