package com.studies.interaction;

import com.studies.logic.*;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Optional;

/**
 *
 * @author lauro.ouverney
 */

public class InterfaceConsole {

    private Scanner sc;
    private FinancialManager manager = new FinancialManager();

    public void runProgram() {

        sc = new Scanner(System.in);

        int option = 0;

        while (option != 9) {

            option = promptForInt("--------- Menu Principal ---------" +
                    "\n" + "Digite a funcao que deseja realizar:" +
                    "\n" + "1-Lançar despesa" +
                    "\n" + "2-Lançar entrada" +
                    "\n" + "3-Editar transacao" +
                    "\n" + "4-Excluir transacao" +
                    "\n" + "5-Exibir todas as transacoes" +
                    "\n" + "6-Exibir transacoes referentes ao mes atual" +
                    "\n" + "7-Exibir despesas por Categoria" +
                    "\n" + "8-Adicionar nova categoria" +
                    "\n" + "9-Encerrar Programa");

            switch (option) {

                case 1:
                    handleCreateExpense();
                    break;

                case 2:
                    handleCreateIncome();
                    break;

                case 3:
                    handleUpdateTransaction();
                    break;

                case 4:
                    handleDeleteTransaction();
                    break;

                case 5:
                    handleShowTransactions();
                    handleGetGeralBalance();
                    break;

                case 6:
                    handleShowMonthTransactions();
                    handleGetMonthBalance();
                    break;

                case 7:
                    handleGetExpensesByCategory();
                    break;

                case 8:
                    handleAddCategory();
                    break;

                case 9:
                    break;

                default:
                    System.out.println("\n" + "Opcao Invalida! Tente novamente.");
            }

            if (option != 9) {
                System.out.println("\n" + "Após a visualizacao, digite qualquer tecla para voltar ao menu principal");
                sc.nextLine();
            } else {
                System.out.println("\n" + "Programa Encerrado!");
            }
            endOfMethod();
        }
        sc.close();
    }

    private void handleCreateExpense() {
        Expense newExpense = null;

        String description = promptForString("\n"
                + "Digite a descricao da despesa: (Se não quiser adicionar uma descrição, apenas de Enter e siga para o proximo item)");

        double value = promptForDouble("\n" + "Digite o valor da despesa:");

        Optional<String> date = promptForChoiceDate("\n"
                + "Deseja adicionar uma data especifica? (Se nao adicionar, a despesa sera atribuida a data de hoje)"
                +
                "\n" + "1-SIM" +
                "\n" + "2-NAO");

        if (date.isPresent()) {

            newExpense = manager.createExpense(description, value, date.get());

        } else {

            newExpense = manager.createExpense(description, value);

        }

        showAllCategories();

        putCategory(newExpense);

        System.out.println("\n" + "Despesa criada com sucesso!");
    }

    private void handleCreateIncome() {

        String description = promptForString("\n"
                + "Digite a descricao da entrada: (Se não quiser adicionar uma descrição, apenas de Enter e siga para o proximo item)");

        double value = promptForDouble("\n" + "Digite o valor da entrada:");

        Optional<String> date = promptForChoiceDate("\n"
                + "Deseja adicionar uma data especifica? (Se nao adicionar, a entrada sera atribuida a data de hoje)"
                +
                "\n" + "1-SIM" +
                "\n" + "2-NAO");

        if (date.isPresent()) {

            manager.createIncome(description, value, date.get());

        } else {

            manager.createIncome(description, value);

        }

        System.out.println("\n" + "Entrada criada com sucesso!");
    }

    private void handleShowTransactions() {
        System.out.println("\n" + "Transacoes registradas ate o momento" + "("
                + manager.getNumberOfTransactions() + "):" + "\n");
        for (Transaction transaction : manager.getAllTransactions()) {

            formattedTransactions(transaction);
        }
    }

    private void handleGetGeralBalance() {
        System.out.println("\n" + "Saldo Geral: " + String.format("R$ %.2f", manager.getGeralBalance()));
    }

    private void endOfMethod() {
        System.out.println("\n-------------------------------------------\n");
    }

