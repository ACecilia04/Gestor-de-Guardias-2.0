package gui.componentes;

import gui.auxiliares.Paleta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public class CustomCalendar extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JLabel etiquetaMes;
    private final JLabel etiquetaAgno;
    private final JPanel daysPanel;
    private final Paleta paleta = new Paleta();
    private final Color colorIcono = paleta.getColorCasillaTabla().darker();
    private final Color colorIconoPres = paleta.getColorCaracteristico();
    private final String iconoAnterior = "/iconos/FlechaIzquierda.png";
    private final String iconoProx = "/iconos/FlechaDerecha.png";
    private final Color colorEncabezado1 = paleta.getColorFondoTabla();
    private LocalDate selectedDate;
    private LocalDate fechaSelec;
    private boolean noIgnorarDias = false;


    public CustomCalendar(Dimension dim) {
        setLayout(new BorderLayout());
        selectedDate = LocalDate.now();

        this.setSize(dim);
        this.setPreferredSize(dim);

        // Crear el panel del encabezado
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(colorEncabezado1);

        // Crear el botón de mes anterior
        Boton mesAnt = new Boton();
        mesAnt.addIcono(iconoAnterior);
        mesAnt.setSelectLetra(true);
        mesAnt.setColorIcono(colorIcono);
        mesAnt.setColorIconoPres(colorIconoPres);
        mesAnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousMonth();
            }
        });
        headerPanel.add(mesAnt, BorderLayout.WEST);

        etiquetaMes = new JLabel();
        etiquetaMes.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(etiquetaMes, BorderLayout.CENTER);

        Boton mesProx = new Boton();
        mesProx.addIcono(iconoProx);
        mesProx.setSelectLetra(true);
        mesProx.setColorIcono(colorIcono);
        mesProx.setColorIconoPres(colorIconoPres);
        mesProx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextMonth();
            }
        });
        headerPanel.add(mesProx, BorderLayout.EAST);

        // Crear el panel para los botones de año
        JPanel yearPanel = new JPanel(new BorderLayout());
        // Crear la etiqueta del mes y año
        etiquetaAgno = new JLabel();
        etiquetaAgno.setHorizontalAlignment(JLabel.CENTER);
        yearPanel.add(etiquetaAgno, BorderLayout.CENTER);
        yearPanel.setBackground(colorEncabezado1);

        Boton agnoAnt = new Boton();
        agnoAnt.addIcono(iconoAnterior);
        agnoAnt.setSelectLetra(true);
        agnoAnt.setColorIcono(colorIcono);
        agnoAnt.setColorIconoPres(colorIconoPres);
        agnoAnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousYear();
            }
        });
        yearPanel.add(agnoAnt, BorderLayout.WEST);

        Boton agnoProx = new Boton();
        agnoProx.addIcono(iconoProx);
        agnoProx.setSelectLetra(true);
        agnoProx.setColorIcono(colorIcono);
        agnoProx.setColorIconoPres(colorIconoPres);
        agnoProx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextYear();
            }
        });
        yearPanel.add(agnoProx, BorderLayout.EAST);
        headerPanel.add(yearPanel, BorderLayout.NORTH);

        add(headerPanel, BorderLayout.NORTH);

        agnoProx.setBackground(headerPanel.getBackground());
        agnoAnt.setBackground(headerPanel.getBackground());
        mesProx.setBackground(headerPanel.getBackground());
        mesAnt.setBackground(headerPanel.getBackground());

        // Crear el panel de los días
        daysPanel = new JPanel(new GridLayout(6, 7, 5, 5)); // 6 filas y 7 columnas
        daysPanel.setBackground(Color.WHITE);
        add(daysPanel, BorderLayout.CENTER);

        showCurrentMonth();
    }

    private void showCurrentMonth() {
        showMonth(selectedDate.getYear(), selectedDate.getMonthValue());
    }

    public void mostrarMesActual() {
        fechaSelec = LocalDate.now();
        selectedDate = LocalDate.now();
        showCurrentMonth();
    }

    private void showPreviousMonth() {
        selectedDate = selectedDate.minusMonths(1);
        showCurrentMonth();
    }

    private void showNextMonth() {
        selectedDate = selectedDate.plusMonths(1);
        showCurrentMonth();
    }

    private void showPreviousYear() {
        selectedDate = selectedDate.minusYears(1);
        showCurrentMonth();
    }

    private void showNextYear() {
        selectedDate = selectedDate.plusYears(1);
        showCurrentMonth();
    }

    private void showMonth(final int year, final int month) {
        daysPanel.removeAll();
        etiquetaMes.setText(Month.of(month).name());
        etiquetaAgno.setText(Integer.toString(year));

        YearMonth yearMonth = YearMonth.of(year, month);
        int firstDay = yearMonth.atDay(1).getDayOfWeek().getValue();
        int daysInMonth = yearMonth.lengthOfMonth();

        // Agregar los días del mes anterior
        for (int i = 1; i < firstDay; i++) {
            daysPanel.add(new JLabel(""));
        }

        // Agregar los días del mes actual
        for (int day = 1; day <= daysInMonth; day++) {
            final int dia = day;
            final botonRed dayButton = new botonRed(String.valueOf(day));
            LocalDate fechaAux = LocalDate.of(year, month, day);
            if (fechaAux.isBefore(LocalDate.now()) && !noIgnorarDias) {
                dayButton.setSeleccionable(false);
            } else {
                if (fechaSelec != null && fechaSelec == fechaAux) {
                    dayButton.setSelected(true);
                }

                dayButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectDate(year, month, dia, dayButton);
                    }
                });
            }
            daysPanel.add(dayButton);
        }

        // Agregar espacios vacíos para completar 6 filas
        int totalDays = daysInMonth + (firstDay - 1);
        int emptySpaces = (6 * 7) - totalDays; // Total de espacios en 6 filas

        for (int i = 0; i < emptySpaces; i++) {
            daysPanel.add(new JLabel(""));
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private void selectDate(int year, int month, int day, botonRed selectedButton) {
        // Restablecer el color de todos los botones
        for (int i = 0; i < daysPanel.getComponentCount(); i++) {
            if (daysPanel.getComponent(i) instanceof botonRed button) {
                button.setSelected(false); // Restablecer el estado de selección
                if (button.isSeleccionable()) {
                    button.setBackground(button.getNormalColor());
                } else {
                    button.setBackground(Color.WHITE);
                }
            }
        }

        // Establecer la fecha seleccionada y cambiar el color del botón seleccionado
        fechaSelec = LocalDate.of(year, month, day);
        selectedButton.setSelected(true); // Marcar el botón como seleccionado
    }

    public LocalDate getFechaSelec() {
        return fechaSelec;
    }

    public void setSelectAll(boolean b) {
        noIgnorarDias = b;
        showCurrentMonth();
    }
}


