package utils;

import model.DiaGuardia;
import model.Persona;
import utils.exceptions.EntradaInvalidaException;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
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
     * @param value
     * @return true si el string no es nulo ni vacio
     */
    public static boolean stringEsValido(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean integerEsValido(Integer value) {
        return value != null && value > 0;
    }

    public static boolean stringSoloNumeros(String string) {
        boolean soloNumeros = true;
        for (int i = 0; i < string.length() && soloNumeros; i++)
            if (!Character.isDigit(string.charAt(i)))
                soloNumeros = false;

        return soloNumeros;
    }


}
