package gui.pantallasEmergentes;

import gui.auxiliares.ComboBoxSelectionListener;
import gui.auxiliares.Paleta;
import gui.componentes.*;
import gui.secciones.Ventana;
import model.PeriodoNoPlanificable;
import services.Gestor;
import services.PeriodoNoPlanificableServices;
import services.ServicesLocator;
import utils.exceptions.EntradaInvalidaException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class PantallaFacultad extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JPanel contentPane;
    private final Paleta paleta = new Paleta();

    private final JPanel panel1;
    private final JPanel panel2;
    private final JPanel panel4;
    private final Dimension dim = new Dimension(1100, 700);
    private final Dimension dimBoton = new Dimension(120, 40);
    private final Dimension dimOpc = new Dimension(280, 100);
    private final JPanel panelVacio;
    private final JPanel panelRec;
    private final JPanel panelFac;
    private final Font fuente = new Font("Arial", Font.PLAIN, 15);
    private final Font fuenteE = new Font("Arial", Font.PLAIN, 13);
    private final Font fuente2 = new Font("Arial", Font.PLAIN, 13);
    private final Boton boton1;
    private JPanel panel3;
    private JPanel contentVacio;
    private CustomCalendar calendar;
    private boolean eleccion;
    private Boton aceptar;
    private LocalDate fechaAux;
    private Etiqueta fechaInicio, fechaFinal, duracion;
    private Boton eliminarRec;

    public PantallaFacultad() {
        super(Ventana.getInstance(), "Facultad", true);
        setSize(dim);

        int x = (Ventana.getInstance().getSize().width - this.getWidth()) / 2;
        int y = (Ventana.getInstance().getSize().height - this.getHeight()) / 2;
        setLocation(x, y);

        contentPane = new JPanel();
        this.setContentPane(contentPane);

        setBackground(paleta.getColorFondoTabla());
        contentPane.setBackground(getBackground());
        contentPane.setLayout(new BorderLayout(0, 0));

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

        //Panel2
        panel2 = new JPanel(null);
        panel2.setPreferredSize(dimOpc);
        panel2.setBackground(contentPane.getBackground());

        inicializarPanel2();

        panelFac = new JPanel();
        iniciarPanel1();
        panelRec = new JPanel();
        iniciarPanel2();

        panelVacio = new JPanel(new CardLayout());
        panelVacio.add(panelFac, "panel1");
        panelVacio.add(panelRec, "panel2");

        panel4 = new JPanel();
        panel4.setLayout(new BorderLayout());

        panel4.add(panel1, BorderLayout.SOUTH);
        panel4.add(panel2, BorderLayout.CENTER);
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


    private void inicializarPanel2() {
        Dimension dimBotMenu = new Dimension(220, 35);

        Boton botonFac = new Boton("Informacion");
        botonFac.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel("panel1");
            }
        });
        Boton botonRecesos = new Boton("Admin Recesos");
        botonRecesos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel("panel2");
            }
        });

        ArrayList<Boton> botones = new ArrayList<>();
        botones.add(botonFac);
        botones.add(botonRecesos);

        int sepBot = 20;
        int yBot = sepBot * 4;
        for (Boton e : botones) {
            e.setColorFondo(paleta.getColorCasillaTabla());
            e.setColorPresionado(paleta.getColorCaracteristico());
            e.setColorLetra(paleta.getColorLetraMenu());
            e.setColorLetraPres(Color.WHITE);
            e.setLocation((panel2.getPreferredSize().width - dimBotMenu.width) / 2, yBot);
            e.setNuevoSize(dimBotMenu);
            yBot += sepBot + dimBotMenu.height;
            panel2.add(e);
        }

    }

    public void mostrarPanel(String nombrePanel) {
        CardLayout cardLayout = (CardLayout) panelVacio.getLayout();
        cardLayout.show(panelVacio, nombrePanel);
        repaint();
        revalidate();
    }

    public void iniciarPanel1() {
        panelFac.setLayout(new BorderLayout());
        panelFac.setSize(this.getSize().width - dimOpc.width, this.getSize().height - 100);
        panelFac.setBackground(paleta.getColorFondoTabla());

        JPanel panelSup = new JPanel(null);
        panelSup.setPreferredSize(new Dimension(panelFac.getSize().width, 100));
        panelSup.setBackground(panelFac.getBackground());

        Etiqueta titulo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Informacion de Facultad : " + "INGENIERIA INFORMATICA");

        titulo.setLocation(20, 20);
        panelSup.add(titulo);

        //Content
        JPanel content = new JPanel(null);
        content.setBackground(panelFac.getBackground());
        int x = 20;
        int y = 20;
        int sepEtiq = 20;

        String aux = Integer.toString(ServicesLocator.getInstance().getPersonaServices().
                getPersonaCountByTipo("Estudiante"));
        Etiqueta cantE = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Cantidad de Estudiantes :  " + aux);
        cantE.setLocation(x, y);
        y += cantE.getHeight() + sepEtiq;
        content.add(cantE);

        aux = Integer.toString(ServicesLocator.getInstance().getPersonaServices().getPersonaCountByTipo("Trabajador"));
        Etiqueta cantT = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Cantidad de Trabajadores :  " + aux);
        cantT.setLocation(x, y);
        y += cantT.getHeight() + sepEtiq;
        content.add(cantT);


        panelFac.add(panelSup, BorderLayout.NORTH);
        panelFac.add(content, BorderLayout.CENTER);
    }

    public void iniciarPanel2() {
        panelRec.setLayout(new BorderLayout());
        panelRec.setSize(this.getSize().width - dimOpc.width, this.getSize().height - 100);
        panelRec.setBackground(paleta.getColorFondoTabla());

        JPanel panelSup = new JPanel(null);
        panelSup.setPreferredSize(new Dimension(panelFac.getSize().width, 100));
        panelSup.setBackground(panelFac.getBackground());

        Etiqueta titulo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Administracion de RECESOS DOCENTES");

        titulo.setLocation(20, 20);
        panelSup.add(titulo);

        //Content Vacio
        contentVacio = new JPanel(new CardLayout());


        //InicioContent
        JPanel content = new JPanel(null);
        content.setBackground(panelFac.getBackground());
        int x = 20;
        int y = 20;
        int sepEtiq = 20;


        ArrayList<PeriodoNoPlanificable> recesos = ServicesLocator.getInstance().getPeriodoNoPlanificableServices().getAllPeriodosNoPlanificables();
        String aux = Integer.toString(recesos.size());
        final Etiqueta cantERec = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Cantidad de Recesos :  " + aux);
        cantERec.setLocation(x, y);
        y += cantERec.getHeight() + sepEtiq;
        content.add(cantERec);

        String[] recesosNombre = new String[recesos.size()];
        if (recesos.size() > 0) {
            for (int i = 0; i < recesos.size(); i++) {
                //TODO: aqui habia un .getnombre
                recesosNombre[i] = recesos.get(i).toString();
            }
        } else {
            recesosNombre = null;
        }

        Dimension dimCombo = new Dimension(300, 50);
        final CustomComboBox comboRec = new CustomComboBox(recesosNombre, "Recesos", dimCombo, Cuadro.redBAJA, paleta.getColorCasillaTabla());
        comboRec.setTextoNulo("No hay recesos registrados");
        comboRec.setLocation(x, y);
        content.add(comboRec);

        comboRec.addComboBoxSelectionListener(new ComboBoxSelectionListener() {

            @Override
            public void onItemSelected(String selectedItem) {
                if (selectedItem != null) {
                    PeriodoNoPlanificable rec = Gestor.getInstance().getFacultad().buscarRecesoDocente(selectedItem);
                    mostrarReceso(rec);
                }
            }

        });


        Boton botonAddRec = new Boton("Añadir Receso");
        botonAddRec.setColorFondo(paleta.getColorCasillaTabla());
        botonAddRec.setColorPresionado(paleta.getColorCaracteristico());
        botonAddRec.setColorLetra(paleta.getColorLetraMenu());
        botonAddRec.setColorLetraPres(Color.WHITE);
        botonAddRec.setNuevoSize(new Dimension(150, dimCombo.height));
        botonAddRec.setLocation(x + dimCombo.width + x, y);

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

        content.add(fechaInicio);
        content.add(fechaFinal);
        content.add(duracion);

        eliminarRec = new Boton("Eliminar Receso");
        eliminarRec.setColorFondo(paleta.getColorCasillaTabla());
        eliminarRec.setColorPresionado(paleta.getColorCaracteristico());
        eliminarRec.setColorLetra(paleta.getColorLetraMenu());
        eliminarRec.setColorLetraPres(Color.WHITE);
        eliminarRec.setNuevoSize(new Dimension(150, dimCombo.height));
        eliminarRec.setLocation(x, yE);

        eliminarRec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = "<html><p>Esta seguro de que quiere eliminar este Receso Docente?<br>Esta accion no se puede revertir<br></p></html>";
                Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Receso Guardado", string, "Cancelar", "Aceptar");

                if (!advertencia.getEleccion()) {
                    PeriodoNoPlanificableServices pnpService = ServicesLocator.getInstance().getPeriodoNoPlanificableServices();
                    LocalDate fechaAux2 = Gestor.getInstance().getFacultad().buscarRecesoDocente(comboRec.getEleccion()).getInicio();
                    try {
                        Gestor.getInstance().getFacultad().eliminarRecesoDocente(fechaAux2);
                    } catch (EntradaInvalidaException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    cantERec.setText("Cantidad de periodos no planificables :  " + pnpService.countPeriodoNoPlanificable());
                    comboRec.setOpciones(new String[]{pnpService.getAllPeriodosNoPlanificables().toString()});

                    fechaInicio.setVisible(false);
                    fechaFinal.setVisible(false);
                    duracion.setVisible(false);
                    eliminarRec.setVisible(false);

                }
            }
        });
        content.add(eliminarRec);

        fechaInicio.setVisible(false);
        fechaFinal.setVisible(false);
        duracion.setVisible(false);
        eliminarRec.setVisible(false);

        botonAddRec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentVacio.getLayout();
                cardLayout.show(contentVacio, "panelMini2");
                repaint();
                revalidate();
            }
        });
        content.add(botonAddRec);

        //Panel Creacional
        JPanel panelCrear = new JPanel(null);
        panelCrear.setBackground(panelRec.getBackground());

        Boton atras = new Boton();
        atras.setSelectLetra(true);
        atras.addIcono("/iconos/Estrella.png");
        atras.setColorIcono(paleta.getColorCaracteristico());
        atras.setColorIconoPres(paleta.getColorCaracteristico().darker());
        atras.setLocation(x, 20);
        atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentVacio.getLayout();
                cardLayout.show(contentVacio, "panelMini1");
                repaint();
                revalidate();
            }
        });

        panelCrear.add(atras);

        int sep2 = 20;
        int y2 = 80;
        x = 25;

        Dimension dimTextF = new Dimension(200, 40);
        final CustomTextField nombre = new CustomTextField(dimTextF, "Nombre identif", 30, paleta.getColorFondo());
        nombre.setLocation(x, y2);
        y2 += dimTextF.height + sep2 + 10;
        panelCrear.add(nombre);

        final Etiqueta vacio1 = new Etiqueta(fuenteE, paleta.getColorLetraMenu(), "Campo sin Rellenar");
        vacio1.setForeground(Color.RED);
        vacio1.setLocation(25, nombre.getLocation().y - vacio1.getSize().height - 5);
        panelCrear.add(vacio1);
        vacio1.setVisible(false);


        //Zona1
        Dimension dimEtiq = new Dimension(150, 40);
        Etiqueta etiq1 = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Fecha de Inicio");
        etiq1.setBold(true);
        etiq1.setSize(dimEtiq);
        etiq1.setLocation(x, y2);
        y2 += etiq1.getHeight() + 30;

        //Seleccionador
        yE = y2 - 20;

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
        panelCrear.add(cal);

        panelCrear.add(etiq1);
        panelCrear.add(diaE);
        panelCrear.add(mesE);
        panelCrear.add(agnoE);
        panelCrear.add(dia);
        panelCrear.add(mes);
        panelCrear.add(agno);

        //Calendario
        Dimension dimCalendar = new Dimension(300, 320);

        calendar = new CustomCalendar(dimCalendar);
        calendar.setSelectAll(true);
        calendar.setLocation(x, y2 - 120);
        panelCrear.add(calendar);
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
        panelCrear.add(aceptar);
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
        panelCrear.add(cal2);

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

        panelCrear.add(etiq2);
        panelCrear.add(diaE2);
        panelCrear.add(mesE2);
        panelCrear.add(agnoE2);
        panelCrear.add(dia2);
        panelCrear.add(mes2);
        panelCrear.add(agno2);


        Boton annadir = new Boton("A�adir Receso");
        annadir.setColorFondo(paleta.getColorCasillaTabla());
        annadir.setColorPresionado(paleta.getColorCaracteristico());
        annadir.setColorLetra(paleta.getColorLetraMenu());
        annadir.setColorLetraPres(Color.WHITE);
        annadir.setNuevoSize(new Dimension(150, 40));
        annadir.setLocation(30, aceptar.getLocation().y);

        annadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                boolean correcto = true;
