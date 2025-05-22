package gui.auxiliares;

import java.awt.*;

public class Separador {

    private final int posicion;
    private final double grosor;
    private Color color;

    public Separador(int x, double grosor, Color color) {
        posicion = x;
        this.grosor = grosor;
        this.color = color;
    }

    public Separador(int x, double grosor) {
        posicion = x;
        this.grosor = grosor;
    }

    //Set/Get**********************************************
    public int getPos() {
        return posicion;
    }

    public double getGrosor() {
        return grosor;
    }

    public Color getColor() {
        return color;
    }
}
