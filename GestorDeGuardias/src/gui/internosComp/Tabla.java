package gui.internosComp;

import gui.auxiliares.IsTabla;
import gui.auxiliares.Paleta;
import gui.componentes.Cuadro;
import gui.componentes.CuadroRectoAbajo;
import gui.componentes.CustomScrollBar;
import gui.componentes.Etiqueta;
import gui.pantallasEmergentes.Advertencia;
import gui.secciones.Ventana;
import model.DiaGuardia;
import services.Gestor;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static gui.auxiliares.ConvertidorFecha.traducDiaMes;


public class Tabla extends Cuadro implements IsTabla {
    private static final long serialVersionUID = 1L;

    private static final int redondez = Cuadro.redMED;
    private final Paleta paleta = new Paleta();
    //Tamanos
    private final int tituloLargo = 67;
    private final ArrayList<PanelDiaBase> panelesCasillas = new ArrayList<>();
    private final int sepIzquierda = 46;
    private final Color colorLetraTitulo = Color.WHITE;
    private final Font fuente = new Font("Arial", Font.BOLD, 13);
    private final int distX;
    private final int distY;
    private final JPanel contenedor;
    private final PanelOpcionesPlanif tablaOpciones;
    //Secciones
    CuadroRectoAbajo titulo;
    JPanel panelCasillas;
    Cuadro myself;
    ArrayList<DiaGuardia> dias;
    private int mouseX, mouseY;

