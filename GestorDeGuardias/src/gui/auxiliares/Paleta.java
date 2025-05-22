package gui.auxiliares;


import java.awt.*;

public class Paleta {

    private static Modo modo;
    private final Color colorDestacarCuadro = new Color(240, 243, 250);
    private Color colorFondo = new Color(240, 243, 250);
    private Color colorCaracteristico = new Color(49, 123, 244);
    private Color colorFondoTabla = new Color(255, 255, 255);
    private Color colorCasillaTabla = new Color(241, 241, 243);
    private Color colorCasillaTablaVacia = Color.WHITE;
    private Color colorBorde = new Color(230, 233, 248);
    //Letras
    private Color colorLetraMenu = new Color(62, 63, 67);

    private Color colorLetraSelec = colorCaracteristico;
    private Color colorIcono = new Color(154, 184, 218);
    private Color letraOscura = new Color(82, 99, 166);

    public Color getColorFondoTabla() {
        return colorFondoTabla;
    }

    public void setColorFondoTabla(Color colorFondoTabla) {
        this.colorFondoTabla = colorFondoTabla;
    }

    public Color getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    public Color getColorLetraMenu() {
        return colorLetraMenu;
    }

    public void setColorLetraMenu(Color colorLetraMenu) {
        this.colorLetraMenu = colorLetraMenu;
    }

    public Color getColorLetraSelec() {
        return colorLetraSelec;
    }

    public void setColorLetraSelec(Color colorLetraSelec) {
        this.colorLetraSelec = colorLetraSelec;
    }

    public Color getColorIcono() {
        return colorIcono;
    }

    public void setColorIcono(Color colorIcono) {
        this.colorIcono = colorIcono;
    }

    public Color getColorBorde() {
        return colorBorde;
    }

    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
    }

    public Color getLetraOscura() {
        return letraOscura;
    }

    public void setLetraOscura(Color letraOscura) {
        this.letraOscura = letraOscura;
    }

    public Color getColorCaracteristico() {
        return colorCaracteristico;
    }

    public void setColorCaracteristico(Color colorCaracteristico) {
        this.colorCaracteristico = colorCaracteristico;
    }

    public Color getColorCasillaTabla() {
        return colorCasillaTabla;
    }

    public void setColorCasillaTabla(Color colorCasillaTabla) {
        this.colorCasillaTabla = colorCasillaTabla;
    }

    public Color getColorCasillaTablaVacia() {
        return colorCasillaTablaVacia;
    }

    public void setColorCasillaTablaVacia(Color colorCasillaTablaVacia) {
        this.colorCasillaTablaVacia = colorCasillaTablaVacia;
    }

    enum Modo {
        Claro,
        Oscuro
    }

}
