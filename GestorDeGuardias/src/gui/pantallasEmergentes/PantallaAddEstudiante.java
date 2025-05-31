package gui.pantallasEmergentes;

import gui.secciones.Ventana;
import model.Persona;
import model.TipoPersona;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;
import utils.exceptions.MultiplesErroresException;

import javax.swing.*;

public class PantallaAddEstudiante extends PantallaAddPersona {

    @Override
    protected void realizarAccion() {
        try {
            Persona nuevaPersona = new Persona(boxID.getText(), boxNombre.getText(), boxApellidos.getText(), sexo, new TipoPersona("Estudiante"));
            ServicesLocator.getInstance().getPersonaServices().insertPersona(nuevaPersona);
            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "A�adido Exitosamente", "Estudiante a�adido Exitosamente", "Aceptar");
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