    public Tabla(Dimension dimension, Color color, ArrayList<DiaGuardia> estosDias, PanelOpcionesPlanif tablaOpciones, int distX, int distY, JPanel contenedor) {
        super(dimension, redondez, color);
        PanelDia.setCasillaLargo(dimension.width - sepIzquierda * 2);
        this.setLayout(null);
        myself = this;
        this.setColorBorde(paleta.getColorCaracteristico());
        this.tablaOpciones = tablaOpciones;
        this.distX = distX;
        this.distY = distY;
        this.contenedor = contenedor;
        dias = estosDias;

        tablaOpciones.getGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Gestor.getInstance().guardar(dias);
                    String string = "<html><p>Guardado Exitoso<br><br></p><html>";

                    Advertencia advertencia = new Advertencia(new Dimension(530, 300), "Guardado Exitoso", string, "Aceptar");
                } catch (EntradaInvalidaException e1) {
                    String string = "<html><p style='text-align: center;'> ERROR <br><br><br>" + e1.getMessage() + "</p></html>";

                    Advertencia advertencia = new Advertencia(new Dimension(530, 300), "Advertencia", string, "Aceptar");
                }
            }
        });

        tablaOpciones.getBotonAut().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!tablaOpciones.getDiasSeleccionados().isEmpty()) {
                        Gestor.getInstance().crearPlanificacionAutomaticamente(tablaOpciones.getDiasSeleccionados());
                    } else {
                        Gestor.getInstance().crearPlanificacionAutomaticamente(dias);
                    }

                } catch (MultiplesErroresException e1) {
                    StringBuilder stringAux = new StringBuilder();
                    for (String error : e1.getErrores()) {
                        stringAux.append(error).append("<br>");
                    }

                    String string = "<html><p style='text-align: center;'> ERROR <br><br>" + stringAux + "</p></html>";
                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Errores", string, "Aceptar");
                } catch (EntradaInvalidaException e1) {
                    String string = "<html><p style='text-align: center;'> ERROR <br><br><br>" + e1.getMessage() + "</p></html>";
                    Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", string, "Aceptar");
                }
                actualizarVistaTabla();


            }

        });

        // Agregar MouseListener y MouseMotionListener al panel t�tulo
        inicializarTitulo();


        panelCasillas = new JPanel();
        panelCasillas.setBackground(Color.WHITE);
        panelCasillas.setLayout(new BoxLayout(panelCasillas, BoxLayout.Y_AXIS));
        panelCasillas.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));


        panelCasillas.add(Box.createRigidArea(new Dimension(10, 0)));
        for (int i = 0; i < dias.size(); i++) {
            DiaGuardia dia = dias.get(i);
            Component caja = Box.createRigidArea(new Dimension(0, 5));
            Color colorAux = (i % 2 == 0 ? paleta.getColorCasillaTabla() : paleta.getColorCasillaTablaVacia()); // Alternar colores
            PanelDia nuevoDia = new PanelDia(dia, colorAux, this);

            panelesCasillas.add(nuevoDia);
            panelCasillas.add(nuevoDia);
            panelCasillas.add(caja);

        }

        JScrollPane scrollPane = new JScrollPane(panelCasillas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        CustomScrollBar sp = new CustomScrollBar();
        sp.setOrientation(JScrollBar.HORIZONTAL);
        scrollPane.setHorizontalScrollBar(sp);

        scrollPane.setBounds(30, tituloLargo + 20, getWidth() - 50, this.getHeight() - tituloLargo - 35);

        add(titulo, BorderLayout.NORTH);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.SOUTH);
    }

    public void actualizar() {
        tablaOpciones.actualizarTablaSelec(panelesCasillas);
    }

    public void actualizarVistaTabla() {
        for (PanelDiaBase e : panelesCasillas) {
            e.actualizarTabla();
        }
    }

    private void inicializarTitulo() {
        titulo = new CuadroRectoAbajo(new Dimension(this.getWidth(), tituloLargo), redondez, paleta.getColorCaracteristico());
        titulo.setLayout(null);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(titulo.getBackground());
        panelTitulo.setLayout(null);
        panelTitulo.setSize(titulo.getWidth(), 20);
        panelTitulo.setLocation(80, titulo.getHeight() - panelTitulo.getHeight() - 10);

        titulo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        titulo.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Mover el panel Cuadro
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();

                int xReal = x - mouseX - distX;
                int yReal = y - mouseY - distY;

                int z = xReal + distX;

                int bultoCalculo = contenedor.getWidth() + distX - myself.getSize().width - tablaOpciones.getWidth();
                int bultoCalculo2 = tablaOpciones.getSize().height - myself.getSize().height;

                if (xReal <= 0) {
                    xReal = 0;
                }
                if (yReal <= 0) {
                    yReal = 0;
                }
                if (z > contenedor.getWidth() + distX - myself.getSize().width - tablaOpciones.getWidth()) {
                    xReal = bultoCalculo - distX;
                }
                if (yReal > bultoCalculo2) {
                    yReal = bultoCalculo2;
                }

                setLocation(xReal, yReal);
            }
        });


        Etiqueta ID = new Etiqueta(fuente, colorLetraTitulo, "Carnet");
        int auxY = (panelTitulo.getSize().height - ID.getSize().height) / 2;

        Etiqueta diaT = new Etiqueta(fuente, colorLetraTitulo, "Dia");
        diaT.setPreferredSize(diaT.getSize());

        Etiqueta nombre = new Etiqueta(fuente, colorLetraTitulo, "Nombre");
        nombre.setPreferredSize(nombre.getSize());

        Etiqueta apellidos = new Etiqueta(fuente, colorLetraTitulo, "Apellidos");
        apellidos.setPreferredSize(apellidos.getSize());

        Etiqueta horario = new Etiqueta(fuente, colorLetraTitulo, "Horario");
        horario.setPreferredSize(horario.getSize());

        int sepHorario = 120, sepID = 90, sepApellidos = 130, sepNombre = 170;

        diaT.setLocation(0, auxY);
        horario.setLocation(diaT.getWidth() + sepHorario, auxY);
        ID.setLocation(horario.getLocation().x + horario.getWidth() + sepID, auxY);
        apellidos.setLocation(ID.getLocation().x + ID.getWidth() + sepApellidos, auxY);
        nombre.setLocation(apellidos.getLocation().x + apellidos.getWidth() + sepNombre, auxY);


        panelTitulo.add(diaT);
        panelTitulo.add(horario);
        panelTitulo.add(ID);
        panelTitulo.add(apellidos);
        panelTitulo.add(nombre);

        int auxAgno = dias.getFirst().getFecha().getYear();
        Etiqueta planificacion = new Etiqueta(fuente, colorLetraTitulo, traducDiaMes(dias.get(0).getFecha()) + "  " + auxAgno);
        planificacion.setLocation(15, 10);


        titulo.add(panelTitulo);
        titulo.add(planificacion);

    }

    public ArrayList<DiaGuardia> getDias() {
        return dias;
    }

}
