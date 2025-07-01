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
import java.util.ArrayList;

public class PanelSupOpcionesPlanifs extends JPanel {

    private MostrarPlanif panelReferencia;

    private final Boton nuevoBtn;
    private final Boton editarBtn;
    private final Boton borrarBtn;
    private final Boton pdfBtn;
    private boolean algunMesSeleccionado;

    public PanelSupOpcionesPlanifs(int alto) {
        setBackground(new Paleta().getColorFondoTabla());

        nuevoBtn = new Boton();
        nuevoBtn.addIcono("/iconos/Crear.png");
        nuevoBtn.setSelectLetra(true);
        nuevoBtn.cambiarIconTextGap(10);
        nuevoBtn.setToolTipText("Crear Nuevo");

        nuevoBtn.addActionListener(e -> Ventana.getInstance().mostrarPanel("panelAddPlanif"));

        editarBtn = new Boton();
        editarBtn.addIcono("/iconos/Editar.png");
        editarBtn.setSelectLetra(true);
        editarBtn.cambiarIconTextGap(10);
        editarBtn.setEnabled(algunMesSeleccionado);
        editarBtn.setToolTipText("Editar");
        editarBtn.addActionListener(e -> editar());

        borrarBtn = new Boton();
        borrarBtn.addIcono("/iconos/Borrar.png");
        borrarBtn.setSelectLetra(true);
        borrarBtn.cambiarIconTextGap(10);
        borrarBtn.setEnabled(algunMesSeleccionado);
        borrarBtn.setToolTipText("Borrar");
        borrarBtn.addActionListener(e -> borrar());

        pdfBtn = new Boton();
        pdfBtn.addIcono("/iconos/PDF.png");
        pdfBtn.setSelectLetra(true);
        pdfBtn.cambiarIconTextGap(10);
        pdfBtn.setEnabled(algunMesSeleccionado);
        pdfBtn.setToolTipText("Exportar PDF");
        pdfBtn.addActionListener(e -> exportar());

        add(nuevoBtn);
        add(editarBtn);
        add(borrarBtn);
        add(pdfBtn);

        FlowLayout miLayout = new FlowLayout(FlowLayout.RIGHT, 5, alto - nuevoBtn.getHeight() - 8);
        setLayout(miLayout);
    }

    private void borrar() {
        String string = "<html><p>Si borras una planificación las posteriores <br>tambien se perderan. Esta accion no se puede retroceder<br><br>Presione aceptar para continuar</p></html>";
        Advertencia advertencia = new Advertencia(Ventana.SIZE_ADVERTENCIA, "Advertencia", string, "Cancelar", "Aceptar");
        if (!advertencia.getEleccion()) {
            try {
                ServicesLocator.getInstance().getTurnoDeGuardiaServices().deleteTurnosDeGuardiaAPartirDe(panelReferencia.getSeleccionado().getFechaInicio());
            } catch (SqlServerCustomException | EntradaInvalidaException ex) {
                String errorMsg = "<html><p>Ocurrió un error:<br>" + ex.getMessage() + "</p></html>";
                new Advertencia(Ventana.SIZE_ADVERTENCIA, "Error", errorMsg, "Aceptar", true);

            }
            panelReferencia.actualizarPlanif();
            panelReferencia.setSeleccionado(null);

            revalidate();
            repaint();
        }
    }

    private void exportar() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte PDF");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF", "pdf"));
        int seleccion = chooser.showSaveDialog(panelReferencia);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.toLowerCase().endsWith(".pdf")) path += ".pdf";

            // Obtiene todos los turnos a partir de la fecha de inicio seleccionada
            ArrayList<DiaGuardia> diasGuardia = ServicesLocator.getInstance()
                    .getPlantillaServices()
                    .getPlanificacionesAPartirDe(panelReferencia.getSeleccionado().getFechaInicio());
            // 3. Llamar al servicio de reporte

            new ReporteServices().generarReportePlantilla(diasGuardia, path);

            JOptionPane.showMessageDialog(panelReferencia, "PDF generado exitosamente:\n" + path, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void editar() {
        if (panelReferencia.getSeleccionado() != null) {
            ArrayList<DiaGuardia> diasAux = ServicesLocator.getInstance().getPlantillaServices()
                    .agruparPorDia(ServicesLocator.getInstance().getTurnoDeGuardiaServices()
                            .getTurnosAPartirDe(panelReferencia.getSeleccionado().getFechaInicio()));
            Ventana.getInstance().editarPlanif(diasAux);
        }
    }

    public void setAlgunMesSeleccionado(boolean algunMesSeleccionado) {
        this.algunMesSeleccionado = algunMesSeleccionado;
        editarBtn.setEnabled(algunMesSeleccionado);
        borrarBtn.setEnabled(algunMesSeleccionado);
        pdfBtn.setEnabled(algunMesSeleccionado);
    }


    public void setPanelReferencia(MostrarPlanif panelReferencia) {
        this.panelReferencia = panelReferencia;
    }
}
