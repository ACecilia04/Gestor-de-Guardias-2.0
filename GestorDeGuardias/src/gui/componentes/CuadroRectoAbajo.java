package gui.componentes;

import java.awt.*;
import java.awt.geom.Path2D;


@SuppressWarnings("serial")
public class CuadroRectoAbajo extends Cuadro {
    public CuadroRectoAbajo(Dimension dimension, int redondez, Color color) {
        super(dimension, redondez, color);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Crear una forma personalizada con esquinas superiores redondeadas
        Path2D path = new Path2D.Double();
        path.moveTo(0, redondez); // Mover a la esquina superior izquierda
        path.lineTo(0, getHeight()); // L�nea hacia abajo
        path.lineTo(getWidth(), getHeight()); // L�nea hacia la esquina inferior derecha
        path.lineTo(getWidth(), redondez); // L�nea hacia arriba
        path.quadTo(getWidth(), 0, getWidth() - redondez, 0); // Esquina superior derecha redondeada
        path.lineTo(redondez, 0); // L�nea hacia la esquina superior izquierda redondeada
        path.quadTo(0, 0, 0, redondez); // Esquina superior izquierda redondeada
        path.closePath(); // Cerrar la forma

        // Rellenar la forma
        g2.setColor(getBackground());
        g2.fill(path);
    }

}
