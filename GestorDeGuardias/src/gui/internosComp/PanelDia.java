package gui.internosComp;

import gui.auxiliares.Actualizable;
import gui.auxiliares.IsTabla;
import model.DiaGuardia;

import javax.swing.*;
import java.awt.*;

public class PanelDia extends PanelDiaBase implements Actualizable {
    private static final long serialVersionUID = 1L;

    public PanelDia(DiaGuardia dia, Color color, IsTabla tabla) {
        super(dia, color, tabla);
    }

    @Override
    protected void iniciarCasilla() {
        {
            this.setSize(casillaLargo, getAncho());

            panelTurnos.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

            for (int i = 0; i < dia.getTurnos().size(); i++) {
                PanelTurno nuevo = new PanelTurno(casillaLargo - anchoFecha, getBackground(), dia.getTurnos().get(i), this.dia, this);
                panelTurnos.add(nuevo);
                panelesTurno.add(nuevo);
                panelTurnos.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
    }

}