    private void handleShowMonthTransactions() {
        System.out.println("\n" + "Transacoes registradas nesse mes" + "("
                + manager.getMonthlyTransactions().size() + "):" + "\n");
        for (Transaction transaction : manager.getMonthlyTransactions()) {

            System.out.println(transaction.getDetails());

            System.out.println("Descricao: " + transaction.getDescription() +
                    "\n" + "Valor: " + String.format("R$ %.2f", transaction.getValue()) +
                    "\n" + "Data: " + transaction.getDate().format(Transaction.BRAZILIAN_FORMAT) +
                    "\n");
        }
    }

    private void handleGetMonthBalance() {
        System.out.println("\n" + "Saldo mensal: " + String.format("R$ %.2f", manager.getMonthBalance()));
    }

    private void handleGetExpensesByCategory() {
        System.out.println("\n" + "Valor gasto em cada categoria: " + "\n");
        for (String category : Category.getCategories()) {
            System.out.println(
                    category + ": " + String.format("R$ %.2f", manager.getExpensesByCategory().get(category)));
        }
    }

    private void handleDeleteTransaction() {
        if (manager.getAllTransactions().size() == 0) {
            System.out.println("\n" + "Nenhuma transacao adicionada!");
            return;
        }
        System.out.println("\n" + "Digite o número equivalente a transacao que deseja excluir:");
        for (int i = 0; i < manager.getAllTransactions().size(); i++) {

            Transaction transaction = manager.getAllTransactions().get(i);

            System.out.println(i + 1 + ":");

            formattedTransactions(transaction);
        }

        int option = promptForInt();

        try {
            manager.deleteTransaction(manager.getAllTransactions().get(option - 1));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\n" + "Transacao inexistente, nao foi possivel realizar a exclusão");
            return;
        }

        System.out.println("\n" + "Transacao excluida com sucesso!");
    }

