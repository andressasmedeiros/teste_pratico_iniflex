package br.com.andressa.iniflex.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Formatadores {
    private Formatadores() {
    }

    public static final Locale PT_BR = Locale.of("pt", "BR");
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DecimalFormat DF;

    static {
        var decimalFormatSymbols = new DecimalFormatSymbols(PT_BR);
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        DF = new DecimalFormat("#,##0.00", decimalFormatSymbols);
    }

    public static String data(LocalDate date) {
        return date.format(DTF);
    }

    public static String numero(BigDecimal decimal) {
        return DF.format(decimal);
    }
}
