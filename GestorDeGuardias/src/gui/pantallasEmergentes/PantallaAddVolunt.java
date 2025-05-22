package gui.pantallasEmergentes;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.CustomCalendar;
import gui.componentes.CustomTextField;
import gui.componentes.Etiqueta;
import gui.secciones.Ventana;
import services.Gestor;
import logica.excepciones.EntradaInvalidaException;
import logica.excepciones.MultiplesErroresException;
import model.Persona;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class PantallaAddVolunt extends JDialog {
    private final Dimension dim = new Dimension(750, 550);
    private final Dimension dimBoton = new Dimension(120, 40);

    private final Paleta paleta = new Paleta();
    private final JPanel contentPane;

    private final Font fuente = new Font("Arial", Font.PLAIN, 17);
    private final Font fuente2 = new Font("Arial", Font.PLAIN, 15);

    private final JPanel panel1;
    private final JPanel panelVolunt;
    private final Boton boton1;
    private final Boton boton2;
    private LocalDate fechaAux;

    private CustomCalendar calendar;
    private Boton aceptar;
    private boolean eleccion = false;


    private final Persona persona;


    public PantallaAddVolunt(Persona persona) {
        super(Ventana.getInstance(), "A�adir Voluntariedad", true);
        setSize(dim);
        this.persona = persona;
        setResizable(false);

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


        boton2 = new Boton("Cancelar");

        boton2.setColorLetra(paleta.getColorLetraMenu());
        boton2.setColorLetraPres(Color.WHITE);
        boton2.setColorFondo(paleta.getColorCasillaTabla());
        boton2.setColorPresionado(paleta.getColorCaracteristico());

        boton2.setNuevoSize(dimBoton);

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel1.add(boton2);
        panel1.add(boton1);

        panelVolunt = new JPanel();
        iniciarPanel2();


        JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout());

        contentPane.add(panel1, BorderLayout.SOUTH);
        contentPane.add(panelVolunt, BorderLayout.CENTER);


        //Bordes
        Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, paleta.getColorBorde());
        Border border3 = BorderFactory.createMatteBorder(0, 5, 0, 0, paleta.getColorBorde());
        Border border2 = BorderFactory.createMatteBorder(15, 10, 20, 10, Color.WHITE);
        //		Border border4 = BorderFactory.createMatteBorder(5, 0, 0, 0,paleta.getColorBorde());
        //		Border bordeMargen = BorderFactory.createEmptyBorder(0, 0, 5, 10);
        //		Border margenDoubleBorder = BorderFactory.createCompoundBorder( border4, bordeMargen);

        panel4.setBorder(border3);
        contentPane.setBorder(border);

        setVisible(true);
    }


    public void iniciarPanel2() {
        panelVolunt.setLayout(null);
        panelVolunt.setSize(this.getSize().width, this.getSize().height - 100);
        panelVolunt.setBackground(paleta.getColorFondoTabla());

        int sep2 = 20;
        int y2 = 80;
        int x = 25;


        //Zona1
        Dimension dimEtiq = new Dimension(200, 40);
        Etiqueta etiq1 = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Fecha de Voluntariedad");
        etiq1.setBold(true);
        etiq1.setSize(dimEtiq);
        etiq1.setLocation(x, y2);
        y2 += etiq1.getHeight() + 30;

        //Seleccionador
        int yE = y2 - 20;

        final CustomTextField dia = new CustomTextField(new Dimension(50, 40), "", 2, paleta.getColorFondo(), 3, 3);
        dia.soloNumeros(true);
        final Etiqueta diaE = new Etiqueta(fuente2, paleta.getColorLetraMenu(), "Dia");
        diaE.setLocation(x, yE);
        dia.setLocation(x, y2);
        x += dia.getWidth() + sep2;

        final CustomTextField mes = new CustomTextField(new Dimension(50, 40), "", 2, paleta.getColorFondo(), 3, 3);
        mes.soloNumeros(true);
        final Etiqueta mesE = new Etiqueta(fuente2, paleta.getColorLetraMenu(), "Mes");
        mesE.setLocation(x, yE);
        mes.setLocation(x, y2);
        x += mes.getWidth() + sep2;

        final CustomTextField agno = new CustomTextField(new Dimension(80, 40), "", 4, paleta.getColorFondo(), 3, 3);
        agno.soloNumeros(true);
        final Etiqueta agnoE = new Etiqueta(fuente2, paleta.getColorLetraMenu(), "A�o");
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
        panelVolunt.add(cal);

        panelVolunt.add(etiq1);
        panelVolunt.add(diaE);
        panelVolunt.add(mesE);
        panelVolunt.add(agnoE);
        panelVolunt.add(dia);
        panelVolunt.add(mes);
        panelVolunt.add(agno);

        //Calendario
        Dimension dimCalendar = new Dimension(300, 320);

        calendar = new CustomCalendar(dimCalendar);
        calendar.setSelectAll(true);
        calendar.setLocation(x, y2 - 120);
        panelVolunt.add(calendar);
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
        panelVolunt.add(aceptar);
        aceptar.setVisible(false);


        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (dia.getText().isEmpty() || mes.getText().isEmpty() || agno.getText().isEmpty()) {
                    String string = "<html><p style='text-align: center;'> ERROR <br><br><br> Debe rellenar todos los campos de fecha" + "</p></html>";
                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", string, "Aceptar");
                } else {
                    LocalDate fechaInicio = LocalDate.of(Integer.valueOf(agno.getText()), Integer.valueOf(mes.getText()), Integer.valueOf(dia.getText()));

                    try {
                        Gestor.getInstance().getFacultad().annadirFechaDeDisponibilidad(persona.getCi(), fechaAux);
                        dispose();
                        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "A�adido Exitosamente", "Voluntariedad a�adida Exitosamente", "Aceptar");
                    } catch (EntradaInvalidaException e1) {
                        String string = "<html><p style='text-align: center;'> ERROR <br><br><br>" + e1.getMessage() + "</p></html>";
                        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", string, "Aceptar");
                    } catch (MultiplesErroresException e1) {
                        StringBuilder stringAux = new StringBuilder();
                        for (String error : e1.getErrores()) {
                            stringAux.append(error).append("<br>");
                        }

                        String string = "<html><p style='text-align: center;'> ERROR <br><br>" + stringAux + "</p></html>";
                        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Errores", string, "Aceptar");
                    }

                }


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

                }
                repaint();
                revalidate();
            }
        });


    }

}
