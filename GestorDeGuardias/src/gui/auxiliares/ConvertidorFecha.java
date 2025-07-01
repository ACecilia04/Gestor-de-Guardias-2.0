package gui.auxiliares;

import java.time.LocalDate;

public class ConvertidorFecha {

    public static String traducDiaSemana(int fechaSem) {
        String fechaSemana = "NO_ENCONTRADO";
        switch (fechaSem) {
            case 1:
                fechaSemana = "Lunes";
                break;
            case 2:
                fechaSemana = "Martes";
                break;
            case 3:
                fechaSemana = "Miercoles";
                break;
            case 4:
                fechaSemana = "Jueves";
                break;
            case 5:
                fechaSemana = "Viernes";
                break;
            case 6:
                fechaSemana = "Sabado";
                break;
            case 7:
                fechaSemana = "Domingo";
                break;

            default:
        }

        return fechaSemana;
    }

    public static String traducDiaMes(LocalDate fecha) {
        String fechaMes = "NO_ENCONTRADO";
        int fechaSem = fecha.getMonthValue();
        switch (fechaSem) {
            case 1:
                fechaMes = "Enero";
                break;
            case 2:
                fechaMes = "Febrero";
                break;
            case 3:
                fechaMes = "Marzo";
                break;
            case 4:
                fechaMes = "Abril";
                break;
            case 5:
                fechaMes = "Mayo";
                break;
            case 6:
                fechaMes = "Junio";
                break;
            case 7:
                fechaMes = "Julio";
                break;
            case 8:
                fechaMes = "Agosto";
                break;
            case 9:
                fechaMes = "Septiembre";
                break;
            case 10:
                fechaMes = "Octubre";
                break;
            case 11:
                fechaMes = "Noviembre";
                break;
            case 12:
                fechaMes = "Diciembre";
                break;

            default:
        }

        return fechaMes;
    }

    public static int traducDiaSemana(String dia) {
        if (dia == null) return -1;
        return switch (dia.toLowerCase()) {
            case "lunes" -> 1;
            case "martes" -> 2;
            case "miercoles", "miércoles" -> 3;
            case "jueves" -> 4;
            case "viernes" -> 5;
            case "sabado", "sábado" -> 6;
            case "domingo" -> 7;
            default -> -1;
        };
    }
}


