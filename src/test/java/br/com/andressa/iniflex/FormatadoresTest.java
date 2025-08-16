package br.com.andressa.iniflex;

import br.com.andressa.iniflex.utils.Formatadores;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatadoresTest {

    @Test
    void testaFormatacaoDeData() {
        LocalDate data = LocalDate.of(2023, 8, 15);
        assertEquals("15/08/2023", Formatadores.data(data));
    }

    @Test
    void testaFormatacaoDeNumero() {
        BigDecimal valor = new BigDecimal("1234.56");
        assertEquals("1.234,56", Formatadores.numero(valor));
    }
}
