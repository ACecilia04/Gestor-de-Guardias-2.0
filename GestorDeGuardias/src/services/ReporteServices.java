package services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import model.DiaGuardia;
import model.Horario;
import model.Persona;
import model.TurnoDeGuardia;

import java.awt.*;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReporteServices {

    // Reporte de todas las personas
    public void generarReporteTodasLasPersonas(List<Persona> personas, String rutaArchivo, String Titulo) {
        generarReportePersonas(personas, rutaArchivo, Titulo);
    }


    // Método privado reutilizable
    private void generarReportePersonas(List<Persona> personas, String nombreArchivo, String tituloReporte) {
        Document documento = new Document(PageSize.A4); // Horizontal para mayor espacio
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            // Logo (opcional) Image.getInstance("D:/Programming/Projects/Gestor-de-Guardias-2.0/GestorDeGuardias/src/iconos/logoPDF.jpg");
            try {
                Image logo = Image.getInstance("D:/Programming/Projects/Gestor-de-Guardias-2.0/GestorDeGuardias/src/iconos/logoPDF.jpg");
                logo.scaleToFit(80, 80);
                logo.setAlignment(Image.ALIGN_RIGHT);
                documento.add(logo);
            } catch (Exception ignored) { }

            // Título
            Paragraph titulo = new Paragraph(tituloReporte,
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            // Estadísticas simples cant est cant trab
            int total = personas.size();
            int est = 0;
            int trab = 0;
            for (Persona p : personas) {
                if (p.getTipo().equals("Estudiante")) est++;
                if (p.getTipo().equals("Trabajador")) trab++;
            }
            Paragraph resumen = new Paragraph(
                    String.format("Total de Personas: %d\nEstudiantes: %d (%.2f%%)\nTrabajadores: %d (%.2f%%)\n",
                            total,
                            est, total > 0 ? (est * 100.0 / total) : 0,
                            trab, total > 0 ? (trab * 100.0 / total) : 0
                    ),
                    FontFactory.getFont(FontFactory.HELVETICA, 14)
            );
            documento.add(resumen);
            documento.add(Chunk.NEWLINE);

            // Tabla de datos
            float[] widths = {2.5f, 4.5f, 3f, 2f, 2.5f, 2f, 2.5f, 2.5f};
            PdfPTable tabla = new PdfPTable(widths);
            tabla.setWidthPercentage(95);

            String[] encabezados = {
                    "Tipo", "Nombre", "Sexo", "Carnet",
                    "Últ. Guardia", "Guardias Rec.", "Baja", "Reincorporación"
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
                tabla.addCell(p.getTipo() != null ? p.getTipo().getNombre() : "");
                tabla.addCell(
                        ((p.getNombre() != null ? p.getNombre() : "") +
                                (p.getApellido() != null ? " " + p.getApellido() : "")).trim()
                );
                tabla.addCell(p.getSexo() != null ? String.valueOf(p.getSexo().charAt(0)) : "");
                tabla.addCell(p.getCarnet() != null ? p.getCarnet() : "");
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

    public void generarReportePlantilla(ArrayList<DiaGuardia> plantilla, String nombreArchivo) {
        Document documento = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            // Título
            Paragraph titulo = new Paragraph("Reporte de Plantilla de Guardias",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            // Tabla
            float[] widths = {2f, 2f, 6f, 2f};
            PdfPTable tabla = new PdfPTable(widths);
            tabla.setWidthPercentage(98);

            String[] encabezados = {"Fecha", "Horario", "Personas Asignadas", "Cumplido"};
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tabla.addCell(celda);
            }

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

            for (DiaGuardia dia : plantilla) {
                String fechaStr = dia.getFecha().format(formatoFecha);
                for (TurnoDeGuardia turno : dia.getTurnos()) {
                    Horario horario = turno.getHorario();
                    String horarioStr = horario.getInicio().format(formatoHora) + " - " + horario.getFin().format(formatoHora);

                    // Personas asignadas
                    ArrayList<Persona> personas = turno.getPersonasAsignadas();
                    String personasStr = personas.isEmpty()
                            ? "-"
                            : personas.stream()
                            .map(p -> (p.getNombre() != null ? p.getNombre() : "") +
                                    (p.getApellido() != null ? " " + p.getApellido() : ""))
                            .collect(Collectors.joining(", "));

                    // Cumplido
                    Boolean hecho = turno.getCumplimiento();
                    String cumplidoStr = (hecho != null && hecho) ? "Sí" : "No";

                    // Agregar celdas
                    tabla.addCell(fechaStr);
                    tabla.addCell(horarioStr);
                    tabla.addCell(personasStr);
                    tabla.addCell(cumplidoStr);
                }
            }

            documento.add(tabla);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }
}