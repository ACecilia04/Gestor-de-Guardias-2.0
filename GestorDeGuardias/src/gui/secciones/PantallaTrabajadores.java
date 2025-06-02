package gui.secciones;

import gui.auxiliares.CustomTablaComplex;
import gui.componentes.Buscar;
import gui.componentes.CustomCheckBox;
import gui.internosComp.PanelOpcionesTrabajador;
import gui.pantallasEmergentes.Advertencia;
import model.Persona;
import model.TipoPersona;
import services.ReporteServices;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

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
    private final PanelOpcionesTrabajador tablaOpciones;
    private final int opcionesAncho = 300;
    private final int margen = 25;
    private CustomTablaComplex tabla;

    public PantallaTrabajadores() {
        this.setLayout(new BorderLayout());
        this.tablaOpciones = new PanelOpcionesTrabajador(new Dimension(opcionesAncho, 100));

        //Hacer que funcionen los checks
        ArrayList<CustomCheckBox> checks = new ArrayList<>();
        checks.add(tablaOpciones.getCheckBaja());
        checks.add(tablaOpciones.getCheckDisp());
        checks.add(tablaOpciones.getCheckFem());
        checks.add(tablaOpciones.getCheckMasc());

        for (CustomCheckBox e : checks) {
            e.addActionListener(e12 -> {
                ArrayList<Persona> personaAux = checkFiltros((ArrayList<Persona>)ServicesLocator.getInstance().getPersonaServices().getPersonasByTipo(new TipoPersona("Trabajador")));
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
                    if (persona != null && persona.getTipo().equals("Trabajador")) {
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
            if (persona != null && persona.getTipo().equals("Trabajador")) {
                personaUnica.add(persona);
            }
            revalidarTabla(personaUnica);
        });


        tablaOpciones.getBotonExport().addActionListener(e -> {
            // 1. Obtener los trabajadores filtrados
            ArrayList<Persona> filtrados = checkFiltros(
                    (ArrayList<Persona>)ServicesLocator.getInstance().getPersonaServices().getPersonasByTipo(new TipoPersona("Trabajador"))
            );
            if (filtrados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay trabajadores para exportar con los filtros actuales.", "Sin datos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Dialogo para elegir donde guardar
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Guardar reporte PDF");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF", "pdf"));
            int seleccion = chooser.showSaveDialog(this);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                String title = chooser.getName();
                if (!path.toLowerCase().endsWith(".pdf")) path += ".pdf";

                // 3. Llamar al servicio de reporte
                new ReporteServices().generarReporteTodasLasPersonas(filtrados, path,title);

                JOptionPane.showMessageDialog(this, "PDF generado exitosamente:\n" + path, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
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
                            ServicesLocator.getInstance().getPersonaServices().darBaja(ID, fechaAux);
                        } catch (MultiplesErroresException | SqlServerCustomException ex) {
                            throw new RuntimeException(ex);
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
                    String string = "<html><p>Estas a punto de eliminar a un Trabajador del <br>registro. Esta accion no se puede retroceder<br><br>Presione aceptar para continuar</p></html>";
                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Advertencia", string, "Cancelar", "Aceptar");
                    if (!advertencia.getEleccion()) {
                        try {
                            ServicesLocator.getInstance().getPersonaServices().deletePersonaByCi(ID);
                        } catch (SqlServerCustomException | EntradaInvalidaException ex) {
                            throw new RuntimeException(ex);
                        }
                        revalidarTabla();
                    }


                }
                tablaOpciones.mostrarPanel("panel1");
            }
        });


        tablaOpciones.getBotonEliminar().setSeleccionable(false);
        tablaOpciones.getBotonBaja().setSeleccionable(false);
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
        this.tabla.revalidarTabla(checkFiltros((ArrayList<Persona>)ServicesLocator.getInstance().getPersonaServices().getPersonasByTipo(new TipoPersona("Trabajador"))));

        revalidate();
        repaint();
    }

    public ArrayList<Persona> checkFiltros(ArrayList<Persona> personas) {
        ArrayList<Persona> aux = new ArrayList<>();

        for (Persona e : personas) {
            boolean selec = tablaOpciones.getCheckDisp().isSelected();
//                    || e.getDisponibilidadParaFecha(LocalDate.now()) != Disponibilidad.DISPONIBLE;
//            if (!tablaOpciones.getCheckBaja().isSelected() && e.getDisponibilidadParaFecha(LocalDate.now()) == Disponibilidad.BAJA && selec) {
//                selec = false;
//            }
            if (!tablaOpciones.getCheckMasc().isSelected() && e.getSexo().equalsIgnoreCase("masculino") && selec) {
                selec = false;
            }
            if (!tablaOpciones.getCheckFem().isSelected() && e.getSexo().equalsIgnoreCase("femenino") && selec) {
                selec = false;
            }

            if (selec) {
                aux.add(e);
            }
        }

        return aux;
    }


}