package gui.pantallasEmergentes;

import gui.secciones.Ventana;
import services.Gestor;
import utils.exceptions.EntradaInvalidaException;
import utils.exceptions.MultiplesErroresException;

public class PantallaAddEstudiante extends PantallaAddPersona {

    @Override
    protected void realizarAccion() {
        try {
            Gestor.getInstance().getFacultad().annadirEstudiante(boxID.getText(), boxNombre.getText(), boxApellidos.getText(), sexo);
            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "A�adido Exitosamente", "Estudiante a�adido Exitosamente", "Aceptar");
            this.dispose();
        } catch (EntradaInvalidaException e1) {
            String string = "<html><p style='text-align: center;'> ERROR <br><br><br>" + e1.getMessage() + "</p></html>";
            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", string, "Aceptar");
        } catch (MultiplesErroresException e1) {
            StringBuilder stringAux = new StringBuilder();
            for (String error : e1.getErrores()) {
                stringAux.append(error).append("<br>");
            }

            String string = "<html><p style='text-align: center;'> ERROR <br><br>" + stringAux + "</p></html>";
            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Errores", string, "Aceptar");
        }
    }


}