//
//                if (nombre.getText().isEmpty()) {
//                    vacio1.setVisible(true);
//
//                    revalidate();
//                    repaint();
//                    correcto = false;
//                }
//                if (correcto) {
                    vacio1.setVisible(false);
                    LocalDate fechaInicio = LocalDate.of(Integer.parseInt(agno.getText()), Integer.parseInt(mes.getText()), Integer.parseInt(dia.getText()));
                    LocalDate fechaFinal = LocalDate.of(Integer.parseInt(agno2.getText()), Integer.parseInt(mes2.getText()), Integer.parseInt(dia2.getText()));
//                    try {
                        ServicesLocator.getInstance().getPeriodoNoPlanificableServices().insertPeriodoNoPlanificable(fechaInicio, fechaFinal);
                        String string = "<html><p>Receso Guardado Exitosamente<br><br></p></html>";
                        cantERec.setText("Cantidad de periodos no planificables :  " + ServicesLocator.getInstance().getPeriodoNoPlanificableServices().countPeriodoNoPlanificable());

                        comboRec.setOpciones(new String[]{ServicesLocator.getInstance().getPeriodoNoPlanificableServices().getAllPeriodosNoPlanificables().toString()});
                        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Receso Guardado", string, "Aceptar");
                        CardLayout cardLayout = (CardLayout) contentVacio.getLayout();
                        cardLayout.show(contentVacio, "panelMini1");

                        nombre.getTextField().setText("");
                        repaint();
                        revalidate();
