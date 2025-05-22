package gui.pantallasEmergentes;

import gui.auxiliares.ComboBoxSelectionListener;
import gui.auxiliares.Paleta;
import gui.componentes.*;
import gui.secciones.Ventana;
import logica.excepciones.EntradaInvalidaException;
import logica.excepciones.MultiplesErroresException;
import model.Persona;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class PantallaLicenciasTrabajador extends JDialog {
    private final Dimension dim = new Dimension(900, 600);
    private final Dimension dimBoton = new Dimension(120, 40);
    private final Dimension dimBotonMenu = new Dimension(180, 35);

    private final Paleta paleta = new Paleta();
    private final JPanel panelVacio;
    private final JPanel contentPane;

    private final JPanel panelOpciones;
    private final Dimension dimOpc = new Dimension(200, 100);

    private final Font fuente = new Font("Arial", Font.PLAIN, 17);
    private final Font fuente2 = new Font("Arial", Font.PLAIN, 15);

    private JPanel contentVacio;
    private final JPanel panel1;
    private final JPanel panelVer;
    private final JPanel panelLicFin;
    private final Boton boton1;
    private LocalDate fechaAux;

    private CustomCalendar calendar;
    private Boton aceptar;
    private boolean eleccion = false;


    private final Persona persona;
    private Etiqueta cantLicencias;
    private CustomComboBox comboLic;

    private CustomComboBox comboLic2;
    private Etiqueta fechaInicio;
    private Etiqueta fechaFinal;
    private Etiqueta duracion;
    private Boton eliminarLic;

    public PantallaLicenciasTrabajador(Persona persona) {
        super(Ventana.getInstance(), "Facultad", true);
        setSize(dim);
        this.persona = persona;

        int x = (Ventana.getInstance().getSize().width - this.getWidth()) / 2;
        int y = (Ventana.getInstance().getSize().height - this.getHeight()) / 2;
        setLocation(x, y);

        contentPane = new JPanel();
        this.setContentPane(contentPane);

        setBackground(paleta.getColorFondoTabla());
        contentPane.setBackground(getBackground());
        contentPane.setLayout(new BorderLayout(0, 0));

        //Aceptar
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        panel1.setBackground(getBackground());

        boton1 = new Boton("Aceptar");

        boton1.setColorLetra(paleta.getColorLetraMenu());
        boton1.setColorLetraPres(Color.WHITE);
        boton1.setColorFondo(paleta.getColorCaracteristico());
        boton1.setColorPresionado(paleta.getColorCaracteristico());
        boton1.setBordeado(true);

        boton1.setNuevoSize(dimBoton);


        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel1.add(boton1);


        //Menu
        panelOpciones = new JPanel(null);
        panelOpciones.setPreferredSize(dimOpc);
        panelOpciones.setBackground(contentPane.getBackground());

        inicializarOpciones();

        panelVer = new JPanel();
        iniciarPanel1();
        panelLicFin = new JPanel();
        iniciarPanel2();


        panelVacio = new JPanel(new CardLayout());
        panelVacio.add(panelVer, "panel1");
        panelVacio.add(panelLicFin, "panel2");

        JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout());

        panel4.add(panel1, BorderLayout.SOUTH);
        panel4.add(panelOpciones, BorderLayout.CENTER);
        contentPane.add(panel4, BorderLayout.EAST);
        contentPane.add(panelVacio, BorderLayout.CENTER);


        //Bordes
        Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, paleta.getColorBorde());
        Border border3 = BorderFactory.createMatteBorder(0, 5, 0, 0, paleta.getColorBorde());
        Border border2 = BorderFactory.createMatteBorder(15, 10, 20, 10, Color.WHITE);
        //		Border border4 = BorderFactory.createMatteBorder(5, 0, 0, 0,paleta.getColorBorde());
        //		Border bordeMargen = BorderFactory.createEmptyBorder(0, 0, 5, 10);
        //		Border margenDoubleBorder = BorderFactory.createCompoundBorder( border4, bordeMargen);


        panelVacio.setBorder(border2);
        panel4.setBorder(border3);
        contentPane.setBorder(border);

        setVisible(true);
    }

    private void inicializarOpciones() {


        Boton botonVer = new Boton("Ver Licencias");
        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel("panel1");
            }
        });
        Boton botonLic1 = new Boton("A�adir Lic. con Fin");
        botonLic1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel("panel2");
            }
        });

        ArrayList<Boton> botones = new ArrayList<>();
        botones.add(botonVer);
        botones.add(botonLic1);

        int sepBot = 20;
        int yBot = sepBot * 4;
        for (Boton e : botones) {
            e.setColorFondo(paleta.getColorCasillaTabla());
            e.setColorPresionado(paleta.getColorCaracteristico());
            e.setColorLetra(paleta.getColorLetraMenu());
            e.setColorLetraPres(Color.WHITE);
            e.setLocation((panelOpciones.getPreferredSize().width - dimBotonMenu.width) / 2, yBot);
            e.setNuevoSize(dimBotonMenu);
            yBot += sepBot + dimBotonMenu.height;
            panelOpciones.add(e);
        }


    }

    public void mostrarPanel(String nombrePanel) {
        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        cardLayout.show(panelVacio, nombrePanel);
        repaint();
        revalidate();
    }

    public void iniciarPanel1() {
        panelVer.setLayout(new BorderLayout());
        panelVer.setSize(this.getSize().width - dimOpc.width, this.getSize().height - 100);
        panelVer.setBackground(paleta.getColorFondoTabla());

        JPanel panelSup = new JPanel(null);
        panelSup.setPreferredSize(new Dimension(panelVer.getSize().width, 100));
        panelSup.setBackground(panelVer.getBackground());

        Etiqueta titulo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Administracion de RECESOS DOCENTES");

        titulo.setLocation(20, 20);
        panelSup.add(titulo);

        //Content Vacio
        contentVacio = new JPanel(new CardLayout());


        //InicioContent
        panelVer.setBackground(contentPane.getBackground());
        panelVer.setLayout(null);
        int x = 20;
        int y = 20;
        int sepEtiq = 20;

        final Etiqueta infoEst = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Estudiante : " + persona.getNombre() + " " + persona.getApellidos());
        infoEst.setLocation(x, y);
        y += infoEst.getHeight() + sepEtiq;
        panelVer.add(infoEst);

        ArrayList<? extends Licencia> licencias = persona.getLicencias();
        String aux = Integer.toString(licencias.size());
        cantLicencias = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Cantidad de Licencias :  " + aux);
        cantLicencias.setLocation(x, y);
        y += cantLicencias.getHeight() + sepEtiq;
        panelVer.add(cantLicencias);

        String[] licenciaNombre = new String[licencias.size()];
        if (licencias.size() > 0) {
            for (int i = 0; i < licencias.size(); i++) {
                licenciaNombre[i] = "Licencia " + (i + 1);
            }
        } else {
            licenciaNombre = null;
        }

        Dimension dimCombo = new Dimension(300, 50);
        comboLic = new CustomComboBox(licenciaNombre, "Selecciona una Licencia", dimCombo, Cuadro.redBAJA, paleta.getColorCasillaTabla());
        comboLic.setTextoNulo("Este estudiante no tiene licencias registradas");
        comboLic.setLocation(x, y);
        panelVer.add(comboLic);


        comboLic.addComboBoxSelectionListener(new ComboBoxSelectionListener() {

            @Override
            public void onItemSelected(String string) {
                if (string != null) {
                    if (string.startsWith("Licencia ")) {
                        try {
                            String numeroStr = string.substring(9);
                            int posicion = Integer.parseInt(numeroStr) - 1;

                            ArrayList<? extends Licencia> licencias = persona.getLicencias();

                            if (posicion >= 0 && posicion < licencias.size()) {
                                Licencia rec = licencias.get(posicion);
                                mostrarLicencia(rec);
                            } else {
                                // Manejo de error si la posici�n est� fuera de rango
                                System.out.println("Posici�n fuera de rango.");
                            }
                        } catch (NumberFormatException e) {
                            // Manejo de error si el n�mero no es v�lido
                            System.out.println("Formato de licencia no v�lido: " + string);
                        }
                    }
                }
            }
        });

        int sepE = 40;
        int yE = y + dimCombo.height + sepE * 2;
        fechaInicio = new Etiqueta(fuente, paleta.getColorLetraMenu(), "");
        fechaInicio.setLocation(x, yE);
        yE += fechaInicio.getHeight() + sepE;

        fechaFinal = new Etiqueta(fuente, paleta.getColorLetraMenu(), "");
        fechaFinal.setLocation(x, yE);
        yE += fechaFinal.getHeight() + sepE;

        duracion = new Etiqueta(fuente, paleta.getColorLetraMenu(), "");
        duracion.setLocation(x, yE);
        yE += fechaFinal.getHeight() + sepE;

        panelVer.add(fechaInicio);
        panelVer.add(fechaFinal);
        panelVer.add(duracion);

        eliminarLic = new Boton("Eliminar Licencia");
        eliminarLic.setColorFondo(paleta.getColorCasillaTabla());
        eliminarLic.setColorPresionado(paleta.getColorCaracteristico());
        eliminarLic.setColorLetra(paleta.getColorLetraMenu());
        eliminarLic.setColorLetraPres(Color.WHITE);
        eliminarLic.setNuevoSize(new Dimension(150, dimCombo.height));
        eliminarLic.setLocation(x, yE);

        eliminarLic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string2 = "<html><p>Esta seguro de que quiere eliminar esta Licencia?<br>Esta accion no se puede revertir<br></p></html>";
                Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Receso Guardado", string2, "Cancelar", "Aceptar");

                if (!advertencia.getEleccion()) {
                    String string = comboLic.getEleccion();
                    if (string != null) {
                        if (string.startsWith("Licencia ")) {
                            try {
                                String numeroStr = string.substring(9);
                                int posicion = Integer.parseInt(numeroStr) - 1;

                                ArrayList<? extends Licencia> licencias = persona.getLicencias();

                                if (posicion >= 0 && posicion < licencias.size()) {
                                    Licencia rec = licencias.get(posicion);
                                    try {
                                        persona.eliminarLicencia(rec);

                                        cantLicencias.setText("Cantidad de Licencias :  " + persona.getLicencias().size());

                                        String[] licenciaNombre = new String[licencias.size()];
                                        if (licencias.size() > 0) {
                                            for (int i = 0; i < licencias.size(); i++) {
                                                licenciaNombre[i] = "Licencia " + (i + 1);
                                            }
                                        } else {
                                            licenciaNombre = null;
                                        }

                                        fechaInicio.setVisible(false);
                                        fechaFinal.setVisible(false);
                                        duracion.setVisible(false);
                                        eliminarLic.setVisible(false);

                                        comboLic.setOpciones(licenciaNombre);
                                    } catch (EntradaInvalidaException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            } catch (NumberFormatException e1) {

                                System.out.println("Formato de licencia no v�lido: " + string);
                            }
                        }
                    }

                }
            }
        });

        panelVer.add(eliminarLic);


        fechaInicio.setVisible(false);
        fechaFinal.setVisible(false);
        duracion.setVisible(false);
        eliminarLic.setVisible(false);

    }

    public void iniciarPanel2() {
        panelLicFin.setLayout(null);
        panelLicFin.setSize(this.getSize().width - dimOpc.width, this.getSize().height - 100);
        panelLicFin.setBackground(paleta.getColorFondoTabla());


        int sep2 = 20;
        int y2 = 80;
        int x = 25;


        //Zona1
        Dimension dimEtiq = new Dimension(150, 40);
        Etiqueta etiq1 = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Fecha de Inicio");
        etiq1.setBold(true);
        etiq1.setSize(dimEtiq);
        etiq1.setLocation(x, y2);
        y2 += etiq1.getHeight() + 30;

        //Seleccionador
        int yE = y2 - 20;

        final CustomTextField dia = new CustomTextField(new Dimension(50, 40), "", 2, paleta.getColorFondo(), 3, 3);
        dia.soloNumeros(true);
        Etiqueta diaE = new Etiqueta(fuente2, paleta.getColorLetraMenu(), "Dia");
        diaE.setLocation(x, yE);
        dia.setLocation(x, y2);
        x += dia.getWidth() + sep2;

        final CustomTextField mes = new CustomTextField(new Dimension(50, 40), "", 2, paleta.getColorFondo(), 3, 3);
        mes.soloNumeros(true);
        Etiqueta mesE = new Etiqueta(fuente2, paleta.getColorLetraMenu(), "Mes");
        mesE.setLocation(x, yE);
        mes.setLocation(x, y2);
        x += mes.getWidth() + sep2;

        final CustomTextField agno = new CustomTextField(new Dimension(80, 40), "", 4, paleta.getColorFondo(), 3, 3);
        agno.soloNumeros(true);
        Etiqueta agnoE = new Etiqueta(fuente2, paleta.getColorLetraMenu(), "A�o");
        agnoE.setLocation(x, yE);
        agno.setLocation(x, y2);
        x += agno.getWidth() + sep2;

        Boton cal = new Boton();
        cal.setSelectLetra(true);
        cal.addIcono("/iconos/Estrella.png");
        cal.setColorIcono(paleta.getColorCaracteristico());
        cal.setColorIconoPres(paleta.getColorCaracteristico().darker());
        cal.setLocation(x, y2);

        cal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setVisible(true);
                aceptar.setVisible(true);
                eleccion = true;
                repaint();
                revalidate();
            }
        });

        x += cal.getWidth() + sep2 * 2;
        panelLicFin.add(cal);

        panelLicFin.add(etiq1);
        panelLicFin.add(diaE);
        panelLicFin.add(mesE);
        panelLicFin.add(agnoE);
        panelLicFin.add(dia);
        panelLicFin.add(mes);
        panelLicFin.add(agno);

        //Calendario
        Dimension dimCalendar = new Dimension(300, 320);

        calendar = new CustomCalendar(dimCalendar);
        calendar.setSelectAll(true);
        calendar.setLocation(x, y2 - 120);
        panelLicFin.add(calendar);
        calendar.setVisible(false);

        aceptar = new Boton("Seleccionar Fecha");
        aceptar.setColorFondo(paleta.getColorCasillaTabla());
        aceptar.setColorPresionado(paleta.getColorCaracteristico());
        aceptar.setColorLetra(paleta.getColorLetraMenu());
        aceptar.setColorLetraPres(Color.WHITE);
        aceptar.setNuevoSize(new Dimension(170, 40));
        int locY = calendar.getLocation().y + calendar.getHeight();
        int locX = calendar.getLocation().x + calendar.getWidth() - aceptar.getWidth();
        aceptar.setLocation(locX, locY);
        panelLicFin.add(aceptar);
        aceptar.setVisible(false);

        //Zona2
        y2 += 50;
        x = 25;
        Etiqueta etiq2 = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Fecha de Fin");
        etiq2.setBold(true);
        etiq2.setSize(dimEtiq);
        etiq2.setLocation(x, y2);
        y2 += etiq2.getHeight() + 30;

        //Seleccionador
        yE = y2 - 20;

        final CustomTextField dia2 = new CustomTextField(new Dimension(50, 40), "", 2, paleta.getColorFondo(), 3, 3);
        dia2.soloNumeros(true);
        Etiqueta diaE2 = new Etiqueta(fuente2, paleta.getColorLetraMenu(), "Dia");
        diaE2.setLocation(x, yE);
        dia2.setLocation(x, y2);
        x += dia2.getWidth() + sep2;

        final CustomTextField mes2 = new CustomTextField(new Dimension(50, 40), "", 2, paleta.getColorFondo(), 3, 3);
        mes2.soloNumeros(true);
        Etiqueta mesE2 = new Etiqueta(fuente2, paleta.getColorLetraMenu(), "Mes");
        mesE2.setLocation(x, yE);
        mes2.setLocation(x, y2);
        x += mes2.getWidth() + sep2;

        final CustomTextField agno2 = new CustomTextField(new Dimension(80, 40), "", 4, paleta.getColorFondo(), 3, 3);
        agno2.soloNumeros(true);
        Etiqueta agnoE2 = new Etiqueta(fuente2, paleta.getColorLetraMenu(), "A�o");
        agnoE2.setLocation(x, yE);
        agno2.setLocation(x, y2);
        x += agno2.getWidth() + sep2;

        Boton cal2 = new Boton();
        cal2.setSelectLetra(true);
        cal2.addIcono("/iconos/Estrella.png");
        cal2.setColorIcono(paleta.getColorCaracteristico());
        cal2.setColorIconoPres(paleta.getColorCaracteristico().darker());
        cal2.setLocation(x, y2);
        panelLicFin.add(cal2);

        Dimension dimCombo = new Dimension(280, 40);
        comboLic2 = new CustomComboBox(TipoLicencia.getOpciones(), "Seleccionar Licencia", dimCombo, Cuadro.redBAJA, paleta.getColorCasillaTabla());
        comboLic2.setTextoNulo("Seleccionar Licencia");
        y2 += agno2.getHeight() + 20;
        comboLic2.setLocation(20, 20);
        comboLic2.getEtiqueta().setNuevoSizeLetra(16);
        panelLicFin.add(comboLic2);


        cal2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setVisible(true);
                aceptar.setVisible(true);
                eleccion = false;
                repaint();
                revalidate();
            }
        });

        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setVisible(false);
                aceptar.setVisible(false);
                fechaAux = calendar.getFechaSelec();
                if (eleccion) {
                    dia.getTextField().setText(Integer.toString(fechaAux.getDayOfMonth()));
                    agno.getTextField().setText(Integer.toString(fechaAux.getYear()));
                    mes.getTextField().setText(Integer.toString(fechaAux.getMonth().getValue()));

                } else {
                    dia2.getTextField().setText(Integer.toString(fechaAux.getDayOfMonth()));
                    agno2.getTextField().setText(Integer.toString(fechaAux.getYear()));
                    mes2.getTextField().setText(Integer.toString(fechaAux.getMonth().getValue()));
                }
                repaint();
                revalidate();
            }
        });

        panelLicFin.add(etiq2);
        panelLicFin.add(diaE2);
        panelLicFin.add(mesE2);
        panelLicFin.add(agnoE2);
        panelLicFin.add(dia2);
        panelLicFin.add(mes2);
        panelLicFin.add(agno2);


        y2 += comboLic.getHeight() + 20;
        Boton annadir = new Boton("A�adir Licencia");
        annadir.setColorFondo(paleta.getColorCasillaTabla());
        annadir.setColorPresionado(paleta.getColorCaracteristico());
        annadir.setColorLetra(paleta.getColorLetraMenu());
        annadir.setColorLetraPres(Color.WHITE);
        annadir.setNuevoSize(new Dimension(150, 40));
        annadir.setLocation(30, y2);

        annadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean correcto = !dia.getText().isEmpty() && !mes.getText().isEmpty() && !agno.getText().isEmpty();
                if (dia2.getText().isEmpty() || mes2.getText().isEmpty() || agno2.getText().isEmpty()) {
                    correcto = false;
                }

                if (correcto) {
                    LocalDate fechaInicio = LocalDate.of(Integer.valueOf(agno.getText()), Integer.valueOf(mes.getText()), Integer.valueOf(dia.getText()));
                    LocalDate fechaFinal = LocalDate.of(Integer.valueOf(agno2.getText()), Integer.valueOf(mes2.getText()), Integer.valueOf(dia2.getText()));
                    try {

                        if (comboLic2.getEleccion() == null) {

                            String string = "<html><p style='text-align: center;'> ERROR <br><br>Seleccione el tipo de licencia" + "</p></html>";
                            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Errores", string, "Aceptar");
                        } else {
                            TipoLicencia tipoLic = TipoLicencia.fromString(comboLic2.getEleccion());
                            ((Trabajador) persona).annadirLicencia(fechaInicio, fechaFinal, tipoLic);
                            String string = "<html><p>Licencia Guardada Exitosamente<br><br></p></html>";
                            ArrayList<? extends Licencia> licencias = persona.getLicencias();
                            cantLicencias.setText("Cantidad de Licencias :  " + licencias.size());

                            String[] licenciaNombre = new String[licencias.size()];
                            if (licencias.size() > 0) {
                                for (int i = 0; i < licencias.size(); i++) {
                                    licenciaNombre[i] = "Licencia " + (i + 1);
                                }
                            } else {
                                licenciaNombre = null;
                            }

                            comboLic.setOpciones(licenciaNombre);
                            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Licencia Guardada", string, "Aceptar");
                            mostrarPanel("panel1");
                            repaint();
                            revalidate();
                        }
                    } catch (EntradaInvalidaException e1) {
                        String string = "<html><p style='text-align: center;'> ERROR <br><br>" + e1.getMessage() + "</p></html>";
                        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", string, "Aceptar");
                    } catch (MultiplesErroresException e1) {
                        StringBuilder stringAux = new StringBuilder();
                        for (String error : e1.getErrores()) {
                            stringAux.append(error).append("<br>");
                        }

                        String string = "<html><p style='text-align: center;'> ERROR <br>" + stringAux + "</p></html>";
                        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Errores", string, "Aceptar");
                    }

                } else {
                    String string = "<html><p style='text-align: center;'> ERROR <br><br>Todos los campos deben ser rellenados" + "</p></html>";
                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Errores", string, "Aceptar");
                }
                repaint();
                revalidate();
            }
        });
        panelLicFin.add(annadir);
    }

    protected void mostrarLicencia(Licencia lic) {
        // TODO Auto-generated method stub
        if (lic != null) {
            fechaInicio.setTexto("Fecha de Inicio: " + lic.getInicio());
            if (lic.getFin() != null) {
                fechaFinal.setTexto("Fecha de Final: " + lic.getFin());
                fechaFinal.setVisible(true);
                Period periodo = Period.between(lic.getInicio(), lic.getFin());
                int dias = periodo.getDays();
                duracion.setTexto("Duracion total: " + dias);
            } else {
                fechaFinal.setVisible(false);
                duracion.setTexto("Duracion total: Sin fin previsto");
            }


            fechaInicio.setVisible(true);

            duracion.setVisible(true);
            eliminarLic.setVisible(true);
        } else {
            fechaInicio.setVisible(false);
            fechaFinal.setVisible(false);
            duracion.setVisible(false);
            eliminarLic.setVisible(false);
        }

    }
}
