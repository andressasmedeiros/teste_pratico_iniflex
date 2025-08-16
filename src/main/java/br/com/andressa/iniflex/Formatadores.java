package br.com.andressa.iniflex;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Formatadores {
    private Formatadores() {}

    public static final Locale PT_BR = new Locale("pt", "BR");
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DecimalFormat DF;
    static {
        var s = new DecimalFormatSymbols(PT_BR);
        s.setDecimalSeparator(',');
        s.setGroupingSeparator('.');
        DF = new DecimalFormat("#,##0.00", s);
    }

    public static String data(LocalDate d) { return d.format(DTF); }
    public static String numero(BigDecimal n) { return DF.format(n); }
}
