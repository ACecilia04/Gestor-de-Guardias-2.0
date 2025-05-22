package gui.pantallasEmergentes;

import gui.secciones.Ventana;
import logica.excepciones.EntradaInvalidaException;
import logica.excepciones.MultiplesErroresException;
import services.Gestor;

public class PantallaAddTrabajador extends PantallaAddPersona {

    @Override
    protected void realizarAccion() {
        try {
            Gestor.getInstance().getFacultad().annadirTrabajador(boxID.getText(), boxNombre.getText(), boxApellidos.getText(), sexo);
            Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "A�adido Exitosamente", "Trabajador a�adido Exitosamente", "Aceptar");
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
