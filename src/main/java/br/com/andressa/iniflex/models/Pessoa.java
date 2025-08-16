package br.com.andressa.iniflex.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public abstract sealed class Pessoa permits Funcionario {
    private final String nome;
    private final LocalDate dataNascimento;

    protected Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = Objects.requireNonNull(nome, "nome não pode ser nulo").trim();
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "dataNascimento não pode ser nula");
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int getIdadeEmAnos(LocalDate referencia) {
        return Period.between(dataNascimento, referencia).getYears();
    }
}
