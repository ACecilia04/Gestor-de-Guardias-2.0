package gui.secciones;

import gui.internosComp.PanelSupOpcionesUsuarios;
import gui.internosComp.TablaUsuarios;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

public class PantallaUsuarios extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private TablaUsuarios tabla;

    public PantallaUsuarios() {
        setLayout(new BorderLayout());
    }

    public TablaUsuarios getTabla() {
        return tabla;
    }

    public void setTabla(ArrayList<Usuario> usuarios) {
        if (tabla != null) {
            this.remove(tabla);
        }

        Ventana venAux = Ventana.getInstance();
        Dimension auxDim = new Dimension(venAux.getPanelVacio().getSize().width , venAux.getPanelVacio().getSize().height);
        this.tabla = new TablaUsuarios(auxDim, Color.WHITE, usuarios);

        this.add(tabla, BorderLayout.CENTER);
        this.tabla.actualizarVistaTabla();

        revalidate();
        repaint();
    }

    // Métodos para los botones de la barra superior
    public void agregarUsuario() {
        // TODO: lógica para agregar usuario
        JOptionPane.showMessageDialog(this, "Agregar usuario (implementa la lógica aquí)");
    }

    public void modificarUsuario() {
        // TODO: lógica para modificar usuario seleccionado
        JOptionPane.showMessageDialog(this, "Modificar usuario (implementa la lógica aquí)");
    }

    public void eliminarUsuario() {
        // TODO: lógica para eliminar usuario seleccionado
        JOptionPane.showMessageDialog(this, "Eliminar usuario (implementa la lógica aquí)");
    }
}