    private void handleUpdateTransaction() {
        if (manager.getAllTransactions().size() == 0) {
            System.out.println("\n" + "Nenhuma transacao adicionada!");
            return;
        }

        System.out.println("\n" + "Digite o número equivalente a transacao que deseja editar:");
        for (int i = 0; i < manager.getAllTransactions().size(); i++) {

            Transaction transaction = manager.getAllTransactions().get(i);

            System.out.println(i + 1 + ":");

            formattedTransactions(transaction);
        }

        int option = promptForInt();

        int option2 = 0;

        Transaction transaction = manager.getAllTransactions().get(option - 1);

        if (transaction.getClass() == Expense.class) {

            Expense expense = (Expense) transaction;

            while (option2 != 5) {

                option2 = promptForInt("\n" + "O que você quer editar?" +
                        "\n" + "1-Descricao" +
                        "\n" + "2-Valor" +
                        "\n" + "3-Data" +
                        "\n" + "4-Categoria" +
                        "\n" + "5-Sair");

                String description, date, category;
                double value;

                switch (option2) {

                    case 1:
                        description = promptForString("\n" + "Digite a nova descricao:");
                        manager.updateTransaction(expense, description);
                        break;

                    case 2:
                        value = promptForDouble("\n" + "Digite o novo valor:");
                        manager.updateTransaction(expense, value);
                        break;

                    case 3:
                        try {
                            date = promptForString("\n" + "Digite a nova data:");
                            LocalDate dateDate = LocalDate.parse(date, Transaction.BRAZILIAN_FORMAT);
                            manager.updateTransaction(expense, dateDate);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 4:
                        try {
                            category = promptForString("\n" + "Digite a nova categoria:");
                            manager.updateCategory(expense, category);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }

                    case 5:
                        break;

                    default:
                        System.out.println("\n" + "Opcao invalida!");
                        return;

                }

            }
            System.out.println("\n" + "Transacao editada com sucesso!");

        } else {
            while (option2 != 4) {

                option2 = promptForInt("\n" + "O que você quer editar?" +
                        "\n" + "1-Descricao" +
                        "\n" + "2-Valor" +
                        "\n" + "3-Data" +
                        "\n" + "4-Sair");

                String description, date;
                double value;

                switch (option2) {

                    case 1:
                        description = promptForString("\n" + "Digite a nova descricao:");
                        manager.updateTransaction(transaction, description);
                        break;

                    case 2:
                        value = promptForDouble("\n" + "Digite o novo valor:");
                        sc.nextLine();
                        manager.updateTransaction(transaction, value);
                        break;

                    case 3:
                        try {
                            date = promptForString("\n" + "Digite a nova data:");
                            LocalDate dateDate = LocalDate.parse(date, Transaction.BRAZILIAN_FORMAT);
                            manager.updateTransaction(transaction, dateDate);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 4:
                        break;

                    default:
                        System.out.println("\n" + "Opcao invalida!");
                        return;

                }
            }
            System.out.println("\n" + "Transacao editada com sucesso!");
        }
    }

    private void handleAddCategory() {
        String category = promptForString("\n" + "Digite a categoria que deseja adicionar:");
        try {
            Category.addCategories(category);
            System.out.println("\n" + "Categoria adicionado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private double promptForDouble(String prompt) {

        double value;

        while (true) {

            System.out.println(prompt);

            String valueString = sc.nextLine();

            try {
                value = Double.parseDouble(valueString);
                return value;

            } catch (NumberFormatException e) {
                System.out.println("\n" + "Digite um numero valido!");
            }
        }
    }

    private Optional<String> promptForChoiceDate(String prompt) {

        while (true) {

            System.out.println(prompt);

            int option = promptForInt();

            switch (option) {

                case 1:

                    while (true) {
                        System.out.println("\n" + "Digite a data seguindo o formato 'dd/MM/yyyy': ");
                        String date = sc.nextLine();

                        try {
                            LocalDate.parse(date, Transaction.BRAZILIAN_FORMAT);
                            return Optional.of(date);

                        } catch (DateTimeParseException e) {
                            System.out.println("\n" + "Formato de data inválido!");
                        }
                    }

                case 2:
                    return Optional.empty();

                default:
                    System.out.println("Opcao invalida!");
            }
        }

    }

    private String promptForString(String prompt) {

        System.out.println(prompt);
        String text = sc.nextLine();

        return text;
    }

    private int promptForInt() {

        int option;

        while (true) {

            String optionString = sc.nextLine();

            try {
                option = Integer.parseInt(optionString);
                return option;

            } catch (NumberFormatException e) {
                System.out.println("\n" + "Digite um numero valido!");
            }
        }
    }

    private int promptForInt(String prompt) {

        int option;

        while (true) {

            System.out.println(prompt);

            String optionString = sc.nextLine();

            try {
                option = Integer.parseInt(optionString);
                return option;

            } catch (NumberFormatException e) {
                System.out.println("\n" + "Digite um numero valido!");
            }
        }
    }

    private void showAllCategories() {
        System.out.println("\n" + "Defina qual a categoria da despesa:");
        for (int i = 0; i < Category.getCategories().size(); i++) {
            System.out.println(i + 1 + "-" + Category.getCategories().get(i));
        }
        System.out.println(
                "0-Caso sua categoria nao esteja na lista, para criar uma nova categoria para sua despesa");
    }

    private void putCategory(Expense newExpense) {

        int optionCategory = promptForInt();

        while (true) {

            switch (optionCategory) {

                case 0:

                    String category = promptForString("\n"
                            + "Digite o nome da nova categoria: (Ela tambem ficara disponivel para uso em futuras despesas)");

                    try {
                        Category.addCategories(category);
                        newExpense.setCategory(category);
                        System.out.println("\n" + "Categoria adicionada com sucesso!" + "\n");
                        return;

                    } catch (IllegalArgumentException e) {
                        newExpense.setCategory("Sem categoria");
                        System.out.println(e.getMessage());
                        System.out.println(
                                "\n" + "A despesa sera classificada com 'Sem categoria', caso realmente queria adicionar uma nova categoria para essa despesa, utilize a opção no menu");
                        return;
                    }

                default:

                    try {

                        newExpense.setCategory(Category.getCategories().get(optionCategory - 1));
                        return;

                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("\n" + "Digite uma opcao valida!");
                        optionCategory = promptForInt();
                    }

            }
        }

    }

    private void formattedTransactions(Transaction transaction) {

        System.out.println(transaction.getDetails());

        System.out.println("Descricao: " + transaction.getDescription() +
                "\n" + "Valor: " + String.format("R$ %.2f", transaction.getValue()) +
                "\n" + "Data: " + transaction.getDate().format(Transaction.BRAZILIAN_FORMAT) +
                "\n");

    }

}
