package com.studies;


import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class InterfaceConsole {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        handleCreateExpense(sc);

        endOfMethod();

        handleCreateIncome(sc);

        endOfMethod();

        handleShowTransactions();

        endOfMethod();

        handleGetGeralBalance();

        endOfMethod();
    }

    public static void handleCreateIncome(Scanner sc){
        System.out.println("Digite a descricao da entrada:");
        String description = sc.nextLine();
        System.out.println("Digite o valor da entrada:");
        double valor = sc.nextDouble();
        System.out.println(
                "Deseja adicionar uma data especifica? (Se nao adicionar, a entrada sera atribuida a data de hoje)" +
                        "\n" + "1-SIM" +
                        "\n" + "2-NAO");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {

            case 1:

                while (true) {
                    System.out.println("Digite a data seguindo o padrao 'dd/MM/yyyy':");
                    String data = sc.next();
                    sc.nextLine();

                    try {
                        FinancialManager.createIncome(description, valor, data);
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
        System.out.println("Entrada criada com sucesso!");
    }

    public static void handleCreateExpense(Scanner sc) {
        System.out.println("Digite a descricao da despesa:");
        String description = sc.nextLine();
        System.out.println("Digite o valor da despesa:");
        double valor = sc.nextDouble();
        System.out.println(
                "Deseja adicionar uma data especifica? (Se nao adicionar, a despesa sera atribuida a data de hoje)" +
                        "\n" + "1-SIM" +
                        "\n" + "2-NAO");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {

            case 1:

                while (true) {
                    System.out.println("Digite a data seguindo o padrao 'dd/MM/yyyy':");
                    String data = sc.next();
                    sc.nextLine();

                    try {
                        FinancialManager.createExpense(description, valor, data);
                        break;

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

            case 2:
                FinancialManager.createExpense(description, valor);
                break;

            default:
        }
        System.out.println("Despesa criada com sucesso!");
    }

    public static void handleShowTransactions() {
        System.out.println("\n"+"Transacoes registradas ate o momento"+"("+FinancialManager.getNumberOfTransactions() +"):"+"\n");
        FinancialManager.showTransactions();
    }

    public static void handleGetGeralBalance(){
        System.out.println("Saldo Geral: "+ FinancialManager.getGeralBalance());
    }

    public static void endOfMethod(){
        System.out.println("\n------------------------------------\n");
    }
}
