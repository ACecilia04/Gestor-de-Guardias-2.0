package gui.pantallasEmergentes;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.componentes.Etiqueta;
import gui.secciones.Ventana;
import model.Configuracion;
import model.Horario;
import services.ServicesLocator;
import java.time.LocalTime;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PantallaAddConfig extends JDialog {
    protected static final long serialVersionUID = 1L;

    protected JPanel contentPane;
    protected JPanel panelTitulo, panelInf, panelBotones;
    protected Paleta paleta = new Paleta();

    protected Dimension dim = new Dimension(500, 500);
    protected Dimension dimBoton = new Dimension(120, 40);

    protected Font fuente = new Font("Arial", Font.PLAIN, 14);

    // Campos de configuración
    protected JSpinner spinnerCantidad;
    protected JComboBox<String> comboTipoPersona;
    protected JComboBox<String> comboSexo;
    protected JComboBox<String> comboDiasSemana;
    protected DefaultListModel<String> modeloHorarios;
    protected JList<String> listaHorarios;

    protected int margenIzquierdo = 40;

    public PantallaAddConfig() {
        super(Ventana.getInstance(), "Añadir Configuración", true);
        setResizable(false);
        setSize(dim);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setBackground(paleta.getColorFondoTabla());
        contentPane.setLayout(new BorderLayout());

        int x = (Ventana.getInstance().getSize().width - this.getWidth()) / 2;
        int y = (Ventana.getInstance().getSize().height - this.getHeight()) / 2;
        setLocation(x, y);

        inicializarTitulo();
        inicializarPanelInf();
        inicializarPanelBotones();

        contentPane.add(panelTitulo, BorderLayout.NORTH);
        contentPane.add(panelInf, BorderLayout.CENTER);
        contentPane.add(panelBotones, BorderLayout.SOUTH);

        Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, paleta.getColorBorde());
        Border border2 = BorderFactory.createMatteBorder(3, 0, 3, 0, paleta.getColorBorde());
        contentPane.setBorder(border);
        panelInf.setBorder(border2);
        contentPane.requestFocus();

        setVisible(true);
    }

    private void inicializarPanelBotones() {
        panelBotones = new JPanel();
        panelBotones.setBackground(contentPane.getBackground());
        panelBotones.setPreferredSize(new Dimension(this.getSize().width, 100));
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 28));

        Boton botonCancelar = new Boton("Cancelar");
        botonCancelar.setNuevoSize(dimBoton);
        botonCancelar.setColorLetra(paleta.getColorLetraMenu());
        botonCancelar.setColorFondo(paleta.getColorCasillaTabla());
        botonCancelar.addActionListener(e -> dispose());

        Boton botonAceptar = new Boton("Aceptar");
        botonAceptar.setNuevoSize(dimBoton);
        botonAceptar.setColorLetra(Color.WHITE);
        botonAceptar.setColorFondo(paleta.getColorCaracteristico());

        botonAceptar.addActionListener(e -> {
            Configuracion record = new Configuracion();
            // Aquí puedes agregar la lógica de aceptar y guardar la configuración
            int cantidad = (Integer) spinnerCantidad.getValue();
            String tipoPersona = (String) comboTipoPersona.getSelectedItem();
            String sexoSeleccionado = (String) comboSexo.getSelectedItem();
            String diaSeleccionado = (String) comboDiasSemana.getSelectedItem();
            //List<Horario> horarios = listaHorarios.getSelectedValuesList();
            Horario horario = stringToHorario(listaHorarios.getSelectedValue());
            ServicesLocator.getInstance().getConfiguracionServices().insertConfiguracion();
            dispose();
        });

        panelBotones.add(botonCancelar);
        panelBotones.add(botonAceptar);
    }

    private void inicializarPanelInf() {
        panelInf = new JPanel(null);
        panelInf.setBackground(contentPane.getBackground());

        int y = 20;

        // Cantidad de personas
        Etiqueta etiquetaCantidad = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Cantidad de personas en el turno:");
        etiquetaCantidad.setLocation(margenIzquierdo, y);
        etiquetaCantidad.setSize(new Dimension(220, 25));
        panelInf.add(etiquetaCantidad);

        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        spinnerCantidad.setBounds(margenIzquierdo + 230, y, 60, 25);
        panelInf.add(spinnerCantidad);
        y += 40;

        // Tipo de persona
        Etiqueta etiquetaTipo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Tipo de persona:");
        etiquetaTipo.setLocation(margenIzquierdo, y);
        etiquetaTipo.setSize(new Dimension(220, 25));
        panelInf.add(etiquetaTipo);

        comboTipoPersona = new JComboBox<>(new String[]{"Cualquiera", "Estudiante", "Trabajador"});
        comboTipoPersona.setBounds(margenIzquierdo + 230, y, 120, 25);
        panelInf.add(comboTipoPersona);
        y += 40;

        // Sexo
        Etiqueta etiquetaSexo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Sexo:");
        etiquetaSexo.setLocation(margenIzquierdo, y);
        etiquetaSexo.setSize(new Dimension(220, 25));
        panelInf.add(etiquetaSexo);

        comboSexo = new JComboBox<>(new String[]{"Ambos", "Femenino", "Masculino"});
        comboSexo.setBounds(margenIzquierdo + 230, y, 120, 25);
        panelInf.add(comboSexo);
        y += 40;

        // Día de la semana
        Etiqueta etiquetaDia = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Día de la semana:");
        etiquetaDia.setLocation(margenIzquierdo, y);
        etiquetaDia.setSize(new Dimension(220, 25));
        panelInf.add(etiquetaDia);

        // Suponemos que hay una función getDiasSemana()
        ArrayList<String> diasSemana = getDiasSemana();
        comboDiasSemana = new JComboBox<>((ComboBoxModel) diasSemana);
        comboDiasSemana.setBounds(margenIzquierdo + 230, y, 120, 25);
        panelInf.add(comboDiasSemana);
        y += 40;

        // Lista de horarios
        Etiqueta etiquetaHorarios = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Horarios registrados:");
        etiquetaHorarios.setLocation(margenIzquierdo, y);
        etiquetaHorarios.setSize(new Dimension(220, 25));
        panelInf.add(etiquetaHorarios);

        modeloHorarios = new DefaultListModel<>();
        listaHorarios = new JList<>(modeloHorarios);
        JScrollPane scrollHorarios = new JScrollPane(listaHorarios);
        scrollHorarios.setBounds(margenIzquierdo, y + 30, 320, 70);
        panelInf.add(scrollHorarios);

        // Botón añadir horario
        Boton botonAddHorario = new Boton("Añadir horario");
        botonAddHorario.setNuevoSize(new Dimension(140, 32));
        botonAddHorario.setLocation(margenIzquierdo + 340, y + 30);
        botonAddHorario.setColorLetra(Color.WHITE);
        botonAddHorario.setColorFondo(paleta.getColorCaracteristico());

        // INTEGRACIÓN: abrir la pantalla de añadir horario y agregar a la lista
        botonAddHorario.addActionListener(e -> {
            PantallaAddHorario dialog = new PantallaAddHorario(this);
            if (dialog.isAceptado()) {
                String inicio = dialog.getHoraInicio();
                String fin = dialog.getHoraFin();
                if (!inicio.isEmpty() && !fin.isEmpty()) {
                    String horario = inicio + "-" + fin;
                    // Evitar duplicados en la lista
                    boolean existe = false;
                    for (int i = 0; i < modeloHorarios.getSize(); i++) {
                        if (modeloHorarios.getElementAt(i).equals(horario)) {
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        modeloHorarios.addElement(horario);
                    } else {
                        JOptionPane.showMessageDialog(this, "Ese horario ya está registrado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        panelInf.add(botonAddHorario);

        // Al cambiar el día, refresca la lista de horarios
        comboDiasSemana.addActionListener(e -> recargarHorarios());

        // Primera carga de horarios para el día seleccionado por defecto
        recargarHorarios();

        // Altura dinámica del panel
        panelInf.setPreferredSize(new Dimension(this.getSize().width, y + 150));
    }

    private void inicializarTitulo() {
        panelTitulo = new JPanel();
        panelTitulo.setBackground(contentPane.getBackground());
        panelTitulo.setSize(this.getSize().width, 50);
        panelTitulo.setPreferredSize(panelTitulo.getSize());
        panelTitulo.setLayout(new BorderLayout());

        Etiqueta titulo = new Etiqueta(fuente, paleta.getColorLetraMenu(), "Configuración de Turno");
        titulo.setBold(true);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitulo.add(titulo);
    }

    // Suponiendo que esta función devuelve los días de la semana
    private ArrayList<String> getDiasSemana() {
        // TODO: Implementa según tu lógica real
        ArrayList<String> Dias = new ArrayList<>();
        return dias;
    }

    // Carga o recarga la lista de horarios
    private void recargarHorarios() {
        modeloHorarios.clear();
        ArrayList<Horario> horarios = (ArrayList<Horario>) ServicesLocator.getInstance().getHorarioServices().getAllHorarios();;
        for (Horario h : horarios) {
            modeloHorarios.addElement(h.toString());
        }
    }


    public static Horario stringToHorario(String s) {
        if (s == null || !s.contains("-")) return null;
        String[] partes = s.split("-");
        if (partes.length != 2) return null;
        try {
            LocalTime inicio = LocalTime.parse(partes[0]);
            LocalTime fin = LocalTime.parse(partes[1]);
            return new Horario(inicio, fin);
        } catch (Exception e) {
            // Si el formato es inválido
            return null;
        }
    }
}