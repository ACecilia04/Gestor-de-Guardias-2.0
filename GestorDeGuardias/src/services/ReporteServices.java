package services;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import model.DiaGuardia;
import model.Persona;
import model.TurnoDeGuardia;

import java.awt.*;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReporteServices {

    // Reporte de todas las personas
    public void generarReporteTodasLasPersonas(List<Persona> personas, String rutaArchivo, String Titulo) {
        generarReportePersonas(personas, rutaArchivo, Titulo);
    }


    // Método privado reutilizable
    private void generarReportePersonas(List<Persona> personas, String nombreArchivo, String tituloReporte) {
        Document documento = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            // Logo
            try {
                Image logo = Image.getInstance("D:/Programming/Projects/Gestor-de-Guardias-2.0/GestorDeGuardias/src/iconos/logoPDF.jpg");
                logo.scaleToFit(60, 60);
                logo.setAlignment(Image.ALIGN_RIGHT);
                documento.add(logo);
            } catch (Exception ignored) {
            }

            // Título
            Paragraph titulo = new Paragraph(tituloReporte,
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(15);
            documento.add(titulo);

            // Estadísticas simples
            int total = personas.size();
            int est = 0, trab = 0;
            for (Persona p : personas) {
                if ("Estudiante".equals(p.getTipo().getNombre())) est++;
                if ("Trabajador".equals(p.getTipo().getNombre())) trab++;
            }
            // Resumen alineado a la izquierda y con más separación y formato
            Paragraph resumen = new Paragraph();
            resumen.setFont(FontFactory.getFont(FontFactory.HELVETICA, 13));
            resumen.setAlignment(Element.ALIGN_LEFT);
            resumen.add("Total de Personas: " + total + "\n");
            resumen.add("Estudiantes: " + est + String.format(" (%.2f%%)\n", total > 0 ? (est * 100.0 / total) : 0));
            resumen.add("Trabajadores: " + trab + String.format(" (%.2f%%)", total > 0 ? (trab * 100.0 / total) : 0));
            resumen.setSpacingAfter(15);
            documento.add(resumen);

            // Tabla de datos
            float[] widths = {2.3f, 4.5f, 1.7f, 3f, 2.2f, 2.5f, 2.2f, 2.5f};
            PdfPTable tabla = new PdfPTable(widths);
            tabla.setWidthPercentage(90);

            String[] encabezados = {
                    "Tipo", "Nombre", "Sexo", "Carnet",
                    "Últ. Guardia", "Guardias Rec.", "Baja", "Reincorporación"
            };

            // Encabezados con mayor altura y alineación central
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.BLACK);
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado, fontHeader));
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setPaddingTop(6);
                celda.setPaddingBottom(6);
                tabla.addCell(celda);
            }

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Font fontCell = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.DARK_GRAY);

            for (Persona p : personas) {
                // Tipo
                tabla.addCell(new PdfPCell(new Phrase(
                        p.getTipo() != null && p.getTipo().getNombre() != null ? p.getTipo().getNombre() : "",
                        fontCell)));
                // Nombre
                String nombreCompleto = ((p.getNombre() != null ? p.getNombre() : "") +
                        (p.getApellido() != null ? " " + p.getApellido() : "")).trim();
                tabla.addCell(new PdfPCell(new Phrase(nombreCompleto, fontCell)));
                // Sexo
                tabla.addCell(new PdfPCell(new Phrase(
                        p.getSexo() != null ? String.valueOf(p.getSexo().charAt(0)) : "",
                        fontCell)));
                // Carnet
                tabla.addCell(new PdfPCell(new Phrase(
                        p.getCarnet() != null ? p.getCarnet() : "",
                        fontCell)));
                // Últ. Guardia
                tabla.addCell(new PdfPCell(new Phrase(
                        p.getUltimaGuardiaAsignada() != null ? p.getUltimaGuardiaAsignada().format(formatoFecha) : "",
                        fontCell)));
                // Guardias Rec.
                tabla.addCell(new PdfPCell(new Phrase(
                        String.valueOf(p.getGuardiasDeRecuperacion()),
                        fontCell)));
                // Baja
                tabla.addCell(new PdfPCell(new Phrase(
                        p.getBaja() != null ? p.getBaja().format(formatoFecha) : "",
                        fontCell)));
                // Reincorporación
                tabla.addCell(new PdfPCell(new Phrase(
                        p.getReincorporacion() != null ? p.getReincorporacion().format(formatoFecha) : "",
                        fontCell)));
            }

            // Espaciado entre filas y bordes sutiles
            for (PdfPRow row : tabla.getRows()) {
                for (PdfPCell cell : row.getCells()) {
                    if (cell != null) {
                        cell.setPaddingTop(4);
                        cell.setPaddingBottom(4);
                        cell.setBorderWidth(0.8f);
                    }
                }
            }

            documento.add(tabla);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }


    public void generarReportePlantilla(ArrayList<DiaGuardia> plantilla, String nombreArchivo) {
        Document documento = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            Paragraph titulo = new Paragraph("Reporte de Plantilla de Guardias",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
            // Font fontDia = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);  // Ya no se usa esto
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 11);

            for (DiaGuardia dia : plantilla) {
                // Título del día (ahora con fontHeader, más pequeño)
                String diaLabel = dia.getFecha().format(formatoFecha) + " - " +
                        dia.getFecha().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"));
                Paragraph diaTitulo = new Paragraph(diaLabel, fontHeader); // <-- usa fontHeader de 12pt!
                diaTitulo.setSpacingBefore(10);
                diaTitulo.setSpacingAfter(5);
                documento.add(diaTitulo);

                // Tabla de turnos del día
                float[] widths = {2f, 4f, 4f, 4f, 2f};
                PdfPTable tabla = new PdfPTable(widths);
                tabla.setWidthPercentage(98);

                String[] encabezados = {"Horario", "Carnet", "Apellidos", "Nombre", "Cumplido"};
                for (String encabezado : encabezados) {
                    PdfPCell celda = new PdfPCell(new Phrase(encabezado, fontHeader));
                    celda.setBackgroundColor(Color.LIGHT_GRAY);
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabla.addCell(celda);
                }

                for (TurnoDeGuardia turno : dia.getTurnos()) {
                    String horarioStr = turno.getHorario().getInicio().format(formatoHora)
                            + " - " + turno.getHorario().getFin().format(formatoHora);

                    ArrayList<Persona> personas = turno.getPersonasAsignadas();
                    if (personas != null && !personas.isEmpty()) {
                        for (Persona p : personas) {
                            tabla.addCell(new PdfPCell(new Phrase(horarioStr, fontNormal)));
                            tabla.addCell(new PdfPCell(new Phrase(p.getCarnet(), fontNormal)));
                            tabla.addCell(new PdfPCell(new Phrase(p.getApellido(), fontNormal)));
                            tabla.addCell(new PdfPCell(new Phrase(p.getNombre(), fontNormal)));
                            Boolean hecho = turno.getCumplimiento();
                            tabla.addCell(new PdfPCell(new Phrase(
                                    (hecho == null) ? "" : (hecho ? "Sí" : "No"), fontNormal)));
                            horarioStr = ""; // para no repetir el horario
                        }
                    } else if (turno.getPersonaAsignada() != null) {
                        Persona p = turno.getPersonaAsignada();
                        tabla.addCell(new PdfPCell(new Phrase(horarioStr, fontNormal)));
                        tabla.addCell(new PdfPCell(new Phrase(p.getCarnet(), fontNormal)));
                        tabla.addCell(new PdfPCell(new Phrase(p.getApellido(), fontNormal)));
                        tabla.addCell(new PdfPCell(new Phrase(p.getNombre(), fontNormal)));
                        Boolean hecho = turno.getCumplimiento();
                        tabla.addCell(new PdfPCell(new Phrase(
                                (hecho == null) ? "" : (hecho ? "Sí" : "No"), fontNormal)));
                    }
                }

                documento.add(tabla);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }

}