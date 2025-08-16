package br.com.andressa.iniflex;

import br.com.andressa.iniflex.enums.Funcao;
import br.com.andressa.iniflex.models.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegrasNegocioTest {

    private static Funcionario buildFuncionario(String nome, String ddMMyyyy, String salario, Funcao funcao) {
        LocalDate nasc = LocalDate.parse(ddMMyyyy, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return new Funcionario(nome, nasc, new BigDecimal(salario), funcao);
    }

    private static List<Funcionario> buildListaFuncionarios() {
        return new ArrayList<>(List.of(
                buildFuncionario("Maria", "18/10/2000", "2009.44", Funcao.OPERADOR),
                buildFuncionario("João", "12/05/1990", "2284.38", Funcao.OPERADOR),
                buildFuncionario("Caio", "02/05/1961", "9836.14", Funcao.COORDENADOR),
                buildFuncionario("Miguel", "14/01/1988", "19119.88", Funcao.DIRETOR),
                buildFuncionario("Alice", "05/01/1995", "2234.68", Funcao.RECEPCIONISTA),
                buildFuncionario("Heitor", "19/11/1999", "1582.72", Funcao.OPERADOR),
                buildFuncionario("Arthur", "31/03/1993", "4071.84", Funcao.CONTADOR),
                buildFuncionario("Laura", "08/07/1994", "3017.45", Funcao.GERENTE),
                buildFuncionario("Heloísa", "24/05/2003", "1606.85", Funcao.ELETRICISTA),
                buildFuncionario("Helena", "02/09/1996", "2799.93", Funcao.GERENTE)
        ));
    }

    @Test
    void removeJoaoEConfereTamanho() {
        List<Funcionario> funcionarios = buildListaFuncionarios();
        funcionarios.removeIf(funcionario -> "João".equalsIgnoreCase(funcionario.getNome()));

        assertEquals(9, funcionarios.size());
        assertTrue(funcionarios.stream().noneMatch(funcionario -> "João".equalsIgnoreCase(funcionario.getNome())));
    }

    @Test
    void totalSalariosSemReajuste() {
        List<Funcionario> funcionarios = buildListaFuncionarios();
        BigDecimal totalSalarios = funcionarios
                .stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(new BigDecimal("48563.31"), totalSalarios);
    }

    @Test
    void funcionarioPossuiAjusteSalarial() {
        List<Funcionario> funcionarios = buildListaFuncionarios();
        funcionarios.forEach(funcionario -> funcionario.aplicarReajustePercentual(new BigDecimal("0.10")));
        Funcionario maria = funcionarios
                .stream()
                .filter(funcionario -> "Maria".equalsIgnoreCase(funcionario.getNome()))
                .findFirst()
                .orElseThrow();

        assertEquals(new BigDecimal("2210.38"), maria.getSalario());
    }
}
