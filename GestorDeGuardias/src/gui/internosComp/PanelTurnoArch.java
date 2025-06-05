package gui.internosComp;

import gui.auxiliares.Actualizable;
import gui.componentes.CustomRadioButton;
import model.DiaGuardia;
import model.TurnoDeGuardia;
import services.ServicesLocator;
import utils.dao.SqlServerCustomException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTurnoArch extends PanelTurno{
    private static final long serialVersionUID = 1L;

    private CustomRadioButton cumplido, noCumplido;
    private ButtonGroup radioGrupo;

    public PanelTurnoArch(int ancho, Color color, final TurnoDeGuardia turno, DiaGuardia fecha, final Actualizable act) {
        super(ancho, color, turno, fecha, act);
        LayoutManager layout = this.getLayout();

        if (layout instanceof FlowLayout flowLayout) {
            if (this.getSize().width - 1000 > 150) {
                flowLayout.setHgap(30);
            } else {
                flowLayout.setHgap(10);
            }
        }


        cumplido = new CustomRadioButton("Cumplido");
        noCumplido = new CustomRadioButton("Incumplido");

        cumplido.setNuevaFuente(fuente);


        noCumplido.setNuevaFuente(fuente);

        radioGrupo = new ButtonGroup();
        radioGrupo.add(cumplido);
        radioGrupo.add(noCumplido);

        if(turno.getCumplimiento() != null){
            if(turno.getCumplimiento()){
                cumplido.setSelected(true);
                turno.actualizarCumplimiento(true);
            } if(!turno.getCumplimiento()){
                noCumplido.setSelected(true);
                turno.actualizarCumplimiento(false);
            }
        }

        cumplido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ServicesLocator.getInstance().getTurnoDeGuardiaServices().updateTurnoDeGuardia(turno);
                } catch (SqlServerCustomException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        noCumplido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turno.actualizarCumplimiento(false);
                try {
                    ServicesLocator.getInstance().getTurnoDeGuardiaServices().updateTurnoDeGuardia(turno);
                } catch (SqlServerCustomException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(cumplido);
        add(noCumplido);

    }

    public void cambiar(Color fondo,Color letra){
        super.cambiar(fondo, letra);
        cumplido.setForeground(letra);
        noCumplido.setForeground(letra);
    }

    public CustomRadioButton getCumplido() {
        return cumplido;
    }

    public CustomRadioButton getNoCumplido() {
        return noCumplido;
    }

    public ButtonGroup getRadioGrupo() {
        return radioGrupo;
    }


}

