package gui.pantallasEmergentes;

import gui.secciones.Ventana;
import model.Persona;
import model.TipoPersona;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;
import utils.exceptions.MultiplesErroresException;

import javax.swing.*;

public class PantallaAddTrabajador extends PantallaAddPersona {

    @Override
    protected void realizarAccion() {
        try {
            Persona nuevaPersona = new Persona(boxID.getText(), boxNombre.getText(), boxApellidos.getText(), sexo, new TipoPersona("Trabajador"));
            ServicesLocator.getInstance().getPersonaServices().insertPersona(nuevaPersona);
            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Añadido Exitosamente", "Trabajador añadido Exitosamente", "Aceptar");
            this.dispose();
        }catch (MultiplesErroresException e) {
            StringBuilder errores = new StringBuilder();
            for (String error : e.getErrores()) {
                errores.append(error).append("\n");
            }
            JOptionPane.showMessageDialog(this, errores.toString(), "Errores de validación", JOptionPane.ERROR_MESSAGE);
        }catch (SqlServerCustomException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errores de validación", JOptionPane.ERROR_MESSAGE);
        }
    }
}
