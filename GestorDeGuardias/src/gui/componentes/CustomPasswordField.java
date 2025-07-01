package gui.componentes;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;


public class CustomPasswordField extends Cuadro {
    //Distancias
    private static final int sepAncho = 20;
    private static final int sepLargo = 5;
    private static final int espBoton = 40;
    //Utiles
    private String textoPorDefecto;
    //Componentes
    private final Cuadro myself;
    private final JPasswordField textField;
    private final Boton toggleButton;
    private final Font fuente = new Font("Arial", Font.PLAIN, 14);
    //Iconos
    private final String iconoVisible = "/iconos/Invisible.png";
    private final String iconoInvisible = "/iconos/Visible.png";
    private final Color colorSelec = Color.ORANGE;
    private final Color colorLetra = Color.BLACK;
    private final Color colorLetraPres = Color.WHITE;
    //Colores
    private final Color colorFondo;
    //Booleanos
    private boolean isPasswordVisible = false; // Estado de visibilidad


    public CustomPasswordField(Dimension dim, final String texto, int redondez, int maxCaracteres, Color color) {
        super(dim, redondez, color);
        this.colorFondo = color;
        this.textoPorDefecto = texto;

        myself = this;

        setBackground(Color.LIGHT_GRAY);
        setLayout(null);

        // Crear el JTextField
        Dimension dimAux = new Dimension(dim.width + espBoton, dim.height);
        setSize(dimAux);
        setPreferredSize(dimAux);

        textField = new JPasswordField();
        textField.setSize(new Dimension(dim.width - sepAncho * 2, dim.height - sepLargo * 2)); // Tama�o m�s peque�o
        textField.setLocation(sepAncho, sepLargo);
        textField.setBorder(null);
        textField.setFont(fuente);
        textField.setDocument(new LimitedDocument(maxCaracteres)); // Limitar caracteres
        textField.setBackground(getBackground());
        textField.setForeground(Color.GRAY);
        textField.setText(textoPorDefecto);

        //Caret
        textField.setCaretColor(Color.RED);
        DefaultCaret caret = (DefaultCaret) textField.getCaret();
        caret.setBlinkRate(500);

        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (new String(textField.getPassword()).equals(textoPorDefecto)) {
                    textField.setText("");
                    textField.setForeground(colorLetraPres);
                } else {
                    textField.setForeground(colorLetraPres);
                }
                togglePasswordVisibility();
                myself.setColorFondo(colorSelec);
                toggleButton.setColorIconoPres(Color.ORANGE.darker());
                toggleButton.setColorFondo(colorSelec);
                textField.setBackground(colorSelec);
            }


            public void focusLost(FocusEvent evt) {
                if (new String(textField.getPassword()).isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(textoPorDefecto);
                    if (isPasswordVisible) {
                        togglePasswordVisibility();
                    } else {
                        isPasswordVisible = true;
                        togglePasswordVisibility();
                        isPasswordVisible = false;
                        toggleButton.addIcono(iconoInvisible);
                    }

                } else {
                    textField.setForeground(colorLetra);
                }
                toggleButton.setColorIconoPres(Color.GRAY);
                myself.setColorFondo(colorFondo);
                toggleButton.setColorFondo(colorFondo);
                textField.setBackground(colorFondo);
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                textField.requestFocus();
            }
        });

        toggleButton = new Boton();
        toggleButton.addIcono(iconoInvisible);
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPasswordVisible = !isPasswordVisible;
                togglePasswordVisibility();
                textField.requestFocus();
            }
        });

        toggleButton.setSelectLetra(true);
        toggleButton.setColorIcono(Color.WHITE);
        toggleButton.setColorIconoPres(Color.GRAY);
        toggleButton.setLocation(dim.width, (this.getSize().height - toggleButton.getHeight()) / 2);

        isPasswordVisible = true;
        togglePasswordVisibility();
        isPasswordVisible = false;
        toggleButton.addIcono(iconoInvisible);

        // Agregar componentes al JPanel
        add(textField);
        add(toggleButton);
    }

    public String getTextoPorDefecto() {
        return textoPorDefecto;
    }

    //Para ocultar
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            toggleButton.addIcono(iconoVisible);

            textField.setEchoChar('\u0000');

        } else {
            toggleButton.addIcono(iconoInvisible);
            textField.setEchoChar('\u2022');
        }
        repaint();
        revalidate();
    }

//    public JPasswordField getPasswordField() {
//        return textField;
//    }

    public String getText() {
        return new String(textField.getPassword());
    }

    public void setTextoPorDefecto(String texto) {
        textField.setText(texto);
    }

    private static class LimitedDocument extends PlainDocument {
        private final int limit;

        public LimitedDocument(int limit) {
            this.limit = limit;
        }

        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offs, str, a);
            }
        }
    }
    public void setEditable(boolean editable) {
        textField.setEditable(editable);
        textField.setFocusable(editable);
        if (!editable) {
            textField.setForeground(Color.DARK_GRAY); // Optional: visually signal non-editability
        } else if (!textField.getText().equals(textoPorDefecto)) {
            textField.setForeground(colorLetra); // Restore normal text color
        }
    }
}


