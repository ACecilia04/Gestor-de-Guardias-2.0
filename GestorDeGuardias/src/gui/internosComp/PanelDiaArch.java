package gui.internosComp;

import gui.auxiliares.Actualizable;
import gui.auxiliares.IsTabla;
import model.DiaGuardia;
import model.TurnoDeGuardia;

import javax.swing.*;
import java.awt.*;

public class PanelDiaArch extends PanelDiaBase implements Actualizable {
    private static final long serialVersionUID = 1L;

    public PanelDiaArch(DiaGuardia dia, Color color, IsTabla tabla) {
        super(dia, color, tabla);
    }

    @Override
    protected void iniciarCasilla() {
        {
            this.setSize(casillaLargo, getAncho());

            panelTurnos.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

            for (TurnoDeGuardia turno : dia.getTurnos()) {
                PanelTurno nuevo = new PanelTurnoArch(casillaLargo - anchoFecha, getBackground(), turno, dia, this);
                panelTurnos.add(nuevo);
                panelesTurno.add(nuevo);
                panelTurnos.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
    }

}