package gui.internosComp;

import gui.auxiliares.CustomTablaComplex;
import rdb.entity.Persona;
import rdb.entity.Trabajador;

import java.time.LocalDate;
import java.util.ArrayList;

public class TablaTrabajadores extends CustomTablaComplex {
    private static final long serialVersionUID = 1L;

    @Override
    public void revalidarTabla(ArrayList<Persona> personas) {
        modelo.setRowCount(0);
        for (Persona e : personas) {
            if (e instanceof Trabajador) {
                String[] aux = new String[7];
                aux[0] = e.getCi();
                aux[1] = e.getApellidos();
                aux[2] = e.getNombre();
                aux[3] = e.getSexo().name();
                aux[4] = e.getDisponibilidadParaFecha(LocalDate.now()).name();
                aux[5] = Integer.toString(9);

                modelo.addRow(aux);
            }
        }
        tabla.revalidate();
        tabla.repaint();
    }

    @Override
    public String getTitulo() {
        return "Trabajadores";
    }

    @Override
    public String[] getColumnas() {
        String[] aux = {"ID", "Apellidos", "Nombre", "Sexo", "Disponibilidad", "Cant Guardias"};
        return aux;
    }

    @Override
    public void actualizar() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getCarnet() {
        int selectedRow = tabla.getSelectedRow();
        String ID = null;
        if (selectedRow != -1) {
            ID = (String) modelo.getValueAt(selectedRow, 0);
        }
        return ID;
    }


}
