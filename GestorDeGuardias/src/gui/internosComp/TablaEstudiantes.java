package gui.internosComp;

import gui.auxiliares.CustomTablaComplex;
import model.Persona;

import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;

public class TablaEstudiantes extends CustomTablaComplex {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void revalidarTabla(ArrayList<Persona> personas) {
        modelo.setRowCount(0);
        for (Persona e : personas) {
            if (e.getTipo().equals("estudiante")) {
                String[] aux = new String[6];
                aux[0] = e.getCarnet();
                aux[1] = e.getApellido();
                aux[2] = e.getNombre();
                aux[3] = e.getSexo();
                aux[4] = e.getDisponibilidad(LocalDate.now());
                aux[5] = String.valueOf(e.getDiasDesdeUltimaGuardiaAsignada());

                modelo.addRow(aux);
            }
        }
        tabla.revalidate();
        tabla.repaint();

    }


    @Override
    public String getTitulo() {
        return "Estudiantes";
    }

    @Override
    public String[] getColumnas() {
        return new String[]{"ID", "Apellidos", "Nombre", "Sexo", "Disponibilidad", "Días desde última Asignación"};
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