//                    } catch (EntradaInvalidaException e1) {
//                        String string = "<html><p style='text-align: center;'> ERROR <br><br>" + e1.getMessage() + "</p></html>";
//                        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", string, "Aceptar");
//                    } catch (MultiplesErroresException e1) {
//                        StringBuilder stringAux = new StringBuilder();
//                        for (String error : e1.getErrores()) {
//                            stringAux.append(error).append("<br>");
//                        }

//                        String string = "<html><p style='text-align: center;'> ERROR <br>" + stringAux + "</p></html>";
//                        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Errores", string, "Aceptar");
//                    }


//                } else {
//                    String string = "<html><p style='text-align: center;'> ERROR <br><br>Todos los campos deben ser rellenados" + "</p></html>";
//                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Errores", string, "Aceptar");
//                }
//                repaint();
//                revalidate();
           }
        });
        panelCrear.add(annadir);


        contentVacio.add(content, "panelMini1");
        contentVacio.add(panelCrear, "panelMini2");
        panelRec.add(panelSup, BorderLayout.NORTH);
        panelRec.add(contentVacio, BorderLayout.CENTER);
    }

    protected void mostrarReceso(PeriodoNoPlanificable recMostrar) {
        // TODO Auto-generated method stub
        if (recMostrar != null) {
            fechaInicio.setTexto("Fecha de Inicio: " + recMostrar.getInicio());
            fechaFinal.setTexto("Fecha de Final: " + recMostrar.getFin());
            Period periodo = Period.between(recMostrar.getInicio(), recMostrar.getFin());
            int dias = periodo.getDays();
            duracion.setTexto("Duracion total: " + dias);

            fechaInicio.setVisible(true);
            fechaFinal.setVisible(true);
            duracion.setVisible(true);
            eliminarRec.setVisible(true);
        } else {
            fechaInicio.setVisible(false);
            fechaFinal.setVisible(false);
            duracion.setVisible(false);
            eliminarRec.setVisible(false);
        }

    }


    public void iniciarPanel3() {

    }
}
