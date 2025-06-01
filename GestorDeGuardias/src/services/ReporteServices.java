package services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.FontFactory;
import model.Persona;
import java.awt.*;
import java.awt.Image;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReporteServices {

    // Reporte de todas las personas
    public void generarReporteTodasLasPersonas(List<Persona> personas, String nombreArchivo) {
        generarReportePersonas(personas, nombreArchivo, "Reporte de Todas las Personas");
    }

    // Reporte de personas de baja
    public void generarReportePersonasDeBaja(List<Persona> personas, String nombreArchivo) {
        generarReportePersonas(personas, nombreArchivo, "Reporte de Personas de Baja");
    }

    // Método privado reutilizable
    private void generarReportePersonas(List<Persona> personas, String nombreArchivo, String tituloReporte) {
        Document documento = new Document(PageSize.A4.rotate()); // Horizontal para mayor espacio
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            // Logo (opcional)
//            try {
//                Image logo = Image.getInstance("logo.png");
//                logo.scaleToFit(80, 80);
//                logo.setAlignment(Image.ALIGN_RIGHT);
//                documento.add(logo);
//            } catch (Exception ignored) { }

            // Título
            Paragraph titulo = new Paragraph(tituloReporte,
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

//            // Estadísticas simples
//            int total = personas.size();
//            int activos = 0;
//            int bajas = 0;
//            for (Persona p : personas) {
//                if (p.isActivo()) activos++;
//                if (p.isDeBaja()) bajas++;
//            }
//            Paragraph resumen = new Paragraph(
//                    String.format("Total personas: %d\nActivos: %d (%.2f%%)\nDe baja: %d (%.2f%%)\n",
//                            total,
//                            activos, total > 0 ? (activos * 100.0 / total) : 0,
//                            bajas, total > 0 ? (bajas * 100.0 / total) : 0
//                    ),
//                    FontFactory.getFont(FontFactory.HELVETICA, 14)
//            );
//            documento.add(resumen);
//            documento.add(Chunk.NEWLINE);

            // Tabla de datos
            float[] widths = {2.5f, 2.5f, 2f, 3f, 2f, 2.5f, 2f, 2.5f, 2.5f};
            PdfPTable tabla = new PdfPTable(widths);
            tabla.setWidthPercentage(95);

            String[] encabezados = {
                    "Nombre", "Apellido", "Sexo", "Carnet", "Tipo",
                    "Última Guardia", "Guardias Recuperación", "Baja", "Reincorporación"
            };
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tabla.addCell(celda);
            }

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Persona p : personas) {
                tabla.addCell(p.getNombre() != null ? p.getNombre() : "");
                tabla.addCell(p.getApellido() != null ? p.getApellido() : "");
                tabla.addCell(p.getSexo() != null ? p.getSexo() : "");
                tabla.addCell(p.getCarnet() != null ? p.getCarnet() : "");
                tabla.addCell(p.getTipo() != null ? p.getTipo().getNombre() : "");
                tabla.addCell(p.getUltimaGuardiaHecha() != null ? p.getUltimaGuardiaHecha().format(formatoFecha) : "");
                tabla.addCell(String.valueOf(p.getGuardiasDeRecuperacion()));
                tabla.addCell(p.getBaja() != null ? p.getBaja().format(formatoFecha) : "");
                tabla.addCell(p.getReincorporacion() != null ? p.getReincorporacion().format(formatoFecha) : "");
            }

            documento.add(tabla);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }
}