package br.com.andressa.iniflex;

import br.com.andressa.iniflex.enums.Funcao;
import br.com.andressa.iniflex.models.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FuncionarioTest {

    @Test
    void aplicaReajustePercentual() {
        Funcionario funcionario = new Funcionario(
                "Maria",
                LocalDate.of(2000, 10, 18),
                new BigDecimal("1000.00"),
                Funcao.OPERADOR
        );

        funcionario.aplicarReajustePercentual(new BigDecimal("0.10"));

        assertEquals(new BigDecimal("1100.00"), funcionario.getSalario());
    }
}
