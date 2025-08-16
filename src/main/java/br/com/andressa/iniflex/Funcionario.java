package br.com.andressa.iniflex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public final class Funcionario extends Pessoa {
    private static final RoundingMode RM = RoundingMode.HALF_UP;

    private BigDecimal salario;
    private final Funcao funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, Funcao funcao) {
        super(nome, dataNascimento);
        this.salario = Objects.requireNonNull(salario).setScale(2, RM);
        this.funcao  = Objects.requireNonNull(funcao);
    }

    public BigDecimal getSalario() { return salario; }
    public Funcao getFuncao() { return funcao; }

    public void setSalario(BigDecimal novo) {
        this.salario = Objects.requireNonNull(novo).setScale(2, RM);
    }

    public void aplicarReajustePercentual(BigDecimal percentual) {
        BigDecimal fator = BigDecimal.ONE.add(Objects.requireNonNull(percentual));
        setSalario(salario.multiply(fator));
    }

    @Override
    public String toString() {
        return getNome() + " | " + Formatadores.data(getDataNascimento()) + " | "
                + Formatadores.numero(getSalario()) + " | " + funcao;
    }
}
