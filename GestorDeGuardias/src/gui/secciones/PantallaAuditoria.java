package gui.secciones;

import gui.internosComp.TablaAuditoria;
import model.Auditoria;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

public class PantallaAuditoria extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private TablaAuditoria tabla;

    public PantallaAuditoria() {
        setLayout(new BorderLayout());
    }

    public TablaAuditoria getTabla() {
        return tabla;
    }

    public void setTabla(ArrayList<Auditoria> trazas) {
        if (tabla != null) {
            this.remove(tabla);
        }

        Ventana venAux = Ventana.getInstance();
        Dimension auxDim = new Dimension(venAux.getPanelVacio().getSize().width, venAux.getPanelVacio().getSize().height);
        this.tabla = new TablaAuditoria(auxDim, Color.WHITE, trazas);

        this.add(tabla, BorderLayout.CENTER);
        this.tabla.actualizarVistaTabla();

        revalidate();
        repaint();
    }
}