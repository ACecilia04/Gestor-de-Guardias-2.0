package gui.auxiliares;

import gui.componentes.CustomScrollBar;
import model.Persona;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

public abstract class CustomTablaComplex extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    protected Paleta paleta = new Paleta();
    protected JTable tabla;
    protected ArrayList<Component> componentes;
    protected DefaultTableModel modelo;
    protected Font fuenteHeather = new Font("Arial", Font.PLAIN, 14);
    protected Color casilla1 = Color.WHITE;
    protected Color casilla2 = paleta.getColorCasillaTabla();
    protected int columnaSelec = -1;
    protected ArrayList<Actualizable> actualizables;

    public CustomTablaComplex() {

        modelo = new DefaultTableModel(getColumnas(), 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);

        componentes = new ArrayList<>();

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    columnaSelec = tabla.getSelectedRow(); // Get the selected row;
                    actualizar();
                }
            }
        });

        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 5));
        tabla.setRowHeight(30);


        tabla.setBackground(Color.WHITE);
        tabla.setOpaque(true);

        tabla.setDefaultRenderer(Object.class, new CustomCellRenderer());

        // Crear un JScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setVerticalScrollBar(new CustomScrollBar());
        scrollPane.setHorizontalScrollBar(new CustomScrollBar());
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setBackground(paleta.getColorFondoTabla());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Crear un panel para el t�tulo
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(paleta.getColorFondoTabla());
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Crear una etiqueta para el t�tulo
        JLabel titleLabel = new JLabel(getTitulo());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(paleta.getColorLetraMenu());


        titlePanel.add(titleLabel);

        ponerMinimo(75);


        tabla.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
        tabla.getTableHeader().setReorderingAllowed(false);

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void ponerMinimo(int ancho) {
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            TableColumn column = tabla.getColumnModel().getColumn(i);
            column.setMinWidth(ancho);
        }
    }

    public abstract void revalidarTabla(ArrayList<Persona> personas);

    public abstract String getTitulo();

    public abstract String[] getColumnas();

    public abstract void actualizar();


//    public void limpiarTabla() {
//        modelo.setRowCount(0);
//    }
//
//    public int getCantFilas() {
//        return modelo.getRowCount();
//    }

    public ArrayList<Component> getComponentes() {
        return componentes;
    }

    public void setComponentes(ArrayList<Component> componentes) {
        this.componentes = componentes;
    }

    public JTable getTabla() {
        return tabla;
    }

    public int getColumnaSelec() {
        return columnaSelec;
    }

//    public void setColumnaSelec(int x) {
//        columnaSelec = x;
//    }
//
//    public ArrayList<Actualizable> getActualizables() {
//        return actualizables;
//    }

    public void setActualizables(ArrayList<Actualizable> actualizables) {
        this.actualizables = actualizables;
    }

    public abstract String getCarnet();

    private class CustomCellRenderer extends DefaultTableCellRenderer {
        @Serial
        private static final long serialVersionUID = 1L;
        private final Font font = new Font("Arial", Font.PLAIN, 14);

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            final Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Set the background color based on selection state and row index
            if (isSelected) {
                cell.setBackground(paleta.getColorCaracteristico()); // Set background color to blue for selected cells
                cell.setForeground(Color.WHITE);
            } else {
                // Alternate colors based on the row index
                cell.setForeground(paleta.getColorLetraMenu());
                if (row % 2 == 0) {
                    cell.setBackground(casilla2); // Even rows
                } else {
                    cell.setBackground(casilla1); // Odd rows

                }
            }


            cell.setFont(font); // Set the custom font
            return cell;
        }
    }

    private class CustomHeaderRenderer extends DefaultTableCellRenderer {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Set the background color based on selection state and row index
            cell.setBackground(Color.WHITE); // Set background color to blue for selected cells

            cell.setFont(fuenteHeather); // Set the custom font
            return cell;
        }
    }

}