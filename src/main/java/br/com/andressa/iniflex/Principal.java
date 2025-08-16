package br.com.andressa.iniflex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.andressa.iniflex.Funcao.*;

public final class Principal {

    private static final BigDecimal SAL_MIN = new BigDecimal("1212.00");
    private static final RoundingMode RM = RoundingMode.HALF_UP;
    private static final Locale LOCALE = Formatadores.PT_BR;
    private static final Collator COLLATOR = Collator.getInstance(LOCALE);
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final int W_NOME = 16;
    private static final int W_DATA = 12;
    private static final int W_SAL  = 14;
    private static final int W_FUNC = 14;

    private static final int W_K = 16;
    private static final int W_V = 20;

    private Principal() { }

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(cargaInicial());

        removerPorNome(funcionarios, "João");

        titulo("Funcionários");
        imprimirTabela4(funcionarios);

        aplicarReajuste(funcionarios, new BigDecimal("0.10"));

        Map<Funcao, List<Funcionario>> porFuncao = agruparPorFuncao(funcionarios);

        titulo("Agrupados por função");
        imprimirAgrupado(porFuncao);

        titulo("Aniversariantes (Outubro e Dezembro)");
        imprimirTabela4(aniversariantes(funcionarios, 10, 12));

        titulo("Mais velho");
        maisVelho(funcionarios).ifPresent(f -> {
            imprimirTabela4(List.of(f));
            imprimirTabela2("Idade", f.getIdadeEmAnos(LocalDate.now()) + " anos");
        });

        titulo("Ordenados por nome");
        imprimirTabela4(ordenarPorNome(funcionarios));

        titulo("Total dos salários");
        imprimirTabela2("Total", Formatadores.numero(totalSalarios(funcionarios)));

        titulo("Equivalente em salários mínimos (R$ 1.212,00)");
        imprimirMinimos(funcionarios);
    }

    private static List<Funcionario> cargaInicial() {
        return List.of(
                novo("Maria",  "18/10/2000", "2009.44", OPERADOR),
                novo("João",   "12/05/1990", "2284.38", OPERADOR),
                novo("Caio",   "02/05/1961", "9836.14", COORDENADOR),
                novo("Miguel", "14/01/1988", "19119.88", DIRETOR),
                novo("Alice",  "05/01/1995", "2234.68", RECEPCIONISTA),
                novo("Heitor", "19/11/1999", "1582.72", OPERADOR),
                novo("Arthur", "31/03/1993", "4071.84", CONTADOR),
                novo("Laura",  "08/07/1994", "3017.45", GERENTE),
                novo("Heloísa","24/05/2003", "1606.85", ELETRICISTA),
                novo("Helena", "02/09/1996", "2799.93", GERENTE)
        );
    }

    private static Funcionario novo(String nome, String ddMMyyyy, String salario, Funcao funcao) {
        LocalDate nasc = LocalDate.parse(ddMMyyyy, DTF);
        return new Funcionario(nome, nasc, new BigDecimal(salario), funcao);
    }

    private static void removerPorNome(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    private static void aplicarReajuste(List<Funcionario> funcionarios, BigDecimal percentual) {
        funcionarios.forEach(f -> f.aplicarReajustePercentual(percentual));
    }

    private static Map<Funcao, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(
                Funcionario::getFuncao,
                () -> new EnumMap<>(Funcao.class),
                Collectors.toList()
        ));
    }

    private static List<Funcionario> aniversariantes(List<Funcionario> funcionarios, int... meses) {
        Set<Integer> set = Arrays.stream(meses).boxed().collect(Collectors.toSet());
        return funcionarios.stream()
                .filter(f -> set.contains(f.getDataNascimento().getMonthValue()))
                .toList();
    }

    private static Optional<Funcionario> maisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream().min(Comparator.comparing(Funcionario::getDataNascimento));
    }

    private static List<Funcionario> ordenarPorNome(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome, COLLATOR))
                .toList();
    }

    private static BigDecimal totalSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RM);
    }

    private static void titulo(String t) {
        System.out.println("\n— " + t + " —");
    }

    private static String sep4() {
        return "+" + "-".repeat(W_NOME+2) + "+" + "-".repeat(W_DATA+2) + "+"
                + "-".repeat(W_SAL+2) + "+" + "-".repeat(W_FUNC+2) + "+";
    }

    private static void imprimirTabela4(List<Funcionario> lista) {
        if (lista.isEmpty()) {
            System.out.println("(vazio)");
            return;
        }
        String sep = sep4();
        System.out.println(sep);
        System.out.printf("| %-" + W_NOME + "s | %-" + W_DATA + "s | %-" + W_SAL + "s | %-" + W_FUNC + "s |%n",
                "Nome", "Nascimento", "Salário", "Função");
        System.out.println(sep);
        for (Funcionario f : lista) {
            System.out.printf("| %-" + W_NOME + "s | %-" + W_DATA + "s | %-" + W_SAL + "s | %-" + W_FUNC + "s |%n",
                    f.getNome(),
                    Formatadores.data(f.getDataNascimento()),
                    Formatadores.numero(f.getSalario()),
                    f.getFuncao().name());
        }
        System.out.println(sep);
    }

    private static String sep2() {
        return "+" + "-".repeat(W_K+2) + "+" + "-".repeat(W_V+2) + "+";
    }

    private static void imprimirTabela2(String chave, String valor) {
        String sep = sep2();
        System.out.println(sep);
        System.out.printf("| %-" + W_K + "s | %-" + W_V + "s |%n", "Descrição", "Valor");
        System.out.println(sep);
        System.out.printf("| %-" + W_K + "s | %-" + W_V + "s |%n", chave, valor);
        System.out.println(sep);
    }

    private static void imprimirMinimos(List<Funcionario> funcionarios) {
        String sep = sep2();
        System.out.println(sep);
        System.out.printf("| %-" + W_K + "s | %-" + W_V + "s |%n", "Nome", "Salários mínimos");
        System.out.println(sep);
        for (Funcionario f : funcionarios) {
            BigDecimal qtd = f.getSalario().divide(SAL_MIN, 2, RM);
            System.out.printf("| %-" + W_K + "s | %-" + W_V + "s |%n",
                    f.getNome(), Formatadores.numero(qtd));
        }
        System.out.println(sep);
    }

    private static void imprimirAgrupado(Map<Funcao, List<Funcionario>> porFuncao) {
        porFuncao.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Enum::name)))
                .forEach(e -> {
                    System.out.println(e.getKey().name() + ":");
                    imprimirTabela4(e.getValue());
                    System.out.println();
                });
    }
}
