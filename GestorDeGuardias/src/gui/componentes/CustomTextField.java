package gui.componentes;

import gui.auxiliares.Paleta;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class CustomTextField extends Cuadro {
    private static final int redondez = Cuadro.redBAJA;
    private final String textoPorDefecto;
    protected JTextField textField;
    protected Color colorLetraPres = Color.WHITE;
    Paleta paleta = new Paleta();
    private final Font fuente = new Font("Arial", Font.PLAIN, 17);
    private int sepAncho = 20, sepLargo = 10;
    private int espBoton = 30;
    private Color colorFondo = Color.LIGHT_GRAY;
    private Color colorSelec = Color.ORANGE;
    private final Color colorLetra = paleta.getColorLetraMenu();
    private final Cuadro myself;
    private KeyAdapter listenerAux;
    private boolean filtroActivo;
    private boolean soloNumeros = false;
    private boolean soloLetras = false;
    private final int maxCaracteres;

    public CustomTextField(Dimension dim, final String texto, int maxCaracteres, Color color) {
        super(dim, redondez, color);
        this.colorFondo = color;
        this.textoPorDefecto = texto;
        this.filtroActivo = false;
        this.maxCaracteres = maxCaracteres;

        myself = this;

        setBackground(Color.LIGHT_GRAY);
        setLayout(null);

        // Crear el JTextField
        inicializar(dim);

    }

    public CustomTextField(Dimension dim, final String texto, int maxCaracteres, Color color, int sepAncho, int sepAltura) {
        super(dim, redondez, color);
        this.colorFondo = color;
        this.textoPorDefecto = texto;
        this.filtroActivo = false;
        this.maxCaracteres = maxCaracteres;
        this.espBoton = 0;
        this.sepAncho = sepAncho;
        this.sepLargo = sepAltura;


        myself = this;

        setBackground(Color.LIGHT_GRAY);
        setLayout(null);

        inicializar(dim);

        textField.setHorizontalAlignment(JTextField.CENTER);

    }

    private void inicializar(Dimension dim) {
        Dimension dimAux = new Dimension(dim.width + espBoton, dim.height);
        setSize(dimAux);
        setPreferredSize(dimAux);
        textField = new JTextField(textoPorDefecto, 10);
        textField.setSize(new Dimension(dim.width - sepAncho * 2, dim.height - sepLargo * 2)); // Tama�o m�s peque�o
        textField.setForeground(Color.GRAY);
        textField.setLocation(sepAncho, sepLargo);
        textField.setBackground(colorFondo);
        textField.setBorder(null);
        textField.setFont(fuente);
        textField.setDocument(new LimitedDocument(maxCaracteres)); // Limitar caracteres
        textField.setText(textoPorDefecto); // Establecer texto por defecto

        textField.setCaretColor(Color.RED);
        DefaultCaret caret = (DefaultCaret) textField.getCaret();
        caret.setBlinkRate(500); // Cambia la velocidad de parpadeo (en milisegundos)

        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {

                if (textField.getText().equals(textoPorDefecto)) {
                    textField.setText("");
                }

                textField.setForeground(colorLetraPres);
                myself.setColorFondo(colorSelec);
                textField.setBackground(colorSelec);

                // Volver a aplicar el filtro al ganar el foco
                if (filtroActivo) {
                    aplicarFiltro();
                }
            }

            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    quitarFiltro();
                    textField.setForeground(Color.GRAY);
                    textField.setText(textoPorDefecto);

                } else {
                    textField.setForeground(colorLetra);
                }
                myself.setColorFondo(colorFondo);
                textField.setBackground(colorFondo);
            }
        });

        listenerAux = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    textField.transferFocus();
                }
            }
        };

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                textField.requestFocus();
            }
        });

        add(textField);
    }

    public String getText() {
        String texto = "";
        if (!textField.getText().equals(textoPorDefecto) && !textField.getText().isEmpty()) {
            texto = textField.getText();
        }
        return texto;
    }

    public String getTextoPorDefecto() {
        return textoPorDefecto;
    }

    public void setColorSelec(Color colorSelec) {
        this.colorSelec = colorSelec;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void pasaFoco(boolean x) {
        if (x) {
            // Agregar KeyListener para detectar la tecla Enter
            textField.addKeyListener(listenerAux);
        } else {
            // Eliminar el KeyListener
            for (KeyListener listener : textField.getKeyListeners()) {
                if (listener == listenerAux) {
                    textField.removeKeyListener(listener);
                }
            }
        }
    }

    public void soloNumeros(boolean x) {
        filtroActivo = x;
        soloNumeros = x;
        if (x) {
            aplicarFiltro();
        } else {
            ((PlainDocument) textField.getDocument()).setDocumentFilter(null);
        }
    }

    public void soloLetras(boolean x) {
        filtroActivo = x;
        soloLetras = x;
        if (x) {
            aplicarFiltro();
        } else {
            ((PlainDocument) textField.getDocument()).setDocumentFilter(null);
        }
    }

    private void aplicarFiltro() {
        if (filtroActivo) {
            if (soloNumeros) {
                ((PlainDocument) textField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
            } else if (soloLetras) {
                ((PlainDocument) textField.getDocument()).setDocumentFilter(new LetrasDocumentFilter());
            }
        }
    }

    private void quitarFiltro() {
        ((PlainDocument) textField.getDocument()).setDocumentFilter(null);
    }

    private static class LimitedDocument extends PlainDocument {
        private final int limit;

        public LimitedDocument(int limit) {
            this.limit = limit;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offs, str, a);
            }
        }
    }

    // Clase interna para el DocumentFilter que permite solo n�meros
    private class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;
            if (isNumeric(text)) {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                int newLength = currentText.length() - length + text.length();
                if (newLength <= ((LimitedDocument) fb.getDocument()).limit) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }

        private boolean isNumeric(String text) {
            return text.matches("[0-9]*");
        }
    }

    // Clase interna para el DocumentFilter que permite solo letras
    private class LetrasDocumentFilter extends DocumentFilter {
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;
            if (isLetter(text)) {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                int newLength = currentText.length() - length + text.length();
                if (newLength <= ((LimitedDocument) fb.getDocument()).limit) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }

        private boolean isLetter(String text) {
            return text.matches("[a-zA-Z������������ ]*");
        }
    }
}

