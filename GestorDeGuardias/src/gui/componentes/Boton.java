package gui.componentes;

import gui.auxiliares.Actualizable;
import gui.auxiliares.Paleta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Boton extends JPanel implements Actualizable {
    private final Paleta paleta = new Paleta();

    //Accion
    private ActionListener actionListener;
    private MouseAdapter adaptador;

    //Imprimir
    private int redondez = Cuadro.redBAJA;

    //Componentes
    private final JLabel etiqueta = new JLabel();
    private String textoLabel;

    //Tipo
    private boolean selecLetra = false;
    private boolean seleccionable = true;

    private boolean bordeado = false;
    private final int grosorBorde = 3;
    private Color colorBordeado = paleta.getColorCaracteristico();

    //Color
    private Color colorFondo;
    private Color colorPresionado;

    private Color colorIcono;
    private Color colorIconoPres = paleta.getColorCaracteristico();

    private Color colorLetra = paleta.getColorLetraMenu();
    private Color colorLetraPres = paleta.getColorLetraSelec();

    //Color cuando Bloqueado
    private final Color colorFondoBlock = paleta.getColorCasillaTabla();
    private final Color colorIconoBlock = Color.GRAY;
    private final Color colorLetraBlock = Color.GRAY;

    //Aux
    private int distanciaX = 2, distanciaY = 2;
    private boolean select = false;


    /**
     * Este es para Botones simples
     *
     */
    public Boton(String texto) {
        textoLabel = texto;
        inicializar();

        Font fuente = new Font("Arial", Font.PLAIN, 17);
        etiqueta.setFont(fuente);

        etiqueta.setSize(etiqueta.getPreferredSize());
        etiqueta.setLocation(distanciaX, distanciaY);
        add(etiqueta);
        this.setPreferredSize(new Dimension(etiqueta.getSize().width + distanciaX * 2, etiqueta.getHeight() + distanciaY * 2));
        this.setSize(getPreferredSize());
    }

    /**
     * Este es para Iconos sin texto
     */
    public Boton() {
        textoLabel = "";
        inicializar();

        etiqueta.setSize(etiqueta.getPreferredSize());
        etiqueta.setLocation(distanciaX, distanciaY);
        add(etiqueta);

        this.setPreferredSize(new Dimension(etiqueta.getSize().width + distanciaX * 2, etiqueta.getHeight() + distanciaY * 2));
        this.setSize(getPreferredSize());
    }

    //Lo que hago por amor al arte...
    private static BufferedImage changeIconColor(Image img, Color color) {
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.dispose();
        return bufferedImage;
    }

    private void inicializar() {
        setLayout(null);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        etiqueta.setOpaque(true);
        etiqueta.setText(textoLabel);
        etiqueta.setForeground(colorLetra);
        etiqueta.setBorder(new EmptyBorder(0, 0, 0, 0));

        adaptador = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Button clicked"));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                select = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                select = false;
                repaint();
            }
        };
        addMouseListener(adaptador);
    }

    /**
     * Icono a la izquierda
     * */
    public void addIcono(String rutaImagen) {
        ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
        etiqueta.setIcon(icono);
        etiqueta.setIconTextGap(15);
        etiqueta.setHorizontalAlignment(JLabel.LEFT);

        int alturaMayor = Math.max(icono.getIconHeight(), etiqueta.getSize().height);

        etiqueta.setSize(new Dimension(etiqueta.getPreferredSize().width, alturaMayor));
        if (etiqueta.getText().isEmpty()) {
            redondez = 0;

            this.setPreferredSize(new Dimension(etiqueta.getSize().width + 10, etiqueta.getSize().height + 10));
            setNuevoSize(this.getPreferredSize());

        } else {
            this.setPreferredSize(new Dimension(etiqueta.getSize().width + distanciaX * 2, etiqueta.getPreferredSize().height + distanciaY * 2));
            this.setSize(getPreferredSize());
        }

    }

    /**
     * Para crear boton con fondo
     * si no, transparente
     */
    private void setFondoContenedor() {
        Container cont = SwingUtilities.getAncestorOfClass(Container.class, this);
        if (cont != null && colorFondo == null) {
            colorFondo = cont.getBackground();
            colorPresionado = colorFondo.darker();
            setBackground(colorFondo);
        }
        if(cont != null)
             setBackground(cont.getBackground());

    }

    @Override
    public void addNotify() {
        super.addNotify();
        setFondoContenedor();
    }

