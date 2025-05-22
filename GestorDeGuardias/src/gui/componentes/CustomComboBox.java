package gui.componentes;

import gui.auxiliares.ComboBoxSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CustomComboBox extends Cuadro {
    private final Boton botSel;
    private final Etiqueta info;
    private CustomPopupMenu popupMenu;
    private final int separacion = 20;
    private final int separacionBoton = 10;
    private final Cuadro myself;
    private String[] opciones;

    private final String titulo;

    private String textoNulo = "NONE";

    private final List<ComboBoxSelectionListener> selectionListeners;

    public CustomComboBox(final String[] opc, String titulo, Dimension dimension, int redondez, Color color) {
        super(dimension, redondez, color);
        myself = this;
        this.titulo = titulo;

        setLayout(null);
        opciones = opc;

        selectionListeners = new ArrayList<>();

        // Boton
        botSel = new Boton();
        botSel.addIcono("/iconos/FlechaAbajo.png");
        botSel.setSelectLetra(true);

        // Texto
        info = new Etiqueta(titulo);
        info.setSize(new Dimension(this.getSize().width - separacion * 2 - separacionBoton - botSel.getSize().width, info.getSize().height));
        info.setLocation(separacion, (this.getSize().height - info.getSize().height) / 2);
        botSel.setLocation(info.getLocation().x + info.getSize().width + separacionBoton, (this.getSize().height - botSel.getSize().height) / 2);

        add(info);
        add(botSel);

        // MouseListener para el ComboBox
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                popupMenu = new CustomPopupMenu(myself.getSize().width);
                if (opciones == null || opciones.length == 0) {
                    JMenuItem menuItem = new JMenuItem(textoNulo);
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            popupMenu.setVisible(false);
                        }
                    });
                    menuItem.setEnabled(false);
                    popupMenu.addItem(menuItem);
                } else {
                    for (final String option : opciones) {
                        JMenuItem menuItem = new JMenuItem(option);
                        menuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                info.setText(option);
                                if (info.getPreferredSize().width <= botSel.getLocation().x - 20) {
                                    info.setSize(info.getPreferredSize());
                                } else {
                                    info.setSize(botSel.getLocation().x - 20, info.getPreferredSize().height);
                                }
                                notificarListeners(option);
                                popupMenu.setVisible(false);
                            }
                        });
                        popupMenu.addItem(menuItem);
                    }
                }

                popupMenu.show(myself, 0, myself.getHeight());
            }

        });

        botSel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Crear el CustomPopupMenu
                popupMenu = new CustomPopupMenu(myself.getSize().width);
                if (opciones == null || opciones.length == 0) {
                    JMenuItem menuItem = new JMenuItem(textoNulo);
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            popupMenu.setVisible(false);
                        }
                    });
                    menuItem.setEnabled(false);
                    popupMenu.addItem(menuItem);
                } else {
                    for (final String option : opciones) {
                        JMenuItem menuItem = new JMenuItem(option);
                        menuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                info.setText(option);
                                if (info.getPreferredSize().width <= botSel.getLocation().x - 20) {
                                    info.setSize(info.getPreferredSize());
                                } else {
                                    info.setSize(botSel.getLocation().x - 20, info.getPreferredSize().height);
                                }
                                notificarListeners(option);
                                popupMenu.setVisible(false);
                            }
                        });
                        popupMenu.addItem(menuItem);
                    }
                }

                popupMenu.show(myself, 0, myself.getHeight());
            }
        });
    }

    public Etiqueta getEtiqueta() {
        return info;
    }

    public Boton getBoton() {
        return botSel;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public void setOpciones(String[] opciones) {
        this.opciones = opciones;
        info.setText(titulo);

    }

    //Listeners
    public void addComboBoxSelectionListener(ComboBoxSelectionListener listener) {
        selectionListeners.add(listener);
    }

    public void removeComboBoxSelectionListener(ComboBoxSelectionListener listener) {
        selectionListeners.remove(listener);
    }

    private void notificarListeners(String selectedItem) {
        if (!selectionListeners.isEmpty()) {
            for (ComboBoxSelectionListener listener : selectionListeners) {
                listener.onItemSelected(selectedItem);
            }
        }

    }

    public String getTextoNulo() {
        return textoNulo;
    }

    public void setTextoNulo(String textoNulo) {
        this.textoNulo = textoNulo;
    }

    public String getEleccion() {
        String eleccion = null;
        if (textoNulo != null && info.getText() != textoNulo) {
            eleccion = info.getText();
        }
        return eleccion;
    }
}
