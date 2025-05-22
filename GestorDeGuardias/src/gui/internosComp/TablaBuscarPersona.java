package gui.internosComp;

import gui.auxiliares.Actualizable;
import gui.auxiliares.CustomTablaComplex;
import rdb.entity.Persona;

import java.util.ArrayList;

public class TablaBuscarPersona extends CustomTablaComplex {
    private static final long serialVersionUID = 1L;

    public TablaBuscarPersona() {

    }

    @Override
    public void revalidarTabla(ArrayList<Persona> personas) {
        modelo.setRowCount(0);
        for (Persona e : personas) {
            String[] aux = new String[5];
            aux[0] = e.getCi();
            aux[1] = e.getApellidos();
            aux[2] = e.getNombre();
            aux[3] = Integer.toString(9);

            modelo.addRow(aux);

        }
        tabla.revalidate();
        tabla.repaint();

    }

    @Override
    public String getTitulo() {
        return "Buscar Persona";
    }

    @Override
    public String[] getColumnas() {
        String[] aux = {"ID", "Apellidos", "Nombre", "Cant Guardias"};
        return aux;
    }

    @Override
    public void actualizar() {
        for (Actualizable a : actualizables) {
            a.actualizar();
        }

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
