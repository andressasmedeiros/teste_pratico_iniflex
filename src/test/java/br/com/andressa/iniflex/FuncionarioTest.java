package br.com.andressa.iniflex;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncionarioTest {

    @Test
    void aplicaReajustePercentual() {
        Funcionario f = new Funcionario(
                "Maria",
                LocalDate.of(2000, 10, 18),
                new BigDecimal("1000.00"),
                Funcao.OPERADOR
        );

        f.aplicarReajustePercentual(new BigDecimal("0.10"));

        assertEquals(new BigDecimal("1100.00"), f.getSalario());
    }
}