//    public void setRedondez(int red) {
//        this.redondez = red;
//    }
//
//    public void setDistancias(int x, int y) {
//        this.distanciaX = x;
//        this.distanciaY = y;
//        etiqueta.setLocation(distanciaX, distanciaY);
//        this.setPreferredSize(new Dimension(etiqueta.getSize().width + distanciaX * 2, etiqueta.getHeight() + distanciaY * 2));
//        this.setSize(getPreferredSize());
//    }

    public void setText(String texto) {
        etiqueta.setText(texto);
        etiqueta.setSize(etiqueta.getPreferredSize());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!seleccionable) {
            g2.setColor(colorFondoBlock);
            etiqueta.setForeground(colorLetraBlock);
            etiqueta.setBackground(colorFondoBlock);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), redondez, redondez);
            ImageIcon icono = (ImageIcon) etiqueta.getIcon();
            if (icono != null) {
                BufferedImage coloredImage = changeIconColor(icono.getImage(), colorIconoBlock);
                etiqueta.setIcon(new ImageIcon(coloredImage));
            }
            g2.dispose();
        } else {
            if (!selecLetra) {

                if (bordeado) {
                    if (!select) {
                        g2.setColor(colorBordeado);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), redondez, redondez);
                        g2.setColor(colorFondo);
                        g2.fillRoundRect(grosorBorde, grosorBorde, getWidth() - grosorBorde - 3, getHeight() - grosorBorde - 3, redondez, redondez);
                        etiqueta.setForeground(colorLetra);
                        g2.dispose();

                    } else {
                        g2.setColor(colorPresionado);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), redondez, redondez);
                        etiqueta.setForeground(Color.WHITE);
                        g2.dispose();
                    }
                } else {
                    if (!select) {
                        g2.setColor(colorFondo);
                        etiqueta.setForeground(colorLetra);
                    } else {
                        g2.setColor(colorPresionado);
                        etiqueta.setForeground(colorLetraPres);
                    }
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), redondez, redondez);
                    g2.dispose();
                }
                etiqueta.setBackground(select ? colorPresionado : colorFondo);
            } else {
                g2.setColor(colorFondo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), redondez, redondez);
                g2.dispose();

                etiqueta.setBackground(colorFondo);

                if (!select) {
                    etiqueta.setForeground(colorLetra);
                    ImageIcon icono = (ImageIcon) etiqueta.getIcon();
                    BufferedImage coloredImage = changeIconColor(icono.getImage(), colorIcono);
                    etiqueta.setIcon(new ImageIcon(coloredImage));
                } else {
                    etiqueta.setForeground(colorLetraPres);
                    ImageIcon icono = (ImageIcon) etiqueta.getIcon();
                    BufferedImage coloredImage = changeIconColor(icono.getImage(), colorIconoPres);
                    etiqueta.setIcon(new ImageIcon(coloredImage));
                }
            }
        }


    }

    public void addActionListener(ActionListener listener) {
        this.actionListener = listener;
    }

    /**
     * Para decir si es boton tipo borde
     */
    public void setSelectLetra(boolean x) {
        selecLetra = x;
        if (x && etiqueta.getIcon() != null) {
            ImageIcon icono = (ImageIcon) etiqueta.getIcon();
            colorIcono = paleta.getColorIcono();
            BufferedImage coloredImage = changeIconColor(icono.getImage(), colorIcono); // Cambia a cualquier color
            etiqueta.setIcon(new ImageIcon(coloredImage));
        }
    }

    /**
     * Para decir si es boton tipo borde y ademas con Color
     */
    public void setSelectLetra(boolean x, Color color) {
        selecLetra = x;
        if (x && etiqueta.getIcon() != null) {
            ImageIcon icono = (ImageIcon) etiqueta.getIcon();
            colorIcono = color;
            BufferedImage coloredImage = changeIconColor(icono.getImage(), colorIcono); // Cambia a cualquier color
            etiqueta.setIcon(new ImageIcon(coloredImage));
        }
    }

    public void setNuevoSize(Dimension dim) {
        setSize(dim);
        setPreferredSize(dim);
        etiqueta.setLocation((getWidth() - etiqueta.getWidth()) / 2, (getHeight() - etiqueta.getHeight()) / 2);
    }

//    public Color getColorLetra() {
//        return colorLetra;
//    }

    public void setColorLetra(Color colorLetra) {
        this.colorLetra = colorLetra;
        etiqueta.setForeground(colorLetra);
    }

    public void cambiarIconTextGap(int x) {
        etiqueta.setIconTextGap(x);
    }

//    public Color getColorLetraPres() {
//        return colorLetraPres;
//    }
//
//    public void setColorLetraPres(Color colorLetraPres) {
//        this.colorLetraPres = colorLetraPres;
//    }
//
//    public Color getColorBordeado() {
//        return colorBordeado;
//    }
//
//    public void setColorBordeado(Color colorBordeado) {
//        this.colorBordeado = colorBordeado;
//    }
//
//    public Color getColorPresionado() {
//        return colorPresionado;
//    }

    public void setColorPresionado(Color colorPresionado) {
        colorFondo = paleta.getColorFondo();
        this.colorPresionado = colorPresionado;
    }

//    public boolean isBordeado() {
//        return bordeado;
//    }

    public void setBordeado(boolean bordeado) {
        this.bordeado = bordeado;
    }

//    public Color getColorIcono() {
//        return colorIcono;
//    }

    public void setColorIcono(Color colorIcono) {
        this.colorIcono = colorIcono;
    }

//    public Color getColorIconoPres() {
//        return colorIconoPres;
//    }

    public void setColorIconoPres(Color colorIconoPres) {
        this.colorIconoPres = colorIconoPres;
    }

    public boolean isSeleccionable() {
        return seleccionable;
    }

    public void setSeleccionable(boolean seleccionable) {
        if (seleccionable) {
            addMouseListener(adaptador);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            this.removeMouseListener(adaptador);
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        this.seleccionable = seleccionable;
    }

    @Override
    public void actualizar() {
        // TODO Auto-generated method stub

    }

    public JLabel getEtiqueta() {
        return etiqueta;
    }

    public void setFuente(Font fuente) {
        etiqueta.setFont(fuente);
        etiqueta.setSize(etiqueta.getPreferredSize());

    }

    public Color getColorFondo() {

        return colorFondo;
    }

    public void setColorFondo(Color color) {
        colorFondo = color;
        colorPresionado = colorFondo.darker();
        repaint();
        revalidate();
    }
}
