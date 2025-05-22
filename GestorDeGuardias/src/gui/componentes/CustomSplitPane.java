package gui.componentes;

import gui.auxiliares.Paleta;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

@SuppressWarnings("serial")
public class CustomSplitPane extends JSplitPane {
    private final Paleta paleta = new Paleta();

    private Color colorLineaDivisoria = paleta.getColorBorde();
    private final int grosor = 5;

    public CustomSplitPane(JComponent topComponent, JComponent bottomComponent, int x) {
        super(x);

        if (x == JSplitPane.VERTICAL_SPLIT) {
            setTopComponent(topComponent);
            setBottomComponent(bottomComponent);

        } else {
            this.setLeftComponent(topComponent);
            this.setRightComponent(bottomComponent);
        }
        this.setDividerSize(grosor);


        // Personalizar el color de la l�nea divisoria
        setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(colorLineaDivisoria);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
            }
        });
        setBorder(null);
    }

    public Color getLineaDivisoria() {
        return colorLineaDivisoria;
    }

    public void setLineaDivisoria(Color lineaDivisoria) {
        this.colorLineaDivisoria = lineaDivisoria;
    }
}
