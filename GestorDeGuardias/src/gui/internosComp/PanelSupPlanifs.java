package gui.internosComp;

import gui.auxiliares.Paleta;
import gui.componentes.Boton;
import gui.pantallasEmergentes.Advertencia;
import gui.secciones.MostrarPlanif;
import gui.secciones.Ventana;
import model.DiaGuardia;
import services.ReporteServices;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;
import utils.exceptions.EntradaInvalidaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelSupPlanifs extends JPanel{
    MostrarPlanif panelAfectado;
    public PanelSupPlanifs(int alto, MostrarPlanif panelAfectado) {
        this.panelAfectado = panelAfectado;
        setBackground(new Paleta().getColorFondoTabla());

        Boton nuevoBtn = new Boton();
        nuevoBtn.addIcono("/iconos/Crear.png");
        nuevoBtn.setSelectLetra(true);
        nuevoBtn.cambiarIconTextGap(10);
        nuevoBtn.addActionListener(e -> Ventana.getInstance().mostrarPanel("panelAddPlanif"));


        Boton editarBtn = new Boton();
        editarBtn.addIcono("/iconos/Editar.png");
        editarBtn.setSelectLetra(true);
        editarBtn.cambiarIconTextGap(10);
        editarBtn.addActionListener(e -> editar());

        Boton borrarBtn = new Boton();
        borrarBtn.addIcono("/iconos/Borrar.png");
        borrarBtn.setSelectLetra(true);
        borrarBtn.cambiarIconTextGap(10);
        borrarBtn.addActionListener(e -> borrar());

        Boton pdfBtn = new Boton();
        pdfBtn.addIcono("/iconos/PDF.png");
        pdfBtn.setSelectLetra(true);
        pdfBtn.cambiarIconTextGap(10);
        pdfBtn.addActionListener(e -> exportar());

        add(nuevoBtn);
        add(editarBtn);
        add(borrarBtn);
        add(pdfBtn);

        FlowLayout miLayout = new FlowLayout(FlowLayout.LEFT, 5, alto - nuevoBtn.getHeight() - 8);
        setLayout(miLayout);
        /*
        borrarBtn.setSeleccionable(false);
        editarBtn.setSeleccionable(false);
        nuevoBtn.setSeleccionable(true);
        pdfBtn.setSeleccionable(false);
         */
    }

    private void borrar() {
        String string = "<html><p>Si borras una planificación las posteriores <br>tambien se perderan. Esta accion no se puede retroceder<br><br>Presione aceptar para continuar</p></html>";
        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Advertencia", string, "Cancelar", "Aceptar");
        if (!advertencia.getEleccion()) {
            try {
                ServicesLocator.getInstance().getTurnoDeGuardiaServices().deleteTurnosDeGuardiaAPartirDe(panelAfectado.getSeleccionado().getFechaInicio());
            } catch (SqlServerCustomException | EntradaInvalidaException ex) {
                String errorMsg = "<html><p>Ocurrió un error:<br>" + ex.getMessage() + "</p></html>";
                new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", errorMsg, "Aceptar", true);

            }
            panelAfectado.actualizarPlanif();
            panelAfectado.getSeleccionado().setSeleccionado(false);
        /*
        borrarBtn.setSeleccionable(false);
        editarBtn.setSeleccionable(false);
        nuevoBtn.setSeleccionable(true);
        pdfBtn.setSeleccionable(false);
         */
            revalidate();
            repaint();
        }
    }

    private void exportar() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte PDF");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF", "pdf"));
        int seleccion = chooser.showSaveDialog(panelAfectado);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.toLowerCase().endsWith(".pdf")) path += ".pdf";

            // Obtiene todos los turnos a partir de la fecha de inicio seleccionada
            ArrayList<DiaGuardia> diasGuardia = ServicesLocator.getInstance()
                    .getPlantillaServices()
                    .getPlanificacionesAPartirDe(panelAfectado.getSeleccionado().getFechaInicio());
            // 3. Llamar al servicio de reporte

            new ReporteServices().generarReportePlantilla(diasGuardia, path);

            JOptionPane.showMessageDialog(panelAfectado, "PDF generado exitosamente:\n" + path, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void editar(){
        if (panelAfectado.getSeleccionado() != null) {
            ArrayList<DiaGuardia> diasAux = ServicesLocator.getInstance().getPlantillaServices()
                    .agruparPorDia(ServicesLocator.getInstance().getTurnoDeGuardiaServices()
                            .getTurnosAPartirDe(panelAfectado.getSeleccionado().getFechaInicio()));
            Ventana.getInstance().editarPlanif(diasAux);
        }

    }


}