class botonRed extends JButton {
    private final Paleta paleta = new Paleta();
    private final Font fuente = new Font("Arial", Font.BOLD, 16); // Aumentar el tamaño de la fuente
    private final Color colorFondo = paleta.getColorCasillaTabla();
    private final Color colorFondoPres = paleta.getColorCasillaTabla().darker();
    private final Color pressedColor = paleta.getColorCaracteristico();
    private final String texto;
    private boolean seleccionable = true;
    private boolean selected = false;

    public botonRed(String text) {
        super();
        texto = text;
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        this.setFont(fuente);
        setColores();

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        g.setColor(getForeground());
        FontMetrics metrics = g.getFontMetrics(fuente);
        int x = (getWidth() - metrics.stringWidth(texto)) / 2;
        int y = (getHeight() + metrics.getHeight()) / 2 - 5;
        g.drawString(texto, x, y);
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 50);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            setBackground(paleta.getColorCaracteristico()); // Cambiar a azul si está seleccionado
            setForeground(Color.WHITE);
        } else {
            setBackground(paleta.getColorCasillaTabla()); // Volver al color original si no está seleccionado
            setForeground(paleta.getColorLetraMenu());
        }
    }

    public void setColores() {
        if (seleccionable) {
            setBackground(colorFondo);
            setForeground(paleta.getColorLetraMenu());

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!selected) {
                        setBackground(colorFondoPres);
                    }
                    if (!seleccionable) {
                        setBackground(Color.WHITE);
                        setForeground(paleta.getColorLetraMenu());
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!selected) {
                        setBackground(colorFondo);
                    }
                    if (!seleccionable) {
                        setBackground(Color.WHITE);
                        setForeground(paleta.getColorLetraMenu());
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(pressedColor);
                    if (!seleccionable) {
                        setBackground(Color.WHITE);
                        setForeground(paleta.getColorLetraMenu());
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (!selected) {
                        setBackground(colorFondoPres);
                    }
                    if (!seleccionable) {
                        setBackground(Color.WHITE);
                        setForeground(paleta.getColorLetraMenu());
                    }
                }
            });
        } else {
            setBackground(Color.WHITE);
            setForeground(paleta.getColorLetraMenu());

        }
    }

    public Color getNormalColor() {
        return paleta.getColorCasillaTabla(); // Método para obtener el color normal
    }

    public boolean isSeleccionable() {
        return seleccionable;
    }

    public void setSeleccionable(boolean seleccionable) {
        this.seleccionable = seleccionable;
        setColores();
    }
}
