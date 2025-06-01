package gui.componentes;

import gui.auxiliares.Paleta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CustomTabla extends JPanel {
    private final Paleta paleta = new Paleta();
    private final JTable tabla;
    private final DefaultTableModel modelo;
    private ArrayList<Component> componentes;

    public CustomTabla(String texto) {
        // Crear el modelo de la tabla sin encabezado
        modelo = new DefaultTableModel(new String[]{texto}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir edici�n de celdas
            }
        };
        tabla = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir edici�n de celdas
            }
        };

        componentes = new ArrayList<Component>();


        // Configurar la tabla para que no muestre divisiones
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(5, 5)); // Separaci�n entre celdas
        tabla.setRowHeight(30); // Ajustar la altura de las filas

        // Configurar el color de fondo de las celdas de la tabla
        tabla.setBackground(paleta.getColorCasillaTabla());
        tabla.setOpaque(false); // Hacer que las celdas sean transparentes

        // Eliminar el encabezado de la tabla
        tabla.setTableHeader(null); // Esto eliminar� el encabezado

        // Crear un JScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        scrollPane.setOpaque(false); // Hacer que el JScrollPane sea transparente
        scrollPane.getViewport().setBackground(paleta.getColorFondoTabla()); // Establecer el fondo blanco
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Crear un panel para el t�tulo
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(paleta.getColorFondoTabla()); // Establecer el fondo en Color.PINK
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alinear a la izquierda

        // Crear una etiqueta para el t�tulo
        JLabel titleLabel = new JLabel(texto);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente en negrita y tama�o 20
        titleLabel.setForeground(paleta.getColorLetraMenu()); // Color del texto en azul

        // Agregar la etiqueta al panel del t�tulo
        titlePanel.add(titleLabel);

        // Agregar el panel del t�tulo y el JScrollPane al panel principal
        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH); // Agregar el panel del t�tulo en la parte superior
        add(scrollPane, BorderLayout.CENTER); // Agregar la tabla en el centro
    }

    public void agregarFila(String texto) {
        modelo.addRow(new Object[]{texto});

    }


    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    public int getCantFilas() {
        return modelo.getRowCount();
    }

    public ArrayList<Component> getComponentes() {
        return componentes;
    }

    public void setComponentes(ArrayList<Component> componentes) {
        this.componentes = componentes;
    }
}