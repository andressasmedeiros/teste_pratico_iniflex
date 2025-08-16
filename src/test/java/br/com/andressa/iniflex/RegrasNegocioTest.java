package br.com.andressa.iniflex;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RegrasNegocioTest {

    private static Funcionario f(String nome, String ddMMyyyy, String salario, Funcao funcao) {
        var nasc = LocalDate.parse(ddMMyyyy, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return new Funcionario(nome, nasc, new BigDecimal(salario), funcao);
    }

    private static List<Funcionario> base() {
        return new ArrayList<>(List.of(
                f("Maria","18/10/2000","2009.44", Funcao.OPERADOR),
                f("João","12/05/1990","2284.38", Funcao.OPERADOR),
                f("Caio","02/05/1961","9836.14", Funcao.COORDENADOR),
                f("Miguel","14/01/1988","19119.88", Funcao.DIRETOR),
                f("Alice","05/01/1995","2234.68", Funcao.RECEPCIONISTA),
                f("Heitor","19/11/1999","1582.72", Funcao.OPERADOR),
                f("Arthur","31/03/1993","4071.84", Funcao.CONTADOR),
                f("Laura","08/07/1994","3017.45", Funcao.GERENTE),
                f("Heloísa","24/05/2003","1606.85", Funcao.ELETRICISTA),
                f("Helena","02/09/1996","2799.93", Funcao.GERENTE)
        ));
    }

    @Test
    void removeJoaoEConfereTamanho() {
        var lista = base();
        lista.removeIf(p -> p.getNome().equalsIgnoreCase("João"));
        assertEquals(9, lista.size());
        assertTrue(lista.stream().noneMatch(p -> p.getNome().equalsIgnoreCase("João")));
    }

    @Test
    void totalSalariosSemReajuste() {
        var lista = base();
        var total = lista.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        assertEquals(new BigDecimal("48563.31"), total);
    }

    @Test
    void reajuste10pctEmTodos_confereMaria() {
        var lista = base();
        lista.forEach(p -> p.aplicarReajustePercentual(new BigDecimal("0.10")));
        var maria = lista.stream().filter(p -> p.getNome().equals("Maria")).findFirst().orElseThrow();
        assertEquals(new BigDecimal("2210.38"), maria.getSalario());
    }
}
