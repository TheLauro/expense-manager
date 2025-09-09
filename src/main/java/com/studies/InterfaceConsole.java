package com.studies;

import java.util.Scanner;


/**
 * Hello world!
 *
 */
public class InterfaceConsole {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        handleCreateIncome();

        handleAddCategory();

        endOfMethod();

        handleCreateExpense();

        handleCreateExpense();

        handleGetExpensesByCategory();

        endOfMethod();

        handleShowTransactions();

        endOfMethod();

        handleGetGeralBalance();

        endOfMethod();

        handleUpdateTransaction();

        endOfMethod();

        handleShowTransactions();

        handleGetGeralBalance();

    }

    public static void handleCreateExpense() {
        String description, date;
        double valor = 0;
        Expense newExpense = null;

        System.out.println(
                "Digite a descricao da despesa: (Se não quiser adicionar uma descrição, apenas de Enter e siga para o proximo item)");
        description = sc.nextLine();
        while (true) {

            try {
                System.out.println("Digite o valor da despesa:");
                valor = sc.nextDouble();
                break;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(
                "Deseja adicionar uma data especifica? (Se nao adicionar, a despesa sera atribuida a data de hoje)"
                        +
                        "\n" + "1-SIM" +
                        "\n" + "2-NAO");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {

            case 1:

                while (true) {
                    System.out.println("Digite a data seguindo o padrao 'dd/MM/yyyy':");
                    date = sc.next();
                    sc.nextLine();

                    try {
                        newExpense = FinancialManager.createExpense(description, valor, date);
                        break;

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

            case 2:
                newExpense = FinancialManager.createExpense(description, valor);
                break;

            default:
        }

        System.out.println("Defina qual a categoria da despesa:");
        for (int i = 0; i < Category.getCategories().size(); i++) {
            System.out.println(i + 1 + "-" + Category.getCategories().get(i));
        }
        System.out.println(
                "0-Caso sua categoria nao esteja na lista, para criar uma nova categoria para sua despesa");
        int optionCategory = sc.nextInt();
        sc.nextLine();

        switch (optionCategory) {

            case 0:
                System.out.println(
                        "Digite o nome da nova categoria: (Ela tambem ficara disponivel para uso em futuras despesas)");
                String category = sc.nextLine();
                try {
                    Category.addCategories(category);
                    newExpense.setCategory(category);
                    System.out.println("\n" + "Categoria adicionada com sucesso!" + "\n");

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.out.println(
                            "A despesa sera criada sem uma categoria, caso realmente queria adicionar uma nova categoria para essa despesa, utiliza a opção no menu");
                }
                break;

            default:
                newExpense.setCategory(Category.getCategories().get(optionCategory - 1));

        }
        System.out.println("\n" + "Despesa criada com sucesso!");
    }

    public static void handleCreateIncome() {
        String description, date;
        double valor = 0;

        System.out.println(
                "Digite a descricao da entrada: (Se não quiser adicionar uma descrição, apenas de Enter e siga para o proximo item)");
        description = sc.nextLine();
        while (true) {

            try {
                System.out.println("Digite o valor da entrada:");
                valor = sc.nextDouble();
                break;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(
                "Deseja adicionar uma data especifica? (Se nao adicionar, a entrada sera atribuida a data de hoje)"
                        +
                        "\n" + "1-SIM" +
                        "\n" + "2-NAO");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {

            case 1:

                while (true) {
                    System.out.println("Digite a data seguindo o padrao 'dd/MM/yyyy':");
                    date = sc.next();
                    sc.nextLine();

                    try {
                        FinancialManager.createIncome(description, valor, date);
                        break;

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

            case 2:
                FinancialManager.createIncome(description, valor);
                break;

            default:
        }

        System.out.println("\n" + "Entrada criada com sucesso!");
    }

    public static void handleShowTransactions() {
        System.out.println("\n" + "Transacoes registradas ate o momento" + "("
                + FinancialManager.getNumberOfTransactions() + "):" + "\n");
        for (Transaction transaction : FinancialManager.getAllTransactions()) {

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

    public static void handleGetGeralBalance() {
        System.out.println("Saldo Geral: " + String.format("R$ %.2f", FinancialManager.getGeralBalance()));
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
        System.out.println("Saldo mensal: " + String.format("R$ %.2f", FinancialManager.getMonthBalance()));
    }

    public static void handleGetExpensesByCategory() {
        System.out.println("Valor gasto em cada categoria:");
        for (String category : Category.getCategories()) {
            System.out.println(
                    category + ": " + String.format("R$ %.2f", FinancialManager.getExpensesByCategory().get(category)));
        }
    }

    public static void handleDeleteTransaction() {
        System.out.println("Digite o número equivalente a transacao que deseja excluir:");
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

        System.out.println("Transacao excluida com sucesso!");
    }

    public static void handleUpdateTransaction() {
        System.out.println("Digite o número equivalente a transacao que deseja editar:");
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
        
        Transaction transaction = FinancialManager.getAllTransactions().get(option-1);

        if(transaction.getClass()==Expense.class){

        Expense expense = (Expense) transaction;
        
        while(option2!=5){

        System.out.println("O que você quer editar?");
        System.out.println("1-Descricao"+ 
        "\n" + "2-Valor" + 
        "\n" + "3-Data"+
        "\n"+"4-Categoria"+
        "\n"+"5-Sair");

        option2 = sc.nextInt();
        sc.nextLine();

        String description, date, category;
        double valor;

        try{

        switch (option2){

            case 1:
            System.out.println("Digite a nova descricao:");
            description = sc.nextLine();
            FinancialManager.updateTransaction(expense,description);
            break;

            case 2:
            System.out.println("Digite o novo valor:");
            valor = sc.nextDouble();
            sc.nextLine();
            FinancialManager.updateTransaction(expense,valor);
            break;

            case 3:
            System.out.println("Digite a nova data:");
            date = sc.nextLine();
            FinancialManager.updateTransaction(expense,date);
            break;

            case 4:
            System.out.println("Digite a nova categoria:");
            category = sc.nextLine();
            FinancialManager.updateCategory(expense,category);
            break;

            default:
            System.out.println("Opcao invalida!");
            return;

        }

    } catch(IllegalArgumentException e){
        System.out.println(e.getMessage());
        return;
    }
}
        System.out.println("Transacao editada com sucesso!");

    } else {
        while(option2!=4){

        System.out.println("O que você quer editar?");
        System.out.println("1-Descricao"+ 
        "\n" + "2-Valor" + 
        "\n" + "3-Data"+
        "\n"+"4-Sair");

        option2 = sc.nextInt();
        sc.nextLine();

        String description, date;
        double valor;

        try{

        switch (option2){

            case 1:
            System.out.println("Digite a nova descricao:");
            description = sc.nextLine();
            FinancialManager.updateTransaction(transaction,description);
            break;

            case 2:
            System.out.println("Digite o novo valor:");
            valor = sc.nextDouble();
            sc.nextLine();
            FinancialManager.updateTransaction(transaction,valor);
            break;

            case 3:
            System.out.println("Digite a nova data:");
            date = sc.nextLine();
            FinancialManager.updateTransaction(transaction,date);
            break;

            default:
            System.out.println("Opcao invalida!");
            return;

        }

    } catch(IllegalArgumentException e){
        System.out.println(e.getMessage());
        return;
    }
}
        System.out.println("Transacao editada com sucesso!");
    }
}

    public static void handleAddCategory(){
        System.out.println("Digite a categoria que deseja adicionar:");
        String category = sc.nextLine();
        try {
            Category.addCategories(category);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
