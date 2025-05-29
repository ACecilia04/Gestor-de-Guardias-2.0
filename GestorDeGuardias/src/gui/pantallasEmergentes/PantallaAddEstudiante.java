package gui.pantallasEmergentes;

import gui.secciones.Ventana;
import model.Persona;
import model.TipoPersona;
import services.ServicesLocator;
import utils.exceptions.MultiplesErroresException;

public class PantallaAddEstudiante extends PantallaAddPersona {

    @Override
    protected void realizarAccion() {
        try {
            Persona nuevaPersona = new Persona(boxID.getText(), boxNombre.getText(), boxApellidos.getText(), sexo, new TipoPersona("Estudiante"));
            ServicesLocator.getInstance().getPersonaServices().insertPersona(nuevaPersona);
            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "A�adido Exitosamente", "Estudiante a�adido Exitosamente", "Aceptar");
            this.dispose();
        } catch (MultiplesErroresException e) {
            e.printStackTrace();
//            StringBuilder stringAux = new StringBuilder();
//            for (String error : e1.getErrores()) {
//                stringAux.append(error).append("<br>");
//            }

//            String string = "<html><p style='text-align: center;'> ERROR <br><br>" + stringAux + "</p></html>";
//            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Errores", string, "Aceptar");
        }
    }


}
