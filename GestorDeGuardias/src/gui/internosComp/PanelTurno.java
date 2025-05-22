package gui.internosComp;

import gui.auxiliares.Actualizable;
import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.componentes.CustomPopupMenu;
import gui.componentes.Etiqueta;
import model.DiaGuardia;
import model.Persona;
import model.TurnoGuardia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelTurno extends Cuadro implements Actualizable {
    private static final long serialVersionUID = 1L;
    protected static int alto = 25;
    protected Paleta paleta = new Paleta();
    protected Color colorLetra = paleta.getColorLetraMenu();
    protected TurnoGuardia turno;

    protected Etiqueta horario;
    protected Etiqueta nombre;
    protected Etiqueta apellido;
    protected Etiqueta ID;

    protected Font fuente = new Font("Arial", Font.PLAIN, 16);

    protected int espacioID = 0;
    protected int espacioNombre = 0;
    protected int espacioApellido = 20;

    protected int sizeHorario = 100;
    protected int sizeID = 110 + espacioID;
    protected int sizeNombre = 220 + espacioNombre;
    protected int sizeApellidos = 240 + espacioApellido;

    protected Color colorFondo;
    protected Color colorPasada;
    protected JPanel horarioPanel;
    protected Color colorSelec;

    protected boolean seleccionado = false;
    protected boolean entrado = false;

    protected DiaGuardia fecha;


    public PanelTurno(int ancho, Color color, TurnoGuardia turno, DiaGuardia fecha, final Actualizable act) {
        super(new Dimension(ancho, alto), 0, color);
        this.fecha = fecha;
        this.setPreferredSize(this.getSize());
        this.setMaximumSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 3));
        this.turno = turno;

        colorFondo = color;
        colorPasada = colorFondo.darker();
        colorSelec = paleta.getColorCaracteristico();

        String horarioText = this.turno.getHorario().getText();
        horario = new Etiqueta(fuente, colorLetra, horarioText);
        horarioPanel = new JPanel();
        horarioPanel.setLayout(null);
        horarioPanel.setPreferredSize(new Dimension(sizeHorario, horario.getSize().height));
        horario.setLocation((sizeHorario - horario.getSize().width) / 2, 0);
        horarioPanel.add(horario);
        horarioPanel.setBackground(getBackground());
        add(horarioPanel);

        nombre = new Etiqueta(fuente, colorLetra, "");
        apellido = new Etiqueta(fuente, colorLetra, "");
        ID = new Etiqueta(fuente, colorLetra, "");

        if (this.turno.getPersonaAsignada() != null) {
            setPersona(this.turno.getPersonaAsignada());
        }

        nombre.setSize(new Dimension(sizeNombre, horario.getSize().height));
        nombre.setPreferredSize(nombre.getSize());
        nombre.setBorder(BorderFactory.createEmptyBorder(0, espacioNombre, 0, 0));

        apellido.setSize(new Dimension(sizeApellidos, horario.getSize().height));
        apellido.setPreferredSize(apellido.getSize());
        apellido.setBorder(BorderFactory.createEmptyBorder(0, espacioApellido, 0, 0));

        ID.setSize(new Dimension(sizeID, horario.getSize().height));
        ID.setPreferredSize(ID.getSize());
        ID.setBorder(BorderFactory.createEmptyBorder(0, espacioID, 0, 0));
        ID.setFont(fuente);
        apellido.setFont(fuente);
        nombre.setFont(fuente);

        add(ID);
        add(apellido);
        add(nombre);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Cambiar el borde al hacer clic
                if (!seleccionado) {
                    cambiar(colorSelec, Color.WHITE);
                    seleccionado = true;

                } else {
                    if (entrado) {
                        cambiar(colorFondo, colorLetra);
                    } else {
                        cambiar(colorFondo, colorLetra);
                    }

                    seleccionado = false;

                }
                // Notificar a la clase principal sobre el cambio***********************************************************

                act.actualizar();
                revalidate();
                repaint();
            }

            public void mouseEntered(MouseEvent e) {
                entrado = true;
                if (!seleccionado) {
                    cambiar(colorPasada, colorLetra);
                } else {
                    cambiar(colorSelec.darker(), Color.WHITE);
                }
            }

            public void mouseExited(MouseEvent e) {
                entrado = false;
                if (seleccionado) {
                    cambiar(colorSelec, Color.WHITE);
                } else {
                    cambiar(colorFondo, colorLetra);
                }


            }
        });

        addMouseListener(new MouseAdapter() {
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
        });

    }

    public void setPersona(Persona persona) {
        turno.asignarPersona(persona);
        actualizar();
    }

    public void eliminarPersona() {
        turno.borrarPersonaAsignada();
    }


    public void cambiar(Color fondo, Color letra) {
        setColorFondo(fondo);
        setBackground(fondo);
        ID.setBackground(fondo);
        nombre.setBackground(fondo);
        apellido.setBackground(fondo);
        horarioPanel.setBackground(fondo);
        horario.setBackground(fondo);

        ID.setForeground(letra);
        nombre.setForeground(letra);
        apellido.setForeground(letra);
        horario.setForeground(letra);

    }

    private void mostrarPopup(MouseEvent evt) {
        CustomPopupMenu popup = new CustomPopupMenu();

        JMenuItem itemA = new JMenuItem("A");
        JMenuItem itemB = new JMenuItem("B");
        JMenuItem itemC = new JMenuItem("C");

        // Agregar las opciones al men�
        popup.addItem(itemA);
        popup.addItem(itemB);
        popup.addItem(itemC);

        // Mostrar el men� en la posici�n del clic
        popup.show(evt.getComponent(), evt.getX(), evt.getY());
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Etiqueta getHorario() {
        return horario;
    }

    public void actualizar() {
        Persona aux = turno.getPersonaAsignada();
        if (aux != null) {
            nombre.setText(aux.getNombre());
            apellido.setText(aux.getApellidos());
            ID.setText(aux.getCi());
            revalidate();
            repaint();
        }
    }

    public TurnoGuardia getTurno() {
        return turno;
    }

    public DiaGuardia getFecha() {

        return fecha;
    }


}
