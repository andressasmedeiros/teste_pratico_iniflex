package br.com.andressa.iniflex;

import br.com.andressa.iniflex.enums.Funcao;
import br.com.andressa.iniflex.models.Funcionario;
import br.com.andressa.iniflex.utils.Formatadores;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public final class Principal {

    private static final BigDecimal SAL_MIN = new BigDecimal("1212.00");
    private static final RoundingMode RM = RoundingMode.HALF_UP;
    private static final Locale LOCALE = Formatadores.PT_BR;
    private static final Collator COLLATOR = Collator.getInstance(LOCALE);
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final int W_NOME = 16;
    private static final int W_DATA = 12;
    private static final int W_SAL = 14;
    private static final int W_FUNC = 14;

    private static final int W_K = 16;
    private static final int W_V = 20;

    private Principal() {
    }

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(cargaInicial());

        removerPorNome(funcionarios, "João");

        imprimirTitulo("Funcionários");
        imprimirTabelaQuatroColunas(funcionarios);

        BigDecimal percentual = new BigDecimal("0.10");
        aplicarReajuste(funcionarios, percentual);

        Map<Funcao, List<Funcionario>> agrupamentoFuncionariosPorFuncao = agruparPorFuncao(funcionarios);

        imprimirTitulo("Agrupados por função");
        imprimirAgrupado(agrupamentoFuncionariosPorFuncao);

        List<Funcionario> aniversariantesPorMeses10E12 = getAniversariantesPorMeses(funcionarios, 10, 12);
        imprimirTitulo("Aniversariantes (Outubro e Dezembro)");
        imprimirTabelaQuatroColunas(aniversariantesPorMeses10E12);

        imprimirTitulo("Mais velho");
        Optional<Funcionario> funcionarioMaisVelhoOptional = getFuncionarioMaisVelho(funcionarios);
        funcionarioMaisVelhoOptional.ifPresent(Principal::imprimirFuncionarioNomeIdade);

        imprimirTitulo("Ordenados por ordem alfabética");
        List<Funcionario> funcionariosPorOrdemAlfabetica = ordenarPorNome(funcionarios);
        imprimirTabelaQuatroColunas(funcionariosPorOrdemAlfabetica);

        BigDecimal salarioTotal = getSalarioTotal(funcionarios);
        String salarioTotalFormatado = Formatadores.numero(salarioTotal);
        imprimirTitulo("Total dos salários");
        imprimirTabelaDuasColunas(salarioTotalFormatado);

        imprimirTitulo("Equivalente em salários mínimos (R$ 1.212,00)");
        imprimirQuantidadeSalariosMinimosPorFuncionario(funcionarios);
    }

    private static List<Funcionario> cargaInicial() {
        return List.of(
                novo("Maria", "18/10/2000", "2009.44", Funcao.OPERADOR),
                novo("João", "12/05/1990", "2284.38", Funcao.OPERADOR),
                novo("Caio", "02/05/1961", "9836.14", Funcao.COORDENADOR),
                novo("Miguel", "14/01/1988", "19119.88", Funcao.DIRETOR),
                novo("Alice", "05/01/1995", "2234.68", Funcao.RECEPCIONISTA),
                novo("Heitor", "19/11/1999", "1582.72", Funcao.OPERADOR),
                novo("Arthur", "31/03/1993", "4071.84", Funcao.CONTADOR),
                novo("Laura", "08/07/1994", "3017.45", Funcao.GERENTE),
                novo("Heloísa", "24/05/2003", "1606.85", Funcao.ELETRICISTA),
                novo("Helena", "02/09/1996", "2799.93", Funcao.GERENTE)
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
        return funcionarios
                .stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getFuncao,
                        () -> new EnumMap<>(Funcao.class),
                        Collectors.toList()
                ));
    }

    private static List<Funcionario> getAniversariantesPorMeses(List<Funcionario> funcionarios, int... meses) {
        Set<Integer> mesesParaFiltrar = Arrays.stream(meses)
                .boxed()
                .collect(Collectors.toSet());

        return funcionarios
                .stream()
                .filter(f -> mesesParaFiltrar.contains(f.getDataNascimento().getMonthValue()))
                .toList();
    }

    private static Optional<Funcionario> getFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios
                .stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento));
    }

    private static List<Funcionario> ordenarPorNome(List<Funcionario> funcionarios) {
        return funcionarios
                .stream()
                .sorted(Comparator.comparing(Funcionario::getNome, COLLATOR))
                .toList();
    }

    private static BigDecimal getSalarioTotal(List<Funcionario> funcionarios) {
        return funcionarios
                .stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RM);
    }

    private static void imprimirTitulo(String t) {
        System.out.println("\n— " + t + " —");
    }

    private static String gerarSeparador() {
        return "+" + "-".repeat(W_NOME + 2) + "+" + "-".repeat(W_DATA + 2) + "+"
                + "-".repeat(W_SAL + 2) + "+" + "-".repeat(W_FUNC + 2) + "+";
    }

    private static void imprimirTabelaQuatroColunas(List<Funcionario> lista) {
        if (lista.isEmpty()) {
            System.out.println("(vazio)");
            return;
        }

        String separador = gerarSeparador();
        StringBuilder sb = new StringBuilder();

        String linhaFormato = "| %-" + W_NOME + "s | %-" + W_DATA + "s | %-" + W_SAL + "s | %-" + W_FUNC + "s |%n";

        sb.append(separador).append(System.lineSeparator());
        sb.append(String.format(linhaFormato, "Nome", "Nascimento", "Salário", "Função"));
        sb.append(separador).append(System.lineSeparator());

        for (Funcionario funcionario : lista) {
            sb.append(String.format(
                    linhaFormato,
                    funcionario.getNome(),
                    Formatadores.data(funcionario.getDataNascimento()),
                    Formatadores.numero(funcionario.getSalario()),
                    funcionario.getFuncao().name()
            ));
        }

        sb.append(separador);

        System.out.print(sb);
    }


    private static String sep2() {
        return "+" + "-".repeat(W_K + 2) + "+" + "-".repeat(W_V + 2) + "+";
    }

    private static void imprimirFuncionarioNomeIdade(Funcionario funcionario) {
        int wNome = W_NOME;
        int wIdade = 8;

        String sep = "+" + "-".repeat(wNome + 2) + "+" + "-".repeat(wIdade + 2) + "+";
        String linhaFormato = "| %-" + wNome + "s | %-" + wIdade + "s |%n";

        StringBuilder sb = new StringBuilder();
        sb.append(sep).append(System.lineSeparator());
        sb.append(String.format(linhaFormato, "Nome", "Idade"));
        sb.append(sep).append(System.lineSeparator());
        sb.append(String.format(linhaFormato,
                funcionario.getNome(),
                funcionario.getIdadeEmAnos(LocalDate.now()) + " anos"));
        sb.append(sep);

        System.out.print(sb);
    }

    private static void imprimirTabelaDuasColunas(String valor) {
        String sep = sep2();
        System.out.println(sep);
        System.out.printf("| %-" + W_K + "s | %-" + W_V + "s |%n", "Descrição", "Valor");
        System.out.println(sep);
        System.out.printf("| %-" + W_K + "s | %-" + W_V + "s |%n", "Total", valor);
        System.out.println(sep);
    }

    private static void imprimirQuantidadeSalariosMinimosPorFuncionario(List<Funcionario> funcionarios) {
        String sep = sep2();
        System.out.println(sep);
        System.out.printf("| %-" + W_K + "s | %-" + W_V + "s |%n", "Nome", "Salários mínimos");
        System.out.println(sep);

        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidadeSalarios = funcionario.getSalario().divide(SAL_MIN, 2, RM);
            String quantidadeSalariosFormatado = Formatadores.numero(quantidadeSalarios);
            System.out.printf("| %-" + W_K + "s | %-" + W_V + "s |%n", funcionario.getNome(), quantidadeSalariosFormatado);
        }

        System.out.println(sep);
    }

    private static void imprimirAgrupado(Map<Funcao, List<Funcionario>> porFuncao) {
        porFuncao.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Enum::name)))
                .forEach(e -> {
                    String funcao = e.getKey().name();
                    List<Funcionario> funcionarios = e.getValue();

                    System.out.println(funcao + ":");
                    imprimirTabelaQuatroColunas(funcionarios);
                    System.out.println();
                });
    }
}
