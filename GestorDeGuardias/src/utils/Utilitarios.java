package utils;

import model.Persona;
import utils.exceptions.EntradaInvalidaException;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class Utilitarios {
    public static final String SIMPLE_DATE_FORMAT = "dd-MM-yyyy";
    public static final String SIMPLE_DATE_FORMAT_2 = "MMM-dd";
    public static final String SIMPLE_DATE_FORMAT_3 = "dd-MMM-yyyy";
    public static final String SIMPLE_DATE_FORMAT_5 = "dd-MMMMM-yyyy";
    public static final String SIMPLE_DATE_FORMAT_6 = "dd.MMM.yy";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String SIMPLE_TIME_FORMAT = "HH:mm";
    public static final String NICE_DATE_FORMAT = "EEEE, dd MMMM yyyy";
    public static final String NICE_DATE_FORMAT_2 = "EEE MMM-dd";
    public static final String NICE_DATE_FORMAT_3 = "EEE. dd-MMM-yyyy";
    public static final String NICE_DATE_FORMAT_4 = "MMMM dd, yyyy";
    public static final String SIMPLE_NICE_DATE_FORMAT = "MMM dd, yyyy";
    public static final String NICE_DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm";
    public static final String NICE_DATE_TIME_FORMAT_2 = "dd-MMM-yyyy HH:mm:ss";

    /**
     * @param string
     * @return true si el string no es nulo ni vacio
     */
    public static boolean stringEsValido(String string) {
        return string != null && !string.trim().isEmpty();
    }

    public static boolean stringSoloNumeros(String string) {
        boolean soloNumeros = true;
        for (int i = 0; i < string.length() && soloNumeros; i++)
            if (!Character.isDigit(string.charAt(i)))
                soloNumeros = false;

        return soloNumeros;
    }

    public static boolean fechaEnSemanaPar(LocalDate fecha) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); //para sacar el numero de semana en el a�o
        int numeroDeSemana = fecha.get(weekFields.weekOfWeekBasedYear());

        return numeroDeSemana % 2 == 0;
    }

    private static int division(ArrayList<Persona> personas, int min, int max, LocalDate fecha) throws EntradaInvalidaException {
        int pivot = personas.get(min).getDiasDesdeUltimaGuardiaHecha(fecha);
        int i = min - 1;
        int j = max + 1;


        while (i < j) {
            //encontrar el mas a la izquierda  menor o igual al pivot
            do {
                i++;
            } while (personas.get(i).getDiasDesdeUltimaGuardiaHecha(fecha) > pivot);

            // encontrar el mas a la izquierda mayor o igual al pivot
            do {
                j--;
            } while (personas.get(j).getDiasDesdeUltimaGuardiaHecha(fecha) < pivot);

            swap(personas.get(i), personas.get(j));
        }
        return j;
    }


    private static void swap(Persona persona1, Persona persona2) {
        Persona temp = persona1;
        persona1 = persona2;
        persona2 = temp;
    }

    public static void quickSort(ArrayList<Persona> personas, int min, int max, LocalDate fecha) throws EntradaInvalidaException {
        if (min < max) {
            // di = indice de division
            int di = division(personas, min, max, fecha);

            quickSort(personas, min, di, fecha);
            quickSort(personas, di + 1, max, fecha);
        }
    }

//    public static int binaryDateSearch(ArrayList<DiaGuardia> list, LocalDate key, int min, int max) {
//        int retVal;
//
//        if ((max < min) || (key == null) || (list == null) || (list.isEmpty()))
//            retVal = -1;
//        else {
//            int middle = min + (max - min) / 2;
//            if (key.equals(list.get(middle).getFecha())) {
//                retVal = middle;
//            } else if (key.isBefore(list.get(middle).getFecha())) {
//                retVal = binaryDateSearch(list, key, min, middle - 1);
//            } else {
//                retVal = binaryDateSearch(list, key, middle + 1, max);
//            }
//        }
//        return retVal;
//    }

}
