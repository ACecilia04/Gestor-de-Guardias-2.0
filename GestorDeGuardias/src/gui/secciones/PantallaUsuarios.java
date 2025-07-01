package gui.secciones;

import gui.internosComp.PanelSupOpcionesUsuarios;
import gui.internosComp.TablaUsuarios;
import gui.pantallasEmergentes.Advertencia;
import gui.pantallasEmergentes.PantallaAddUsuario;
import model.Rol;
import model.Usuario;
import services.ServicesLocator;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

public class PantallaUsuarios extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private TablaUsuarios tabla;
    private PanelSupOpcionesUsuarios opcionesReferencia;

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
        Dimension auxDim = new Dimension(venAux.getPanelVacio().getSize().width, venAux.getPanelVacio().getSize().height);
        this.tabla = new TablaUsuarios(auxDim, Color.WHITE, usuarios);

        // Listener de selección
        tabla.setSeleccionListener(usuario -> {
            if (opcionesReferencia != null) {
                opcionesReferencia.setAlgunUsuarioSeleccionado(usuario != null);
            }
        });

        this.add(tabla, BorderLayout.CENTER);
        this.tabla.actualizarVistaTabla();

        // Al cargar, deshabilita edición
        if (opcionesReferencia != null)
            opcionesReferencia.setAlgunUsuarioSeleccionado(false);

        revalidate();
        repaint();
    }

    public Usuario getUsuarioSeleccionado() {
        if (tabla != null) return tabla.getUsuarioSeleccionado();
        return null;
    }

    // Métodos para los botones de la barra superior
    public void agregarUsuario() {
        ArrayList<Rol> roles = (ArrayList<Rol>) ServicesLocator.getInstance().getRolServices().getAllRoles();
        new PantallaAddUsuario(roles, getUsuarioSeleccionado());
        setTabla((ArrayList<Usuario>) ServicesLocator.getInstance().getUsuarioServices().getAllUsuarios());
    }

    public void modificarUsuario() {
        ArrayList<Rol> roles = (ArrayList<Rol>) ServicesLocator.getInstance().getRolServices().getAllRoles();
        new PantallaAddUsuario(roles, this.getUsuarioSeleccionado());

    }

    public void eliminarUsuario() {
        Usuario seleccionado = getUsuarioSeleccionado();

        if (seleccionado.getRol() != null &&
                seleccionado.getRol().getNombre() != null &&
                seleccionado.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            new Advertencia(new Dimension(400, 250), "Operación no permitida", "No se puede eliminar el usuario administrador.", "Aceptar");
            return;
        }

        // Confirmación antes de eliminar
        Advertencia advertencia = new Advertencia(
                new Dimension(400, 150),
                "Confirmar eliminación",
                "¿Está seguro que desea eliminar el usuario: " + seleccionado.getNombre() + "?",
                "Aceptar", "Cancelar"
        );
        if (!advertencia.getEleccion()) {
            return;
        }

        // Lógica real de eliminación
        try {
            ServicesLocator.getInstance().getUsuarioServices().deleteUsuario(seleccionado.getId());
            // Refresca la tabla después de eliminar
            setTabla((ArrayList<Usuario>) ServicesLocator.getInstance().getUsuarioServices().getAllUsuarios());
            new Advertencia(new Dimension(400, 250), "Eliminación exitosa", "Usuario eliminado correctamente.", "Aceptar");
        } catch (Exception ex) {
            new Advertencia(new Dimension(400, 250), "Error", "No se pudo eliminar el usuario: " + ex.getMessage(), "Aceptar");
        }
    }

    public void setOpcionesReferencia(PanelSupOpcionesUsuarios opcionesUsuarios) {
        this.opcionesReferencia = opcionesUsuarios;
    }
}