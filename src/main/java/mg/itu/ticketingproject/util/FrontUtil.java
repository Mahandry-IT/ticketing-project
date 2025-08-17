package mg.itu.ticketingproject.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FrontUtil {

    public static String uniformalizedLetter(String texte) {
        if (texte == null || texte.isEmpty()) {
            return texte;
        }
        texte = texte.trim();
        return texte.substring(0, 1).toUpperCase() + texte.substring(1).toLowerCase();
    }

    public static String formaterDate(LocalDate date, String format) {
        if (date == null || format == null || format.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    public static String formaterDateTime(LocalDateTime dateTime, String format) {
        if (dateTime == null || format == null || format.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    public static String formatNumber(double nombre, String format) {
        if (format == null || format.isEmpty()) {
            return Double.toString(nombre);
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRANCE);
        symbols.setGroupingSeparator(' ');

        DecimalFormat decimalFormat = new DecimalFormat(format, symbols);
        return decimalFormat.format(nombre);
    }

}
