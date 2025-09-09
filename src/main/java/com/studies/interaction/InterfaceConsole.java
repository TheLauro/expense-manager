package com.studies.interaction;

import com.studies.logic.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

/**
 *
 * @author lauro.ouverney
 */

public class InterfaceConsole {

    private static Scanner sc;

    public static void main(String[] args) {

        initializeTransactionsExamples();

        sc = new Scanner(System.in);

        int option = 0;

        while (option != 9) {
            System.out.println("--------- Menu Principal ---------");

            System.out.println("Digite a funcao que deseja realizar:" +
                    "\n" + "1-Lançar despesa" +
                    "\n" + "2-Lançar entrada" +
                    "\n" + "3-Editar transacao" +
                    "\n" + "4-Excluir transacao" +
                    "\n" + "5-Exibir todas as transacoes" +
                    "\n" + "6-Exibir transacoes referentes ao mes atual" +
                    "\n" + "7-Exibir despesas por Categoria" +
                    "\n" + "8-Adicionar nova categoria" +
                    "\n" + "9-Encerrar Programa");

            try {
                option = sc.nextInt();
                
            } catch (InputMismatchException e) {
                
            }
            sc.nextLine();
            
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

    public static void handleCreateExpense() {
        String description, date;
        double valor = 0;
        Expense newExpense = null;

        System.out.println(
                "\n" + "Digite a descricao da despesa: (Se não quiser adicionar uma descrição, apenas de Enter e siga para o proximo item)");
        description = sc.nextLine();

        while (true) {

            try {
                System.out.println("\n" + "Digite o valor da despesa:");
                valor = sc.nextDouble();
                break;

            } catch (InputMismatchException e) {
                System.out.println("\n" + "Digite um numero valido!");
                sc.nextLine();
            }
        }

        System.out.println(
                "\n" + "Deseja adicionar uma data especifica? (Se nao adicionar, a despesa sera atribuida a data de hoje)"
                        +
                        "\n" + "1-SIM" +
                        "\n" + "2-NAO");
        int option = sc.nextInt();
        sc.nextLine();

        choice: while (true) {

            switch (option) {

                case 1:

                    while (true) {
                        System.out.println("\n" + "Digite a data seguindo o padrao 'dd/MM/yyyy':");
                        date = sc.next();
                        sc.nextLine();

                        try {
                            newExpense = FinancialManager.createExpense(description, valor, date);
                            break;

                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break choice;

                case 2:
                    newExpense = FinancialManager.createExpense(description, valor);
                    break choice;

                default:
                    System.out.println("\n" + "Digite uma opcao valida!");
                    option = sc.nextInt();
                    sc.nextLine();
            }
        }

        System.out.println("\n" + "Defina qual a categoria da despesa:");
        for (int i = 0; i < Category.getCategories().size(); i++) {
            System.out.println(i + 1 + "-" + Category.getCategories().get(i));
        }
        System.out.println(
                "0-Caso sua categoria nao esteja na lista, para criar uma nova categoria para sua despesa");
        int optionCategory = sc.nextInt();
        sc.nextLine();

        choiceCategory: while (true) {

            switch (optionCategory) {

                case 0:
                    System.out.println(
                            "\n" + "Digite o nome da nova categoria: (Ela tambem ficara disponivel para uso em futuras despesas)");
                    String category = sc.nextLine();
                    try {
                        Category.addCategories(category);
                        newExpense.setCategory(category);
                        System.out.println("\n" + "Categoria adicionada com sucesso!" + "\n");

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        System.out.println(
                                "\n" + "A despesa sera criada sem uma categoria, caso realmente queria adicionar uma nova categoria para essa despesa, utiliza a opção no menu");
                    }
                    break choiceCategory;

                default:

                    try {

                        newExpense.setCategory(Category.getCategories().get(optionCategory - 1));
                        break choiceCategory;

                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("\n" + "Digite uma opcao valida!");
                        optionCategory = sc.nextInt();
                    }

            }
        }
        System.out.println("\n" + "Despesa criada com sucesso!");
    }

    public static void handleCreateIncome() {
        String description, date;
        double valor = 0;

        System.out.println(
                "\n" + "Digite a descricao da entrada: (Se não quiser adicionar uma descrição, apenas de Enter e siga para o proximo item)");
        description = sc.nextLine();

        while (true) {

            try {
                System.out.println("\n" + "Digite o valor da entrada:");
                valor = sc.nextDouble();

                break;

            } catch (InputMismatchException e) {
                System.out.println("\n" + "Digite um numero valido!");
                sc.nextLine();
            }
        }

        System.out.println(
                "\n" + "Deseja adicionar uma data especifica? (Se nao adicionar, a entrada sera atribuida a data de hoje)"
                        +
                        "\n" + "1-SIM" +
                        "\n" + "2-NAO");
        int option = sc.nextInt();
        sc.nextLine();

        choice: while (true) {

            switch (option) {

                case 1:

                    while (true) {
                        System.out.println("\n" + "Digite a data seguindo o padrao 'dd/MM/yyyy':");
                        date = sc.next();
                        sc.nextLine();

                        try {
                            FinancialManager.createIncome(description, valor, date);
                            break;

                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break choice;

                case 2:
                    FinancialManager.createIncome(description, valor);
                    break choice;

                default:
                    System.out.println("\n" + "Digite uma opcao valida!");
                    option = sc.nextInt();
                    sc.nextLine();
            }
        }

        System.out.println("\n" + "Entrada criada com sucesso!");
    }

    public static void handleShowTransactions() {
        System.out.println("\n" + "Transacoes registradas ate o momento" + "("
                + FinancialManager.getNumberOfTransactions() + "):" + "\n");
        for (Transaction transaction : FinancialManager.getAllTransactions()) {

            if (transaction.getClass() == Expense.class) {
                Expense expense = (Expense) transaction;
                System.out.println("Tipo: Despesa" +
                        "\n" + "Categoria: " + expense.getCategory());

            } else if (transaction.getClass() == Income.class) {
                System.out.println("Tipo: Entrada");
            }

            System.out.println("Descricao: " + transaction.getDescription() +
                    "\n" + "Valor: " + String.format("R$ %.2f", transaction.getValor()) +
                    "\n" + "Data: " + transaction.getDate().format(Transaction.BRAZILIAN_FORMAT) +
                    "\n");
        }
    }

    public static void handleGetGeralBalance() {
        System.out.println("\n" + "Saldo Geral: " + String.format("R$ %.2f", FinancialManager.getGeralBalance()));
    }

    public static void endOfMethod() {
        System.out.println("\n-------------------------------------------\n");
    }

    public static void handleShowMonthTransactions() {
        System.out.println("\n" + "Transacoes registradas nesse mes" + "("
                + FinancialManager.getMonthlyTransactions().size() + "):" + "\n");
        for (Transaction transaction : FinancialManager.getMonthlyTransactions()) {

            if (transaction.getClass() == Expense.class) {
                Expense expense = (Expense) transaction;
                System.out.println("Tipo : Despesa" +
                        "\n" + "Categoria: " + expense.getCategory());

            } else if (transaction.getClass() == Income.class) {
                System.out.println("Tipo: Entrada");
            }

            System.out.println("Descricao: " + transaction.getDescription() +
                    "\n" + "Valor: " + String.format("R$ %.2f", transaction.getValor()) +
                    "\n" + "Data: " + transaction.getDate().format(Transaction.BRAZILIAN_FORMAT) +
                    "\n");
        }
    }

    public static void handleGetMonthBalance() {
        System.out.println("\n" + "Saldo mensal: " + String.format("R$ %.2f", FinancialManager.getMonthBalance()));
    }

    public static void handleGetExpensesByCategory() {
        System.out.println("\n" + "Valor gasto em cada categoria: " + "\n");
        for (String category : Category.getCategories()) {
            System.out.println(
                    category + ": " + String.format("R$ %.2f", FinancialManager.getExpensesByCategory().get(category)));
        }
    }

    public static void handleDeleteTransaction() {
        if(FinancialManager.getAllTransactions().size()==0){
            System.out.println("\n"+"Nenhuma transacao adicionada!");
            return;
        }
        System.out.println("\n" + "Digite o número equivalente a transacao que deseja excluir:");
        for (int i = 0; i < FinancialManager.getAllTransactions().size(); i++) {

            Transaction transaction = FinancialManager.getAllTransactions().get(i);

            System.out.println(i + 1 + ":");

            if (transaction.getClass() == Expense.class) {
                Expense expense = (Expense) transaction;
                System.out.println("Tipo : Despesa" +
                        "\n" + "Categoria: " + expense.getCategory());

            } else if (transaction.getClass() == Income.class) {
                System.out.println("Tipo: Entrada");
            }

            System.out.println("Descricao: " + transaction.getDescription() +
                    "\n" + "Valor: " + String.format("R$ %.2f", transaction.getValor()) +
                    "\n" + "Data: " + transaction.getDate().format(Transaction.BRAZILIAN_FORMAT) +
                    "\n");
        }
        int option = sc.nextInt();
        sc.nextLine();
        try {
            FinancialManager.deleteTransaction(FinancialManager.getAllTransactions().get(option - 1));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\n" + "Transacao inexistente, nao foi possivel realizar a exclusão");
            return;
        }

        System.out.println("\n" + "Transacao excluida com sucesso!");
    }

    public static void handleUpdateTransaction() {
        if(FinancialManager.getAllTransactions().size()==0){
            System.out.println("\n"+"Nenhuma transacao adicionada!");
            return;
        }
        System.out.println("\n" + "Digite o número equivalente a transacao que deseja editar:");
        for (int i = 0; i < FinancialManager.getAllTransactions().size(); i++) {

            Transaction transaction = FinancialManager.getAllTransactions().get(i);

            System.out.println(i + 1 + ":");

            if (transaction.getClass() == Expense.class) {
                Expense expense = (Expense) transaction;
                System.out.println("Tipo : Despesa" +
                        "\n" + "Categoria: " + expense.getCategory());

            } else if (transaction.getClass() == Income.class) {
                System.out.println("Tipo: Entrada");
            }

            System.out.println("Descricao: " + transaction.getDescription() +
                    "\n" + "Valor: " + String.format("R$ %.2f", transaction.getValor()) +
                    "\n" + "Data: " + transaction.getDate().format(Transaction.BRAZILIAN_FORMAT) +
                    "\n");
        }
        int option = sc.nextInt();

        int option2 = 0;

        Transaction transaction = FinancialManager.getAllTransactions().get(option - 1);

        if (transaction.getClass() == Expense.class) {

            Expense expense = (Expense) transaction;

            while (option2 != 5) {

                System.out.println("\n" + "O que você quer editar?");
                System.out.println("\n" + "1-Descricao" +
                        "\n" + "2-Valor" +
                        "\n" + "3-Data" +
                        "\n" + "4-Categoria" +
                        "\n" + "5-Sair");

                option2 = sc.nextInt();
                sc.nextLine();

                String description, date, category;
                double valor;

                    switch (option2) {

                        case 1:
                            System.out.println("\n" + "Digite a nova descricao:");
                            description = sc.nextLine();
                            FinancialManager.updateTransaction(expense, description);
                            break;

                        case 2:
                            System.out.println("\n" + "Digite o novo valor:");
                            valor = sc.nextDouble();
                            sc.nextLine();
                            FinancialManager.updateTransaction(expense, valor);
                            break;

                        case 3:
                        try{
                            System.out.println("\n" + "Digite a nova data:");
                            date = sc.nextLine();
                            LocalDate dateDate = LocalDate.parse(date,Transaction.BRAZILIAN_FORMAT);
                            FinancialManager.updateTransaction(expense, dateDate);
                        } catch(IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                            break;

                        case 4:
                        try{
                            System.out.println("\n" + "Digite a nova categoria:");
                            category = sc.nextLine();
                            FinancialManager.updateCategory(expense, category);
                            break;
                        } catch(IllegalArgumentException e){
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

                System.out.println("\n" + "O que você quer editar?");
                System.out.println("\n" + "1-Descricao" +
                        "\n" + "2-Valor" +
                        "\n" + "3-Data" +
                        "\n" + "4-Sair");

                option2 = sc.nextInt();
                sc.nextLine();

                String description, date;
                double valor;

                    switch (option2) {

                        case 1:
                            System.out.println("\n" + "Digite a nova descricao:");
                            description = sc.nextLine();
                            FinancialManager.updateTransaction(transaction, description);
                            break;

                        case 2:
                            System.out.println("\n" + "Digite o novo valor:");
                            valor = sc.nextDouble();
                            sc.nextLine();
                            FinancialManager.updateTransaction(transaction, valor);
                            break;

                        case 3:
                            try{
                            System.out.println("\n" + "Digite a nova data:");
                            date = sc.nextLine();
                            LocalDate dateDate = LocalDate.parse(date,Transaction.BRAZILIAN_FORMAT);
                            FinancialManager.updateTransaction(transaction, dateDate);
                        } catch(IllegalArgumentException e){
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

    public static void handleAddCategory() {
        System.out.println("\n" + "Digite a categoria que deseja adicionar:");
        String category = sc.nextLine();
        try {
            Category.addCategories(category);
            System.out.println("\n" + "Categoria adicionado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void initializeTransactionsExamples(){
        Expense expense1 = FinancialManager.createExpense("Casa", 500);
        expense1.setCategory("Aluguel");

        FinancialManager.createIncome("Salario", 1500,"01/09/2025");

        Expense expense2 = FinancialManager.createExpense("Conserto do carro", 1000, "01/07/2025");
        expense2.setCategory("Transporte");
        
        FinancialManager.createIncome("Bico de motoboy", 200,"20/08/2025");

        Expense expense3 = FinancialManager.createExpense("Jantar fora", 20,"12/08/2025");
        expense3.setCategory("Alimentacao");

        FinancialManager.createIncome("Me deram de presente", 300, "30/06/2025");
    }
}
