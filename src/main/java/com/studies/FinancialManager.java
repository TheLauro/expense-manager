package com.studies;

import java.util.List;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.HashMap;

public class FinancialManager {

    private static final List<Transaction> allTransactions = new ArrayList<>();
    private static final DateTimeFormatter BRAZILIAN_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private FinancialManager() {

    }

    // Expense Methods
    public static void createExpense(String description, double valor, String date) {
        allTransactions.add(new Expense(description, valor, date));
    }

    public static void createExpense(String description, double valor) {
        allTransactions.add(new Expense(description, valor));
    }

    public static void createExpense(double valor) {
        allTransactions.add(new Expense(valor));
    }

    // Income Methods
    public static void createIncome(String description, double valor, String date) {
        allTransactions.add(new Income(description, valor, date));
    }

    public static void createIncome(String description, double valor) {
        allTransactions.add(new Income(description, valor));
    }

    public static void createIncome(double valor) {
        allTransactions.add(new Income(valor));
    }

    // All Transactions Methods
    public static void deleteTransaction(Transaction transaction) {
        allTransactions.remove(transaction);
    }

    public static void updateTransaction(Transaction transaction, String description, double valor, String date) {
        transaction.setDescription(description);
        transaction.setValor(valor);
        transaction.setDate(date);
    }

    public static void updateTransaction(Transaction transaction, String description, double valor) {
        transaction.setDescription(description);
        transaction.setValor(valor);
    }

    public static void updateTransaction(Transaction transaction, double valor, String date) {
        transaction.setValor(valor);
        transaction.setDate(date);
    }

    public static void updateTransaction(Transaction transaction, String description, String date) {
        transaction.setDescription(description);
        transaction.setDate(date);
    }

    public static void updateTransaction(Transaction transaction, String descOrDate) {
        try {
            LocalDate date = LocalDate.parse(descOrDate, BRAZILIAN_FORMAT);
            transaction.date = date;

        } catch (DateTimeParseException e) {
            transaction.description = descOrDate;
        }
    }

    public static void updateTransaction(Transaction transaction, double valor) {
        transaction.setValor(valor);
    }

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

    public static Map<String, Double> getExpensesByCategory(){
        Map<String, Double> expensesByCategory = new HashMap<>();
        
        for(String category : Category.allCategories){
            for(Transaction transaction : allTransactions){
                if(transaction.getClass()==Expense.class){
                    Expense expense = (Expense) transaction;
                    if(expense.getCategory()==category){
                        expensesByCategory.merge(category,expense.getValor(),Double::sum);
                    }
                }
            }
        }

        return expensesByCategory;
    }
}   
