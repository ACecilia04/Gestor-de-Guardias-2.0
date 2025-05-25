package gui.componentes;

import gui.auxiliares.Paleta;
import utils.exceptions.EntradaInvalidaException;
import model.Persona;
import services.Gestor;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Buscar extends CustomTextField {
    private static final Paleta paleta = new Paleta();
    private static final Color colorFondo = paleta.getColorCasillaTabla();
    private static final Dimension dim = new Dimension(230, 35);
    private final Boton boton;
    private final String icono = "/iconos/Buscar.png";

    public Buscar(String textoPredet, int maxLetras) {
        super(dim, textoPredet, maxLetras, colorFondo);
        this.setColorSelec(paleta.getColorCaracteristico());
        boton = new Boton();
        boton.addIcono(icono);
        boton.setLocation(textField.getLocation().x + textField.getWidth() + 10, (dim.height - boton.getHeight()) / 2);
        boton.setSelectLetra(true);
        boton.setColorFondo(colorFondo);
        boton.setColorIcono(paleta.getColorCaracteristico());
        boton.setColorIconoPres(paleta.getColorCaracteristico().darker());

        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                boton.setColorFondo(paleta.getColorCaracteristico());
                boton.setColorIcono(Color.WHITE);
                boton.setColorIconoPres(Color.YELLOW);
            }

            public void focusLost(FocusEvent evt) {
                boton.setColorFondo(colorFondo);
                boton.setColorIcono(paleta.getColorCaracteristico());
                boton.setColorIconoPres(paleta.getColorCaracteristico().darker());
            }
        });

        this.add(boton);

    }

    public Persona buscar() throws EntradaInvalidaException {
        String ID = this.getText();
        return Gestor.getInstance().getFacultad().buscarPersona(ID);
    }

    public Boton getBoton() {
        return boton;
    }
}
