# Sistema de Gestão de Funcionários — Iniflex

Projeto em **Java** que implementa regras de negócio para manipulação e exibição de informações de funcionários, incluindo:
- Inserção e listagem formatada
- Remoção por nome
- Aplicação de reajuste salarial
- Agrupamento por função
- Filtragem por mês de aniversário
- Identificação do funcionário mais velho
- Ordenação alfabética
- Cálculo de totais e salários mínimos equivalentes

Este projeto segue os requisitos definidos pela Iniflex, com saída formatada em console no padrão brasileiro (datas e valores).

---

## Funcionalidades

1. **Inserir todos os funcionários** na mesma ordem e com as informações fornecidas.
2. **Remover** o funcionário `"João"` da lista.
3. **Imprimir todos os funcionários** com:
    - Datas no formato `dd/MM/yyyy`
    - Valores monetários com separador de milhar `.` e decimal `,`
4. **Aplicar reajuste salarial de 10%** a todos os funcionários.
5. **Agrupar funcionários por função** usando `Map`.
6. **Imprimir funcionários agrupados** por função.
7. **Listar aniversariantes** dos meses **10 (Outubro)** e **12 (Dezembro)**.
8. **Encontrar o funcionário mais velho** e exibir nome e idade.
9. **Ordenar lista de funcionários por nome** respeitando acentuação (`Collator`).
10. **Imprimir o total dos salários dos funcionários**.
11. **Calcular e imprimir quantos salários mínimos** cada funcionário recebe (base R$ 1.212,00).
12. **Saída formatada em tabela** no console, com separadores.

---

## Tecnologias e Bibliotecas

- **Java 21**
- **Maven** — gerenciamento de dependências e execução de testes
- **JUnit 5 (Jupiter)** — testes unitários
- **Collections API** — manipulação de listas e mapas
- **Streams API** — filtragem, agrupamento e ordenação
- **Enum** — para definição de funções (`Funcao`)
- **EnumMap** — para agrupamento por função com mais performance
- **Collator** — ordenação correta no português
- **BigDecimal** — cálculos monetários com precisão
- **DateTimeFormatter** — formatação de datas
- **RoundingMode.HALF_UP** — arredondamento financeiro

---

## Estrutura do Projeto

```
src
 ├── main
 │    └── java
 │         └── br.com.andressa.iniflex
 │              ├── Principal.java          # Classe principal que executa as operações
 │              ├── Funcionario.java        # Modelo de funcionário com atributos e métodos
 │              ├── Pessoa.java             # Classe base para atributos comuns
 │              ├── Formatadores.java       # Métodos utilitários para formatar datas e números
 │              ├── Funcao.java             # Enum com os cargos disponíveis
 │
 └── test
      └── java
           └── br.com.andressa.iniflex
                ├── FuncionarioTest.java     # Testa reajuste salarial individual
                ├── FormatadoresTest.java   # Testa formatação de datas e números
                ├── RegrasNegocioTest.java  # Testa operações de negócio na lista de funcionários
```

---

## Como Executar

### 1. Clonar o repositório
```bash
git clone https://github.com/andressasmedeiros/teste_pratico_iniflex
cd teste_pratico_iniflex
```

### 2. Compilar o projeto
```bash
mvn compile
```

### 3. Executar a aplicação
```bash
mvn exec:java -Dexec.mainClass="br.com.andressa.iniflex.Principal"
```

---

## Executando os Testes

O projeto possui **3 classes de teste** cobrindo regras de negócio e utilitários.

### Rodar todos os testes:
```bash
mvn test
```

### Estrutura dos testes:
- **`FuncionarioTest`** — garante que o reajuste percentual é aplicado corretamente.
- **`FormatadoresTest`** — valida a formatação de datas e números no padrão brasileiro.
- **`RegrasNegocioTest`** — testa remoção, cálculo de total de salários e aplicação de reajuste em massa.

---

## Decisões Técnicas

### Uso de **Enum** (`Funcao`)
Facilita a manutenção, evita erros de digitação e garante valores fixos para cargos.  
Em conjunto com **EnumMap**, traz:
- **Performance** superior a `HashMap` para chaves `enum`
- Menor uso de memória
- Ordem natural das chaves preservada

### Uso de **BigDecimal**
Escolhido para cálculos financeiros pela precisão e controle de arredondamento.

### Uso de **Streams API**
Proporciona código mais limpo e declarativo para:
- Filtrar (`filter`)
- Ordenar (`sorted`)
- Agrupar (`Collectors.groupingBy`)
- Reduzir (`reduce`)

### Uso de **Collator**
Garante ordenação correta para o português (respeitando acentos e cedilha).

### Saída em **tabela no console**
Para melhor legibilidade, o console mostra:
- Cabeçalho
- Dados alinhados por colunas
- Separadores superiores e inferiores

---
> Projeto realizado para processo seletivo de Desenvolvedor Junior da empresa Projedata
