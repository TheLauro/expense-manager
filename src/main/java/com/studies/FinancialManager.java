package com.studies;

import java.util.List;
import java.util.ArrayList;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.HashMap;

public class FinancialManager {

    private static final List<Transaction> allTransactions = new ArrayList<>();

    private FinancialManager() {

    }

    // Expense Create Methods
    public static void createExpense(String description, double valor, String date) {
        allTransactions.add(new Expense(description, valor, date));
    }

    public static void createExpense(String description, double valor) {
        allTransactions.add(new Expense(description, valor));
    }

    public static void createExpense(double valor) {
        allTransactions.add(new Expense(valor));
    }

    // Income Create Methods
    public static void createIncome(String description, double valor, String date) {
        allTransactions.add(new Income(description, valor, date));
    }

    public static void createIncome(String description, double valor) {
        allTransactions.add(new Income(description, valor));
    }

    public static void createIncome(double valor) {
        allTransactions.add(new Income(valor));
    }

    // All Transactions Delete Method
    public static void deleteTransaction(Transaction transaction) {
        allTransactions.remove(transaction);
    }

    // Begin of Update Methods for Transactions

    // Update description, valor and date
    public static void updateTransaction(Transaction transaction, String description, double valor, String date) {
        transaction.setDescription(description);
        transaction.setValor(valor);
        transaction.setDate(date);
    }

    // Update description and valor
    public static void updateTransaction(Transaction transaction, String description, double valor) {
        transaction.setDescription(description);
        transaction.setValor(valor);
    }

    // Update valor and date
    public static void updateTransaction(Transaction transaction, double valor, String date) {
        transaction.setValor(valor);
        transaction.setDate(date);
    }

    // Update description and date
    public static void updateTransaction(Transaction transaction, String description, String date) {
        transaction.setDescription(description);
        transaction.setDate(date);
    }

    // Update description or date
    public static void updateTransaction(Transaction transaction, String descOrDate) {
        try {
            LocalDate date = LocalDate.parse(descOrDate, Transaction.BRAZILIAN_FORMAT);
            transaction.date = date;

        } catch (DateTimeParseException e) {
            transaction.description = descOrDate;
        }
    }

    // Update valor
    public static void updateTransaction(Transaction transaction, double valor) {
        transaction.setValor(valor);
    }

    // End of Update Methods for Transaction

    // Calculate Geral Balance of All Transactions
    public static double getGeralBalance() {
        double valorAllIncomes = 0;
        double valorAllExpenses = 0;

        for (Transaction transaction : allTransactions) {
            if (transaction.getClass() == Income.class) {
                valorAllIncomes += transaction.getValor();
            }

            if (transaction.getClass() == Expense.class) {
                valorAllExpenses += transaction.getValor();
            }
        }

        return valorAllIncomes - valorAllExpenses;
    }

    // Calculate Current Month Balance
    public static double getMonthBalance() {
        double valorMonthIncomes = 0;
        double valorMonthExpenses = 0;

        YearMonth currentMonth = YearMonth.now();

        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();

        for (Transaction transaction : allTransactions) {
            if (!transaction.getDate().isBefore(firstDayOfMonth) && !transaction.getDate().isAfter(lastDayOfMonth)) {
                if (transaction.getClass() == Income.class) {
                    valorMonthIncomes += transaction.getValor();
                }

                if (transaction.getClass() == Expense.class) {
                    valorMonthExpenses += transaction.getValor();
                }
            }
        }
        return valorMonthIncomes - valorMonthExpenses;
    }

    // Calculate total expenses per category
    public static Map<String, Double> getExpensesByCategory() {
        Map<String, Double> expensesByCategory = new HashMap<>();

        for (Transaction transaction : allTransactions) {

            if (transaction.getClass() == Expense.class) {
                Expense expense = (Expense) transaction;

                String category = expense.getCategory();
                double valor = expense.getValor();

                expensesByCategory.merge(category, valor, Double::sum);
            }
        }

        return expensesByCategory;
    }

    public static List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public static void showTransactions() {
        for (Transaction transaction : allTransactions) {

            String typeOfTransaction = "";

            if (transaction.getClass() == Expense.class){
                typeOfTransaction = "Despesa";

            } else if(transaction.getClass() == Income.class){
                typeOfTransaction = "Entrada";
            }

            System.out.println("Tipo: " + typeOfTransaction +
                    "\n" + "Descricao: " + transaction.getDescription() +
                    "\n" + "Valor: " + String.format("R$ %.2f", transaction.getValor()) +
                    "\n" + "Data: " + transaction.getDate().format(Transaction.BRAZILIAN_FORMAT) +
                    "\n");
        }
    }

    public static Integer getNumberOfTransactions() {
        return allTransactions.size();
    }
}
